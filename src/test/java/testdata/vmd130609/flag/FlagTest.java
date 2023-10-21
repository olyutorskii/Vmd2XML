/*
 */

package testdata.vmd130609.flag;

import org.junit.jupiter.api.Test;

import static testdata.CnvAssert.*;

/**
 *
 */
public class FlagTest {

    static Class<?> THISCLASS = FlagTest.class;

    public FlagTest() {
        assert this.getClass() == THISCLASS;
        return;
    }

    @Test
    public void vmd2xml() throws Exception{
        System.out.println("vmd2xml");
        assertVmd2Xml13(THISCLASS, "flag.vmd", "flag.xml");
        return;
    }

    @Test
    public void xml2vmd() throws Exception{
        System.out.println("xml2vmd");
        assertXml2Vmd(THISCLASS, "flag.xml", "flag.vmd");
        return;
    }

}
