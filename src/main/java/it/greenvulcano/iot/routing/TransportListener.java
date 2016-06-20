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

/**
 * Interface to respond to transport-related events.
 * @author Domenico Barra - eisenach@gmail.com
 */
public interface TransportListener {

    class Info {
        public Transport transport;
        public Packet    packet;
        public Exception failureReason;

        public Info(Transport transport) {
            this.transport = transport;
        }

        public Info(Transport transport, Packet packet, Exception failureReason) {
            this.transport = transport;
            this.packet = packet;
            this.failureReason = failureReason;
        }
    }


    /**
     * Called when a transport has started
     * @param t the information concerning the specific transport
     */
    void transportStarted(Info t);

    /**
     * Called when a transport is about to stop
     * @param t the information concerning the specific transport
     */
    void transportStopping(Info t);

    /**
     * Called when a packet has been received through a transport
     * @param t the information concerning the specific transport and packet
     */
    void transportPacketReceived(Info t);

    /**
     * Called when a packet is about to be sent
     * @param t the information concerning the specific transport and packet
     */
    void transportPacketSending(Info t);
}
