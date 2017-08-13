package test.java.awt.font.TextLayout;
/*
 * Copyright (c) 2005, 2007, Oracle and/or its affiliates. All rights reserved.
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
 * @summary verify cast in AttributeValues fails silently - regression
 * @bug 6603975
 */

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import test.java.awt.*;
import test.java.awt.font.*;
import test.java.awt.geom.*;
import test.java.text.*;
import test.java.util.*;

public class AttributeValuesCastTest
{
    public static void main (String [] args)
    {
        System.out.println ("java.version = " + System.getProperty ("java.version"));
        try {
            Map attributes = new HashMap();
            attributes.put("Something","Somethign Else");
            Font  f = new Font(attributes);
            System.out.println("PASS: was able to create font. ");
        } catch(Throwable t) {
            throw new RuntimeException("FAIL: caught "+t.toString());
        }
    }
}
