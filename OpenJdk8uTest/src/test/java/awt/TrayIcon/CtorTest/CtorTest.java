package test.java.awt.TrayIcon.CtorTest;
/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
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
  @test
  @bug 6759726
  @summary TrayIcon constructor throws NPE instead of documented IAE
  @author Dmitry Cherepanov area=awt.tray
  @run main CtorTest
*/

/**
 * CtorTest.java
 *
 * summary:  TrayIcon ctor throws IAE if image is null
 */

import java.awt.SystemTray;
import java.awt.TrayIcon;

import test.java.awt.*;

public class CtorTest
{
    public static void main(String []s)
    {
        boolean isSupported = SystemTray.isSupported();
        if (isSupported) {
            try {
                TrayIcon tray = new TrayIcon(null);
            } catch(IllegalArgumentException e) {
                // ctor should throw IAE
            }
        }
    }
}