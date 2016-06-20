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
import it.greenvulcano.iot.routing.TransportListener;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;

/**
 * @author Domenico Barra - eisenach@gmail.com
 */
public abstract class BasicTransport implements Transport {

    protected NetworkId networkId;
    private ConcurrentLinkedQueue<WeakReference<TransportListener>> listeners = new ConcurrentLinkedQueue<>();

    BasicTransport(NetworkId networkId) {
        this.networkId = networkId;
    }

    @Override
    public final NetworkId getNetworkId() {
        return networkId;
    }

    @Override
    public final void start() throws IOException, AuthException {
        doStart();
        doAuthenticate();
        invokeCallback(TransportListener::transportStarted, new TransportListener.Info(this));
    }

    @Override
    public final void stop() throws IOException {
        invokeCallback(TransportListener::transportStopping, new TransportListener.Info(this));
        doStop();
    }

    @Override
    public final void route(Packet p) throws IOException {
        invokeCallback(TransportListener::transportPacketSending, new TransportListener.Info(this, p, null));
        doRoute(p);
    }

    @Override
    public void addListener(TransportListener lst) {
        listeners.add(new WeakReference(lst));
    }

    @Override
    public void removeListener(TransportListener lst) {
        listeners.removeIf(wr -> lst.equals(wr.get()));
    }

    protected void invokeCallback(BiConsumer<TransportListener, TransportListener.Info> callback,
                                  TransportListener.Info info) {
        listeners.forEach( lst -> {
            TransportListener tl = lst.get();
            if (tl != null) callback.accept(lst.get(), info);
        });
    }

    protected abstract void doStart() throws IOException;
    protected abstract void doAuthenticate() throws AuthException;
    protected abstract void doStop() throws IOException;
    protected abstract void doRoute(Packet p) throws IOException;

}
