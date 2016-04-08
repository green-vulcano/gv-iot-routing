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

import static org.junit.Assert.*;

/**
 * @author Domenico Barra - eisenach@gmail.com
 */
public class NetworkIdTest {

    @org.junit.Test
    public void testPush() throws Exception {
        NetworkId nid = new NetworkId("s1:s2:s3:thing");
        nid.push("s0");
        assertEquals(new NetworkId("s0:s1:s2:s3:thing"), nid);
    }

    @org.junit.Test
    public void testGetThingId() throws Exception {
        NetworkId nid = new NetworkId("s1:s2:s3:thing");
        assertEquals("thing", nid.getThingId());
    }

    @org.junit.Test
    public void testPop() throws Exception {
        NetworkId nid = new NetworkId("s1:s2:s3:thing");
        assertEquals("s1", nid.pop());
        assertEquals(new NetworkId("s2:s3:thing"), nid);
    }

    @org.junit.Test
    public void testToString() throws Exception {
        NetworkId nid = new NetworkId("s1:s2:s3:thing");
        assertEquals("s1:s2:s3:thing", nid.toString());
    }
}