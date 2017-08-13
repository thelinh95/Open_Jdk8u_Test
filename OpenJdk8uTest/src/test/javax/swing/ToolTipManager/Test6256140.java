package test.javax.swing.ToolTipManager;
/*
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 6256140
 * @summary Esc key doesn't restore old value in JFormattedtextField when ToolTip is set
 * @author Alexander Potochkin
 * @run main Test6256140
 */

import sun.awt.SunToolkit;
import test.java.awt.*;
import test.javax.swing.*;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;

public class Test6256140 {

    private static volatile JFormattedTextField ft;

    private final static String initialText = "value";
    private final static JLabel toolTipLabel = new JLabel("tip");

    public static void main(String[] args) throws Exception {

        Robot robot = new Robot();
        robot.setAutoDelay(10);
        SunToolkit toolkit = (SunToolkit) Toolkit.getDefaultToolkit();

        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        toolkit.realSync();

        Point point = ft.getLocationOnScreen();
        robot.mouseMove(point.x, point.y);
        robot.mouseMove(point.x + 3, point.y + 3);

        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        toolkit.realSync();

        if (!isTooltipShowning()) {
            throw new RuntimeException("Tooltip is not shown");
        }

        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
        toolkit.realSync();

        if (isTooltipShowning()) {
            throw new RuntimeException("Tooltip must be hidden now");
        }

        if (isTextEqual()) {
            throw new RuntimeException("FormattedTextField must *not* cancel the updated value this time");
        }

        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
        toolkit.realSync();

        if (!isTextEqual()) {
            throw new RuntimeException("FormattedTextField must cancel the updated value");
        }
    }

    private static boolean isTooltipShowning() throws Exception {
        final boolean[] result = new boolean[1];

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                result[0] = toolTipLabel.isShowing();
            }
        });

        return result[0];
    }

    private static boolean isTextEqual() throws Exception {
        final boolean[] result = new boolean[1];

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                result[0] = initialText.equals(ft.getText());
            }
        });

        return result[0];
    }

    private static void createAndShowGUI() {
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
        ToolTipManager.sharedInstance().setInitialDelay(0);

        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        ft = new JFormattedTextField() {

            public JToolTip createToolTip() {
                JToolTip toolTip = super.createToolTip();
                toolTip.setLayout(new BorderLayout());
                toolTip.add(toolTipLabel);
                return toolTip;
            }
        };
        ft.setToolTipText("   ");
        ft.setValue(initialText);
        frame.add(ft);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        ft.requestFocus();
    }
}
