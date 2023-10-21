/*
 */

package testdata.vmd130609.small;

import org.junit.jupiter.api.Test;

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

    @Test
    public void vmd2xml() throws Exception{
        System.out.println("vmd2xml");
        assertVmd2Xml13(THISCLASS, "onlyFlag.vmd", "onlyFlag.xml");
        return;
    }

    @Test
    public void xml2vmd() throws Exception{
        System.out.println("xml2vmd");
        assertXml2Vmd(THISCLASS, "onlyFlag.xml", "onlyFlag.vmd");
        return;
    }

}
