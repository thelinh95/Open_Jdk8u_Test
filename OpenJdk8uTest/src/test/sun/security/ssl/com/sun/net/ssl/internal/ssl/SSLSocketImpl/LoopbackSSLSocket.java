package test.sun.security.ssl.com.sun.net.ssl.internal.ssl.SSLSocketImpl;
/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
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

/*
 * @test
 * @bug 5067458
 * @summary Loopback SSLSocketImpl createSocket is throwing an exception.
 * @author Xuelei Fan
 */

import java.io.IOException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import test.java.io.*;
import test.java.net.*;
import test.javax.net.ssl.*;

public class LoopbackSSLSocket {

    public static void main(String[] args) throws Exception {
        SSLSocketFactory sf = (SSLSocketFactory)SSLSocketFactory.getDefault();
        // we won't expect a IllegalArgumentException: hostname can't be null.
        try {
            SSLSocket s = (SSLSocket)sf.createSocket((String)null, 0);
            s.close();
        } catch (IOException ioe) {
            // would catch a IOException because there is no listener on
            // that socket.
        }
    }

}
