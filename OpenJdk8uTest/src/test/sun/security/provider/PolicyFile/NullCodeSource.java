package test.sun.security.provider.PolicyFile;
/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 7147830
 * @summary PolicyFile.getPermissions(CodeSource) should return
 *          empty permissions when passed in null code source
 */

import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Policy;

public class NullCodeSource {
    public static void main(String[] args) throws Exception {
        Policy policy = Policy.getPolicy();
        PermissionCollection perms = policy.getPermissions((CodeSource)null);
        if (perms.elements().hasMoreElements()) {
            System.err.println(perms);
            throw new Exception("PermissionCollection is not empty");
        }
    }
}