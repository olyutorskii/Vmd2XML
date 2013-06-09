/*
 */

package testdata.vmd110820.camera;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static testdata.CnvAssert.*;

/**
 *
 */
public class CameraTest {

    static Class<?> THISCLASS = CameraTest.class;

    public CameraTest() {
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
        assertVmd2OldXml(THISCLASS, "camera.vmd", "camera.xml");
        return;
    }

    @Test
    public void xml2vmd() throws Exception{
        System.out.println("xml2vmd");
        assertXml2Vmd(THISCLASS, "camera.xml", "camera.vmd");
        return;
    }

}
