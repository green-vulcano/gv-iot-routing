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

import java.io.IOException;

/**
 * Interface for network transport implementations.
 */
public interface Transport {

    /**
     * Exception to represent authentication problems
     */
    public static class AuthException extends Exception {
        public AuthException(String message) {
            super(message);
        }

        public AuthException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * @return the id of the network to which this transport is
     *         connected, or <code>null</code> if the connection
     *         is to the main site.
     */
    NetworkId getNetworkId();

    /**
     * Starts the transport - i.e. acquires resources and establishes
     * connections to send/receive data over the IoT network
     * @throws IOException if anything goes wrong while starting the transport
     * @throws AuthException if the authentication phase goes wrong
     */
    void start() throws IOException, AuthException;

    /**
     * Stops the transport - i.e. closes connections and releases
     * previously acquired resources
     * @throws IOException if anything goes wrong while stopping the transport
     */
    void stop() throws IOException;

    /**
     * Routes a packet towards its destination
     * @param p the packet to route
     * @throws UnknownNetworkException if the network or host to route
     *         to is unknown
     * @throws IOException if anything goes wrong while routing the packet
     */
    void route(Packet p) throws IOException;

    /**
     * Adds a new listener to this transport
     * @param lst the listener to add
     */
    void addListener(TransportListener lst);

    /**
     * Removes a listener from this transport
     * @param lst the listener to remove
     */
    void removeListener(TransportListener lst);
}
