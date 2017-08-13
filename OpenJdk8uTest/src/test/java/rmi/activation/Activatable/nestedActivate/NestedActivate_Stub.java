package test.java.rmi.activation.Activatable.nestedActivate;
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

public final class NestedActivate_Stub
    extends test.java.rmi.server.RemoteStub
    implements ActivateMe, test.java.rmi.Remote
{
    private static final test.java.rmi.server.Operation[] operations = {
        new test.java.rmi.server.Operation("void ping()"),
        new test.java.rmi.server.Operation("void shutdown()"),
        new test.java.rmi.server.Operation("void unregister()")
    };

    private static final long interfaceHash = 4395146122524413703L;

    private static final long serialVersionUID = 2;

    private static boolean useNewInvoke;
    private static test.java.lang.reflect.Method $method_ping_0;
    private static test.java.lang.reflect.Method $method_shutdown_1;
    private static test.java.lang.reflect.Method $method_unregister_2;

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
            $method_ping_0 = ActivateMe.class.getMethod("ping", new test.java.lang.Class[] {});
            $method_shutdown_1 = ActivateMe.class.getMethod("shutdown", new test.java.lang.Class[] {});
            $method_unregister_2 = ActivateMe.class.getMethod("unregister", new test.java.lang.Class[] {});
        } catch (test.java.lang.NoSuchMethodException e) {
            useNewInvoke = false;
        }
    }

    // constructors
    public NestedActivate_Stub() {
        super();
    }
    public NestedActivate_Stub(test.java.rmi.server.RemoteRef ref) {
        super(ref);
    }

    // methods from remote interfaces

    // implementation of ping()
    public void ping()
        throws test.java.rmi.RemoteException
    {
        try {
            if (useNewInvoke) {
                ref.invoke(this, $method_ping_0, null, 5866401369815527589L);
            } else {
                test.java.rmi.server.RemoteCall call = ref.newCall((test.java.rmi.server.RemoteObject) this, operations, 0, interfaceHash);
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
            ref.invoke(this, $method_shutdown_1, null, -7207851917985848402L);
        } else {
            test.java.rmi.server.RemoteCall call = ref.newCall((test.java.rmi.server.RemoteObject) this, operations, 1, interfaceHash);
            ref.invoke(call);
            ref.done(call);
        }
    }

    // implementation of unregister()
    public void unregister()
        throws test.java.lang.Exception
    {
        if (useNewInvoke) {
            ref.invoke(this, $method_unregister_2, null, -5366864281862648102L);
        } else {
            test.java.rmi.server.RemoteCall call = ref.newCall((test.java.rmi.server.RemoteObject) this, operations, 2, interfaceHash);
            ref.invoke(call);
            ref.done(call);
        }
    }
}
