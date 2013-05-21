/*
 */

package jp.sfjp.mikutoga.vmd2xml;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class CmdLineExceptionTest {

    public CmdLineExceptionTest() {
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
    public void testSomeMethod() {

        CmdLineException exp;

        exp = new CmdLineException("abc");
        assertEquals("abc", exp.getMessage());

        return;
    }

}
