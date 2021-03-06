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

package it.greenvulcano.iot.routing;

import it.greenvulcano.iot.routing.payload.BinaryChunk;

import java.io.IOException;
import java.util.List;

/**
 * @author Domenico Barra - eisenach@gmail.com
 */
public interface PacketCodec {

    Packet createPacket(List<? extends BinaryChunk> chunks, NetworkId destination) throws IOException;

    List<BinaryChunk> readChunks(Packet p) throws IOException;

    byte[] encode(Packet p) throws IOException;

    Packet decode(byte[] p, int offset, int length) throws IOException, VersionException;



}
