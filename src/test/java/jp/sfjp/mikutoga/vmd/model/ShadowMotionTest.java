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
public strictfp class ShadowMotionTest {

    public ShadowMotionTest() {
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
     * Test of rawParamToScope method, of class ShadowMotion.
     */
    @Test
    public void testRawParamToScope() {
        System.out.println("rawParamToScope");

        assertEquals(10000.0, ShadowMotion.rawParamToScope(0.0), 0.0);
        assertEquals(8875.0, ShadowMotion.rawParamToScope(0.01125), 1e-11);

        return;
    }

    /**
     * Test of scopeToRawParam method, of class ShadowMotion.
     */
    @Test
    public void testScopeToRawParam() {
        System.out.println("scopeToRawParam");

        assertEquals(0.1, ShadowMotion.scopeToRawParam(0.0), 0.0);
        assertEquals(0.01125, ShadowMotion.scopeToRawParam(8875.0), 1e-16);

        return;
    }

    /**
     * Test of set/getRawScopeParam method, of class ShadowMotion.
     */
    @Test
    public void testSetGetRawScopeParam() {
        System.out.println("set/getRawScopeParam");

        ShadowMotion motion;

        motion = new ShadowMotion();
        assertEquals(0.01125, motion.getRawScopeParam(), 0.0);

        motion.setRawScopeParam(0.1);
        assertEquals(0.1, motion.getRawScopeParam(), 0.0);

        return;
    }

    /**
     * Test of set/getScope method, of class ShadowMotion.
     */
    @Test
    public void testSetGetScope() {
        System.out.println("set/getScope");

        ShadowMotion motion;

        motion = new ShadowMotion();

        assertEquals(8875.0, motion.getScope(), 1e-11);

        motion.setScope(10000.0);
        assertEquals(10000.0, motion.getScope(), 0.0);

        return;
    }

    /**
     * Test of set/getShadowMode method, of class ShadowMotion.
     */
    @Test
    public void testSetGetShadowMode() {
        System.out.println("set/getShadowMode");

        ShadowMotion motion;

        motion = new ShadowMotion();

        assertSame(ShadowMode.MODE_1, motion.getShadowMode());

        motion.setShadowMode(ShadowMode.MODE_2);
        assertSame(ShadowMode.MODE_2, motion.getShadowMode());

        motion.setShadowMode(ShadowMode.NONE);
        assertSame(ShadowMode.NONE, motion.getShadowMode());

        return;
    }

    /**
     * Test of toString method, of class ShadowMotion.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        ShadowMotion motion;

        motion = new ShadowMotion();

        assertEquals("#0 shadow mode : MODE_1 rawparam=0.01125", motion.toString());

        return;
    }

}
