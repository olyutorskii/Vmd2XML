/*
 */

package testdata.vmd110820.minimum;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static testdata.CnvAssert.*;

/**
 *
 */
public class MinimumTest {

    static Class<?> THISCLASS = MinimumTest.class;

    public MinimumTest() {
        assert this.getClass() == THISCLASS;
        return;
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

    @Test
    public void vmd2xml() throws Exception{
        System.out.println("vmd2xml");
        assertVmd2Xml(THISCLASS, "minmotion.vmd", "minmotion.xml");
        assertVmd2Xml(THISCLASS, "mincam.vmd", "mincam.xml");
        return;
    }

    @Test
    public void xml2vmd() throws Exception{
        System.out.println("xml2vmd");
        assertXml2Vmd(THISCLASS, "minmotion.xml", "minmotion.vmd");
        assertXml2Vmd(THISCLASS, "mincam.xml", "mincam.vmd");
        return;
    }

}
