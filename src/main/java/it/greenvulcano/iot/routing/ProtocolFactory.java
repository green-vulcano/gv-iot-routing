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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Factory for the supported versions of the GreenVulcano IoT format protocol
 * @author Domenico Barra - eisenach@gmail.com
 */
public class ProtocolFactory {

    /**
     * Creates a Protocol instance compatible with the specific version provided.
     * <strong>Note:</strong> only exact version matching is supported for now.
     * @param v the version of the protocol to create
     * @return an instance of {@link Protocol}
     * @throws VersionException if the provided version is not supported
     */
    public static Protocol createProtocol(Version v) throws VersionException {
        Supplier<Protocol> supplier = suppliers.get(v);
        if (supplier != null) {
            return supplier.get();
        }
        throw new VersionException("Unsupported protocol version: " + v);
    }

    /**
     * @return an instance of Protocol at the default version
     */
    public static Protocol createDefaultProtocol() {
        return new it.greenvulcano.iot.routing.v1.ProtocolImpl();
    }

    /**
     * Holds the registered suppliers
     */
    private static Map<Version, Supplier<Protocol>> suppliers = new HashMap<>();
    static {
        suppliers.put(
                it.greenvulcano.iot.routing.v1.VersionImpl.getInstance(),
                it.greenvulcano.iot.routing.v1.ProtocolImpl::new);
    }

}
