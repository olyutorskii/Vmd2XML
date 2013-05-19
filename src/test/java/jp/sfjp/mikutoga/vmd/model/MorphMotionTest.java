/*
 */

package jp.sfjp.mikutoga.vmd.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class MorphMotionTest {

    public MorphMotionTest() {
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
     * Test of get/setMorphName method, of class MorphMotion.
     */
    @Test
    public void testGetSetMorphName() {
        System.out.println("get/setMorphName");

        MorphMotion motion;

        motion = new MorphMotion();
        assertEquals("", motion.getMorphName());

        motion.setMorphName("mmmorph");
        assertEquals("mmmorph", motion.getMorphName());

        return;
    }

    /**
     * Test of get/setFlex method, of class MorphMotion.
     */
    @Test
    public void testGetSetFlex() {
        System.out.println("get/setFlex");

        MorphMotion motion;

        motion = new MorphMotion();
        assertEquals(0.0f, motion.getFlex(), 0.0f);

        motion.setFlex(0.5f);
        assertEquals(0.5f, motion.getFlex(), 0.0f);

        return;
    }

    /**
     * Test of toString method, of class MorphMotion.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        MorphMotion motion;

        motion = new MorphMotion();

        assertEquals("morph name : [] #0 flex = 0", motion.toString());

        motion.setMorphName("mmmorph");
        motion.setFrameNumber(99);
        motion.setFlex(0.5f);
        assertEquals("morph name : [mmmorph] #99 flex = 0.5", motion.toString());


        return;
    }

}
