/*
 */

package testdata.vmd110820.luminous;

import org.junit.jupiter.api.Test;

import static testdata.CnvAssert.*;

/**
 *
 */
public class LuminousTest {

    static Class<?> THISCLASS = LuminousTest.class;

    public LuminousTest() {
        assert this.getClass() == THISCLASS;
        return;
    }

    @Test
    public void vmd2xml() throws Exception{
        System.out.println("vmd2xml");
        assertVmd2OldXml(THISCLASS, "luminous.vmd", "luminous.xml");
        return;
    }

    @Test
    public void xml2vmd() throws Exception{
        System.out.println("xml2vmd");
        assertXml2Vmd(THISCLASS, "luminous.xml", "luminous.vmd");
        return;
    }

}
