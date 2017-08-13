package test.javax.script;
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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
 *
 *
 * This is a dummy script engine implementation
 */
import java.io.Reader;
import java.io.StringReader;

import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.SimpleBindings;

import test.java.io.*;
import test.javax.script.*;

public class DummyScriptEngine extends AbstractScriptEngine {
    public Object eval(String str, ScriptContext ctx) {
        return eval(new StringReader(str), ctx);
    }

    public Object eval(Reader reader, ScriptContext ctx) {
        System.out.println("eval done!");
        return null;
    }

    public ScriptEngineFactory getFactory() {
        return new DummyScriptEngineFactory();
    }

    public Bindings createBindings() {
        return new SimpleBindings();
    }
}
