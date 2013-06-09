/*
 */

package testdata.vmd110820.shadow;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static testdata.CnvAssert.*;

/**
 *
 */
public class ShadowTest {

    static Class<?> THISCLASS = ShadowTest.class;

    public ShadowTest() {
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
        assertVmd2OldXml(THISCLASS, "allShadow.vmd", "allShadow.xml");
        return;
    }

    @Test
    public void xml2vmd() throws Exception{
        System.out.println("xml2vmd");
        assertXml2Vmd(THISCLASS, "allShadow.xml", "allShadow.vmd");
        return;
    }

}
