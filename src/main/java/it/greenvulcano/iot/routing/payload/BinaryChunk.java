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

package it.greenvulcano.iot.routing.payload;

import it.greenvulcano.iot.routing.Packet;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Binary chunk (i.e. just a portion of a packet, no decoding at all).
 */
public class BinaryChunk {
    byte[] payload;
    int    offset;
    int    length;

    /**
     * Constructs a binary chunk with a specific, dedicated payload
     * @param payload the payload to use for this chunk
     */
    public BinaryChunk(byte[] payload) {
        this(payload, 0, payload.length);
    }

    /**
     * Constructs a new binary chunk from a wider payload
     * @param offset the offset within the packet's payload
     * @param length the length of this chunk
     */
    public BinaryChunk(byte[] payload, int offset, int length) {
        this.payload = payload;
        this.offset = offset;
        this.length = length;
    }

    /**
     * @return a new string built from this chunk using the platform's
     *         default character set
     */
    public String asNewString() {
        return new String(payload, offset, length);
    }

    /**
     * @param charset the charset to use when decoding the byte payload
     * @return a new string built from this chunk using the provided
     *         character set
     */
    public String asNewString(Charset charset) {
        return new String(payload, offset, length, charset);
    }

    /**
     * @return a new byte array holding the content of this chunk
     */
    public byte[] asNewByteArray() {
        byte[] newArray = new byte[length];
        System.arraycopy(payload, offset, newArray, 0, length);
        return newArray;
    }

    /**
     * Writes the content of this chunk to an output stream
     * @param os the stream to write to
     * @throws IOException if any I/O error occurs while writing
     */
    public void writeTo(OutputStream os) throws IOException {
        os.write(payload, offset, length);
    }

    public byte[] getPayload() {
        return payload;
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }
}
