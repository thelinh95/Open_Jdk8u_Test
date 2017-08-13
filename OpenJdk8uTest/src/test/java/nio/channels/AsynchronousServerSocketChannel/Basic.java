package test.java.nio.channels.AsynchronousServerSocketChannel;
/*
 * Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/* @test
 * @bug 4607272 6842687
 * @summary Unit test for AsynchronousServerSocketChannel
 * @run main/timeout=180 Basic
 */

import static java.net.StandardSocketOptions.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.nio.channels.AcceptPendingException;
import java.nio.channels.AlreadyBoundException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

import test.java.net.*;
import test.java.nio.channels.*;

public class Basic {

    public static void main(String[] args) throws Exception {
        testBind();
        testAccept();
        testSocketOptions();
    }

    static void testBind() throws Exception {
        System.out.println("-- bind --");

        AsynchronousServerSocketChannel ch = AsynchronousServerSocketChannel.open();
        if (ch.getLocalAddress() != null)
            throw new RuntimeException("Local address should be 'null'");
        ch.bind(new InetSocketAddress(0), 20);

        // check local address after binding
        InetSocketAddress local = (InetSocketAddress)ch.getLocalAddress();
        if (local.getPort() == 0)
            throw new RuntimeException("Unexpected port");
        if (!local.getAddress().isAnyLocalAddress())
            throw new RuntimeException("Not bound to a wildcard address");

        // try to re-bind
        try {
            ch.bind(new InetSocketAddress(0));
            throw new RuntimeException("AlreadyBoundException expected");
        } catch (AlreadyBoundException x) {
        }
        ch.close();

        // check ClosedChannelException
        ch = AsynchronousServerSocketChannel.open();
        ch.close();
        try {
            ch.bind(new InetSocketAddress(0));
            throw new RuntimeException("ClosedChannelException  expected");
        } catch (ClosedChannelException  x) {
        }
    }

    static void testAccept() throws Exception {
        System.out.println("-- accept --");

        final AsynchronousServerSocketChannel listener =
            AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(0));

        InetAddress lh = InetAddress.getLocalHost();
        int port = ((InetSocketAddress)(listener.getLocalAddress())).getPort();
        final InetSocketAddress isa = new InetSocketAddress(lh, port);

        // establish a few loopback connections
        for (int i=0; i<100; i++) {
            SocketChannel sc = SocketChannel.open(isa);
            AsynchronousSocketChannel ch = listener.accept().get();
            sc.close();
            ch.close();
        }

       final AtomicReference<Throwable> exception = new AtomicReference<Throwable>();

        // start accepting
        listener.accept((Void)null, new CompletionHandler<AsynchronousSocketChannel,Void>() {
            public void completed(AsynchronousSocketChannel ch, Void att) {
                try {
                    ch.close();
                } catch (IOException ignore) { }
            }
            public void failed(Throwable exc, Void att) {
                exception.set(exc);
            }
        });

        // check AcceptPendingException
        try {
            listener.accept();
            throw new RuntimeException("AcceptPendingException expected");
        } catch (AcceptPendingException x) {
        }

        // asynchronous close
        listener.close();
        while (exception.get() == null)
            Thread.sleep(100);
        if (!(exception.get() instanceof AsynchronousCloseException))
            throw new RuntimeException("AsynchronousCloseException expected");

        // once closed when a further attemt should throw ClosedChannelException
        try {
            listener.accept().get();
            throw new RuntimeException("ExecutionException expected");
        } catch (ExecutionException x) {
            if (!(x.getCause() instanceof ClosedChannelException))
                throw new RuntimeException("Cause of ClosedChannelException expected");
        } catch (InterruptedException x) {
        }

    }

    static void testSocketOptions() throws Exception {
        System.out.println("-- socket options --");
        AsynchronousServerSocketChannel ch = AsynchronousServerSocketChannel.open();
        try {
            // check supported options
            Set<SocketOption<?>> options = ch.supportedOptions();
            if (!options.contains(SO_REUSEADDR))
                throw new RuntimeException("SO_REUSEADDR should be supported");
            if (!options.contains(SO_RCVBUF))
                throw new RuntimeException("SO_RCVBUF should be supported");

            // allowed to change when not bound
            ch.setOption(SO_RCVBUF, 256*1024);     // can't check
            int before = ch.getOption(SO_RCVBUF);
            int after = ch.setOption(SO_RCVBUF, Integer.MAX_VALUE).getOption(SO_RCVBUF);
            if (after < before)
                 throw new RuntimeException("setOption caused SO_RCVBUF to decrease");
            ch.setOption(SO_REUSEADDR, true);
            checkOption(ch, SO_REUSEADDR, true);
            ch.setOption(SO_REUSEADDR, false);
            checkOption(ch, SO_REUSEADDR, false);
        } finally {
            ch.close();
        }
    }

    static void checkOption(AsynchronousServerSocketChannel ch,
                            SocketOption name, Object expectedValue)
        throws IOException
    {
        Object value = ch.getOption(name);
        if (!value.equals(expectedValue))
            throw new RuntimeException("value not as expected");
    }
}