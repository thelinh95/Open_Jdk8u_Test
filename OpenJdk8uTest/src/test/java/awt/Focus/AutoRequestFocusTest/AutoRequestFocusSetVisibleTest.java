package test.java.awt.Focus.AutoRequestFocusTest;
/*
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
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
  @bug       6187066
  @summary   Tests the Window.autoRequestFocus property for the Window.setVisible() method.
  @author    anton.tarasov: area=awt.focus
  @library    ../../regtesthelpers
  @build      Util
  @run       main AutoRequestFocusSetVisibleTest
*/

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.concurrent.atomic.AtomicBoolean;
import java.lang.reflect.InvocationTargetException;

import test.java.awt.*;
import test.java.awt.event.*;
import test.java.awt.regtesthelpers.Util;

public class AutoRequestFocusSetVisibleTest extends Applet {
    static Frame focusedFrame;
    static Button focusOwner;
    static Frame frame;
    static Button frameButton;
    static Frame frame2;
    static Button frameButton2;
    static Window window;
    static Button winButton;
    static Window ownedWindow;
    static Button ownWinButton;
    static Dialog ownedDialog;
    static Button ownDlgButton;
    static Dialog dialog;
    static Button dlgButton;

    static String toolkitClassName;
    static Robot robot = Util.createRobot();

    public static void main(String[] args) {
        AutoRequestFocusSetVisibleTest app = new AutoRequestFocusSetVisibleTest();
        app.init();
        app.start();
    }

    public void init() {
        // Create instructions for the user here, as well as set up
        // the environment -- set the layout manager, add buttons,
        // etc.
        this.setLayout (new BorderLayout ());
        Sysout.createDialogWithInstructions(new String[]
            {"This is an automatic test. Simply wait until it is done."
            });
        toolkitClassName = Toolkit.getDefaultToolkit().getClass().getName();
    }

    void recreateGUI() {
        if (focusedFrame != null) {
            focusedFrame.dispose();
            frame.dispose();
            frame2.dispose();
            window.dispose();
            ownedWindow.dispose();
            ownedDialog.dispose();
            dialog.dispose();
        }

        focusedFrame = new Frame("Base Frame");
        focusOwner = new Button("button");

        frame = new Frame("Test Frame");
        frameButton = new Button("button");

        frame2 = new Frame("Test Frame");
        frameButton2 = new Button("button");

        window = new Window(focusedFrame);
        winButton = new Button("button");

        ownedWindow = new Window(frame) {
                /*
                 * When 'frame' is shown along with the 'ownedWindow'
                 * (i.e. showWithParent==true) then it can appear
                 * that the 'ownedWindow' is shown too early and
                 * it can't be focused due to its owner can't be
                 * yet activated. So, to avoid this race, we pospone
                 * a little the showing of the 'ownedWindow'.
                 */
                public void show() {
                    robot.delay(100);
                    super.show();
                }
            };
        ownWinButton = new Button("button");

        ownedDialog = new Dialog(frame2);
        ownDlgButton = new Button("button");

        dialog = new Dialog(focusedFrame, "Test Dialog");
        dlgButton = new Button("button");

        focusedFrame.add(focusOwner);
        focusedFrame.setBounds(100, 100, 300, 300);

        frame.setBounds(140, 140, 220, 220);
        frame.add(frameButton);

        frame2.setBounds(140, 140, 220, 220);
        frame2.add(frameButton2);

        window.setBounds(140, 140, 220, 220);
        window.add(winButton);

        ownedWindow.setBounds(180, 180, 140, 140);
        ownedWindow.add(ownWinButton);

        ownedDialog.setBounds(180, 180, 140, 140);
        ownedDialog.add(ownDlgButton);

        dialog.setBounds(140, 140, 220, 220);
        dialog.add(dlgButton);
    }

    public void start() {

        ///////////////////////////////////////////////////////
        // 1. Show Frame with owned modal Dialog without delay.
        //    Check that the Dialog takes focus.
        ///////////////////////////////////////////////////////

        recreateGUI();

        Sysout.println("Stage 1 in progress...");

        dialog.setModal(true);
        dialog.setAutoRequestFocus(false);
        setVisible(focusedFrame, true);

        TestHelper.invokeLaterAndWait(new Runnable() {
                public void run() {
                    dialog.setVisible(true);
                }
            }, robot);

        if (focusOwner.hasFocus()) {
            throw new TestFailedException("the modal dialog must gain focus but it didn't!");
        }
        setVisible(dialog, false);

        //////////////////////////////////////////////////
        // 2. Show Frame, activate, auto hide, auto show.
        //    Check that the Frame takes focus.
        //////////////////////////////////////////////////

        recreateGUI();

        Sysout.println("Stage 2 in progress...");

        setVisible(focusedFrame, false);

        focusedFrame.setAutoRequestFocus(false);
        setVisible(focusedFrame, true);

        Util.clickOnTitle(focusedFrame, robot);
        Util.waitForIdle(robot);

        if (!focusedFrame.isFocused()) {
            throw new Error("Test error: the frame couldn't be focused.");
        }

        focusedFrame.setExtendedState(Frame.ICONIFIED);
        Util.waitForIdle(robot);
        focusedFrame.setExtendedState(Frame.NORMAL);
        Util.waitForIdle(robot);

        if (!focusedFrame.isFocused()) {
            throw new TestFailedException("the restored frame must gain focus but it didn't!");
        }


        ////////////////////////
        // 3.1 Show Frame normal.
        ////////////////////////

        recreateGUI();

        test("Stage 3.1 in progress...", frame, frameButton);


        // 3.2. Show Frame maximized both.
        /////////////////////////////////

        if (!Toolkit.getDefaultToolkit().isFrameStateSupported(Frame.MAXIMIZED_BOTH)) {
            System.out.println("Stage 3.2: Frame.MAXIMIZED_BOTH not supported. Skipping.");
        } else {
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);

            test("Stage 3.2 in progress...", frame, frameButton);
        }


        // 3.3. Show Frame maximized vertically.
        ///////////////////////////////////////

        if (!Toolkit.getDefaultToolkit().isFrameStateSupported(Frame.MAXIMIZED_VERT)) {
            System.out.println("Stage 3.3: Frame.MAXIMIZED_VERT not supported. Skipping.");
        } else {
            frame.setExtendedState(Frame.MAXIMIZED_VERT);

            test("Stage 3.3 in progress...", frame, frameButton);
        }


        // 3.4. Show Frame maximized horizontally.
        /////////////////////////////////////////

        if (!Toolkit.getDefaultToolkit().isFrameStateSupported(Frame.MAXIMIZED_HORIZ)) {
            System.out.println("Stage 3.4: Frame.MAXIMIZED_HORIZ not supported. Skipping.");
        } else {
            frame.setExtendedState(Frame.MAXIMIZED_HORIZ);

            test("Stage 3.4 in progress...", frame, frameButton);
        }


        // 3.5. Show Frame iconified.
        ////////////////////////////

        if (!Toolkit.getDefaultToolkit().isFrameStateSupported(Frame.ICONIFIED)) {
            System.out.println("Stage 3.5: Frame.ICONIFIED not supported. Skipping.");
        } else {
            frame.setExtendedState(Frame.ICONIFIED);

            test("Stage 3.5 in progress...", frame, frameButton);
        }


        ///////////////////
        // 4.1 Show Window.
        ///////////////////
        recreateGUI();
        test("Stage 4.1 in progress...", window, winButton);


        // 4.2 Show Dialog.
        //////////////////

        test("Stage 4.2 in progress...", dialog, dlgButton);


        // 4.3. Show modal Dialog.
        /////////////////////////

        dialog.setModal(true);
        test("Stage 4.3 in progress...", dialog, dlgButton, true);


        ///////////////////////////////////
        // 5.1 Show Frame with owned Window.
        ///////////////////////////////////

        // On Windows, an owned Window will not be focused on its showing
        // if the owner is not currently active.
        if ("sun.awt.windows.WToolkit".equals(toolkitClassName)) {
            Sysout.println("Stage 5.1 - Skiping.");
        } else {
            setVisible(ownedWindow, true);
            setVisible(frame, false); // 'ownedWindow' will be shown along with the owner.

            test("Stage 5.1 in progress...", frame, ownedWindow, ownWinButton, true);
        }


        // 5.2 Show Frame with owned Dialog.
        ///////////////////////////////////

        setVisible(ownedDialog, true);
        setVisible(frame2, false); // 'ownedDialog' will be shown along with the owner.

        test("Stage 5.2 in progress...", frame2, ownedDialog, ownDlgButton, true);


        ///////////////////////////////////
        // 6. Show unblocking modal Dialog.
        ///////////////////////////////////

        if ("sun.awt.motif.MToolkit".equals(toolkitClassName)) {
            Sysout.println("Stage 6 - Skiping.");
        } else {
            Sysout.println("Stage 6 in progress...");

            // ---
            // Testing the bug of activating invisible modal Dialog (awt_Window::SetAndActivateModalBlocker).
            // Having some window not excluded from modality, so that it would be blocked.
            Frame f = new Frame("Aux. Frame");
            f.setSize(100, 100);
            setVisible(f, true);
            // ---

            setVisible(focusedFrame, true);
            if (!focusOwner.hasFocus()) {
                Util.clickOnComp(focusOwner, robot);
                Util.waitForIdle(robot);
                if (!focusOwner.hasFocus()) {
                    throw new Error("Test error: the frame couldn't be focused.");
                }
            }

            dialog.setModal(true);
            dialog.setAutoRequestFocus(false);
            focusedFrame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

            TestHelper.invokeLaterAndWait(new Runnable() {
                    public void run() {
                        dialog.setVisible(true);
                    }
                }, robot);

            if (dialog.isFocused()) {
                throw new TestFailedException("the unblocking dialog shouldn't gain focus but it did!");
            }
            setVisible(dialog, false);
        }

        Sysout.println("Test passed.");
    }

    /*
     * @param msg notifies test stage number
     * @param showWindow a window to show/test (if ownedWindow == null)
     * @param ownedWindow an owned window to show/test, or null if showWindow should be tested
     * @param clickButton a button of the window (owner or owned) expected to be on the top of stack order
     * @param shouldFocusChange true the test window should gain focus
     */
    void test(String msg, final Window showWindow, Window ownedWindow, final Button clickButton, boolean shouldFocusChange) {
        Window testWindow = (ownedWindow == null ? showWindow : ownedWindow);

        Sysout.println(msg);

        if (showWindow.isVisible()) {
            showWindow.dispose();
            Util.waitForIdle(robot);
        }
        if (!focusedFrame.isVisible()) {
            setVisible(focusedFrame, true);
        }
        if (!focusOwner.hasFocus()) {
            Util.clickOnComp(focusOwner, robot);
            Util.waitForIdle(robot);
            if (!focusOwner.hasFocus()) {
                throw new Error("Test error: the frame couldn't be focused.");
            }
        }

        //////////////////////////////////////////
        // Test focus change on showing the window
        //////////////////////////////////////////

        final Runnable showAction = new Runnable() {
                public void run() {
                    showWindow.setAutoRequestFocus(false);
                    showWindow.setVisible(true);
                }
            };

        final Runnable trackerAction = new Runnable() {
                public void run() {
                    if (showWindow instanceof Dialog && ((Dialog)showWindow).isModal()) {
                        TestHelper.invokeLaterAndWait(showAction, robot);
                    } else {
                        showAction.run();
                    }
                }
            };

        if (shouldFocusChange) {
            trackerAction.run();
            Util.waitForIdle(robot);

            if (!testWindow.isFocused()) {
                throw new TestFailedException("the window must gain focus but it didn't!");
            }

        } else if (TestHelper.trackFocusChangeFor(trackerAction, robot)) {
            throw new TestFailedException("the window shouldn't gain focus but it did!");
        }


        ////////////////////////////////////////////
        // Test that the window was shown on the top.
        // Test that it can be focused.
        ////////////////////////////////////////////

        if (!(testWindow instanceof Frame) ||
            ((Frame)testWindow).getExtendedState() != Frame.ICONIFIED)
        {
            boolean performed = Util.trackActionPerformed(clickButton, new Runnable() {
                    public void run() {
                        /*
                         * If 'showWindow' is not on the top then
                         * 'focusOwner' button completely overlaps 'clickButton'
                         * and we won't catch the action.
                         */
                        Util.clickOnComp(clickButton, robot);
                    }
                }, 1000, false);

            if (!performed) {
                // In case of loosing ACTION_PERFORMED, try once more.
                Sysout.println("(ACTION_EVENT was not generated. One more attemp.)");
                performed = Util.trackActionPerformed(clickButton, new Runnable() {
                        public void run() {
                            Util.clickOnComp(clickButton, robot);
                        }
                    }, 1000, false);

                if (!performed) {
                    throw new TestFailedException("the window shown is not on the top!");
                }
            }
        }

        recreateGUI();
    }

    void test(String msg, final Window showWindow, Button clickButton) {
        test(msg, showWindow, null, clickButton, false);
    }
    void test(String msg, final Window showWindow, Button clickButton, boolean shouldFocusChange) {
        test(msg, showWindow, null, clickButton, shouldFocusChange);
    }
    void test(String msg, final Window showWindow, Window ownedWindow, Button clickButton) {
        test(msg, showWindow, ownedWindow, clickButton, false);
    }

    private static void setVisible(Window w, boolean b) {
        w.setVisible(b);
        try {
            Util.waitForIdle(robot);
        } catch (RuntimeException rte) { // InfiniteLoop
            rte.printStackTrace();
        }
        robot.delay(200);
    }
}

class TestFailedException extends RuntimeException {
    TestFailedException(String msg) {
        super("Test failed: " + msg);
    }
}

/****************************************************
 Standard Test Machinery
 DO NOT modify anything below -- it's a standard
  chunk of code whose purpose is to make user
  interaction uniform, and thereby make it simpler
  to read and understand someone else's test.
 ****************************************************/

/**
 This is part of the standard test machinery.
 It creates a dialog (with the instructions), and is the interface
  for sending text messages to the user.
 To print the instructions, send an array of strings to Sysout.createDialog
  WithInstructions method.  Put one line of instructions per array entry.
 To display a message for the tester to see, simply call Sysout.println
  with the string to be displayed.
 This mimics System.out.println but works within the test harness as well
  as standalone.
 */

class Sysout
{
    static TestDialog dialog;

    public static void createDialogWithInstructions( String[] instructions )
    {
        dialog = new TestDialog( new Frame(), "Instructions" );
        dialog.printInstructions( instructions );
//        dialog.setVisible(true);
        println( "Any messages for the tester will display here." );
    }

    public static void createDialog( )
    {
        dialog = new TestDialog( new Frame(), "Instructions" );
        String[] defInstr = { "Instructions will appear here. ", "" } ;
        dialog.printInstructions( defInstr );
//        dialog.setVisible(true);
        println( "Any messages for the tester will display here." );
    }


    public static void printInstructions( String[] instructions )
    {
        dialog.printInstructions( instructions );
    }


    public static void println( String messageIn )
    {
        dialog.displayMessage( messageIn );
    }

}// Sysout  class

/**
  This is part of the standard test machinery.  It provides a place for the
   test instructions to be displayed, and a place for interactive messages
   to the user to be displayed.
  To have the test instructions displayed, see Sysout.
  To have a message to the user be displayed, see Sysout.
  Do not call anything in this dialog directly.
  */
class TestDialog extends Dialog
{

    TextArea instructionsText;
    TextArea messageText;
    int maxStringLength = 80;

    //DO NOT call this directly, go through Sysout
    public TestDialog( Frame frame, String name )
    {
        super( frame, name );
        int scrollBoth = TextArea.SCROLLBARS_BOTH;
        instructionsText = new TextArea( "", 15, maxStringLength, scrollBoth );
        add( "North", instructionsText );

        messageText = new TextArea( "", 5, maxStringLength, scrollBoth );
        add("Center", messageText);

        pack();

//        setVisible(true);
    }// TestDialog()

    //DO NOT call this directly, go through Sysout
    public void printInstructions( String[] instructions )
    {
        //Clear out any current instructions
        instructionsText.setText( "" );

        //Go down array of instruction strings

        String printStr, remainingStr;
        for( int i=0; i < instructions.length; i++ )
        {
            //chop up each into pieces maxSringLength long
            remainingStr = instructions[ i ];
            while( remainingStr.length() > 0 )
            {
                //if longer than max then chop off first max chars to print
                if( remainingStr.length() >= maxStringLength )
                {
                    //Try to chop on a word boundary
                    int posOfSpace = remainingStr.
                        lastIndexOf( ' ', maxStringLength - 1 );

                    if( posOfSpace <= 0 ) posOfSpace = maxStringLength - 1;

                    printStr = remainingStr.substring( 0, posOfSpace + 1 );
                    remainingStr = remainingStr.substring( posOfSpace + 1 );
                }
                //else just print
                else
                {
                    printStr = remainingStr;
                    remainingStr = "";
                }

                instructionsText.append( printStr + "\n" );

            }// while

        }// for

    }//printInstructions()

    //DO NOT call this directly, go through Sysout
    public void displayMessage( String messageIn )
    {
        messageText.append( messageIn + "\n" );
        System.out.println(messageIn);
    }

}// TestDialog  class
