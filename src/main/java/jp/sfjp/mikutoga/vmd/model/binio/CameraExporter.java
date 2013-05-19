/*
 * camera information exporter
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.binio;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import jp.sfjp.mikutoga.bin.export.BinaryExporter;
import jp.sfjp.mikutoga.math.MkPos3D;
import jp.sfjp.mikutoga.vmd.model.BezierParam;
import jp.sfjp.mikutoga.vmd.model.CameraMotion;
import jp.sfjp.mikutoga.vmd.model.CameraRotation;
import jp.sfjp.mikutoga.vmd.model.PosCurve;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;

/**
 * カメラ情報のエクスポーター。
 */
class CameraExporter extends BinaryExporter {

    /**
     * コンストラクタ。
     * @param stream 出力ストリーム
     */
    CameraExporter(OutputStream stream){
        super(stream);
        return;
    }


    /**
     * カメラモーション情報を出力する。
     * @param motion モーションデータ
     * @throws IOException 出力エラー
     */
    void dumpCameraMotion(VmdMotion motion) throws IOException{
        List<CameraMotion> list = motion.getCameraMotionList();

        int count = list.size();
        dumpLeInt(count);

        for(CameraMotion cameraMotion : list){
            int frame = cameraMotion.getFrameNumber();
            dumpLeInt(frame);

            float range = (float) cameraMotion.getRange();
            dumpLeFloat(range);

            MkPos3D targetPos = cameraMotion.getCameraTarget();
            dumpLeFloat((float) targetPos.getXpos());
            dumpLeFloat((float) targetPos.getYpos());
            dumpLeFloat((float) targetPos.getZpos());

            CameraRotation rotation = cameraMotion.getCameraRotation();
            dumpLeFloat((float) rotation.getLatitude());
            dumpLeFloat((float) rotation.getLongitude());
            dumpLeFloat((float) rotation.getRoll());

            dumpCameraCurve(cameraMotion);

            dumpLeInt(cameraMotion.getProjectionAngle());

            byte perspectiveSwitch;
            if(cameraMotion.hasPerspective()) perspectiveSwitch = 0x00;
            else                              perspectiveSwitch = 0x01;
            dumpByte(perspectiveSwitch);
        }

        return;
    }

    /**
     * カメラ補間情報を出力する。
     * @param cameraMotion モーションデータ
     * @throws IOException 出力エラー
     */
    private void dumpCameraCurve(CameraMotion cameraMotion)
            throws IOException{
        PosCurve posCurve = cameraMotion.getTargetPosCurve();
        BezierParam xCurve = posCurve.getIntpltXpos();
        BezierParam yCurve = posCurve.getIntpltYpos();
        BezierParam zCurve = posCurve.getIntpltZpos();
        dumpCameraBezier(xCurve);
        dumpCameraBezier(yCurve);
        dumpCameraBezier(zCurve);

        BezierParam rotCurve   = cameraMotion.getIntpltRotation();
        BezierParam rangeCurve = cameraMotion.getIntpltRange();
        BezierParam projCurve  = cameraMotion.getIntpltProjection();
        dumpCameraBezier(rotCurve);
        dumpCameraBezier(rangeCurve);
        dumpCameraBezier(projCurve);

        return;
    }

    /**
     * ベジェ曲線情報を出力する。
     * @param bezier ベジェ曲線
     * @throws IOException 出力エラー
     */
    private void dumpCameraBezier(BezierParam bezier) throws IOException{
        dumpByte(bezier.getP1x());
        dumpByte(bezier.getP2x());
        dumpByte(bezier.getP1y());
        dumpByte(bezier.getP2y());

        return;
    }

}
