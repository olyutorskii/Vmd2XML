/*
 */

package testdata.vmd110820.motion;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
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
