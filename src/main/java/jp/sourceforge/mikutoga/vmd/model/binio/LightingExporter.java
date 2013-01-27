/*
 * lighting information exporter
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sourceforge.mikutoga.vmd.model.binio;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import jp.sfjp.mikutoga.bin.export.BinaryExporter;
import jp.sourceforge.mikutoga.vmd.model.LuminousColor;
import jp.sourceforge.mikutoga.vmd.model.LuminousMotion;
import jp.sourceforge.mikutoga.vmd.model.LuminousVector;
import jp.sourceforge.mikutoga.vmd.model.ShadowMode;
import jp.sourceforge.mikutoga.vmd.model.ShadowMotion;
import jp.sourceforge.mikutoga.vmd.model.VmdMotion;

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

            LuminousVector vector = luminousMotion.getDirection();
            dumpLeFloat(vector.getVecX());
            dumpLeFloat(vector.getVecY());
            dumpLeFloat(vector.getVecZ());
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

            float rawParam = shadowMotion.getRawScopeParam();
            dumpLeFloat(rawParam);
        }

        return;
    }

}
