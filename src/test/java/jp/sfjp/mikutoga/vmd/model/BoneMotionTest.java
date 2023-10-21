/*
 */

package jp.sfjp.mikutoga.vmd.model;

import jp.sfjp.mikutoga.math.MkPos3D;
import jp.sfjp.mikutoga.math.MkQuat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class BoneMotionTest {

    public BoneMotionTest() {
    }

    /**
     * Test of get/setBoneName method, of class BoneMotion.
     */
    @Test
    public void testGetSetBoneName() {
        System.out.println("getBoneName");

        BoneMotion motion;

        motion = new BoneMotion();
        assertNull(motion.getBoneName());

        motion.setBoneName("");
        assertEquals("", motion.getBoneName());

        motion.setBoneName("bbbone");
        assertEquals("bbbone", motion.getBoneName());

        motion.setBoneName(null);
        assertNull(motion.getBoneName());

        return;
    }

    /**
     * Test of getRotation method, of class BoneMotion.
     */
    @Test
    public void testGetRotation() {
        System.out.println("getRotation");

        BoneMotion motion;
        MkQuat rot;

        motion = new BoneMotion();

        rot = motion.getRotation();
        assertNotNull(rot);

        return;
    }

    /**
     * Test of getIntpltRotation method, of class BoneMotion.
     */
    @Test
    public void testGetIntpltRotation() {
        System.out.println("getIntpltRotation");

        BoneMotion motion;
        BezierParam bez;

        motion = new BoneMotion();

        bez = motion.getIntpltRotation();
        assertNotNull(bez);

        return;
    }

    /**
     * Test of getPosition method, of class BoneMotion.
     */
    @Test
    public void testGetPosition() {
        System.out.println("getPosition");

        BoneMotion motion;
        MkPos3D pos;

        motion = new BoneMotion();

        pos = motion.getPosition();
        assertNotNull(pos);

        return;
    }

    /**
     * Test of getPosCurve method, of class BoneMotion.
     */
    @Test
    public void testGetPosCurve() {
        System.out.println("getPosCurve");

        BoneMotion motion;
        PosCurve curve;

        motion = new BoneMotion();

        curve = motion.getPosCurve();
        assertNotNull(curve);

        return;
    }

    /**
     * Test of hasImplicitPosition method, of class BoneMotion.
     */
    @Test
    public void testHasImplicitPosition() {
        System.out.println("hasImplicitPosition");

        BoneMotion motion;

        motion = new BoneMotion();
        assertTrue(motion.hasImplicitPosition());

        motion = new BoneMotion();
        motion.getPosition().setXpos(1.0);
        assertFalse(motion.hasImplicitPosition());

        motion = new BoneMotion();
        motion.getPosCurve().getIntpltYpos().setP1((byte)21, (byte)21);
        assertFalse(motion.hasImplicitPosition());

        return;
    }

    /**
     * Test of toString method, of class BoneMotion.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        BoneMotion motion;

        motion = new BoneMotion();
        assertEquals(
                  "bone name : [null] #0\n"
                + "rotation q1=0.0 q2=0.0 q3=0.0 w=1.0"
                + " R-Bezier P1=(20, 20) P2=(107, 107)\n"
                + "position x=0.0 y=0.0 z=0.0\n"
                + "X-Bezier P1=(20, 20) P2=(107, 107)\n"
                + "Y-Bezier P1=(20, 20) P2=(107, 107)\n"
                + "Z-Bezier P1=(20, 20) P2=(107, 107)"
                , motion.toString() );

        motion.setBoneName("bbbone");
        motion.setFrameNumber(99);
        motion.getRotation().setQW(-1.0);
        motion.getIntpltRotation().setP1((byte)1, (byte)2);
        motion.getIntpltRotation().setP2((byte)3, (byte)4);
        motion.getPosition().setPosition(1.0, 2.0, 3.0);
        motion.getPosCurve().getIntpltXpos().setP1((byte)5, (byte)6);
        motion.getPosCurve().getIntpltXpos().setP2((byte)7, (byte)8);
        motion.getPosCurve().getIntpltYpos().setP1((byte)9, (byte)10);
        motion.getPosCurve().getIntpltYpos().setP2((byte)11, (byte)12);
        motion.getPosCurve().getIntpltZpos().setP1((byte)13, (byte)14);
        motion.getPosCurve().getIntpltZpos().setP2((byte)15, (byte)16);

        assertEquals(
                  "bone name : [bbbone] #99\n"
                + "rotation q1=0.0 q2=0.0 q3=0.0 w=-1.0"
                + " R-Bezier P1=(1, 2) P2=(3, 4)\n"
                + "position x=1.0 y=2.0 z=3.0\n"
                + "X-Bezier P1=(5, 6) P2=(7, 8)\n"
                + "Y-Bezier P1=(9, 10) P2=(11, 12)\n"
                + "Z-Bezier P1=(13, 14) P2=(15, 16)"
                , motion.toString() );

        return;
    }

}
