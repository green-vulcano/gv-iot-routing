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
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Domenico Barra - eisenach@gmail.com
 */
public class ProtocolTest {

    NetworkId destId;
    int destLength;
    byte[] payload;
    Packet packet;
    byte[] serialized;
    Version version;
    Protocol protocol;

    @Before
    public void setUpClass() throws Exception {
        protocol = new Protocol();
        destId = new NetworkId("s1:s2:s3:thing");
        destLength = destId.toString().length();
        payload = "This is a simple payload!".getBytes();
        packet = new Packet(payload, destId);
        serialized = protocol.serialize(packet);
        version = Version.getInstance();
    }

    @Test
    public void testSerialize() throws Exception {
        assertEquals(version.getMajor(), serialized[1]);
        assertEquals(version.getMinor(), serialized[2]);
        assertEquals(destLength, serialized[3]);
        assertEquals('s', serialized[4]);
        assertEquals('g', serialized[17]);
        assertEquals('!', serialized[serialized.length-1]);
    }

    @Test
    public void testDeserialize() throws Exception {
        Packet newPacket = protocol.deserialize(serialized, 0, serialized.length);
        assertEquals(packet, newPacket);
    }
}