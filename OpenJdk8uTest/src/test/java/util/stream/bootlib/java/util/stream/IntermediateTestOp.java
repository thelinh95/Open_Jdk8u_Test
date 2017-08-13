/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
package test.java.util.stream.bootlib.java.util.stream;

/**
 * A base type for test operations
 */
interface IntermediateTestOp<E_IN, E_OUT> {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static<T> AbstractPipeline chain(AbstractPipeline upstream,
                                            IntermediateTestOp<?, T> op) {
        if (op instanceof StatelessTestOp)
            return StatelessTestOp.chain(upstream, (StatelessTestOp) op);

        if (op instanceof StatefulTestOp)
            return StatefulTestOp.chain(upstream, (StatefulTestOp) op);

        throw new IllegalStateException("Unknown test op type: " + op.getClass().getName());
    }
}
