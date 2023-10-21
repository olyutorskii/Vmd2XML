/*
 */

package testdata.vmd110820.motion;

import org.junit.jupiter.api.Test;

import static testdata.CnvAssert.*;

/**
 *
 */
public class MotionTest {

    static Class<?> THISCLASS = MotionTest.class;

    public MotionTest() {
        assert this.getClass() == THISCLASS;
        return;
    }

    @Test
    public void vmd2xml() throws Exception{
        System.out.println("vmd2xml");
        assertVmd2OldXml(THISCLASS, "motion.vmd", "motion.xml");
        return;
    }

    @Test
    public void xml2vmd() throws Exception{
        System.out.println("xml2vmd");
        assertXml2Vmd(THISCLASS, "motion.xml", "motion.vmd");
        return;
    }

}
