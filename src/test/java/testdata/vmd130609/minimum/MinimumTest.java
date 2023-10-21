/*
 */

package testdata.vmd130609.minimum;

import org.junit.jupiter.api.Test;

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

    @Test
    public void vmd2xml() throws Exception{
        System.out.println("vmd2xml");
        assertVmd2Xml13(THISCLASS, "minimum.vmd", "minimum.xml");
        return;
    }

    @Test
    public void xml2vmd() throws Exception{
        System.out.println("xml2vmd");
        assertXml2Vmd(THISCLASS, "minimum.xml", "minimum.vmd");
        return;
    }

}
