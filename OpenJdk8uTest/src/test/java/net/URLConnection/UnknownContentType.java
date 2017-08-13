package test.java.net.URLConnection;
/*
 * Copyright (c) 2004, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4975103
 * @summary URLConnection.getContentType() does not always return content-type header field
 */

import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import test.java.io.*;
import test.java.net.*;

public class UnknownContentType {
    public static void main(String[] args) throws Exception {
        File tmp = File.createTempFile("bug4975103", null);
        tmp.deleteOnExit();
        URL url = tmp.toURL();
        URLConnection conn = url.openConnection();
        conn.connect();
        String s1 = conn.getContentType();
        String s2 = conn.getHeaderField("content-type");
        if (!s1.equals(s2))
            throw new RuntimeException("getContentType() differs from getHeaderField(\"content-type\")");
    }
}
