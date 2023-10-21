/*
 */

package testdata.vmd110820.xml;

import org.junit.jupiter.api.Test;

import static testdata.CnvAssert.*;

/**
 *
 */
public class XmlTest {

    static Class<?> THISCLASS = XmlTest.class;

    public XmlTest() {
        assert this.getClass() == THISCLASS;
        return;
    }

    @Test
    public void xml2vmd() throws Exception{
        System.out.println("xml2vmd");
        assertXml2Vmd(THISCLASS, "namespace.xml", "onlyBone.vmd");
        return;
    }

}
