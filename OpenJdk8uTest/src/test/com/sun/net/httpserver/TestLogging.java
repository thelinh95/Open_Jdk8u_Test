package test.com.sun.net.httpserver;
/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import test.com.sun.net.httpserver.*;
import test.java.io.*;
import test.java.net.*;
import test.java.security.*;
import test.java.security.cert.*;
import test.java.util.*;
import test.java.util.concurrent.*;
import test.java.util.logging.*;
import test.javax.net.ssl.*;

public class TestLogging extends Test {

    public static void main (String[] args) throws Exception {
        HttpServer s1 = null;
        ExecutorService executor=null;

        try {
            System.out.print ("Test9: ");
            String root = System.getProperty ("test.src")+ "/docs";
            InetSocketAddress addr = new InetSocketAddress (0);
            Logger logger = Logger.getLogger ("com.sun.net.httpserver");
            logger.setLevel (Level.ALL);
            Handler h1 = new ConsoleHandler ();
            h1.setLevel (Level.ALL);
            logger.addHandler (h1);
            s1 = HttpServer.create (addr, 0);
            logger.info (root);
            HttpHandler h = new FileServerHandler (root);
            HttpContext c1 = s1.createContext ("/test1", h);
            executor = Executors.newCachedThreadPool();
            s1.setExecutor (executor);
            s1.start();

            int p1 = s1.getAddress().getPort();

            URL url = new URL ("http://127.0.0.1:"+p1+"/test1/smallfile.txt");
            HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
            InputStream is = urlc.getInputStream();
            while (is.read() != -1) ;
            is.close();

            url = new URL ("http://127.0.0.1:"+p1+"/test1/doesntexist.txt");
            urlc = (HttpURLConnection)url.openConnection();
            try {
                is = urlc.getInputStream();
                while (is.read() != -1) ;
                is.close();
            } catch (IOException e) {
                System.out.println ("caught expected exception");
            }

            Socket s = new Socket ("127.0.0.1", p1);
            OutputStream os = s.getOutputStream();
            //os.write ("GET xxx HTTP/1.1\r\n".getBytes());
            os.write ("HELLO WORLD\r\n".getBytes());
            is = s.getInputStream();
            while (is.read() != -1) ;
            os.close(); is.close(); s.close();
            System.out.println ("OK");
        } finally {
            delay();
            if (s1 != null)
                s1.stop(2);
            if (executor != null)
                executor.shutdown();
        }
    }
}
