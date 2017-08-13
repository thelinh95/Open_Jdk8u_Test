package test.java.lang.System;
/*
 * Copyright (c) 2014, Oracle and/or its affiliates. All rights reserved.
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
 * This class tests to see if the system property java.version is properly
 * reinitialized after setting System.setProperties(null).
 *
 * @test
 * @bug 8030781
 * @summary Test for System.setProperties(null).
 */

public class SetPropertiesNull {

    public static void main(String args[]) {
        final String version = System.getProperty("java.version");
        System.setProperties(null);
        final String newVersion = System.getProperty("java.version");
        if (!version.equals(newVersion)) {
            throw new RuntimeException("java.version differs: '" + version + "'  '"
                               + newVersion + "'");
        }
    }
}