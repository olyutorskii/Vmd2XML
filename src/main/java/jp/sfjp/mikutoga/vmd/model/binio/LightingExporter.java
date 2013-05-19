/*
 * lighting information exporter
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.binio;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import jp.sfjp.mikutoga.bin.export.BinaryExporter;
import jp.sfjp.mikutoga.math.MkVec3D;
import jp.sfjp.mikutoga.vmd.model.LuminousColor;
import jp.sfjp.mikutoga.vmd.model.LuminousMotion;
import jp.sfjp.mikutoga.vmd.model.ShadowMode;
import jp.sfjp.mikutoga.vmd.model.ShadowMotion;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;

/**
 * ライティング情報のエクスポーター。
 */
class LightingExporter extends BinaryExporter {

    /**
     * コンストラクタ。
     * @param stream 出力ストリーム
     */
    LightingExporter(OutputStream stream){
        super(stream);
        return;
    }


    /**
     * 照明情報を出力する。
     * @param motion モーションデータ
     * @throws IOException 出力エラー
     */
    void dumpLuminousMotion(VmdMotion motion) throws IOException{
        List<LuminousMotion> list = motion.getLuminousMotionList();

        int count = list.size();
        dumpLeInt(count);

        for(LuminousMotion luminousMotion : list){
            int frame = luminousMotion.getFrameNumber();
            dumpLeInt(frame);

            LuminousColor color = luminousMotion.getColor();
            dumpLeFloat(color.getColR());
            dumpLeFloat(color.getColG());
            dumpLeFloat(color.getColB());

            MkVec3D vector = luminousMotion.getDirection();
            dumpLeFloat((float) vector.getXVal());
            dumpLeFloat((float) vector.getYVal());
            dumpLeFloat((float) vector.getZVal());
        }

        return;
    }

    /**
     * シャドウ演出情報を出力する。
     * @param motion モーションデータ
     * @throws IOException 出力エラー
     */
    void dumpShadowMotion(VmdMotion motion) throws IOException{
        List<ShadowMotion> list = motion.getShadowMotionList();

        int count = list.size();
        dumpLeInt(count);

        for(ShadowMotion shadowMotion : list){
            int frame = shadowMotion.getFrameNumber();
            dumpLeInt(frame);

            ShadowMode mode = shadowMotion.getShadowMode();
            byte shadowType = mode.getEncodedByte();
            dumpByte(shadowType);

            float rawParam = (float) shadowMotion.getRawScopeParam();
            dumpLeFloat(rawParam);
        }

        return;
    }

}
