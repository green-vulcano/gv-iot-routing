/*
 * Copyright (c) 2016, GreenVulcano Project. All rights reserved.
 *
 * This file is part of the GreenVulcano Open Source Integration Platform.
 * This is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This software is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package it.greenvulcano.iot.routing.v1;

import it.greenvulcano.iot.routing.NetworkId;
import it.greenvulcano.iot.routing.Packet;
import it.greenvulcano.iot.routing.PacketCodec;
import it.greenvulcano.iot.routing.VersionException;
import it.greenvulcano.iot.routing.payload.BinaryChunk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Implementation of @{link PacketCodec} for the GreenVulcano IoT Protocol v1
 * @author Domenico Barra - eisenach@gmail.com
 */
public class PacketCodecImpl implements PacketCodec {

    public static final byte MAGIC_BYTE  = 0x1f;
    public static final int  VARIABLE_PART_OFFSET = 3;
    private static final Version VERSION = Version.getInstance();

    private Charset charset;

    public PacketCodecImpl() {
        this(Charset.defaultCharset());
    }

    public PacketCodecImpl(Charset charset) {
        this.charset = charset;
    }

    @Override
    public Packet decode(byte[] p, int offset, int length)
            throws IOException, VersionException {
        byte magic = p[offset++];
        if (magic != MAGIC_BYTE) {
            return null; // discard packet, it's not ours
        }
        int major = p[offset++];
        int minor = p[offset++];
        if (major != VERSION.getMajor() || minor != VERSION.getMinor()) {
            throw new VersionException(VERSION, major, minor);
        }
        int destLength = p[offset++];
        String destination = null;
        if (destLength > 0) {
            destination = new String(p, offset, destLength, charset);
            offset += destLength;
        }
        byte[] payload = new byte[length - offset];
        System.arraycopy(p, offset, payload, 0, payload.length);
        return new Packet(payload, new NetworkId(destination));
    }

    @Override
    public byte[] encode(Packet p) throws IOException {
        int size = p.getPayload().length + VARIABLE_PART_OFFSET;
        byte[] destination = null;
        if (p.getDestination() != null) {
            destination = p.getDestination().toString().getBytes(charset);
            size += destination.length;
        } else {
            size += 1;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream(size);
        os.write(MAGIC_BYTE);
        os.write(VERSION.getMajor());
        os.write(VERSION.getMinor());
        if (destination != null) {
            os.write(destination.length);
            os.write(destination);
        } else {
            os.write(0); // goes up to central
        }
        os.write(p.getPayload());
        return os.toByteArray();
    }

    @Override
    public Packet createPacket(List<? extends BinaryChunk> chunks, NetworkId destination) throws IOException {
        int totalPayloadSize = chunks.stream().mapToInt(c -> c.getLength()).sum() + chunks.size() + 1;
        ByteArrayOutputStream os = new ByteArrayOutputStream(totalPayloadSize);
        os.write(chunks.size());
        chunks.forEach(c -> {
            os.write(c.getLength());
            os.write(c.getPayload(), c.getOffset(), c.getLength());});
        return new Packet(os.toByteArray(), destination);
    }

    @Override
    public List<BinaryChunk> readChunks(Packet p) throws IOException {
        byte[] payload = p.getPayload();
        int numChunks = payload[0];
        ArrayList<BinaryChunk> list = new ArrayList<>(numChunks);
        int curIdx = 1;
        for (int i=0; i < numChunks; i++) {
            int chunkLength = payload[curIdx++];
            list.add(new BinaryChunk(payload, curIdx, chunkLength));
            curIdx += chunkLength;
        }
        return list;
        // TODO: add basic payload sanity checks
    }
}
