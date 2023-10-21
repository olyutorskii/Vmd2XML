/*
 */

package testdata.vmd110820.euler;

import jp.sfjp.mikutoga.vmd2xml.MotionFileType;
import jp.sfjp.mikutoga.vmd2xml.Vmd2XmlConv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static testdata.CnvAssert.*;

/**
 *
 */
public class EulerTest {

    static Class<?> THISCLASS = EulerTest.class;

    public EulerTest() {
        assert this.getClass() == THISCLASS;
        return;
    }

    @Test
    public void vmd2xml() throws Exception{
        System.out.println("vmd2xml");

        Vmd2XmlConv converter = new Vmd2XmlConv();
        converter.setInType(MotionFileType.VMD);
        converter.setOutType(MotionFileType.XML_110820);
        converter.setNewline("\n");
        converter.setGenerator(null);
        converter.setQuaterniomMode(false);

        assertSame(MotionFileType.VMD, converter.getInTypes());
        assertSame(MotionFileType.XML_110820, converter.getOutTypes());
        assertEquals("\n", converter.getNewline());
        assertNull(converter.getGenerator());
        assertFalse(converter.isQuaterniomMode());

        assertConvert(THISCLASS, "euler.vmd", "euler.xml", converter);

        return;
    }

    @Test
    public void xml2vmd() throws Exception{
        System.out.println("xml2vmd");
        assertXml2Vmd(THISCLASS, "euler.xml", "euler.vmd");
        return;
    }

}
