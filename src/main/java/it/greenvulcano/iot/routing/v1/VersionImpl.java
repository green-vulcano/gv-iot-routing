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

/**
 * @author Domenico Barra - eisenach@gmail.com
 */
public class VersionImpl extends it.greenvulcano.iot.routing.Version {
    @Override
    public int getMajor() {
        return 1;
    }

    @Override
    public int getMinor() {
        return 0;
    }

    @Override
    public int getPatch() {
        return 0;
    }

    private static final VersionImpl INSTANCE = new VersionImpl();

    public static VersionImpl getInstance() {
        return INSTANCE;
    }
}
