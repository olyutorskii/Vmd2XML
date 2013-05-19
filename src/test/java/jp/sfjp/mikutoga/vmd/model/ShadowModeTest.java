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
public class ShadowModeTest {

    public ShadowModeTest() {
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
     * Test of values method, of class ShadowMode.
     */
    @Test
    public void testValues() {
        System.out.println("values");

        ShadowMode[] result = ShadowMode.values();
        assertEquals(3, result.length);

        assertSame(ShadowMode.NONE, result[0]);
        assertSame(ShadowMode.MODE_1, result[1]);
        assertSame(ShadowMode.MODE_2, result[2]);

        return;
    }

    /**
     * Test of valueOf method, of class ShadowMode.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");

        assertSame(ShadowMode.NONE, ShadowMode.valueOf("NONE"));
        assertSame(ShadowMode.MODE_1, ShadowMode.valueOf("MODE_1"));
        assertSame(ShadowMode.MODE_2, ShadowMode.valueOf("MODE_2"));

        return;
    }

    /**
     * Test of getEncodedByte method, of class ShadowMode.
     */
    @Test
    public void testGetEncodedByte() {
        System.out.println("getEncodedByte");

        assertEquals((byte)0x00, ShadowMode.NONE.getEncodedByte());
        assertEquals((byte)0x01, ShadowMode.MODE_1.getEncodedByte());
        assertEquals((byte)0x02, ShadowMode.MODE_2.getEncodedByte());

        return;
    }

    /**
     * Test of decode method, of class ShadowMode.
     */
    @Test
    public void testDecode_byte() {
        System.out.println("decode");

        assertSame(ShadowMode.NONE, ShadowMode.decode((byte)0x00));
        assertSame(ShadowMode.MODE_1, ShadowMode.decode((byte)0x01));
        assertSame(ShadowMode.MODE_2, ShadowMode.decode((byte)0x02));

        assertNull(ShadowMode.decode((byte)-1));
        assertNull(ShadowMode.decode((byte)0x03));

        return;
    }

    /**
     * Test of decode method, of class ShadowMode.
     */
    @Test
    public void testDecode_int() {
        System.out.println("decode");

        assertSame(ShadowMode.NONE, ShadowMode.decode(0x00));
        assertSame(ShadowMode.MODE_1, ShadowMode.decode(0x01));
        assertSame(ShadowMode.MODE_2, ShadowMode.decode(0x02));

        assertNull(ShadowMode.decode(-1));
        assertNull(ShadowMode.decode(0x03));

        return;
    }

}
