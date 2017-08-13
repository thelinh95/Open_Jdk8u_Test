package test.java.nio.charset.CharsetDecoder;
/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4400697
 * @summary Ensure that CharsetDecoder.decode throws BUE on empty input
 */

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import test.java.nio.*;
import test.java.nio.charset.*;


public class EmptyInput {

    public static void main(String[] args) throws Exception {
        ByteBuffer bb = ByteBuffer.allocate(10);
        bb.flip();
        CharsetDecoder cd = Charset.forName("US-ASCII").newDecoder();
        try {
            cd.decode(bb, CharBuffer.allocate(10), true).throwException();
        } catch (BufferUnderflowException x) {
            System.err.println("BufferUnderflowException thrown as expected");
            return;
        }
        throw new Exception("BufferUnderflowException not thrown");
    }

}
