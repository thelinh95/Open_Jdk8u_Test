package test.java.lang.annotation.repeatingAnnotations;
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

/*
 * @test
 * @bug 8019420
 * @summary Repeatable non-inheritable annotation types are mishandled by Core Reflection
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import test.java.lang.annotation.*;
import test.java.lang.reflect.*;
import test.java.util.*;

public class NonInheritableContainee {

    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(InheritedAnnotationContainer.class)
    @interface NonInheritedAnnotationRepeated {
        String name();
    }

    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    @interface InheritedAnnotationContainer {
        NonInheritedAnnotationRepeated[] value();
    }

    @NonInheritedAnnotationRepeated(name="A")
    @NonInheritedAnnotationRepeated(name="B")
    class Parent {}
    class Sample extends Parent {}


    public static void main(String[] args) {

        Annotation[] anns = Sample.class.getAnnotationsByType(
                NonInheritedAnnotationRepeated.class);

        if (anns.length != 0)
            throw new RuntimeException("Non-@Inherited containees should not " +
                    "be inherited even though its container is @Inherited.");
    }
}
