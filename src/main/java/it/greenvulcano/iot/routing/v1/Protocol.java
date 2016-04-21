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
import it.greenvulcano.iot.routing.VersionException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Domenico Barra - eisenach@gmail.com
 */
public class Protocol implements it.greenvulcano.iot.routing.Protocol {

    public static final byte MAGICBYTE = 0x1f;

    private static final Version VERSION = Version.getInstance();

    @Override
    public byte[] serialize(Packet p) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.write(MAGICBYTE);
        os.write(VERSION.getMajor());
        os.write(VERSION.getMinor());
        if (p.getDestination() != null) {
            byte[] destination = p.getDestination().toString().getBytes();
            os.write(destination.length);
            os.write(destination);
        } else {
            os.write(0); // goes up to central
        }
        os.write(p.getPayload());
        return os.toByteArray();
    }

    @Override
    public Packet deserialize(byte[] p, int offset, int length)
            throws IOException, VersionException {
        byte magic = p[offset++];
        if (magic != MAGICBYTE) {
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
            destination = new String(p, offset, destLength);
            offset += destLength;
        }
        byte[] payload = new byte[length - offset];
        System.arraycopy(p, offset, payload, 0, payload.length);
        return new Packet(payload, new NetworkId(destination));
    }
}
