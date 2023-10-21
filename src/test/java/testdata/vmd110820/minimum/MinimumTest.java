/*
 */

package testdata.vmd110820.minimum;

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
        assertVmd2OldXml(THISCLASS, "minmotion.vmd", "minmotion.xml");
        assertVmd2OldXml(THISCLASS, "mincam.vmd", "mincam.xml");
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
