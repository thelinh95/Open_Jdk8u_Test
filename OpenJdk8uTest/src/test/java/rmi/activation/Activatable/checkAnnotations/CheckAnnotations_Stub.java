package test.java.rmi.activation.Activatable.checkAnnotations;
/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
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

// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

public final class CheckAnnotations_Stub
    extends test.java.rmi.server.RemoteStub
    implements MyRMI, test.java.rmi.Remote
{
    private static final test.java.rmi.server.Operation[] operations = {
        new test.java.rmi.server.Operation("void printErr(java.lang.String)"),
        new test.java.rmi.server.Operation("void printOut(java.lang.String)"),
        new test.java.rmi.server.Operation("void shutdown()")
    };

    private static final long interfaceHash = -3955951123118841923L;

    private static final long serialVersionUID = 2;

    private static boolean useNewInvoke;
    private static test.java.lang.reflect.Method $method_printErr_0;
    private static test.java.lang.reflect.Method $method_printOut_1;
    private static test.java.lang.reflect.Method $method_shutdown_2;

    static {
        try {
            test.java.rmi.server.RemoteRef.class.getMethod("invoke",
                new test.java.lang.Class[] {
                    test.java.rmi.Remote.class,
                    test.java.lang.reflect.Method.class,
                    test.java.lang.Object[].class,
                    long.class
                });
            useNewInvoke = true;
            $method_printErr_0 = MyRMI.class.getMethod("printErr", new test.java.lang.Class[] {test.java.lang.String.class});
            $method_printOut_1 = MyRMI.class.getMethod("printOut", new test.java.lang.Class[] {test.java.lang.String.class});
            $method_shutdown_2 = MyRMI.class.getMethod("shutdown", new test.java.lang.Class[] {});
        } catch (test.java.lang.NoSuchMethodException e) {
            useNewInvoke = false;
        }
    }

    // constructors
    public CheckAnnotations_Stub() {
        super();
    }
    public CheckAnnotations_Stub(test.java.rmi.server.RemoteRef ref) {
        super(ref);
    }

    // methods from remote interfaces

    // implementation of printErr(String)
    public void printErr(test.java.lang.String $param_String_1)
        throws test.java.rmi.RemoteException
    {
        try {
            if (useNewInvoke) {
                ref.invoke(this, $method_printErr_0, new test.java.lang.Object[] {$param_String_1}, 1120261287704800747L);
            } else {
                test.java.rmi.server.RemoteCall call = ref.newCall((test.java.rmi.server.RemoteObject) this, operations, 0, interfaceHash);
                try {
                    test.java.io.ObjectOutput out = call.getOutputStream();
                    out.writeObject($param_String_1);
                } catch (test.java.io.IOException e) {
                    throw new test.java.rmi.MarshalException("error marshalling arguments", e);
                }
                ref.invoke(call);
                ref.done(call);
            }
        } catch (test.java.lang.RuntimeException e) {
            throw e;
        } catch (test.java.rmi.RemoteException e) {
            throw e;
        } catch (test.java.lang.Exception e) {
            throw new test.java.rmi.UnexpectedException("undeclared checked exception", e);
        }
    }

    // implementation of printOut(String)
    public void printOut(test.java.lang.String $param_String_1)
        throws test.java.rmi.RemoteException
    {
        try {
            if (useNewInvoke) {
                ref.invoke(this, $method_printOut_1, new test.java.lang.Object[] {$param_String_1}, -7517735248176918178L);
            } else {
                test.java.rmi.server.RemoteCall call = ref.newCall((test.java.rmi.server.RemoteObject) this, operations, 1, interfaceHash);
                try {
                    test.java.io.ObjectOutput out = call.getOutputStream();
                    out.writeObject($param_String_1);
                } catch (test.java.io.IOException e) {
                    throw new test.java.rmi.MarshalException("error marshalling arguments", e);
                }
                ref.invoke(call);
                ref.done(call);
            }
        } catch (test.java.lang.RuntimeException e) {
            throw e;
        } catch (test.java.rmi.RemoteException e) {
            throw e;
        } catch (test.java.lang.Exception e) {
            throw new test.java.rmi.UnexpectedException("undeclared checked exception", e);
        }
    }

    // implementation of shutdown()
    public void shutdown()
        throws test.java.lang.Exception
    {
        if (useNewInvoke) {
            ref.invoke(this, $method_shutdown_2, null, -7207851917985848402L);
        } else {
            test.java.rmi.server.RemoteCall call = ref.newCall((test.java.rmi.server.RemoteObject) this, operations, 2, interfaceHash);
            ref.invoke(call);
            ref.done(call);
        }
    }
}
