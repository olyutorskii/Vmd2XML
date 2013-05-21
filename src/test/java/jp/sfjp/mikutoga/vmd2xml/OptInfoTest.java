/*
 */

package jp.sfjp.mikutoga.vmd2xml;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class OptInfoTest {

    public OptInfoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parseOption method, of class OptInfo.
     */
    @Test
    public void testParseOption() throws Exception {
        System.out.println("parseOption");

        OptInfo result;

        try{
            OptInfo.parseOption();
            fail();
        }catch(CmdLineException e){
            assertEquals("You must specify input file with -i.", e.getMessage());
        }

        try{
            OptInfo.parseOption("XXX");
            fail();
        }catch(CmdLineException e){
            assertEquals("Unknown option : XXX", e.getMessage());
        }

        result = OptInfo.parseOption("-h");
        assertTrue(result.needHelp());
        assertEquals(Vmd2Xml.GENERATOR, result.getGenerator());
        assertEquals("\n", result.getNewline());
        assertSame(MotionFileType.NONE, result.getInFileType());
        assertNull(result.getInFilename());
        assertSame(MotionFileType.NONE, result.getOutFileType());
        assertNull(result.getOutFilename());
        assertTrue(result.isQuaterniomMode());
        assertFalse(result.overwriteMode());

        try{
            OptInfo.parseOption("-i");
            fail();
        }catch(CmdLineException e){
            assertEquals("You need option arg with : -i", e.getMessage());
        }

        try{
            OptInfo.parseOption("-i", "test.vmd");
            fail();
        }catch(CmdLineException e){
            assertEquals("You must specify output file with -o.", e.getMessage());
        }

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml");
        assertFalse(result.needHelp());
        assertSame(MotionFileType.VMD, result.getInFileType());
        assertEquals("test.vmd", result.getInFilename());
        assertSame(MotionFileType.XML_110820, result.getOutFileType());
        assertEquals("test.xml", result.getOutFilename());
        assertEquals(Vmd2Xml.GENERATOR, result.getGenerator());
        assertEquals("\n", result.getNewline());
        assertTrue(result.isQuaterniomMode());
        assertFalse(result.overwriteMode());

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-i", "test2.vmd");
        assertEquals("test2.vmd", result.getInFilename());


        try{
            OptInfo.parseOption("-i", "input", "-o", "test.xml");
            fail();
        }catch(CmdLineException e){
            assertEquals("You must specify input format with -iform.", e.getMessage());
        }

        try{
            OptInfo.parseOption("-i", "input", "-o", "test.xml", "-iform", "ZZZ");
            fail();
        }catch(CmdLineException e){
            assertEquals("Unknown format : \"ZZZ\" must be \"vmd\" or \"xml\" or \"xml110820\"", e.getMessage());
        }

        result = OptInfo.parseOption("-i", "input", "-o", "test.xml", "-iform", "vmd");
        assertSame(MotionFileType.VMD, result.getInFileType());
        assertEquals("input", result.getInFilename());

        result = OptInfo.parseOption("-i", "input", "-o", "test.xml", "-iform", "xml");
        assertSame(MotionFileType.XML_110820, result.getInFileType());

        result = OptInfo.parseOption("-i", "input", "-o", "test.xml", "-iform", "xml110820");
        assertSame(MotionFileType.XML_110820, result.getInFileType());

        try{
            OptInfo.parseOption("-i", "test.vmd", "-o", "output");
            fail();
        }catch(CmdLineException e){
            assertEquals("You must specify output format with -oform.", e.getMessage());
        }

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "output", "-oform", "vmd");
        assertSame(MotionFileType.VMD, result.getOutFileType());
        assertEquals("output", result.getOutFilename());

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-f");
        assertTrue(result.overwriteMode());

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-eyxz");
        assertFalse(result.isQuaterniomMode());

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-quat");
        assertTrue(result.isQuaterniomMode());

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-eyxz", "-quat");
        assertTrue(result.isQuaterniomMode());

        try{
            OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-nl");
            fail();
        }catch(CmdLineException e){
            assertEquals("You need option arg with : -nl", e.getMessage());
        }

        try{
            OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-nl", "QQQ");
            fail();
        }catch(CmdLineException e){
            assertEquals("Unknown newline : \"QQQ\" must be \"lf\" or \"crlf\"", e.getMessage());
        }

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-nl", "lf");
        assertEquals("\n", result.getNewline());

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-nl", "crlf");
        assertEquals("\r\n", result.getNewline());

        try{
            OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-genout");
            fail();
        }catch(CmdLineException e){
            assertEquals("You need option arg with : -genout", e.getMessage());
        }

        try{
            OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-genout", "QQQ");
            fail();
        }catch(CmdLineException e){
            assertEquals("Unknown switch : \"QQQ\" must be \"on\" or \"off\"", e.getMessage());
        }

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-genout", "on");
        assertEquals(Vmd2Xml.GENERATOR, result.getGenerator());

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-genout", "true");
        assertEquals(Vmd2Xml.GENERATOR, result.getGenerator());

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-genout", "yes");
        assertEquals(Vmd2Xml.GENERATOR, result.getGenerator());

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-genout", "off");
        assertNull(result.getGenerator());

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-genout", "false");
        assertNull(result.getGenerator());

        result = OptInfo.parseOption("-i", "test.vmd", "-o", "test.xml", "-genout", "no");
        assertNull(result.getGenerator());


        return;
    }

}
