/*
 * motion listener from XML
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

import jp.sfjp.mikutoga.math.MkPos3D;
import jp.sfjp.mikutoga.math.MkQuat;
import jp.sfjp.mikutoga.vmd.model.BezierParam;
import jp.sfjp.mikutoga.vmd.model.BoneMotion;
import jp.sfjp.mikutoga.vmd.model.MorphMotion;
import jp.sfjp.mikutoga.vmd.model.PosCurve;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import org.xml.sax.Attributes;

/**
 * モーション関連のXML要素出現イベントを受信する。
 */
class SaxMotionListener extends SaxVmdListener{

    private BoneMotion boneMotion;
    private String boneName;
    private String morphName;


    /**
     * コンストラクタ。
     */
    SaxMotionListener() {
        super();
        return;
    }


    /**
     * {@inheritDoc}
     * @param tag {@inheritDoc}
     * @param attr {@inheritDoc}
     */
    @Override
    void openTag(VmdTag tag, Attributes attr){
        switch(tag){
        case BONE_PART:
            openBonePart(attr);
            break;
        case BONE_MOTION:
            openBoneMotion(attr);
            break;
        case BONE_POSITION:
            openBonePosition(attr);
            break;
        case BONE_ROT_QUAT:
            openBoneRotQuat(attr);
            break;
        case BONE_ROT_EYXZ:
            openBoneRotEyxz(attr);
            break;

        case MORPH_PART:
            openMorphPart(attr);
            break;
        case MORPH_MOTION:
            openMorphMotion(attr);
            break;

        default:
            super.openTag(tag, attr);
            break;
        }

        return;
    }

    /**
     * {@inheritDoc}
     * @param tag {@inheritDoc}
     */
    @Override
    void closeTag(VmdTag tag){
        switch(tag){
        case BONE_MOTION:
            closeBoneMotion();
            break;
        case BONE_POSITION:
            setCurrentPosCurve(null);
            break;
        case BONE_ROT_QUAT:
        case BONE_ROT_EYXZ:
            setCurrentBezierParam(null);
            break;
        default:
            super.closeTag(tag);
            break;
        }

        return;
    }

    /**
     * bonePart要素開始の通知。
     * @param attr 属性群
     */
    private void openBonePart(Attributes attr){
        this.boneName = SaxAttr.getStringAttr(attr, XmlAttr.ATTR_NAME);
        return;
    }

    /**
     * boneMotion要素開始の通知。
     * @param attr 属性群
     */
    private void openBoneMotion(Attributes attr){
        this.boneMotion = new BoneMotion();
        this.boneMotion.setBoneName(this.boneName);

        int frameNo = SaxAttr.getIntAttr(attr, XmlAttr.ATTR_FRAME);
        this.boneMotion.setFrameNumber(frameNo);

        return;
    }

    /**
     * boneMotion要素終了の通知。
     */
    private void closeBoneMotion(){
        VmdMotion motion = getVmdMotion();
        motion.addBoneMotion(this.boneMotion);
        return;
    }

    /**
     * bonePosition要素開始の通知。
     * @param attr 属性群
     */
    private void openBonePosition(Attributes attr){
        MkPos3D position = this.boneMotion.getPosition();

        float xPos = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_X_POS);
        float yPos = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_Y_POS);
        float zPos = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_Z_POS);

        position.setXpos(xPos);
        position.setYpos(yPos);
        position.setZpos(zPos);

        PosCurve curve = this.boneMotion.getPosCurve();
        setCurrentPosCurve(curve);

        return;
    }

    /**
     * boneRotQuat要素開始の通知。
     * @param attr 属性群
     */
    private void openBoneRotQuat(Attributes attr){
        MkQuat rotation = this.boneMotion.getRotation();

        float qx = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_QX);
        float qy = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_QY);
        float qz = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_QZ);
        float qw = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_QW);

        rotation.setQ1(qx);
        rotation.setQ2(qy);
        rotation.setQ3(qz);
        rotation.setQW(qw);

        BezierParam bez = this.boneMotion.getIntpltRotation();
        setCurrentBezierParam(bez);

        return;
    }

    /**
     * boneRotEyxz要素開始の通知。
     * @param attr 属性群
     */
    private void openBoneRotEyxz(Attributes attr){
        MkQuat rotation = this.boneMotion.getRotation();

        float xDeg = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_X_DEG);
        float yDeg = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_Y_DEG);
        float zDeg = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_Z_DEG);

        float xRad = (float)StrictMath.toRadians(xDeg);
        float yRad = (float)StrictMath.toRadians(yDeg);
        float zRad = (float)StrictMath.toRadians(zDeg);

        rotation.setEulerYXZ(xRad, yRad, zRad);

        BezierParam bez = this.boneMotion.getIntpltRotation();
        setCurrentBezierParam(bez);

        return;
    }

    /**
     * morphPart要素開始の通知。
     * @param attr 属性群
     */
    private void openMorphPart(Attributes attr){
        this.morphName = SaxAttr.getStringAttr(attr, XmlAttr.ATTR_NAME);
        return;
    }

    /**
     * morphMotion要素開始の通知。
     * @param attr 属性群
     */
    private void openMorphMotion(Attributes attr){
        MorphMotion morphMotion = new MorphMotion();

        int frameNo = SaxAttr.getIntAttr(attr, XmlAttr.ATTR_FRAME);
        float flex = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_FLEX);

        morphMotion.setMorphName(this.morphName);
        morphMotion.setFrameNumber(frameNo);
        morphMotion.setFlex(flex);

        VmdMotion motion = getVmdMotion();
        motion.addMorphMotion(morphMotion);

        return;
    }

}
