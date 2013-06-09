/*
 */

package jp.sfjp.mikutoga.vmd2xml;

import jp.sfjp.mikutoga.vmd.model.xml.XmlMotionFileType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class MotionFileTypeTest {

    public MotionFileTypeTest() {
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
     * Test of values method, of class MotionFileType.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        assertEquals(5, MotionFileType.values().length);
        return;
    }

    /**
     * Test of valueOf method, of class MotionFileType.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        assertSame(MotionFileType.NONE, MotionFileType.valueOf("NONE"));
        return;
    }

    /**
     * Test of isXml method, of class MotionFileType.
     */
    @Test
    public void testIsXml() {
        System.out.println("isXml");

        assertFalse(MotionFileType.NONE.isXml());
        assertFalse(MotionFileType.VMD.isXml());
        assertTrue(MotionFileType.XML_AUTO.isXml());
        assertTrue(MotionFileType.XML_110820.isXml());
        assertTrue(MotionFileType.XML_130609.isXml());

        return;
    }

    /**
     * Test of isVmd method, of class MotionFileType.
     */
    @Test
    public void testIsVmd() {
        System.out.println("isVmd");

        assertFalse(MotionFileType.NONE.isVmd());
        assertTrue(MotionFileType.VMD.isVmd());
        assertFalse(MotionFileType.XML_AUTO.isVmd());
        assertFalse(MotionFileType.XML_110820.isVmd());
        assertFalse(MotionFileType.XML_130609.isVmd());

        return;
    }

    /**
     * Test of toXmlType method, of class MotionFileType.
     */
    @Test
    public void testToXmlType() {
        System.out.println("toXmlType");

        assertSame(XmlMotionFileType.XML_AUTO, MotionFileType.NONE.toXmlType());
        assertSame(XmlMotionFileType.XML_AUTO, MotionFileType.VMD.toXmlType());
        assertSame(XmlMotionFileType.XML_AUTO, MotionFileType.XML_AUTO.toXmlType());
        assertSame(XmlMotionFileType.XML_110820, MotionFileType.XML_110820.toXmlType());
        assertSame(XmlMotionFileType.XML_130609, MotionFileType.XML_130609.toXmlType());

        return;
    }

}
