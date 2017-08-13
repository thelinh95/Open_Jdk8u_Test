package test.com.sun.jdi.sde;
import test.com.sun.jdi.*;
import test.com.sun.jdi.event.*;
import test.com.sun.jdi.request.*;
import test.java.util.*;

import java.io.File;

public class SourceDebugExtensionTest extends TestScaffold {
    ReferenceType targetClass;

    SourceDebugExtensionTest (String args[]) {
        super(args);
    }

    public static void main(String[] args)      throws Exception {
        testSetUp();
        new SourceDebugExtensionTest(args).startTests();
    }

    /********** test set-up **********/

    static void testSetUp() throws Exception {
        InstallSDE.install(new File(System.getProperty("test.classes", "."),
                                    "SourceDebugExtensionTarg.class"),
                           new File(System.getProperty("test.src", "."),
                                    "testString"));
    }

    /********** test core **********/

    protected void runTests() throws Exception {
        /*
         * Get to the top of main()
         * to determine targetClass
         */
        BreakpointEvent bpe = startToMain("SourceDebugExtensionTarg");
        targetClass = bpe.location().declaringType();

        if (!vm().canGetSourceDebugExtension()) {
            failure("FAIL: canGetSourceDebugExtension() is false");
        } else {
            println("canGetSourceDebugExtension() is true");
        }

        String expected = "An expected attribute string";
        String sde = targetClass.sourceDebugExtension();
        if (!sde.equals(expected)) {
            failure("FAIL: got '" + sde +
                    "' expected: '" + expected + "'");
        }

        /*
         * resume the target listening for events
         */
        listenUntilVMDisconnect();

        /*
         * deal with results of test
         * if anything has called failure("foo") testFailed will be true
         */
        if (!testFailed) {
            println("SourceDebugExtensionTest: passed");
        } else {
            throw new Exception("SourceDebugExtensionTest: failed");
        }
    }
}
