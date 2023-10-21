/*
 */

package jp.sfjp.mikutoga.vmd.model;

import jp.sfjp.mikutoga.math.MkPos3D;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class CameraMotionTest {

    public CameraMotionTest() {
    }

    /**
     * Test of getCameraTarget method, of class CameraMotion.
     */
    @Test
    public void testGetCameraTarget() {
        System.out.println("getCameraTarget");

        CameraMotion motion;

        motion = new CameraMotion();

        MkPos3D pos = motion.getCameraTarget();
        assertNotNull(pos);

        return;
    }

    /**
     * Test of getTargetPosCurve method, of class CameraMotion.
     */
    @Test
    public void testGetTargetPosCurve() {
        System.out.println("getTargetPosCurve");

        CameraMotion motion;

        motion = new CameraMotion();

        PosCurve curve = motion.getTargetPosCurve();
        assertNotNull(curve);

        return;
    }

    /**
     * Test of getCameraRotation method, of class CameraMotion.
     */
    @Test
    public void testGetCameraRotation() {
        System.out.println("getCameraRotation");

        CameraMotion motion;

        motion = new CameraMotion();

        CameraRotation rot = motion.getCameraRotation();
        assertNotNull(rot);

        return;
    }

    /**
     * Test of getIntpltRotation method, of class CameraMotion.
     */
    @Test
    public void testGetIntpltRotation() {
        System.out.println("getIntpltRotation");

        CameraMotion motion;

        motion = new CameraMotion();

        BezierParam bez = motion.getIntpltRotation();
        assertNotNull(bez);

        return;
    }

    /**
     * Test of get/setRange method, of class CameraMotion.
     */
    @Test
    public void testGetSetRange() {
        System.out.println("get/setRange");

        CameraMotion motion;

        motion = new CameraMotion();

        assertEquals(0.0, motion.getRange(), 0.0);

        motion.setRange(-35.0);
        assertEquals(-35.0, motion.getRange(), 0.0);

        return;
    }

    /**
     * Test of getIntpltRange method, of class CameraMotion.
     */
    @Test
    public void testGetIntpltRange() {
        System.out.println("getIntpltRange");

        CameraMotion motion;

        motion = new CameraMotion();

        BezierParam bez = motion.getIntpltRange();
        assertNotNull(bez);

        return;
    }

    /**
     * Test of has/setPerspective method, of class CameraMotion.
     */
    @Test
    public void testHasSetPerspective() {
        System.out.println("has/setPerspective");

        CameraMotion motion;

        motion = new CameraMotion();
        assertFalse(motion.hasPerspective());

        motion.setPerspectiveMode(true);
        assertTrue(motion.hasPerspective());

        motion.setPerspectiveMode(false);
        assertFalse(motion.hasPerspective());

        return;
    }

    /**
     * Test of get/set ProjectionAngle method, of class CameraMotion.
     */
    @Test
    public void testGetSetProjectionAngle() {
        System.out.println("get/setProjectionAngle");

        CameraMotion motion;
        int angle;

        motion = new CameraMotion();

        angle = motion.getProjectionAngle();
        assertEquals(0, angle);

        motion.setProjectionAngle(25);
        angle = motion.getProjectionAngle();
        assertEquals(25, angle);

        return;
    }

    /**
     * Test of getIntpltProjection method, of class CameraMotion.
     */
    @Test
    public void testGetIntpltProjection() {
        System.out.println("getIntpltProjection");

        CameraMotion motion;

        motion = new CameraMotion();
        BezierParam bez = motion.getIntpltProjection();
        assertNotNull(bez);

        return;
    }

    /**
     * Test of toString method, of class CameraMotion.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        CameraMotion motion;

        motion = new CameraMotion();

        assertEquals(
                  "#0 latitude=0 longitude=0 roll=0 Rot-Bezier P1=(20, 20) P2=(107, 107)\n"
                + "range : 0 Range-Bezier P1=(20, 20) P2=(107, 107)\n"
                + "target-pos : x=0.0 y=0.0 z=0.0\n"
                + "X-Bezier P1=(20, 20) P2=(107, 107)\n"
                + "Y-Bezier P1=(20, 20) P2=(107, 107)\n"
                + "Z-Bezier P1=(20, 20) P2=(107, 107)\n"
                +"perspective : false\n"
                +"projection angle : 0deg Bezier P1=(20, 20) P2=(107, 107)",
                motion.toString() );

        return;
    }

}
