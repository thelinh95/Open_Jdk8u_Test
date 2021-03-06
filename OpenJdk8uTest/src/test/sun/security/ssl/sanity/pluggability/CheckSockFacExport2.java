package test.sun.security.ssl.sanity.pluggability;
/*
 * Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4635454
 * @summary Check pluggability of SSLSocketFactory and
 *     SSLServerSocketFactory classes.
 * @run main/othervm CheckSockFacExport2
 *
 *     SunJSSE does not support dynamic system properties, no way to re-use
 *     system properties in samevm/agentvm mode.
 */
import java.security.Security;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

import test.java.net.*;
import test.java.security.*;
import test.javax.net.ssl.*;

public class CheckSockFacExport2 {

    public static void main(String argv[]) throws Exception {
        // reserve the security properties
        String reservedSFacAlg =
            Security.getProperty("ssl.SocketFactory.provider");
        String reservedSSFacAlg =
            Security.getProperty("ssl.ServerSocketFactory.provider");

        try {
            Security.setProperty("ssl.SocketFactory.provider",
                "MySSLSocketFacImpl");
            MySSLSocketFacImpl.useStandardCipherSuites();
            Security.setProperty("ssl.ServerSocketFactory.provider",
                "MySSLServerSocketFacImpl");
            MySSLServerSocketFacImpl.useStandardCipherSuites();

            boolean result = false;
            for (int i = 0; i < 2; i++) {
                switch (i) {
                case 0:
                    System.out.println("Testing SSLSocketFactory:");
                    SSLSocketFactory sf = (SSLSocketFactory)
                        SSLSocketFactory.getDefault();
                    result = (sf instanceof MySSLSocketFacImpl);
                    break;

                case 1:
                    System.out.println("Testing SSLServerSocketFactory:");
                    SSLServerSocketFactory ssf = (SSLServerSocketFactory)
                        SSLServerSocketFactory.getDefault();
                    result = (ssf instanceof MySSLServerSocketFacImpl);
                    break;
                default:
                    throw new Exception("Internal Test Error");
                }
                if (result) {
                    System.out.println("...accepted valid SFs");
                } else {
                    throw new Exception("...wrong SF is used");
                }
            }
            System.out.println("Test Passed");
        } finally {
            // restore the security properties
            if (reservedSFacAlg == null) {
                reservedSFacAlg = "";
            }

            if (reservedSSFacAlg == null) {
                reservedSSFacAlg = "";
            }
            Security.setProperty("ssl.SocketFactory.provider",
                                                            reservedSFacAlg);
            Security.setProperty("ssl.ServerSocketFactory.provider",
                                                            reservedSSFacAlg);
        }
    }
}
