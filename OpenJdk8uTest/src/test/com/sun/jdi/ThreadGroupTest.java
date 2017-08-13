package test.com.sun.jdi;
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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

import test.com.sun.jdi.*;
import test.com.sun.jdi.connect.*;
import test.com.sun.jdi.event.*;
import test.com.sun.jdi.request.*;
import test.java.util.*;

    /********** target program **********/

class ThreadGroupTarg {
    public static void main(String[] args){
        System.out.println("Howdy!");
        System.out.println("Goodbye from ThreadGroupTarg!");
    }
}

    /********** test program **********/

public class ThreadGroupTest extends TestScaffold {
    ReferenceType targetClass;
    ThreadReference mainThread;

    // Helper thread to fetch VirtualMachineManager
    static class Fetcher implements Runnable {
        public void run() {
            Bootstrap.virtualMachineManager();
        }
    }

    ThreadGroupTest (String args[]) {
        super(args);
    }

    public static void main(String[] args)      throws Exception {
        // create a random thread group, and run a thread in that group to
        // fetch the VirtualMachineManager
        ThreadGroup tg = new ThreadGroup("Gus");
        Fetcher fetcher = new Fetcher();
        Thread thr = new Thread(tg, fetcher);
        thr.start();
        try {
            thr.join();
        } catch (InterruptedException x) { }
        // now destroy the thread group
        tg.destroy();

        // run the test
        new ThreadGroupTest(args).startTests();
    }

    /********** test core **********/

    protected void runTests() throws Exception {

        // run the target until it completes
        startToMain("ThreadGroupTarg");
        listenUntilVMDisconnect();

        /*
         * deal with results of test
         * if anything has called failure("foo") testFailed will be true
         */
        if (!testFailed) {
            println("ThreadGroupTest: passed");
        } else {
            throw new Exception("ThreadGroupTest: failed");
        }
    }
}
