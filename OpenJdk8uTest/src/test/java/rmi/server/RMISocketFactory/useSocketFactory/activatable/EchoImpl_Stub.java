package test.java.rmi.server.RMISocketFactory.useSocketFactory.activatable;
import java.lang.Object;

/*
 * Copyright (c) 1998, 2008, Oracle and/or its affiliates. All rights reserved.
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

public final class EchoImpl_Stub
    extends test.java.rmi.server.RemoteStub
    implements Echo, test.java.rmi.Remote
{
    private static final long serialVersionUID = 2;

    private static test.java.lang.reflect.Method $method_echoNot_0;
    private static test.java.lang.reflect.Method $method_shutdown_1;

    static {
        try {
            $method_echoNot_0 = Echo.class.getMethod("echoNot", new test.java.lang.Class[] {byte[].class});
            $method_shutdown_1 = Echo.class.getMethod("shutdown", new test.java.lang.Class[] {});
        } catch (test.java.lang.NoSuchMethodException e) {
            throw new test.java.lang.NoSuchMethodError(
                "stub class initialization failed");
        }
    }

    // constructors
    public EchoImpl_Stub(test.java.rmi.server.RemoteRef ref) {
        super(ref);
    }

    // methods from remote interfaces

    // implementation of echoNot(byte[])
    public byte[] echoNot(byte[] $param_arrayOf_byte_1)
        throws test.java.rmi.RemoteException
    {
        try {
            Object $result = ref.invoke(this, $method_echoNot_0, new test.java.lang.Object[] {$param_arrayOf_byte_1}, -4295721514897591756L);
            return ((byte[]) $result);
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
        ref.invoke(this, $method_shutdown_1, null, -7207851917985848402L);
    }
}