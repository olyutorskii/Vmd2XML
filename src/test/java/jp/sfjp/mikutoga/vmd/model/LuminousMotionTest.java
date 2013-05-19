/*
 */

package jp.sfjp.mikutoga.vmd.model;

import jp.sfjp.mikutoga.math.MkVec3D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class LuminousMotionTest {

    public LuminousMotionTest() {
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
     * Test of getColor method, of class LuminousMotion.
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor");

        LuminousMotion motion;

        motion = new LuminousMotion();

        LuminousColor color = motion.getColor();
        assertNotNull(color);

        assertEquals(0.602f, color.getColR(), 0.0f);
        assertEquals(0.602f, color.getColG(), 0.0f);
        assertEquals(0.602f, color.getColB(), 0.0f);

        return;
    }

    /**
     * Test of getDirection method, of class LuminousMotion.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");

        LuminousMotion motion;

        motion = new LuminousMotion();

        MkVec3D vec = motion.getDirection();
        assertNotNull(vec);

        assertEquals(-0.5, vec.getXVal(), 0.0);
        assertEquals(-1.0, vec.getYVal(), 0.0);
        assertEquals( 0.5, vec.getZVal(), 0.0);

        return;
    }

    /**
     * Test of toString method, of class LuminousMotion.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        LuminousMotion motion;

        motion = new LuminousMotion();

        assertEquals("#0 luminous color : r=0.602 g=0.602 b=0.602 direction : vec=[-0.5, -1.0, 0.5]",
                motion.toString());

        return;
    }

}
