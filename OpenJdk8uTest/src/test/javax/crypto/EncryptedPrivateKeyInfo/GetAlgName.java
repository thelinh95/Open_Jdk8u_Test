package test.javax.crypto.EncryptedPrivateKeyInfo;
/*
 * Copyright (c) 2003, 2007, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4941596
 * @summary Test the EncryptedPrivateKeyInfo.getAlgName(...) methods.
 * @author Valerie Peng
 */
import java.security.AlgorithmParameters;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import test.java.io.*;
import test.java.nio.*;
import test.java.security.*;
import test.java.security.spec.*;
import test.java.util.*;
import test.javax.crypto.*;
import test.javax.crypto.spec.*;

public class GetAlgName {
    private static String PASSWD = "password";

    private static final String[] ALGOS = {
        "PBEWithMD5AndDES", "PBEWithSHA1AndDESede", "PBEWithSHA1AndRC2_40"
    };
    private static final byte[] BYTES = new byte[20];

    public static void main(String[] argv) throws Exception {
        boolean status = true;
        PBEKeySpec ks = new PBEKeySpec(PASSWD.toCharArray());
        EncryptedPrivateKeyInfo epki;
        for (int i = 0; i < ALGOS.length; i++) {
            String algo = ALGOS[i];
            // generate AlgorithmParameters object
            SecretKeyFactory skf =
                SecretKeyFactory.getInstance(algo, "SunJCE");
            SecretKey key = skf.generateSecret(ks);
            Cipher c = Cipher.getInstance(algo, "SunJCE");
            c.init(Cipher.ENCRYPT_MODE, key);
            c.doFinal(BYTES); // force the parameter generation if not already

            AlgorithmParameters ap = c.getParameters();
            epki = new EncryptedPrivateKeyInfo(ap, BYTES);
            if (!epki.getAlgName().equalsIgnoreCase(algo)) {
                System.out.println("...expect: " + algo);
                System.out.println("...got: " + epki.getAlgName());
                status = false;
            }
        }
        if (!status) {
            throw new Exception("One or more tests failed");
        } else {
            System.out.println("Test Passed");
        }
    }
}
