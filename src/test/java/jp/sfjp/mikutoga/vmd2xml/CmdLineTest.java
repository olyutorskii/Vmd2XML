/*
 */

package jp.sfjp.mikutoga.vmd2xml;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class CmdLineTest {

    public CmdLineTest() {
    }

    /**
     * Test of parse method, of class CmdLine.
     */
    @Test
    public void testParse_StringArr() {
        System.out.println("parse");

        List<CmdLine> result;
        CmdLine line;

        result = CmdLine.parse();
        assertNotNull(result);
        assertTrue(result.isEmpty());

        result = CmdLine.parse("");
        assertEquals(1, result.size());
        line = result.get(0);
        assertNull(line.getOptSwitch());
        assertEquals(1, line.getOptArgs().size());
        assertEquals("", line.getOptArgs().get(0));

        result = CmdLine.parse("-h");
        assertEquals(1, result.size());
        line = result.get(0);
        assertEquals(OptSwitch.OPT_HELP, line.getOptSwitch());
        assertEquals(1, line.getOptArgs().size());
        assertEquals("-h", line.getOptArgs().get(0));

        result = CmdLine.parse("-i", "infile");
        assertEquals(1, result.size());
        line = result.get(0);
        assertEquals(OptSwitch.OPT_INFILE, line.getOptSwitch());
        assertEquals(2, line.getOptArgs().size());
        assertEquals("-i", line.getOptArgs().get(0));
        assertEquals("infile", line.getOptArgs().get(1));

        result = CmdLine.parse("-i");
        assertEquals(1, result.size());
        line = result.get(0);
        assertEquals(OptSwitch.OPT_INFILE, line.getOptSwitch());
        assertEquals(1, line.getOptArgs().size());
        assertEquals("-i", line.getOptArgs().get(0));

        result = CmdLine.parse("-h", "-i", "infile", "-f");
        assertEquals(3, result.size());

        line = result.get(0);
        assertEquals(OptSwitch.OPT_HELP, line.getOptSwitch());
        assertEquals(1, line.getOptArgs().size());
        assertEquals("-h", line.getOptArgs().get(0));

        line = result.get(1);
        assertEquals(OptSwitch.OPT_INFILE, line.getOptSwitch());
        assertEquals(2, line.getOptArgs().size());
        assertEquals("-i", line.getOptArgs().get(0));
        assertEquals("infile", line.getOptArgs().get(1));

        line = result.get(2);
        assertEquals(OptSwitch.OPT_FORCE, line.getOptSwitch());
        assertEquals(1, line.getOptArgs().size());
        assertEquals("-f", line.getOptArgs().get(0));

        return;
    }

    /**
     * Test of parse method, of class CmdLine.
     */
    @Test
    public void testParse_List() {
        System.out.println("parse");

        List<CmdLine> result;
        CmdLine line;

        result = CmdLine.parse(Arrays.asList("-i", "infile"));
        assertEquals(1, result.size());
        line = result.get(0);
        assertEquals(OptSwitch.OPT_INFILE, line.getOptSwitch());
        assertEquals(2, line.getOptArgs().size());
        assertEquals("-i", line.getOptArgs().get(0));
        assertEquals("infile", line.getOptArgs().get(1));

        return;
    }

}
