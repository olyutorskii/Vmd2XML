/*
 */

package testdata.vmd110820.small;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static testdata.CnvAssert.*;

/**
 *
 */
public class SmallTest {

    static Class<?> THISCLASS = SmallTest.class;

    public SmallTest() {
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
        assertVmd2OldXml(THISCLASS, "onlyBone.vmd", "onlyBone.xml");
        assertVmd2OldXml(THISCLASS, "onlyMorph.vmd", "onlyMorph.xml");
        assertVmd2OldXml(THISCLASS, "onlyCamera.vmd", "onlyCamera.xml");
        assertVmd2OldXml(THISCLASS, "onlyLuminous.vmd", "onlyLuminous.xml");
        assertVmd2OldXml(THISCLASS, "onlyShadow.vmd", "onlyShadow.xml");
        return;
    }

    @Test
    public void xml2vmd() throws Exception{
        System.out.println("xml2vmd");
        assertXml2Vmd(THISCLASS, "onlyBone.xml", "onlyBone.vmd");
        assertXml2Vmd(THISCLASS, "onlyMorph.xml", "onlyMorph.vmd");
        assertXml2Vmd(THISCLASS, "onlyCamera.xml", "onlyCamera.vmd");
        assertXml2Vmd(THISCLASS, "onlyLuminous.xml", "onlyLuminous.vmd");
        assertXml2Vmd(THISCLASS, "onlyShadow.xml", "onlyShadow.vmd");
        return;
    }

}
