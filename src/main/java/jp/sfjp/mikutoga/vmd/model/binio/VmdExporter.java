/*
 * vmd exporter
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.binio;

import java.io.IOException;
import java.io.OutputStream;
import jp.sfjp.mikutoga.bin.export.IllegalTextExportException;
import jp.sfjp.mikutoga.vmd.IllegalVmdDataException;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;

/**
 * VMDファイルのエクスポーター。
 */
public class VmdExporter {

    private BasicExporter    basicExporter = null;
    private CameraExporter   cameraExporter = null;
    private LightingExporter lightingExporter = null;
    private BoolExporter     boolExporter = null;


    /**
     * コンストラクタ。
     */
    public VmdExporter(){
        super();
        return;
    }

    /**
     * モーションデータをVMDファイル形式で出力する。
     * <p>異常時には出力データのフラッシュが試みられる。
     * @param motion モーションデータ
     * @param ostream 出力先ストリーム
     * @throws IOException 出力エラー
     * @throws IllegalVmdDataException モーションデータに不備が発見された
     */
    public void dumpVmdMotion(VmdMotion motion, OutputStream ostream)
            throws IOException, IllegalVmdDataException{
        this.basicExporter    = new BasicExporter(ostream);
        this.cameraExporter   = new CameraExporter(ostream);
        this.lightingExporter = new LightingExporter(ostream);
        this.boolExporter     = new BoolExporter(ostream);

        try{
            dumpVmdMotionImpl(motion);
        }finally{
            ostream.flush();
        }

        return;
    }

    /**
     * モーションデータをVMDファイル形式で出力する。
     * @param motion モーションデータ
     * @throws IOException 出力エラー
     * @throws IllegalVmdDataException モーションデータに不備が発見された
     */
    private void dumpVmdMotionImpl(VmdMotion motion)
            throws IOException, IllegalVmdDataException{
        this.basicExporter.dumpHeader();

        try{
            this.basicExporter.dumpModelName(motion);
            this.basicExporter.dumpBoneMotion(motion);
            this.basicExporter.dumpMorphMotion(motion);
        }catch(IllegalTextExportException e){
            throw new IllegalVmdDataException(e);
        }

        this.cameraExporter.dumpCameraMotion(motion);
        this.lightingExporter.dumpLuminousMotion(motion);
        this.lightingExporter.dumpShadowMotion(motion);

        if(motion.getNumberedFlagList().isEmpty()) return;
        try{
            this.boolExporter.dumpNumberedFlagMotion(motion);
        }catch(IllegalTextExportException e){
            throw new IllegalVmdDataException(e);
        }

        return;
    }

}
