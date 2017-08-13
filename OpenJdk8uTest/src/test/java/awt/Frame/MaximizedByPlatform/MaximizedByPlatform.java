package test.java.awt.Frame.MaximizedByPlatform;
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 8026143
 * @summary [macosx] Maximized state could be inconsistent between peer and frame
 * @author Petr Pchelko
 * @run main MaximizedByPlatform
 */

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

import sun.awt.OSInfo;
import sun.awt.SunToolkit;
import test.java.awt.*;

public class MaximizedByPlatform {
    private static Frame frame;
    private static Rectangle availableScreenBounds;

    public static void main(String[] args) {
        if (OSInfo.getOSType() != OSInfo.OSType.MACOSX) {
            // Test only for macosx. Pass
            return;
        }

        availableScreenBounds = getAvailableScreenBounds();

        // Test 1. The maximized state is set in setBounds
        try {
            frame = new Frame();
            frame.setBounds(100, 100, 100, 100);
            frame.setVisible(true);

            ((SunToolkit)Toolkit.getDefaultToolkit()).realSync();

            frame.setBounds(availableScreenBounds.x, availableScreenBounds.y,
                    availableScreenBounds.width, availableScreenBounds.height);

            ((SunToolkit)Toolkit.getDefaultToolkit()).realSync();

            if (frame.getExtendedState() != Frame.MAXIMIZED_BOTH) {
                throw new RuntimeException("Maximized state was not set for frame in setBounds");
            }
        } finally {
            frame.dispose();
        }


        // Test 2. The maximized state is set in setVisible
        try {
            frame = new Frame();
            frame.setBounds(availableScreenBounds.x, availableScreenBounds.y,
                    availableScreenBounds.width + 100, availableScreenBounds.height);
            frame.setVisible(true);

            ((SunToolkit)Toolkit.getDefaultToolkit()).realSync();

            if (frame.getExtendedState() != Frame.MAXIMIZED_BOTH) {
                throw new RuntimeException("Maximized state was not set for frame in setVisible");
            }
        } finally {
            frame.dispose();
        }
    }

    private static Rectangle getAvailableScreenBounds() {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final GraphicsEnvironment graphicsEnvironment =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice graphicsDevice =
                graphicsEnvironment.getDefaultScreenDevice();

        final Dimension screenSize = toolkit.getScreenSize();
        final Insets screenInsets = toolkit.getScreenInsets(
                graphicsDevice.getDefaultConfiguration());

        final Rectangle availableScreenBounds = new Rectangle(screenSize);

        availableScreenBounds.x += screenInsets.left;
        availableScreenBounds.y += screenInsets.top;
        availableScreenBounds.width -= (screenInsets.left + screenInsets.right);
        availableScreenBounds.height -= (screenInsets.top + screenInsets.bottom);
        return availableScreenBounds;
    }
}
