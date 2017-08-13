package test.java.net.Authenticator;
/*
 * Copyright (c) 2001, 2002, Oracle and/or its affiliates. All rights reserved.
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
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import test.java.io.*;
import test.java.net.*;
import test.java.util.*;

/**
 * @test
 * @bug 4474947
 * @summary  fix for bug #4244472 is incomplete - HTTP authorization still needs work
 */

/*
 * Note, this is not a general purpose test for Basic Authentication because
 * it does not check the correctness of the data, only whether the user
 * authenticator gets called once as expected
 */

public class BasicTest {

    static class BasicServer extends Thread {

        ServerSocket server;

        Socket s;
        InputStream is;
        OutputStream os;

        static final String realm = "wallyworld";

        String reply1 = "HTTP/1.1 401 Unauthorized\r\n"+
            "WWW-Authenticate: Basic realm=\""+realm+"\"\r\n\r\n";

        String reply2 = "HTTP/1.1 200 OK\r\n"+
            "Date: Mon, 15 Jan 2001 12:18:21 GMT\r\n" +
            "Server: Apache/1.3.14 (Unix)\r\n" +
            "Connection: close\r\n" +
            "Content-Type: text/html; charset=iso-8859-1\r\n" +
            "Content-Length: 10\r\n\r\n";

        BasicServer (ServerSocket s) {
            server = s;
        }

        void readAll (Socket s) throws IOException {
            byte[] buf = new byte [128];
            InputStream is = s.getInputStream ();
            while (is.available() > 0) {
                is.read (buf);
            }
        }

        public void run () {
            try {
                System.out.println ("Server 1: accept");
                s = server.accept ();
                readAll (s);
                System.out.println ("accepted");
                os = s.getOutputStream();
                os.write (reply1.getBytes());
                Thread.sleep (500);

                System.out.println ("Server 2: accept");
                s = server.accept ();
                readAll (s);
                System.out.println ("accepted");
                os = s.getOutputStream();
                os.write ((reply2+"HelloWorld").getBytes());

                /* Second request now */

                System.out.println ("Server 3: accept");
                s = server.accept ();
                readAll (s);
                System.out.println ("accepted");
                os = s.getOutputStream();
                os.write (reply1.getBytes());
                Thread.sleep (500);
                s.close ();

                System.out.println ("Server 4: accept");
                s = server.accept ();
                readAll (s);
                System.out.println ("accepted");
                os = s.getOutputStream();
                os.write ((reply2+"HelloAgain").getBytes());
            }
            catch (Exception e) {
                System.out.println (e);
            }
            finished ();
        }

        public synchronized void finished () {
            notifyAll();
        }

    }

    static class MyAuthenticator extends Authenticator {
        MyAuthenticator () {
            super ();
        }

        int count = 0;

        public PasswordAuthentication getPasswordAuthentication ()
            {
            count ++;
            System.out.println ("Auth called");
            return (new PasswordAuthentication ("user", "passwordNotCheckedAnyway".toCharArray()));
        }

        public int getCount () {
            return (count);
        }
    }


    static void read (InputStream is) throws IOException {
        int c;
        System.out.println ("reading");
        while ((c=is.read()) != -1) {
            System.out.write (c);
        }
        System.out.println ("");
        System.out.println ("finished reading");
    }

    public static void main (String args[]) throws Exception {
        MyAuthenticator auth = new MyAuthenticator ();
        Authenticator.setDefault (auth);
        ServerSocket ss = new ServerSocket (0);
        int port = ss.getLocalPort ();
        BasicServer server = new BasicServer (ss);
        synchronized (server) {
            server.start();
            System.out.println ("client 1");
            URL url = new URL ("http://localhost:"+port+"/d1/d2/d3/foo.html");
            URLConnection urlc = url.openConnection ();
            InputStream is = urlc.getInputStream ();
            read (is);
            System.out.println ("client 2");
            url = new URL ("http://localhost:"+port+"/d1/foo.html");
            urlc = url.openConnection ();
            is = urlc.getInputStream ();
            read (is);
            server.wait ();
            // check if authenticator was called once (ok) or twice (not)
                int f = auth.getCount();
            if (f != 1) {
                throw new RuntimeException ("Authenticator was called "+f+" times. Should be 1");
            }
        }
    }
}
