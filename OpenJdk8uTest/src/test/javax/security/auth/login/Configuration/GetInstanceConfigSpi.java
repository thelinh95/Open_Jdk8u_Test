package test.javax.security.auth.login.Configuration;
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

import java.security.AccessController;
import java.security.Configuration;
import java.security.PrivilegedAction;
import java.security.URIParameter;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.ConfigurationSpi;

import com.sun.security.auth.login.ConfigFile;

import test.com.sun.security.auth.login.*;
import test.java.security.*;
import test.javax.security.auth.login.*;

public class GetInstanceConfigSpi extends ConfigurationSpi {

    private Configuration c;

    public GetInstanceConfigSpi(final Configuration.Parameters params) {

        c = AccessController.doPrivileged
            (new PrivilegedAction<Configuration>() {
            public Configuration run() {
                if (params instanceof URIParameter) {
                    URIParameter uriParam = (URIParameter)params;
                    return new ConfigFile(uriParam.getURI());
                }
                return new ConfigFile();
            }
        });
    }

    public AppConfigurationEntry[] engineGetAppConfigurationEntry(String name) {
        return c.getAppConfigurationEntry(name);
    }
}