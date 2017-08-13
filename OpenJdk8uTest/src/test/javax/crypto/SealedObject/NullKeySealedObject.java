package test.javax.crypto.SealedObject;
/*
 * Copyright (c) 2005, 2007, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 6205732
 * @summary (spec) javax.crypto.SealedObject.getObject(Key, String): NPE
 *     is not specified
 * @author Brad R. Wetmore
 */
import test.java.security.*;
import test.javax.crypto.*;
import test.javax.crypto.spec.*;

import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class NullKeySealedObject {

    public static void main(String[] argv) throws Exception {

        Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
        SecretKey cipherKey = new SecretKeySpec(new byte[8], "DES");

        Key obj = new SecretKeySpec(new byte[8], "ANY");
        c.init(Cipher.ENCRYPT_MODE, cipherKey);
        SealedObject so = new SealedObject(obj, c);

        try {
            so.getObject((Key)null);
            throw new Exception("Sealed Object didn't throw an exception");
        } catch (NullPointerException e) {
            System.out.println("Got expected NullPointer");
        }

        try {
            so.getObject((Key)null, "ANY");
            throw new Exception("Sealed Object didn't throw an exception");
        } catch (NullPointerException e) {
            System.out.println("Got expected NullPointer");
        }
    }
}