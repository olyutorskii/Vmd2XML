/*
 */

package testdata.vmd110820.xml;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    public void xml2vmd() throws Exception{
        System.out.println("xml2vmd");
        assertXml2Vmd(THISCLASS, "namespace.xml", "onlyBone.vmd");
        return;
    }

}