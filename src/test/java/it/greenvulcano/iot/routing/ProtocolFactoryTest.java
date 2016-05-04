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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Domenico Barra - eisenach@gmail.com
 */
public class ProtocolFactoryTest {

    @Test
    public void testCreateProtocol() throws Exception {

        Protocol p = ProtocolFactory.createProtocol(it.greenvulcano.iot.routing.v1.VersionImpl.getInstance());
        assertTrue(p instanceof it.greenvulcano.iot.routing.v1.ProtocolImpl);

        try {
            ProtocolFactory.createProtocol(new Version() {
                @Override
                public int getMajor() {
                    return 9192;
                }

                @Override
                public int getMinor() {
                    return 3;
                }

                @Override
                public int getPatch() {
                    return 5;
                }
            });
            fail("Should have thrown VersionException");
        } catch (VersionException e) {
            // OK!
        }
    }

    @Test
    public void testCreateDefaultProtocol() throws Exception {
        Protocol p = ProtocolFactory.createDefaultProtocol();
        assertTrue(p instanceof it.greenvulcano.iot.routing.v1.ProtocolImpl);
    }
}