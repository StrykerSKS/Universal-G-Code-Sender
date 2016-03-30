/*
    Copywrite 2012-2016 Will Winder

    This file is part of Universal Gcode Sender (UGS).

    UGS is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    UGS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with UGS.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.willwinder.universalgcodesender.utils;

import com.willwinder.universalgcodesender.types.GcodeCommand;
import com.willwinder.universalgcodesender.utils.GcodeStream;
import com.willwinder.universalgcodesender.utils.GcodeStreamReader;
import com.willwinder.universalgcodesender.utils.GcodeStreamWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author wwinder
 */
public class GcodeStreamTest {
    
    public static File createTempDirectory() throws IOException
    {
        final File temp;

        temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

        if(!(temp.delete()))
        {
            throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
        }

        if(!(temp.mkdir()))
        {
            throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
        }

        return (temp);
    }
    static File tempDir;

    @BeforeClass
    static public void setup() throws IOException {
        tempDir = createTempDirectory();
    }

    @AfterClass
    static public void teardown() throws IOException {
        FileUtils.forceDelete(tempDir);
    }

    /**
     * Writes 1,000,000 rows to a file then reads it back out.
     */
    @Test
    public void testGcodeStreamReadWrite() throws FileNotFoundException, IOException {
        int rows = 1000000;
        File f = new File(tempDir,"gcodeFile");
        try {
            try (GcodeStreamWriter gsw = new GcodeStreamWriter(f)) {
                for (int i = 0; i < rows; i++) {
                    gsw.addLine("Line " + i + " before", "Line " + i + " after", null, i);
                }
            }

            try (GcodeStreamReader gsr = new GcodeStreamReader(f)) {
                Assert.assertEquals(rows, gsr.getNumRows());

                int count = 0;
                while (gsr.getNumRowsRemaining() > 0) {
                    GcodeCommand gc = gsr.getNextCommand();
                    Assert.assertEquals("Line " + count + " after", gc.getCommandString());
                    Assert.assertEquals("", gc.getComment());
                    Assert.assertEquals(count, gc.getCommandNumber());
                    count++;
                    Assert.assertEquals(rows - count, gsr.getNumRowsRemaining());
                }

                Assert.assertEquals(rows, count);
            }
        } finally {
            FileUtils.forceDelete(f);
        }
    }
}
