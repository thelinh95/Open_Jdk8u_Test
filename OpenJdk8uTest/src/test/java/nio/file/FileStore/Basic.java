package test.java.nio.file.FileStore;
/*
 * Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 4313887 6873621 6979526 7006126 7020517
 * @summary Unit test for java.nio.file.FileStore
 * @library ..
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserDefinedFileAttributeView;

import test.java.nio.file.*;
import test.java.nio.file.attribute.*;
import test.java.util.*;

public class Basic {

    static final long G = 1024L * 1024L * 1024L;

    public static void main(String[] args) throws IOException {
        Path dir = TestUtil.createTemporaryDirectory();
        try {
            doTests(dir);
        } finally {
            TestUtil.removeAll(dir);
        }
    }

    static void assertTrue(boolean okay) {
        if (!okay)
            throw new RuntimeException("Assertion failed");
    }

    static void checkWithin1GB(long value1, long value2) {
        long diff = Math.abs(value1 - value2);
        if (diff > G)
            throw new RuntimeException("values differ by more than 1GB");
    }

    static void doTests(Path dir) throws IOException {
        /**
         * Test: Directory should be on FileStore that is writable
         */
        assertTrue(!Files.getFileStore(dir).isReadOnly());

        /**
         * Test: Two files should have the same FileStore
         */
        Path file1 = Files.createFile(dir.resolve("foo"));
        Path file2 = Files.createFile(dir.resolve("bar"));
        FileStore store1 = Files.getFileStore(file1);
        FileStore store2 = Files.getFileStore(file2);
        assertTrue(store1.equals(store2));
        assertTrue(store2.equals(store1));
        assertTrue(store1.hashCode() == store2.hashCode());

        /**
         * Test: File and FileStore attributes
         */
        assertTrue(store1.supportsFileAttributeView("basic"));
        assertTrue(store1.supportsFileAttributeView(BasicFileAttributeView.class));
        assertTrue(store1.supportsFileAttributeView("posix") ==
            store1.supportsFileAttributeView(PosixFileAttributeView.class));
        assertTrue(store1.supportsFileAttributeView("dos") ==
            store1.supportsFileAttributeView(DosFileAttributeView.class));
        assertTrue(store1.supportsFileAttributeView("acl") ==
            store1.supportsFileAttributeView(AclFileAttributeView.class));
        assertTrue(store1.supportsFileAttributeView("user") ==
            store1.supportsFileAttributeView(UserDefinedFileAttributeView.class));

        /**
         * Test: Space atributes
         */
        File f = file1.toFile();
        long total = f.getTotalSpace();
        long free = f.getFreeSpace();
        long usable = f.getUsableSpace();

        // check values are "close"
        checkWithin1GB(total,  store1.getTotalSpace());
        checkWithin1GB(free,   store1.getUnallocatedSpace());
        checkWithin1GB(usable, store1.getUsableSpace());

        // get values by name
        checkWithin1GB(total,  (Long)store1.getAttribute("totalSpace"));
        checkWithin1GB(free,   (Long)store1.getAttribute("unallocatedSpace"));
        checkWithin1GB(usable, (Long)store1.getAttribute("usableSpace"));

        /**
         * Test: Enumerate all FileStores
         */
        FileStore prev = null;
        for (FileStore store: FileSystems.getDefault().getFileStores()) {
            System.out.format("%s (name=%s type=%s)\n", store, store.name(),
                store.type());

            // check space attributes are accessible
            store.getTotalSpace();
            store.getUnallocatedSpace();
            store.getUsableSpace();

            // two distinct FileStores should not be equal
            assertTrue(!store.equals(prev));
            prev = store;
        }
    }
}