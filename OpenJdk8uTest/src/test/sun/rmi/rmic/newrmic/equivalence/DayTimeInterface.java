package test.sun.rmi.rmic.newrmic.equivalence;
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

// RMI Activation Functional Test

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.activation.ActivationException;
import java.rmi.activation.ActivationGroupID;
import java.rmi.activation.ActivationID;
import java.rmi.activation.UnknownObjectException;

import test.java.rmi.*;
import test.java.rmi.activation.*;

// DayTimeInterface

public interface DayTimeInterface extends Remote {

    public void ping() throws RemoteException;

    public java.util.Date getDayTime() throws java.rmi.RemoteException;

    public void exit() throws RemoteException;

    public ActivationID getActivationID() throws RemoteException;

    public ActivationGroupID getCurrentGroupID() throws RemoteException;

    public void inactive()
        throws RemoteException, UnknownObjectException, ActivationException;

    public void register()
        throws RemoteException, UnknownObjectException, ActivationException;

    public void unregister()
        throws RemoteException, UnknownObjectException, ActivationException;
}
