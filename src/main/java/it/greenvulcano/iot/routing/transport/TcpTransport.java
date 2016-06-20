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

package it.greenvulcano.iot.routing.transport;

import it.greenvulcano.iot.routing.NetworkId;
import it.greenvulcano.iot.routing.Packet;
import it.greenvulcano.iot.routing.Transport;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Domenico Barra - eisenach@gmail.com
 */
public class TcpTransport extends BasicTransport {

    private String host;
    private int port;

    public TcpTransport(NetworkId id, String host, int port) {
        super(id);
        this.host = host;
        this.port = port;
    }

    @Override
    protected void doStart() throws IOException {

    }

    @Override
    protected void doAuthenticate() throws AuthException {

    }

    @Override
    protected void doStop() throws IOException {

    }

    @Override
    protected void doRoute(Packet p) throws IOException {

    }
}
