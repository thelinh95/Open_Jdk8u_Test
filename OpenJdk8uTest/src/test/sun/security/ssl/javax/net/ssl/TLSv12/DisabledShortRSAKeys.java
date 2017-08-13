package test.sun.security.ssl.javax.net.ssl.TLSv12;
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
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

// SunJSSE does not support dynamic system properties, no way to re-use
// system properties in samevm/agentvm mode.

/*
 * @test
 * @bug 7109274
 * @summary Consider disabling support for X.509 certificates with RSA keys
 *          less than 1024 bits
 *
 * @run main/othervm DisabledShortRSAKeys PKIX TLSv1.2
 * @run main/othervm DisabledShortRSAKeys SunX509 TLSv1.2
 * @run main/othervm DisabledShortRSAKeys PKIX TLSv1.1
 * @run main/othervm DisabledShortRSAKeys SunX509 TLSv1.1
 * @run main/othervm DisabledShortRSAKeys PKIX TLSv1
 * @run main/othervm DisabledShortRSAKeys SunX509 TLSv1
 * @run main/othervm DisabledShortRSAKeys PKIX SSLv3
 * @run main/othervm DisabledShortRSAKeys SunX509 SSLv3
 */

import test.java.io.*;
import test.java.net.*;
import test.java.security.spec.*;
import test.java.util.*;
import test.javax.net.ssl.*;

import java.security.Security;
import java.security.KeyStore;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


public class DisabledShortRSAKeys {

    /*
     * =============================================================
     * Set the various variables needed for the tests, then
     * specify what tests to run on each side.
     */

    /*
     * Should we run the client or server in a separate thread?
     * Both sides can throw exceptions, but do you have a preference
     * as to which side should be the main thread.
     */
    static boolean separateServerThread = true;

    /*
     * Where do we find the keystores?
     */
    // Certificates and key used in the test.
    static String trustedCertStr =
        "-----BEGIN CERTIFICATE-----\n" +
        "MIICkjCCAfugAwIBAgIBADANBgkqhkiG9w0BAQQFADA7MQswCQYDVQQGEwJVUzEN\n" +
        "MAsGA1UEChMESmF2YTEdMBsGA1UECxMUU3VuSlNTRSBUZXN0IFNlcml2Y2UwHhcN\n" +
        "MTEwODE5MDE1MjE5WhcNMzIwNzI5MDE1MjE5WjA7MQswCQYDVQQGEwJVUzENMAsG\n" +
        "A1UEChMESmF2YTEdMBsGA1UECxMUU3VuSlNTRSBUZXN0IFNlcml2Y2UwgZ8wDQYJ\n" +
        "KoZIhvcNAQEBBQADgY0AMIGJAoGBAM8orG08DtF98TMSscjGsidd1ZoN4jiDpi8U\n" +
        "ICz+9dMm1qM1d7O2T+KH3/mxyox7Rc2ZVSCaUD0a3CkhPMnlAx8V4u0H+E9sqso6\n" +
        "iDW3JpOyzMExvZiRgRG/3nvp55RMIUV4vEHOZ1QbhuqG4ebN0Vz2DkRft7+flthf\n" +
        "vDld6f5JAgMBAAGjgaUwgaIwHQYDVR0OBBYEFLl81dnfp0wDrv0OJ1sxlWzH83Xh\n" +
        "MGMGA1UdIwRcMFqAFLl81dnfp0wDrv0OJ1sxlWzH83XhoT+kPTA7MQswCQYDVQQG\n" +
        "EwJVUzENMAsGA1UEChMESmF2YTEdMBsGA1UECxMUU3VuSlNTRSBUZXN0IFNlcml2\n" +
        "Y2WCAQAwDwYDVR0TAQH/BAUwAwEB/zALBgNVHQ8EBAMCAQYwDQYJKoZIhvcNAQEE\n" +
        "BQADgYEALlgaH1gWtoBZ84EW8Hu6YtGLQ/L9zIFmHonUPZwn3Pr//icR9Sqhc3/l\n" +
        "pVTxOINuFHLRz4BBtEylzRIOPzK3tg8XwuLb1zd0db90x3KBCiAL6E6cklGEPwLe\n" +
        "XYMHDn9eDsaq861Tzn6ZwzMgw04zotPMoZN0mVd/3Qca8UJFucE=\n" +
        "-----END CERTIFICATE-----";

    static String targetCertStr =
        "-----BEGIN CERTIFICATE-----\n" +
        "MIICNDCCAZ2gAwIBAgIBDDANBgkqhkiG9w0BAQQFADA7MQswCQYDVQQGEwJVUzEN\n" +
        "MAsGA1UEChMESmF2YTEdMBsGA1UECxMUU3VuSlNTRSBUZXN0IFNlcml2Y2UwHhcN\n" +
        "MTExMTA3MTM1NTUyWhcNMzEwNzI1MTM1NTUyWjBPMQswCQYDVQQGEwJVUzENMAsG\n" +
        "A1UEChMESmF2YTEdMBsGA1UECxMUU3VuSlNTRSBUZXN0IFNlcml2Y2UxEjAQBgNV\n" +
        "BAMTCWxvY2FsaG9zdDBcMA0GCSqGSIb3DQEBAQUAA0sAMEgCQQC3Pb49OSPfOD2G\n" +
        "HSXFCFx1GJEZfqG9ZUf7xuIi/ra5dLjPGAaoY5QF2QOa8VnOriQCXDfyXHxsuRnE\n" +
        "OomxL7EVAgMBAAGjeDB2MAsGA1UdDwQEAwID6DAdBgNVHQ4EFgQUXNCJK3/dtCIc\n" +
        "xb+zlA/JINlvs/MwHwYDVR0jBBgwFoAUuXzV2d+nTAOu/Q4nWzGVbMfzdeEwJwYD\n" +
        "VR0lBCAwHgYIKwYBBQUHAwEGCCsGAQUFBwMCBggrBgEFBQcDAzANBgkqhkiG9w0B\n" +
        "AQQFAAOBgQB2qIDUxA2caMPpGtUACZAPRUtrGssCINIfItETXJZCx/cRuZ5sP4D9\n" +
        "N1acoNDn0hCULe3lhXAeTC9NZ97680yJzregQMV5wATjo1FGsKY30Ma+sc/nfzQW\n" +
        "+h/7RhYtoG0OTsiaDCvyhI6swkNJzSzrAccPY4+ZgU8HiDLzZTmM3Q==\n" +
        "-----END CERTIFICATE-----";

    // Private key in the format of PKCS#8, key size is 512 bits.
    static String targetPrivateKey =
        "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAtz2+PTkj3zg9hh0l\n" +
        "xQhcdRiRGX6hvWVH+8biIv62uXS4zxgGqGOUBdkDmvFZzq4kAlw38lx8bLkZxDqJ\n" +
        "sS+xFQIDAQABAkByx/5Oo2hQ/w2q4L8z+NTRlJ3vdl8iIDtC/4XPnfYfnGptnpG6\n" +
        "ZThQRvbMZiai0xHQPQMszvAHjZVme1eDl3EBAiEA3aKJHynPVCEJhpfCLWuMwX5J\n" +
        "1LntwJO7NTOyU5m8rPECIQDTpzn5X44r2rzWBDna/Sx7HW9IWCxNgUD2Eyi2nA7W\n" +
        "ZQIgJerEorw4aCAuzQPxiGu57PB6GRamAihEAtoRTBQlH0ECIQDN08FgTtnesgCU\n" +
        "DFYLLcw1CiHvc7fZw4neBDHCrC8NtQIgA8TOUkGnpCZlQ0KaI8KfKWI+vxFcgFnH\n" +
        "3fnqsTgaUs4=";

    static char passphrase[] = "passphrase".toCharArray();

    /*
     * Is the server ready to serve?
     */
    volatile static boolean serverReady = false;

    /*
     * Turn on SSL debugging?
     */
    static boolean debug = false;

    /*
     * Define the server side of the test.
     *
     * If the server prematurely exits, serverReady will be set to true
     * to avoid infinite hangs.
     */
    void doServerSide() throws Exception {
        SSLContext context = generateSSLContext(null, targetCertStr,
                                            targetPrivateKey);
        SSLServerSocketFactory sslssf = context.getServerSocketFactory();
        SSLServerSocket sslServerSocket =
            (SSLServerSocket)sslssf.createServerSocket(serverPort);
        serverPort = sslServerSocket.getLocalPort();

        /*
         * Signal Client, we're ready for his connect.
         */
        serverReady = true;

        try (SSLSocket sslSocket = (SSLSocket)sslServerSocket.accept()) {
            try (InputStream sslIS = sslSocket.getInputStream()) {
                sslIS.read();
            }

            throw new Exception(
                    "RSA keys shorter than 1024 bits should be disabled");
        } catch (SSLHandshakeException sslhe) {
            // the expected exception, ignore
        }
    }

    /*
     * Define the client side of the test.
     *
     * If the server prematurely exits, serverReady will be set to true
     * to avoid infinite hangs.
     */
    void doClientSide() throws Exception {

        /*
         * Wait for server to get started.
         */
        while (!serverReady) {
            Thread.sleep(50);
        }

        SSLContext context = generateSSLContext(trustedCertStr, null, null);
        SSLSocketFactory sslsf = context.getSocketFactory();

        try (SSLSocket sslSocket =
            (SSLSocket)sslsf.createSocket("localhost", serverPort)) {

            // only enable the target protocol
            sslSocket.setEnabledProtocols(new String[] {enabledProtocol});

            // enable a block cipher
            sslSocket.setEnabledCipherSuites(
                new String[] {"TLS_DHE_RSA_WITH_AES_128_CBC_SHA"});

            try (OutputStream sslOS = sslSocket.getOutputStream()) {
                sslOS.write('B');
                sslOS.flush();
            }

            throw new Exception(
                    "RSA keys shorter than 1024 bits should be disabled");
        } catch (SSLHandshakeException sslhe) {
            // the expected exception, ignore
        }
    }

    /*
     * =============================================================
     * The remainder is just support stuff
     */
    private static String tmAlgorithm;        // trust manager
    private static String enabledProtocol;    // the target protocol

    private static void parseArguments(String[] args) {
        tmAlgorithm = args[0];
        enabledProtocol = args[1];
    }

    private static SSLContext generateSSLContext(String trustedCertStr,
            String keyCertStr, String keySpecStr) throws Exception {

        // generate certificate from cert string
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        // create a key store
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(null, null);

        // import the trused cert
        Certificate trusedCert = null;
        ByteArrayInputStream is = null;
        if (trustedCertStr != null) {
            is = new ByteArrayInputStream(trustedCertStr.getBytes());
            trusedCert = cf.generateCertificate(is);
            is.close();

            ks.setCertificateEntry("RSA Export Signer", trusedCert);
        }

        if (keyCertStr != null) {
            // generate the private key.
            PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(
                                Base64.getMimeDecoder().decode(keySpecStr));
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPrivateKey priKey =
                    (RSAPrivateKey)kf.generatePrivate(priKeySpec);

            // generate certificate chain
            is = new ByteArrayInputStream(keyCertStr.getBytes());
            Certificate keyCert = cf.generateCertificate(is);
            is.close();

            Certificate[] chain = null;
            if (trusedCert != null) {
                chain = new Certificate[2];
                chain[0] = keyCert;
                chain[1] = trusedCert;
            } else {
                chain = new Certificate[1];
                chain[0] = keyCert;
            }

            // import the key entry.
            ks.setKeyEntry("Whatever", priKey, passphrase, chain);
        }

        // create SSL context
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmAlgorithm);
        tmf.init(ks);

        SSLContext ctx = SSLContext.getInstance("TLS");
        if (keyCertStr != null && !keyCertStr.isEmpty()) {
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("NewSunX509");
            kmf.init(ks, passphrase);

            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            ks = null;
        } else {
            ctx.init(null, tmf.getTrustManagers(), null);
        }

        return ctx;
    }


    // use any free port by default
    volatile int serverPort = 0;

    volatile Exception serverException = null;
    volatile Exception clientException = null;

    public static void main(String[] args) throws Exception {
        if (debug)
            System.setProperty("javax.net.debug", "all");

        /*
         * Get the customized arguments.
         */
        parseArguments(args);

        /*
         * Start the tests.
         */
        new DisabledShortRSAKeys();
    }

    Thread clientThread = null;
    Thread serverThread = null;

    /*
     * Primary constructor, used to drive remainder of the test.
     *
     * Fork off the other side, then do your work.
     */
    DisabledShortRSAKeys() throws Exception {
        Exception startException = null;
        try {
            if (separateServerThread) {
                startServer(true);
                startClient(false);
            } else {
                startClient(true);
                startServer(false);
            }
        } catch (Exception e) {
            startException = e;
        }

        /*
         * Wait for other side to close down.
         */
        if (separateServerThread) {
            if (serverThread != null) {
                serverThread.join();
            }
        } else {
            if (clientThread != null) {
                clientThread.join();
            }
        }

        /*
         * When we get here, the test is pretty much over.
         * Which side threw the error?
         */
        Exception local;
        Exception remote;

        if (separateServerThread) {
            remote = serverException;
            local = clientException;
        } else {
            remote = clientException;
            local = serverException;
        }

        Exception exception = null;

        /*
         * Check various exception conditions.
         */
        if ((local != null) && (remote != null)) {
            // If both failed, return the curthread's exception.
            local.initCause(remote);
            exception = local;
        } else if (local != null) {
            exception = local;
        } else if (remote != null) {
            exception = remote;
        } else if (startException != null) {
            exception = startException;
        }

        /*
         * If there was an exception *AND* a startException,
         * output it.
         */
        if (exception != null) {
            if (exception != startException && startException != null) {
                exception.addSuppressed(startException);
            }
            throw exception;
        }

        // Fall-through: no exception to throw!
    }

    void startServer(boolean newThread) throws Exception {
        if (newThread) {
            serverThread = new Thread() {
                public void run() {
                    try {
                        doServerSide();
                    } catch (Exception e) {
                        /*
                         * Our server thread just died.
                         *
                         * Release the client, if not active already...
                         */
                        System.err.println("Server died...");
                        serverReady = true;
                        serverException = e;
                    }
                }
            };
            serverThread.start();
        } else {
            try {
                doServerSide();
            } catch (Exception e) {
                serverException = e;
            } finally {
                serverReady = true;
            }
        }
    }

    void startClient(boolean newThread) throws Exception {
        if (newThread) {
            clientThread = new Thread() {
                public void run() {
                    try {
                        doClientSide();
                    } catch (Exception e) {
                        /*
                         * Our client thread just died.
                         */
                        System.err.println("Client died...");
                        clientException = e;
                    }
                }
            };
            clientThread.start();
        } else {
            try {
                doClientSide();
            } catch (Exception e) {
                clientException = e;
            }
        }
    }
}
