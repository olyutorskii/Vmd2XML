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
public class LuminousColorTest {

    public LuminousColorTest() {
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
     * Test of get/setCol method, of class LuminousColor.
     */
    @Test
    public void testGetSetCol() {
        System.out.println("get/setCol");

        LuminousColor color;

        color = new LuminousColor();

        assertEquals(0.602f, color.getColR(), 0.0f);
        assertEquals(0.602f, color.getColG(), 0.0f);
        assertEquals(0.602f, color.getColB(), 0.0f);

        color.setColR(0.5f);
        assertEquals(0.5f, color.getColR(), 0.0f);

        color.setColG(0.5f);
        assertEquals(0.5f, color.getColG(), 0.0f);

        color.setColB(0.5f);
        assertEquals(0.5f, color.getColB(), 0.0f);

        return;
    }

    /**
     * Test of toString method, of class LuminousColor.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        LuminousColor color;

        color = new LuminousColor();

        assertEquals("r=0.602 g=0.602 b=0.602", color.toString());

        return;
    }

}
