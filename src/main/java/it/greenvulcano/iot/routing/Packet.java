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

import java.util.Arrays;

/**
 * @author Domenico Barra - eisenach@gmail.com
 */
public class Packet {

    byte[] payload;
    NetworkId destination;
    String topic;

    public Packet(byte[] payload) {
        this(payload, null);
    }

    public Packet(byte[] payload, NetworkId destination) {
        this.payload = payload;
        this.destination = destination;
    }

    public byte[] getPayload() {
        return payload;
    }

    public NetworkId getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Packet packet = (Packet) o;

        if (!Arrays.equals(payload, packet.payload)) return false;
        return destination != null ? destination.equals(packet.destination) : packet.destination == null;

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(payload);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        return result;
    }


}
