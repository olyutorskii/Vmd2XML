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
public class CameraRotationTest {

    public CameraRotationTest() {
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
     * Test of get/set method, of class CameraRotation.
     */
    @Test
    public void testGetSetParams() {
        System.out.println("getLatitude");

        CameraRotation rotation;

        rotation = new CameraRotation();

        assertEquals(0.0, rotation.getLatitude(), 0.0);
        assertEquals(0.0, rotation.getLongitude(), 0.0);
        assertEquals(0.0, rotation.getRoll(), 0.0);

        rotation.setLatitude(1.0);
        rotation.setLongitude(2.0);
        rotation.setRoll(3.0);

        assertEquals(1.0, rotation.getLatitude(), 0.0);
        assertEquals(2.0, rotation.getLongitude(), 0.0);
        assertEquals(3.0, rotation.getRoll(), 0.0);

        return;
    }

    /**
     * Test of toString method, of class CameraRotation.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        CameraRotation rotation;

        rotation = new CameraRotation();

        assertEquals("latitude=0 longitude=0 roll=0", rotation.toString());

        rotation.setLatitude(1.1f);
        rotation.setLongitude(2.2f);
        rotation.setRoll(3.3f);
        assertEquals("latitude=1.1 longitude=2.2 roll=3.3", rotation.toString());

        return;
    }

}
