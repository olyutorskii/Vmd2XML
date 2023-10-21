/*
 */

package testdata.vmd110820.shadow;

import org.junit.jupiter.api.Test;

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
