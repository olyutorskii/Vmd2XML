/*
 */

package jp.sfjp.mikutoga.vmd.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class BezierParamTest {

    public BezierParamTest() {
    }

    /**
     * Test of setP2 method, of class BezierParam.
     */
    @Test
    public void testSetP() {
        System.out.println("getsetP");

        BezierParam bez;

        bez = new BezierParam();
        assertEquals(20, bez.getP1x());
        assertEquals(20, bez.getP1y());
        assertEquals(107, bez.getP2x());
        assertEquals(107, bez.getP2y());

        bez.setP1x((byte)21);
        assertEquals(21, bez.getP1x());
        assertEquals(20, bez.getP1y());
        assertEquals(107, bez.getP2x());
        assertEquals(107, bez.getP2y());

        bez.setP1y((byte)22);
        assertEquals(21, bez.getP1x());
        assertEquals(22, bez.getP1y());
        assertEquals(107, bez.getP2x());
        assertEquals(107, bez.getP2y());

        bez.setP2x((byte)108);
        assertEquals(21, bez.getP1x());
        assertEquals(22, bez.getP1y());
        assertEquals(108, bez.getP2x());
        assertEquals(107, bez.getP2y());

        bez.setP2y((byte)109);
        assertEquals(21, bez.getP1x());
        assertEquals(22, bez.getP1y());
        assertEquals(108, bez.getP2x());
        assertEquals(109, bez.getP2y());

        bez.setP1((byte)23, (byte)24);
        assertEquals(23, bez.getP1x());
        assertEquals(24, bez.getP1y());
        assertEquals(108, bez.getP2x());
        assertEquals(109, bez.getP2y());

        bez.setP2((byte)110, (byte)111);
        assertEquals(23, bez.getP1x());
        assertEquals(24, bez.getP1y());
        assertEquals(110, bez.getP2x());
        assertEquals(111, bez.getP2y());

        return;
    }

    /**
     * Test of isLinear method, of class BezierParam.
     */
    @Test
    public void testIsLinear() {
        System.out.println("isLinear");

        BezierParam bez;

        bez = new BezierParam();
        assertTrue(bez.isLinear());

        bez.setP1((byte)10, (byte)10);
        assertTrue(bez.isLinear());

        bez.setP2((byte)120, (byte)120);
        assertTrue(bez.isLinear());

        bez.setP1((byte)0, (byte)0);
        assertTrue(bez.isLinear());

        bez.setP2((byte)127, (byte)127);
        assertTrue(bez.isLinear());

        bez.setP1((byte)1, (byte)2);
        assertFalse(bez.isLinear());

        bez.setP1((byte)0, (byte)0);
        bez.setP2((byte)126, (byte)127);
        assertFalse(bez.isLinear());

        return;
    }

    /**
     * Test of isDefaultLinear method, of class BezierParam.
     */
    @Test
    public void testIsDefaultLinear() {
        System.out.println("isDefaultLinear");

        BezierParam bez;

        bez = new BezierParam();
        assertTrue(bez.isDefaultLinear());
        assertTrue(bez.isLinear());

        bez.setP1((byte)30, (byte)30);
        assertFalse(bez.isDefaultLinear());
        assertTrue(bez.isLinear());

        bez.setDefaultEaseInOut();
        assertFalse(bez.isDefaultLinear());

        return;
    }

    /**
     * Test of isDefaultEaseInOut method, of class BezierParam.
     */
    @Test
    public void testIsDefaultEaseInOut() {
        System.out.println("isDefaultEaseInOut");

        BezierParam bez;

        bez = new BezierParam();
        assertFalse(bez.isDefaultEaseInOut());

        bez.setDefaultEaseInOut();
        assertTrue(bez.isDefaultEaseInOut());

        return;
    }

    /**
     * Test of setDefaultLinear method, of class BezierParam.
     */
    @Test
    public void testSetDefaultLinear() {
        System.out.println("setDefaultLinear");

        BezierParam bez;

        bez = new BezierParam();
        bez.setDefaultLinear();
        assertEquals(20, bez.getP1x());
        assertEquals(20, bez.getP1y());
        assertEquals(107, bez.getP2x());
        assertEquals(107, bez.getP2y());

        return;
    }

    /**
     * Test of setDefaultEaseInOut method, of class BezierParam.
     */
    @Test
    public void testSetDefaultEaseInOut() {
        System.out.println("setDefaultEaseInOut");

        BezierParam bez;

        bez = new BezierParam();
        bez.setDefaultEaseInOut();
        assertEquals(64, bez.getP1x());
        assertEquals(0, bez.getP1y());
        assertEquals(64, bez.getP2x());
        assertEquals(127, bez.getP2y());

        return;
    }

    /**
     * Test of toString method, of class BezierParam.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        BezierParam bez;

        bez = new BezierParam();
        assertEquals("P1=(20, 20) P2=(107, 107)", bez.toString());

        bez.setP1((byte)0, (byte)0);
        assertEquals("P1=(0, 0) P2=(107, 107)", bez.toString());

        bez.setP2((byte)127, (byte)127);
        assertEquals("P1=(0, 0) P2=(127, 127)", bez.toString());

        return;
    }

}
