/*
 */

package testdata.vmd110820.morph;

import org.junit.jupiter.api.Test;

import static testdata.CnvAssert.*;

/**
 *
 */
public class MorphTest {

    static Class<?> THISCLASS = MorphTest.class;

    public MorphTest() {
        assert this.getClass() == THISCLASS;
        return;
    }

    @Test
    public void vmd2xml() throws Exception{
        System.out.println("vmd2xml");
        assertVmd2OldXml(THISCLASS, "morph.vmd", "morph.xml");
        return;
    }

    @Test
    public void xml2vmd() throws Exception{
        System.out.println("xml2vmd");
        assertXml2Vmd(THISCLASS, "morph.xml", "morph.vmd");
        return;
    }

}
