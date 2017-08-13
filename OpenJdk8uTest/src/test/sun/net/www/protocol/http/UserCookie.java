package test.sun.net.www.protocol.http;
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

/*
 * @test
 * @bug 6439651
 * @run main/othervm UserAuth
 * @summary Sending "Cookie" header with JRE 1.5.0_07 doesn't work anymore
 */

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.List;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import sun.net.www.protocol.http.HttpURLConnection;
import test.com.sun.net.httpserver.*;
import test.java.io.*;
import test.java.net.*;
import test.java.util.*;

public class UserCookie
{
    com.sun.net.httpserver.HttpServer httpServer;

    public static void main(String[] args) {
        new UserCookie();
    }

    public UserCookie() {
        try {
            startHttpServer();
            doClient();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void doClient() {
        try {
            // set default CookieHandler to accept only accepts cookies from original server.
            CookieHandler.setDefault(new CookieManager());

            InetSocketAddress address = httpServer.getAddress();

            URL url = new URL("http://" + address.getHostName() + ":" + address.getPort() + "/test/");
            HttpURLConnection uc = (HttpURLConnection)url.openConnection();
            uc.setRequestProperty("Cookie", "value=ValueDoesNotMatter");
            int resp = uc.getResponseCode();

            System.out.println("Response Code is " + resp);
            if (resp != 200)
                throw new RuntimeException("Failed: Cookie header was not retained");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpServer.stop(1);
        }
    }

     /**
     * Http Server
     */
    void startHttpServer() throws IOException {
        httpServer = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(0), 0);

        // create HttpServer context
        HttpContext ctx = httpServer.createContext("/test/", new MyHandler());

        httpServer.start();
    }

    class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Headers reqHeaders = t.getRequestHeaders();

            List<String> cookie = reqHeaders.get("Cookie");

            if (cookie == null || !cookie.get(0).equals("value=ValueDoesNotMatter"))
                t.sendResponseHeaders(400, -1);

            t.sendResponseHeaders(200, -1);
            t.close();
        }
    }



}