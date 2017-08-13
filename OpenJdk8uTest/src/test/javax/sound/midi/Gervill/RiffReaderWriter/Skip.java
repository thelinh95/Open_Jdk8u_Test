package test.javax.sound.midi.Gervill.RiffReaderWriter;
/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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
   @summary Test RiffReader skip method */

import java.io.File;
import java.io.FileInputStream;

import com.sun.media.sound.*;

import test.javax.sound.sampled.*;

public class Skip {

    private static void assertEquals(Object a, Object b) throws Exception
    {
        if(!a.equals(b))
            throw new RuntimeException("assertEquals fails!");
    }

    public static void main(String[] args) throws Exception {
        RIFFWriter writer = null;
        RIFFReader reader = null;
        File tempfile = File.createTempFile("test",".riff");
        try
        {
            writer = new RIFFWriter(tempfile, "TEST");
            RIFFWriter chunk = writer.writeChunk("TSCH");
            chunk.write((byte)33);
            chunk.write((byte)44);
            writer.close();
            writer = null;
            FileInputStream fis = new FileInputStream(tempfile);
            reader = new RIFFReader(fis);
            RIFFReader readchunk = reader.nextChunk();
            reader.skip(1);
            assertEquals(readchunk.read(), 44);
            fis.close();
            reader = null;


        }
        finally
        {
            if(writer != null)
                writer.close();
            if(reader != null)
                reader.close();

            if(tempfile.exists())
                if(!tempfile.delete())
                    tempfile.deleteOnExit();
        }
    }
}