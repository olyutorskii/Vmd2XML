/*
 */

package jp.sfjp.mikutoga.vmd.model;

import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class PosCurveTest {

    public PosCurveTest() {
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

    /**
     * Test of getIntplt method, of class PosCurve.
     */
    @Test
    public void testGetIntplt() {
        System.out.println("getIntpltZpos");

        PosCurve curve;

        curve = new PosCurve();

        BezierParam xbz = curve.getIntpltXpos();
        BezierParam ybz = curve.getIntpltYpos();
        BezierParam zbz = curve.getIntpltZpos();

        assertNotNull(xbz);
        assertNotNull(ybz);
        assertNotNull(zbz);

        assertNotSame(xbz, ybz);
        assertNotSame(ybz, zbz);
        assertNotSame(zbz, xbz);

        return;
    }

    /**
     * Test of isDefaultLinear method, of class PosCurve.
     */
    @Test
    public void testIsDefaultLinear() {
        System.out.println("isDefaultLinear");

        PosCurve curve;

        curve = new PosCurve();
        assertTrue(curve.isDefaultLinear());

        curve = new PosCurve();
        curve.getIntpltXpos().setP1((byte)21, (byte)21);
        assertFalse(curve.isDefaultLinear());
        curve.getIntpltXpos().setP1((byte)20, (byte)20);
        assertTrue(curve.isDefaultLinear());

        curve = new PosCurve();
        curve.getIntpltYpos().setP1((byte)21, (byte)21);
        assertFalse(curve.isDefaultLinear());
        curve.getIntpltYpos().setP1((byte)20, (byte)20);
        assertTrue(curve.isDefaultLinear());

        curve = new PosCurve();
        curve.getIntpltZpos().setP1((byte)21, (byte)21);
        assertFalse(curve.isDefaultLinear());
        curve.getIntpltZpos().setP1((byte)20, (byte)20);
        assertTrue(curve.isDefaultLinear());

        return;
    }

    /**
     * Test of toString method, of class PosCurve.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        PosCurve curve;

        curve = new PosCurve();
        assertEquals(
                  "X-Bezier P1=(20, 20) P2=(107, 107)\n"
                + "Y-Bezier P1=(20, 20) P2=(107, 107)\n"
                + "Z-Bezier P1=(20, 20) P2=(107, 107)",
                curve.toString() );

        curve.getIntpltXpos().setP1x((byte)1);
        curve.getIntpltYpos().setP1x((byte)2);
        curve.getIntpltZpos().setP1x((byte)3);
        assertEquals(
                  "X-Bezier P1=(1, 20) P2=(107, 107)\n"
                + "Y-Bezier P1=(2, 20) P2=(107, 107)\n"
                + "Z-Bezier P1=(3, 20) P2=(107, 107)",
                curve.toString() );

        return;
    }

    /**
     * Test of iterator method, of class PosCurve.
     */
    @Test
    public void testIterator() {
        System.out.println("iterator");
        PosCurve curve;

        curve = new PosCurve();

        Iterator<BezierParam> it = curve.iterator();

        assertTrue(it.hasNext());
        BezierParam bz1 = it.next();

        assertTrue(it.hasNext());
        BezierParam bz2 = it.next();

        assertTrue(it.hasNext());
        BezierParam bz3 = it.next();

        assertFalse(it.hasNext());

        assertSame(curve.getIntpltXpos(), bz1);
        assertSame(curve.getIntpltYpos(), bz2);
        assertSame(curve.getIntpltZpos(), bz3);

        return;
    }

}
