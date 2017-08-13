package test.java.net.URLConnection;
/*
 * Copyright (c) 2001, 2010, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4143541 4147035 4244362
 * @summary URLConnection cannot enumerate request properties,
 *          and URLConnection can neither get nor set multiple
 *          request properties w/ same key
 *
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import test.java.io.*;
import test.java.net.*;
import test.java.util.*;

public class URLConnectionHeaders {

    static class XServer extends Thread {
        ServerSocket srv;
        Socket s;
        InputStream is;
        OutputStream os;

        XServer (ServerSocket s) {
            srv = s;
        }

        Socket getSocket () {
            return (s);
        }

        public void run() {
            try {
                String x;
                s = srv.accept ();
                is = s.getInputStream ();
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                os = s.getOutputStream ();
                BufferedWriter w = new BufferedWriter(new OutputStreamWriter(os));
                w.write("HTTP/1.1 200 OK\r\n");
                w.write("Content-Type: text/plain\r\n");
                while ((x=r.readLine()).length() != 0) {
                    System.out.println("request: "+x);
                    if (!x.startsWith("GET")) {
                        w.write(x);
                        w.newLine();
                    }
                }
                w.newLine();
                w.flush();
                s.close ();
            } catch (IOException e) { e.printStackTrace();
            } finally {
                try { srv.close(); } catch (IOException unused) {}
            }
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket serversocket = new ServerSocket (0);
            int port = serversocket.getLocalPort ();
            XServer server = new XServer (serversocket);
            server.start ();
            Thread.sleep (200);
            URL url = new URL ("http://localhost:"+port+"/index.html");
            URLConnection uc = url.openConnection ();

            // add request properties
            uc.addRequestProperty("Cookie", "cookie1");
            uc.addRequestProperty("Cookie", "cookie2");
            uc.addRequestProperty("Cookie", "cookie3");

            Map e = uc.getRequestProperties();

            if (!((List)e.get("Cookie")).toString().equals("[cookie3, cookie2, cookie1]")) {
                throw new RuntimeException("getRequestProperties fails");
            }

            e = uc.getHeaderFields();

            if ((e.get("Content-Type") == null) || (e.get(null) == null)) {
                throw new RuntimeException("getHeaderFields fails");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
