package test.java.io.Serializable.unresolvedClassDesc;
/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4482471
 *
 * @clean Write Read Foo
 * @build Write Foo
 * @run main Write
 * @clean Write Foo
 * @build Read
 * @run main Read
 * @clean Read
 *
 * @summary Verify that even if an incoming ObjectStreamClass is not resolvable
 *          to a local class, the ObjectStreamClass object itself is still
 *          deserializable (without incurring a ClassNotFoundException).
 */

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

import test.java.io.*;

public class Write {
    public static void main(String[] args) throws Exception {
        ObjectOutputStream oout =
            new ObjectOutputStream(new FileOutputStream("tmp.ser"));
        ObjectStreamClass desc = ObjectStreamClass.lookup(Foo.class);
        Foo foo = new Foo();
        oout.writeObject(desc);
        oout.writeObject(new Object[]{ desc }); // test indirect references
        oout.writeObject(foo);
        oout.writeObject(new Object[]{ foo });
        oout.close();
    }
}
