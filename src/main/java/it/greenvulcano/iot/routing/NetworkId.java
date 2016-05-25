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

import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Uniquely identifies a <i>thing</i> (i.e. a GreenVulcano node or any other
 * device) over a GreenVulcano IoT network.
 *
 * An ID is structured as follows: <code>S1:S2:S3:ThingID</code>.
 * This implies the following network structure:
 * <pre>
 *                     [ Main Site ]
 *                          |
 *            ------------------------------
 *            |             |              |
 *          [ S1 ]         ...            ...
 *            |
 *     ---------------
 *     |      |      |
 *    ...    ...   [ S2 ]
 *                   |
 *            --------------|
 *            |      |    [ S3 ]
 *           ...    ...     |- ...
 *                          |- ThingID
 *                          |- ...
 * </pre>
 *
 * <strong>Note:</strong> this implementation is not particularly
 * optimized for space (under the assumption that you are running on
 * a GreenVulcano instance anyway), but rather tries to save some CPU
 * cycles when used (under the assumption that it could run on slow
 * processors).<br/>
 *
 * <strong>Note:</strong> this class is <strong>not</strong> thread-safe.
 *
 * @author Domenico Barra - eisenach@gmail.com
 */
public class NetworkId {

    public static final String SEPARATOR = ":";

    /**
     * Holds the splitted network path in a reverse order, so that
     * a stack logic can be used efficiently.
     */
    private ArrayList<String> reversePath;

    /**
     * Caches the string representation of this network id
     */
    private AtomicReference<String> asString = new AtomicReference<>();

    /**
     * Constructs a new NetworkID.
     * @param path
     */
    public NetworkId(String path) {
        if (path == null || path.length() == 0) {
            throw new IllegalArgumentException("Parameter 'name' must be non-empty");
        }
        this.reversePath = splitReverse(path);
    }

    /**
     * Adds a network id or sub-path to the current path
     * @param network the network id or sub-path to add
     */
    public void push(String network) {
        if (network == null || network.length() == 0) return;
        if (network.contains(SEPARATOR)) {
            reversePath.addAll(splitReverse(network));
        } else {
            reversePath.add(network);
        }
        asString.set(null);
    }

    /**
     * @return the ID of the <i>thing</i> i.e. the terminal device
     */
    public String getThingId() {
        if (reversePath.size() > 0) {
            return reversePath.get(0);
        }
        return null;
    }

    /**
     * Removes (and returns, if it exists) the uppermost network segment id.
     * @return the uppermost network segment, or <code>null</code> if only the
     *         thing ID is left.
     */
    public String pop() {
        if (reversePath.size() == 1) return null;
        String removed = reversePath.remove(reversePath.size() - 1);
        asString.set(null);
        return removed;
    }

    @Override
    public String toString() {
        String s = asString.get();
        if (s == null) {
            s = joinReverse(reversePath);
            asString.set(s);
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NetworkId networkId = (NetworkId) o;
        return reversePath != null ? reversePath.equals(networkId.reversePath) : networkId.reversePath == null;
    }

    @Override
    public int hashCode() {
        return reversePath != null ? reversePath.hashCode() : 0;
    }

    private static ArrayList<String> splitReverse(String path) {
        StringTokenizer st = new StringTokenizer(path, SEPARATOR);
        /*
           Allocating one more slot because most of the times the operation to
           perform will be to push or pop just one element; therefore, most
           of the times, we'll avoid reallocation while keeping the needed
           memory to a minimum.
         */
        ArrayList<String> list = new ArrayList<>(st.countTokens() + 1);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        Collections.reverse(list);
        return list;
    }

    private static String joinReverse(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i=list.size()-1; i >= 0; i--) {
            sb.append(list.get(i));
            if (i > 0) sb.append(SEPARATOR);
        }
        return sb.toString();
    }

}
