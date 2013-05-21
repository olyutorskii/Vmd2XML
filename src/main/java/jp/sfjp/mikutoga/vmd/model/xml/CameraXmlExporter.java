/*
 * camera xml exporter
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

import java.io.IOException;
import java.util.List;
import jp.sfjp.mikutoga.math.MkPos3D;
import jp.sfjp.mikutoga.vmd.model.BezierParam;
import jp.sfjp.mikutoga.vmd.model.CameraMotion;
import jp.sfjp.mikutoga.vmd.model.CameraRotation;
import jp.sfjp.mikutoga.vmd.model.PosCurve;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import jp.sourceforge.mikutoga.xml.ProxyXmlExporter;

/**
 *カメラ情報のXMLエクスポーター。
 */
class CameraXmlExporter extends ProxyXmlExporter {

    private final ExtraXmlExporter extraExporter;


    /**
     * コンストラクタ。
     * @param delegate 委譲先
     */
    CameraXmlExporter(VmdXmlExporter delegate) {
        super(delegate);
        this.extraExporter = new ExtraXmlExporter(delegate);
        return;
    }


    /**
     * カメラ演出データを出力する。
     * @param vmdMotion 演出データ
     * @throws IOException 出力エラー
     */
    void putCameraSequence(VmdMotion vmdMotion)
            throws IOException{
        List<CameraMotion> list = vmdMotion.getCameraMotionList();
        if( ! list.isEmpty() ){
            ind().putBlockComment(XmlComment.CAMERA_COMMENT);
            ind().putBlockComment(XmlComment.BEZIER_COMMENT);
        }

        ind().putSimpleSTag(VmdTag.CAMERA_SEQUENCE.tag()).ln();

        pushNest();
        if( ! list.isEmpty() ) ln();
        for(CameraMotion camera : list){
            putCameraMotion(camera);
        }
        popNest();

        ind().putETag(VmdTag.CAMERA_SEQUENCE.tag()).ln(2);

        return;
    }

    /**
     * カメラモーションを出力する。
     * @param cameraMotion カメラモーション
     * @throws IOException 出力エラー
     */
    private void putCameraMotion(CameraMotion cameraMotion)
            throws IOException{
        ind().putOpenSTag(VmdTag.CAMERA_MOTION.tag()).sp();
        int frameNo = cameraMotion.getFrameNumber();
        putIntAttr(XmlAttr.ATTR_FRAME, frameNo).sp();
        if( ! cameraMotion.hasPerspective() ){
            putAttr(XmlAttr.ATTR_HAS_PERSPECTIVE, "false").sp();
        }
        putCloseSTag().ln();

        pushNest();
        putCameraTarget(cameraMotion);
        putCameraRotation(cameraMotion);
        putCameraRange(cameraMotion);
        putProjection(cameraMotion);
        popNest();

        ind().putETag(VmdTag.CAMERA_MOTION.tag()).ln(2);

        return;
    }

    /**
     * カメラターゲット情報を出力する。
     * @param cameraMotion カメラモーション
     * @throws IOException 出力エラー
     */
    private void putCameraTarget(CameraMotion cameraMotion)
            throws IOException{
        ind().putOpenSTag(VmdTag.CAMERA_TARGET.tag()).sp();

        MkPos3D position = cameraMotion.getCameraTarget();

        float xPos = (float) position.getXpos();
        float yPos = (float) position.getYpos();
        float zPos = (float) position.getZpos();

        putFloatAttr(XmlAttr.ATTR_X_POS, xPos).sp();
        putFloatAttr(XmlAttr.ATTR_Y_POS, yPos).sp();
        putFloatAttr(XmlAttr.ATTR_Z_POS, zPos).sp();

        PosCurve posCurve = cameraMotion.getTargetPosCurve();
        if(posCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();

            pushNest();
            this.extraExporter.putPositionCurve(posCurve);
            popNest();

            ind().putETag(VmdTag.CAMERA_TARGET.tag()).ln();
        }

        return;
    }

    /**
     * カメラ回転情報を出力する。
     * @param cameraMotion カメラモーション
     * @throws IOException 出力エラー
     */
    private void putCameraRotation(CameraMotion cameraMotion)
            throws IOException{
        ind().putOpenSTag(VmdTag.CAMERA_ROTATION.tag()).sp();

        CameraRotation rotation = cameraMotion.getCameraRotation();

        float latitude  = (float) rotation.getLatitude();
        float longitude = (float) rotation.getLongitude();
        float roll      = (float) rotation.getRoll();

        putFloatAttr(XmlAttr.ATTR_X_RAD, latitude) .sp();
        putFloatAttr(XmlAttr.ATTR_Y_RAD, longitude).sp();
        putFloatAttr(XmlAttr.ATTR_Z_RAD, roll)     .sp();

        BezierParam rotCurve = cameraMotion.getIntpltRotation();
        if(rotCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();
            pushNest();
            ind();
            this.extraExporter.putBezierCurve(rotCurve);
            ln();
            popNest();
            ind().putETag(VmdTag.CAMERA_ROTATION.tag()).ln();
        }

        return;
    }

    /**
     * カメラ距離情報を出力する。
     * @param cameraMotion カメラモーション
     * @throws IOException 出力エラー
     */
    private void putCameraRange(CameraMotion cameraMotion)
            throws IOException{
        ind().putOpenSTag(VmdTag.CAMERA_RANGE.tag()).sp();

        float range = (float) cameraMotion.getRange();
        putFloatAttr(XmlAttr.ATTR_RANGE, range).sp();

        BezierParam rangeCurve = cameraMotion.getIntpltRange();
        if(rangeCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();
            pushNest();
            ind();
            this.extraExporter.putBezierCurve(rangeCurve);
            ln();
            popNest();
            ind().putETag(VmdTag.CAMERA_RANGE.tag()).ln();
        }

        return;
    }

    /**
     * スクリーン投影情報を出力する。
     * @param cameraMotion カメラモーション
     * @throws IOException 出力エラー
     */
    private void putProjection(CameraMotion cameraMotion)
            throws IOException{
        ind().putOpenSTag(VmdTag.PROJECTION.tag()).sp();

        int angle = cameraMotion.getProjectionAngle();
        putIntAttr(XmlAttr.ATTR_VERT_DEG, angle).sp();

        BezierParam projCurve = cameraMotion.getIntpltProjection();
        if(projCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();
            pushNest();
            ind();
            this.extraExporter.putBezierCurve(projCurve);
            ln();
            popNest();
            ind().putETag(VmdTag.PROJECTION.tag()).ln();
        }

        return;
    }

}
