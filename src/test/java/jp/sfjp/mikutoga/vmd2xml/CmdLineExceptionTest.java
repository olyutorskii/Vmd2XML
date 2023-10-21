/*
 */

package jp.sfjp.mikutoga.vmd2xml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class CmdLineExceptionTest {

    public CmdLineExceptionTest() {
    }

    @Test
    public void testSomeMethod() {

        CmdLineException exp;

        exp = new CmdLineException("abc");
        assertEquals("abc", exp.getMessage());

        return;
    }

}
