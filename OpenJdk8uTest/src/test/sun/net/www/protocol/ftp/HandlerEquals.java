package test.sun.net.www.protocol.ftp;
/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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

/**
 * @test
 * @bug 4278192
 * @summary  URLStreamHandler.equals(URL,URL) ignores authority field
 */
import java.net.URL;

import test.java.io.*;
import test.java.net.*;

public class HandlerEquals {
    public static void main (String args[]) throws Exception {
        int errorCnt = 0 ;

        URL url1 = new URL("ftp://he@host/file#ref") ;
        URL url2 = new URL("ftp://she@host/file#ref") ;

        if (url1.equals(url2)) {
            throw new RuntimeException("URLStreamHandler.equals failure.");
        } else {
            System.out.println("Success.");
        }
    }
}
