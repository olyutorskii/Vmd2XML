/*
 */

package testdata.vmd110820.camera;

import org.junit.jupiter.api.Test;

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
