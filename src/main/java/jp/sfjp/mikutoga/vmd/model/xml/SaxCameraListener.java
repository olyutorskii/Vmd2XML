/*
 * camera listener from XML
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

import java.util.List;
import jp.sfjp.mikutoga.math.MkPos3D;
import jp.sfjp.mikutoga.vmd.model.BezierParam;
import jp.sfjp.mikutoga.vmd.model.CameraMotion;
import jp.sfjp.mikutoga.vmd.model.CameraRotation;
import jp.sfjp.mikutoga.vmd.model.PosCurve;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import jp.sfjp.mikutoga.xml.SaxAttr;
import org.xml.sax.Attributes;

/**
 * カメラ関連のXML要素出現イベントを受信する。
 */
class SaxCameraListener extends SaxVmdListener{

    private CameraMotion currentCamera = null;


    /**
     * コンストラクタ。
     */
    SaxCameraListener(){
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
        case CAMERA_MOTION:
            openCameraMotion(attr);
            break;
        case CAMERA_TARGET:
            openCameraTarget(attr);
            break;
        case CAMERA_ROTATION:
            openCameraRotation(attr);
            break;
        case CAMERA_RANGE:
            openCameraRange(attr);
            break;
        case PROJECTION:
            openProjection(attr);
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
        case CAMERA_MOTION:
            closeCameraMotion();
            break;
        case CAMERA_TARGET:
            setCurrentPosCurve(null);
            break;
        case CAMERA_ROTATION:
        case CAMERA_RANGE:
        case PROJECTION:
            setCurrentBezierParam(null);
            break;
        default:
            super.closeTag(tag);
            break;
        }

        return;
    }

    /**
     * cameraMotion要素開始の通知。
     * @param attr 属性群
     */
    private void openCameraMotion(Attributes attr){
        this.currentCamera = new CameraMotion();

        int frameNo = SaxAttr.getIntAttr(attr, XmlAttr.ATTR_FRAME);
        this.currentCamera.setFrameNumber(frameNo);

        boolean hasPerspective =
                SaxAttr.getBooleanAttr(attr,
                                       XmlAttr.ATTR_HAS_PERSPECTIVE,
                                       true );
        this.currentCamera.setPerspectiveMode(hasPerspective);

        return;
    }

    /**
     * cameraMotion要素終了の通知。
     */
    private void closeCameraMotion(){
        VmdMotion motion = getVmdMotion();
        List<CameraMotion> cameraList = motion.getCameraMotionList();
        cameraList.add(this.currentCamera);

        this.currentCamera = null;

        return;
    }

    /**
     * cameraTarget要素開始の通知。
     * @param attr 属性群
     */
    private void openCameraTarget(Attributes attr){
        MkPos3D targetPos = this.currentCamera.getCameraTarget();

        float xPos = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_X_POS);
        float yPos = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_Y_POS);
        float zPos = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_Z_POS);

        targetPos.setPosition(xPos, yPos, zPos);

        PosCurve curve = this.currentCamera.getTargetPosCurve();
        setCurrentPosCurve(curve);

        return;
    }

    /**
     * cameraRotation要素開始の通知。
     * @param attr 属性群
     */
    private void openCameraRotation(Attributes attr){
        CameraRotation cameraRotation =
                this.currentCamera.getCameraRotation();

        float latitude  = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_X_RAD);
        float longitude = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_Y_RAD);
        float roll      = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_Z_RAD);

        cameraRotation.setLatitude(latitude);
        cameraRotation.setLongitude(longitude);
        cameraRotation.setRoll(roll);

        BezierParam bez = this.currentCamera.getIntpltRotation();
        setCurrentBezierParam(bez);

        return;
    }

    /**
     * cameraRange要素開始の通知。
     * @param attr 属性群
     */
    private void openCameraRange(Attributes attr){
        float range = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_RANGE);
        this.currentCamera.setRange(range);

        BezierParam bez = this.currentCamera.getIntpltRange();
        setCurrentBezierParam(bez);

        return;
    }

    /**
     * projection要素開始の通知。
     * @param attr 属性群
     */
    private void openProjection(Attributes attr){
        int vertDeg = SaxAttr.getIntAttr(attr, XmlAttr.ATTR_VERT_DEG);
        this.currentCamera.setProjectionAngle(vertDeg);

        BezierParam bez = this.currentCamera.getIntpltProjection();
        setCurrentBezierParam(bez);

        return;
    }

}
