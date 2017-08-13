package test.javax.security.auth.login.Configuration;
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

/*
 * @test
 * @bug 4464458
 * @summary ConfigFile.refresh should be synchronized
 *
 * @run main/othervm -Djava.security.auth.login.config=file:${test.src}/Synchronize.config Synchronize
 */

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

import test.javax.security.auth.login.*;

public class Synchronize extends Thread {

    private static final int loop = 500;

    public void run() {
        try {
            System.out.println("attempting to access configuration...");
            Configuration config = Configuration.getConfiguration();
            AppConfigurationEntry[] entries = config.getAppConfigurationEntry
                                                        ("Synchronize");
            config.refresh();
            System.out.println("done accessing configuration...");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
        try {
            Synchronize[] threads = new Synchronize[loop];
            for (int i = 0; i < loop; i++) {
                threads[i] = new Synchronize();
            }

            for (int i = 0; i < loop; i++) {
                threads[i].start();
            }

            for (int i = 0; i < loop; i++) {
                threads[i].join();
            }
            System.out.println("test passed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("test failed");
            throw new SecurityException(e.toString());
        }
    }
}
