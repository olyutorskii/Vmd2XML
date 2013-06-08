/*
 */

package jp.sfjp.mikutoga.vmd.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class VmdMotionTest {

    public VmdMotionTest() {
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
     * Test of getせｔModelName method, of class VmdMotion.
     */
    @Test
    public void testGetSetModelName() {
        System.out.println("get/setModelName");

        VmdMotion motion;

        motion = new VmdMotion();

        assertEquals("カメラ・照明", motion.getModelName());

        motion.setModelName("");
        assertEquals("", motion.getModelName());

        motion.setModelName("model");
        assertEquals("model", motion.getModelName());

        try{
            motion.setModelName(null);
            fail();
        }catch(NullPointerException e){
            // GOOD
        }

        return;
    }

    /**
     * Test of isModelMotion method, of class VmdMotion.
     */
    @Test
    public void testIsModelMotion() {
        System.out.println("isModelMotion");

        VmdMotion motion;

        motion = new VmdMotion();

        assertFalse(motion.isModelMotion());

        motion.setModelName("model");
        assertTrue(motion.isModelMotion());

        motion.setModelName("カメラ・照明");
        assertFalse(motion.isModelMotion());

        return;
    }

    /**
     * Test of getBonePartMap method, of class VmdMotion.
     */
    @Test
    public void testGetBonePartMap() {
        System.out.println("getBonePartMap");

        VmdMotion motion;
        Map<String, List<BoneMotion>> map;

        motion = new VmdMotion();

        map = motion.getBonePartMap();
        assertNotNull(map);
        assertTrue(map.isEmpty());

        return;
    }

    /**
     * Test of getMorphPartMap method, of class VmdMotion.
     */
    @Test
    public void testGetMorphPartMap() {
        System.out.println("getMorphPartMap");

        VmdMotion motion;
        Map<String, List<MorphMotion>> map;

        motion = new VmdMotion();

        map = motion.getMorphPartMap();
        assertNotNull(map);
        assertTrue(map.isEmpty());

        return;
    }

    /**
     * Test of getCameraMotionList method, of class VmdMotion.
     */
    @Test
    public void testGetCameraMotionList() {
        System.out.println("getCameraMotionList");

        VmdMotion motion;
        List<CameraMotion> list;

        motion = new VmdMotion();

        list = motion.getCameraMotionList();
        assertNotNull(list);
        assertTrue(list.isEmpty());

        return;
    }

    /**
     * Test of getLuminousMotionList method, of class VmdMotion.
     */
    @Test
    public void testGetLuminousMotionList() {
        System.out.println("getLuminousMotionList");

        VmdMotion motion;
        List<LuminousMotion> list;

        motion = new VmdMotion();

        list = motion.getLuminousMotionList();
        assertNotNull(list);
        assertTrue(list.isEmpty());

        return;
    }

    /**
     * Test of getShadowMotionList method, of class VmdMotion.
     */
    @Test
    public void testGetShadowMotionList() {
        System.out.println("getShadowMotionList");

        VmdMotion motion;
        List<ShadowMotion> list;

        motion = new VmdMotion();

        list = motion.getShadowMotionList();
        assertNotNull(list);
        assertTrue(list.isEmpty());

        return;
    }

    /**
     * Test of addBoneMotion method, of class VmdMotion.
     */
    @Test
    public void testAddBoneMotion() {
        System.out.println("addBoneMotion");

        VmdMotion motion;
        BoneMotion bone;

        motion = new VmdMotion();

        bone = new BoneMotion();
        bone.setBoneName("X");
        bone.setFrameNumber(99);
        motion.addBoneMotion(bone);

        bone = new BoneMotion();
        bone.setBoneName("Y");
        bone.setFrameNumber(9);
        motion.addBoneMotion(bone);

        bone = new BoneMotion();
        bone.setBoneName("A");
        bone.setFrameNumber(10);
        motion.addBoneMotion(bone);

        bone = new BoneMotion();
        bone.setBoneName("Y");
        bone.setFrameNumber(1);
        motion.addBoneMotion(bone);

        Map<String, List<BoneMotion>> map = motion.getBonePartMap();

        Set<String> keySet = map.keySet();
        assertEquals(3, keySet.size());
        Iterator<String> it;
        it = keySet.iterator();
        assertEquals("X", it.next());
        assertEquals("Y", it.next());
        assertEquals("A", it.next());

        assertEquals(1, map.get("X").size());
        assertEquals(2, map.get("Y").size());
        assertEquals(1, map.get("A").size());

        assertEquals(99, map.get("X").get(0).getFrameNumber());
        assertEquals(9, map.get("Y").get(0).getFrameNumber());
        assertEquals(1, map.get("Y").get(1).getFrameNumber());
        assertEquals(10, map.get("A").get(0).getFrameNumber());

        return;
    }

    /**
     * Test of addMorphMotion method, of class VmdMotion.
     */
    @Test
    public void testAddMorphMotion() {
        System.out.println("addMorphMotion");

        VmdMotion motion;
        MorphMotion morph;

        motion = new VmdMotion();

        morph = new MorphMotion();
        morph.setMorphName("X");
        morph.setFrameNumber(99);
        motion.addMorphMotion(morph);

        morph = new MorphMotion();
        morph.setMorphName("Y");
        morph.setFrameNumber(9);
        motion.addMorphMotion(morph);

        morph = new MorphMotion();
        morph.setMorphName("A");
        morph.setFrameNumber(10);
        motion.addMorphMotion(morph);

        morph = new MorphMotion();
        morph.setMorphName("Y");
        morph.setFrameNumber(1);
        motion.addMorphMotion(morph);

        Map<String, List<MorphMotion>> map = motion.getMorphPartMap();

        Set<String> keySet = map.keySet();
        assertEquals(3, keySet.size());
        Iterator<String> it;
        it = keySet.iterator();
        assertEquals("X", it.next());
        assertEquals("Y", it.next());
        assertEquals("A", it.next());

        assertEquals(1, map.get("X").size());
        assertEquals(2, map.get("Y").size());
        assertEquals(1, map.get("A").size());

        assertEquals(99, map.get("X").get(0).getFrameNumber());
        assertEquals(9, map.get("Y").get(0).getFrameNumber());
        assertEquals(1, map.get("Y").get(1).getFrameNumber());
        assertEquals(10, map.get("A").get(0).getFrameNumber());

        return;
    }

    /**
     * Test of frameSort method, of class VmdMotion.
     */
    @Test
    public void testFrameSort() {
        System.out.println("frameSort");

        VmdMotion motion;

        motion = new VmdMotion();

        BoneMotion bmotion;

        bmotion = new BoneMotion();
        bmotion.setBoneName("bone");
        bmotion.setFrameNumber(2);
        motion.addBoneMotion(bmotion);

        bmotion = new BoneMotion();
        bmotion.setBoneName("bone");
        bmotion.setFrameNumber(1);
        motion.addBoneMotion(bmotion);

        MorphMotion mmotion;

        mmotion = new MorphMotion();
        mmotion.setMorphName("morph");
        mmotion.setFrameNumber(20);
        motion.addMorphMotion(mmotion);

        mmotion = new MorphMotion();
        mmotion.setMorphName("morph");
        mmotion.setFrameNumber(10);
        motion.addMorphMotion(mmotion);

        CameraMotion cmotion;

        cmotion = new CameraMotion();
        cmotion.setFrameNumber(200);
        motion.getCameraMotionList().add(cmotion);

        cmotion = new CameraMotion();
        cmotion.setFrameNumber(100);
        motion.getCameraMotionList().add(cmotion);

        LuminousMotion lmotion;

        lmotion = new LuminousMotion();
        lmotion.setFrameNumber(2000);
        motion.getLuminousMotionList().add(lmotion);

        lmotion = new LuminousMotion();
        lmotion.setFrameNumber(1000);
        motion.getLuminousMotionList().add(lmotion);

        ShadowMotion smotion;

        smotion = new ShadowMotion();
        smotion.setFrameNumber(20000);
        motion.getShadowMotionList().add(smotion);

        smotion = new ShadowMotion();
        smotion.setFrameNumber(10000);
        motion.getShadowMotionList().add(smotion);

        motion.frameSort();

        assertEquals(2, motion.getBonePartMap().get("bone").size());
        assertEquals(1, motion.getBonePartMap().get("bone").get(0).getFrameNumber());
        assertEquals(2, motion.getBonePartMap().get("bone").get(1).getFrameNumber());

        assertEquals(2, motion.getMorphPartMap().get("morph").size());
        assertEquals(10, motion.getMorphPartMap().get("morph").get(0).getFrameNumber());
        assertEquals(20, motion.getMorphPartMap().get("morph").get(1).getFrameNumber());

        assertEquals(2, motion.getCameraMotionList().size());
        assertEquals(100, motion.getCameraMotionList().get(0).getFrameNumber());
        assertEquals(200, motion.getCameraMotionList().get(1).getFrameNumber());

        assertEquals(2, motion.getLuminousMotionList().size());
        assertEquals(1000, motion.getLuminousMotionList().get(0).getFrameNumber());
        assertEquals(2000, motion.getLuminousMotionList().get(1).getFrameNumber());

        assertEquals(2, motion.getShadowMotionList().size());
        assertEquals(10000, motion.getShadowMotionList().get(0).getFrameNumber());
        assertEquals(20000, motion.getShadowMotionList().get(1).getFrameNumber());

        return;
    }

    /**
     * Test of toString method, of class VmdMotion.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        VmdMotion motion;

        motion = new VmdMotion();

        assertEquals(
                "model name : カメラ・照明\n"
                +"bone#0 morph#0 camera#0 luminous#0 shadow#0 flag#0",
                motion.toString());

        motion.setModelName("model");
        motion.addBoneMotion(new BoneMotion());
        motion.addMorphMotion(new MorphMotion());
        motion.addMorphMotion(new MorphMotion());
        motion.getCameraMotionList().add(new CameraMotion());
        motion.getCameraMotionList().add(new CameraMotion());
        motion.getCameraMotionList().add(new CameraMotion());
        motion.getLuminousMotionList().add(new LuminousMotion());
        motion.getLuminousMotionList().add(new LuminousMotion());
        motion.getLuminousMotionList().add(new LuminousMotion());
        motion.getLuminousMotionList().add(new LuminousMotion());
        motion.getShadowMotionList().add(new ShadowMotion());
        motion.getShadowMotionList().add(new ShadowMotion());
        motion.getShadowMotionList().add(new ShadowMotion());
        motion.getShadowMotionList().add(new ShadowMotion());
        motion.getShadowMotionList().add(new ShadowMotion());
        motion.getNumberedFlagList().add(new NumberedVmdFlag());
        motion.getNumberedFlagList().add(new NumberedVmdFlag());
        motion.getNumberedFlagList().add(new NumberedVmdFlag());
        motion.getNumberedFlagList().add(new NumberedVmdFlag());
        motion.getNumberedFlagList().add(new NumberedVmdFlag());

        assertEquals(
                "model name : model\n"
                +"bone#1 morph#2 camera#3 luminous#4 shadow#5 flag#5",
                motion.toString());

        return;
    }

}
