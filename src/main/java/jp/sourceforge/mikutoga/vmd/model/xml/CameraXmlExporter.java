/*
 * camera xml exporter
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sourceforge.mikutoga.vmd.model.xml;

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
class CameraXmlExporter extends ProxyXmlExporter{


    /**
     * コンストラクタ。
     * @param proxy 委譲先
     */
    CameraXmlExporter(VmdXmlExporter proxy) {
        super(proxy);
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
            ind().putBlockComment(XmlSyms.CAMERA_COMMENT);
            ind().putBlockComment(XmlSyms.BEZIER_COMMENT);
        }

        ind().putSimpleSTag(XmlSyms.TAG_CAMERA_SEQUENCE).ln();

        pushNest();
        if( ! list.isEmpty() ) ln();
        for(CameraMotion camera : list){
            putCameraMotion(camera);
        }
        popNest();

        ind().putETag(XmlSyms.TAG_CAMERA_SEQUENCE).ln(2);

        return;
    }

    /**
     * カメラモーションを出力する。
     * @param cameraMotion カメラモーション
     * @throws IOException 出力エラー
     */
    private void putCameraMotion(CameraMotion cameraMotion)
            throws IOException{
        ind().putOpenSTag(XmlSyms.TAG_CAMERA_MOTION).sp();
        int frameNo = cameraMotion.getFrameNumber();
        putIntAttr(XmlSyms.ATTR_FRAME, frameNo).sp();
        if( ! cameraMotion.hasPerspective() ){
            putAttr(XmlSyms.ATTR_HAS_PERSPECTIVE, "false").sp();
        }
        putCloseSTag().ln();

        pushNest();
        putCameraTarget(cameraMotion);
        putCameraRotation(cameraMotion);
        putCameraRange(cameraMotion);
        putProjection(cameraMotion);
        popNest();

        ind().putETag(XmlSyms.TAG_CAMERA_MOTION).ln(2);

        return;
    }

    /**
     * カメラターゲット情報を出力する。
     * @param cameraMotion カメラモーション
     * @throws IOException 出力エラー
     */
    private void putCameraTarget(CameraMotion cameraMotion)
            throws IOException{
        ind().putOpenSTag(XmlSyms.TAG_CAMERA_TARGET).sp();
        MkPos3D position = cameraMotion.getCameraTarget();
        putFloatAttr(XmlSyms.ATTR_X_POS, (float) position.getXpos()).sp();
        putFloatAttr(XmlSyms.ATTR_Y_POS, (float) position.getYpos()).sp();
        putFloatAttr(XmlSyms.ATTR_Z_POS, (float) position.getZpos()).sp();

        PosCurve posCurve = cameraMotion.getTargetPosCurve();
        if(posCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();

            pushNest();
            putPositionCurve(posCurve);
            popNest();

            ind().putETag(XmlSyms.TAG_CAMERA_TARGET).ln();
        }

        return;
    }

    /**
     * 位置移動補間カーブを出力する。
     * @param posCurve 移動補間情報
     * @throws IOException 出力エラー
     */
    private void putPositionCurve(PosCurve posCurve)
            throws IOException{
        BezierParam xCurve = posCurve.getIntpltXpos();
        BezierParam yCurve = posCurve.getIntpltYpos();
        BezierParam zCurve = posCurve.getIntpltZpos();

        ind().putLineComment("X-Y-Z interpolation *3").ln();

        ind();
        putBezierCurve(xCurve);
        ln();

        ind();
        putBezierCurve(yCurve);
        ln();

        ind();
        putBezierCurve(zCurve);
        ln();

        return;
    }

    /**
     * カメラ回転情報を出力する。
     * @param cameraMotion カメラモーション
     * @throws IOException 出力エラー
     */
    private void putCameraRotation(CameraMotion cameraMotion)
            throws IOException{
        ind().putOpenSTag(XmlSyms.TAG_CAMERA_ROTATION).sp();
        CameraRotation rotation = cameraMotion.getCameraRotation();
        float latitude = (float) rotation.getLatitude();
        float longitude = (float) rotation.getLongitude();
        float roll = (float) rotation.getRoll();
        putFloatAttr(XmlSyms.ATTR_X_RAD, latitude).sp();
        putFloatAttr(XmlSyms.ATTR_Y_RAD, longitude).sp();
        putFloatAttr(XmlSyms.ATTR_Z_RAD, roll).sp();

        BezierParam rotCurve = cameraMotion.getIntpltRotation();
        if(rotCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();
            pushNest();
            ind();
            putBezierCurve(rotCurve);
            ln();
            popNest();
            ind().putETag(XmlSyms.TAG_CAMERA_ROTATION).ln();
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
        ind().putOpenSTag(XmlSyms.TAG_CAMERA_RANGE).sp();
        float range = (float) cameraMotion.getRange();
        putFloatAttr(XmlSyms.ATTR_RANGE, range).sp();

        BezierParam rangeCurve = cameraMotion.getIntpltRange();
        if(rangeCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();
            pushNest();
            ind();
            putBezierCurve(rangeCurve);
            ln();
            popNest();
            ind().putETag(XmlSyms.TAG_CAMERA_RANGE).ln();
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
        ind().putOpenSTag(XmlSyms.TAG_PROJECTION).sp();
        int angle = cameraMotion.getProjectionAngle();
        putIntAttr(XmlSyms.ATTR_VERT_DEG, angle).sp();

        BezierParam projCurve = cameraMotion.getIntpltProjection();
        if(projCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();
            pushNest();
            ind();
            putBezierCurve(projCurve);
            ln();
            popNest();
            ind().putETag(XmlSyms.TAG_PROJECTION).ln();
        }

        return;
    }

    /**
     * ベジェ曲線による補間曲線情報を出力する。
     * @param bezier ベジェ曲線
     * @throws IOException 出力エラー
     */
    private void putBezierCurve(BezierParam bezier)
            throws IOException{
        if(bezier.isDefaultLinear()){
            putSimpleEmpty(XmlSyms.TAG_DEF_LINEAR);
        }else if(bezier.isDefaultEaseInOut()){
            putSimpleEmpty(XmlSyms.TAG_DEF_EASE_IN_OUT);
        }else{
            putOpenSTag(XmlSyms.TAG_BEZIER).sp();
            putIntAttr(XmlSyms.ATTR_P1X, bezier.getP1x()).sp();
            putIntAttr(XmlSyms.ATTR_P1Y, bezier.getP1y()).sp();
            putIntAttr(XmlSyms.ATTR_P2X, bezier.getP2x()).sp();
            putIntAttr(XmlSyms.ATTR_P2Y, bezier.getP2y()).sp();
            putCloseEmpty();
        }
        return;
    }

}
