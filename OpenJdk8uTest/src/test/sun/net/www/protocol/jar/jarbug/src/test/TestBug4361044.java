package test.sun.net.www.protocol.jar.jarbug.src.test;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;

import test.java.io.*;
import test.java.net.*;
import test.java.util.jar.*;

/*
 * ResourceBundle from jar not found if jar exists in path
 * which has symbol !
 *
 * 3 votes
 *
 * Note: Execute "real" test in separate vm instance so that any locks
 * held on files will be released when this separate vm exits and the
 * invoking vm can clean up if necessary.
 */
public class TestBug4361044 extends JarTest
{
    public void run(String[] args) throws Exception {
        if (args.length == 0 ) {  // execute the test in another vm.
            System.out.println("Test: " + getClass().getName());
            Process process = Runtime.getRuntime().exec(javaCmd + " TestBug4361044 -test");

            BufferedReader isReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader esReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            Redirector outRedirector = new Redirector(isReader, System.out);
            Redirector errRedirector = new Redirector(esReader, System.err);

            (new Thread(outRedirector)).start();
            (new Thread(errRedirector)).start();

            process.waitFor();

            // Delete any remaining files from the test
            File testDir = new File(tmpdir + File.separator + getClass().getName());
            deleteRecursively(testDir);

            if (outRedirector.getHasReadData() || errRedirector.getHasReadData())
                throw new RuntimeException("Failed: No output should have been received from the process");

        } else {   // run the test.
            File tmp = createTempDir();
            try {
                File dir = new File(tmp, "dir!name");
                dir.mkdir();
                File testFile = copyResource(dir, "jar1.jar");
                URL[] urls = new URL[1];
                urls[0] = new URL("jar:" + testFile.toURL() + "!/");
                URLClassLoader loader = new URLClassLoader(urls);
                loader.loadClass("jar1.LoadResourceBundle").newInstance();
            } finally {
                deleteRecursively(tmp);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new TestBug4361044().run(args);
    }
}