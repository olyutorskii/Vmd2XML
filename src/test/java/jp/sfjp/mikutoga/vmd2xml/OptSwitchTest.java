/*
 */

package jp.sfjp.mikutoga.vmd2xml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class OptSwitchTest {

    public OptSwitchTest() {
    }

    /**
     * Test of values method, of class OptSwitch.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        assertEquals(10, OptSwitch.values().length);
        return;
    }

    /**
     * Test of valueOf method, of class OptSwitch.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        assertSame(OptSwitch.OPT_HELP, OptSwitch.valueOf("OPT_HELP"));
        return;
    }

    /**
     * Test of getConsoleHelp method, of class OptSwitch.
     */
    @Test
    public void testGetConsoleHelp() {
        System.out.println("getConsoleHelp");

        String help = OptSwitch.getConsoleHelp();

        assertNotNull(help);
        assertFalse(help.isEmpty());

        return;
    }

    /**
     * Test of parse method, of class OptSwitch.
     */
    @Test
    public void testParse() {
        System.out.println("parse");

        assertNull(OptSwitch.parse(null));
        assertNull(OptSwitch.parse(""));
        assertNull(OptSwitch.parse("XXX"));

        assertEquals(OptSwitch.OPT_HELP, OptSwitch.parse("-h"));
        assertEquals(OptSwitch.OPT_HELP, OptSwitch.parse("-help"));
        assertEquals(OptSwitch.OPT_HELP, OptSwitch.parse("-?"));

        assertEquals(OptSwitch.OPT_INFILE, OptSwitch.parse("-i"));
        assertEquals(OptSwitch.OPT_OUTFILE, OptSwitch.parse("-o"));
        assertEquals(OptSwitch.OPT_FORCE, OptSwitch.parse("-f"));
        assertEquals(OptSwitch.OPT_NEWLINE, OptSwitch.parse("-nl"));
        assertEquals(OptSwitch.OPT_GENOUT, OptSwitch.parse("-genout"));
        assertEquals(OptSwitch.OPT_IFORM, OptSwitch.parse("-iform"));
        assertEquals(OptSwitch.OPT_OFORM, OptSwitch.parse("-oform"));
        assertEquals(OptSwitch.OPT_QUAT, OptSwitch.parse("-quat"));
        assertEquals(OptSwitch.OPT_EYXZ, OptSwitch.parse("-eyxz"));

        return;
    }

    /**
     * Test of getExArgNum method, of class OptSwitch.
     */
    @Test
    public void testGetExArgNum() {
        System.out.println("getExArgNum");

        for(OptSwitch sw : OptSwitch.values()){
            switch(sw){
            case OPT_HELP:
            case OPT_FORCE:
            case OPT_QUAT:
            case OPT_EYXZ:
                assertEquals(0, sw.getExArgNum());
                break;
            default:
                assertEquals(1, sw.getExArgNum());
                break;
            }
        }

        return;
    }

}
