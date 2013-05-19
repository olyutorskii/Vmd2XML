/*
 * lighting xml exporter
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sourceforge.mikutoga.vmd.model.xml;

import java.io.IOException;
import java.util.List;
import jp.sfjp.mikutoga.math.MkVec3D;
import jp.sfjp.mikutoga.vmd.model.LuminousColor;
import jp.sfjp.mikutoga.vmd.model.LuminousMotion;
import jp.sfjp.mikutoga.vmd.model.ShadowMode;
import jp.sfjp.mikutoga.vmd.model.ShadowMotion;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import jp.sourceforge.mikutoga.xml.ProxyXmlExporter;
import jp.sourceforge.mikutoga.xml.XmlExporter;

/**
 * ライティング情報のXMLエクスポーター。
 */
class LightingXmlExpoter extends ProxyXmlExporter {

    /**
     * コンストラクタ。
     * @param proxy 委譲先
     */
    LightingXmlExpoter(XmlExporter proxy) {
        super(proxy);
        return;
    }

    /**
     * 照明演出データを出力する。
     * @param vmdMotion 演出データ
     * @throws IOException 出力エラー
     */
    void putLuminousSequence(VmdMotion vmdMotion)
            throws IOException{
        ind().putSimpleSTag(XmlSyms.TAG_LUMI_SEQUENCE).ln();

        pushNest();
        List<LuminousMotion> list = vmdMotion.getLuminousMotionList();
        if( ! list.isEmpty() ) ln();
        for(LuminousMotion luminous : list){
            putLuminousMotion(luminous);
        }
        popNest();

        ind().putETag(XmlSyms.TAG_LUMI_SEQUENCE).ln(2);

        return;
    }

    /**
     * 照明モーションを出力する。
     * @param luminousMotion 照明モーション
     * @throws IOException 出力エラー
     */
    private void putLuminousMotion(LuminousMotion luminousMotion)
            throws IOException{
        ind().putOpenSTag(XmlSyms.TAG_LUMINOUS_ACT).sp();
        int frameNo = luminousMotion.getFrameNumber();
        putIntAttr(XmlSyms.ATTR_FRAME, frameNo).sp();
        putCloseSTag().ln();

        LuminousColor color = luminousMotion.getColor();
        MkVec3D vector = luminousMotion.getDirection();

        pushNest();
        putLuminousColor(color);
        putLuminousDirection(vector);
        popNest();

        ind().putETag(XmlSyms.TAG_LUMINOUS_ACT).ln(2);

        return;
    }

    /**
     * 光源色情報を出力する。
     * @param color 光源色
     * @throws IOException 出力エラー
     */
    private void putLuminousColor(LuminousColor color)
            throws IOException{
        ind().putOpenSTag(XmlSyms.TAG_LUMI_COLOR).sp();
        putFloatAttr(XmlSyms.ATTR_R_COL, color.getColR()).sp();
        putFloatAttr(XmlSyms.ATTR_G_COL, color.getColG()).sp();
        putFloatAttr(XmlSyms.ATTR_B_COL, color.getColB()).sp();
        putCloseEmpty().ln();

        return;
    }

    /**
     * 照明方向情報を出力する。
     * @param vector 照明方向
     * @throws IOException 出力エラー
     */
    private void putLuminousDirection(MkVec3D vector)
            throws IOException{
        ind().putOpenSTag(XmlSyms.TAG_LUMI_DIRECTION).sp();
        putFloatAttr(XmlSyms.ATTR_X_VEC, (float) vector.getXVal()).sp();
        putFloatAttr(XmlSyms.ATTR_Y_VEC, (float) vector.getYVal()).sp();
        putFloatAttr(XmlSyms.ATTR_Z_VEC, (float) vector.getZVal()).sp();
        putCloseEmpty().ln();

        return;
    }

    /**
     * シャドウ演出データを出力する。
     * @param vmdMotion 演出データ
     * @throws IOException 出力エラー
     */
    void putShadowSequence(VmdMotion vmdMotion)
            throws IOException{
        List<ShadowMotion> list = vmdMotion.getShadowMotionList();
        if( ! list.isEmpty() ){
            ind().putBlockComment(XmlSyms.SHADOW_COMMENT);
        }

        ind().putSimpleSTag(XmlSyms.TAG_SHADOW_SEQUENCE).ln();

        pushNest();
        for(ShadowMotion shadow : list){
            putShadowMotion(shadow);
        }
        popNest();

        ind().putETag(XmlSyms.TAG_SHADOW_SEQUENCE).ln(2);

        return;
    }

    /**
     * シャドウモーションを出力する。
     * @param shadowMotion シャドウモーション
     * @throws IOException 出力エラー
     */
    private void putShadowMotion(ShadowMotion shadowMotion)
            throws IOException{
        ind().putOpenSTag(XmlSyms.TAG_SHADOW_ACT).sp();

        int frameNo = shadowMotion.getFrameNumber();
        ShadowMode mode = shadowMotion.getShadowMode();
        float rawParam = (float) shadowMotion.getRawScopeParam();

        putIntAttr(XmlSyms.ATTR_FRAME, frameNo).sp();
        putAttr(XmlSyms.ATTR_MODE, mode.name()).sp();
        putFloatAttr(XmlSyms.ATTR_RAW_PARAM, rawParam).sp();

        putCloseEmpty();

        double uiVal = ShadowMotion.rawParamToScope(rawParam);
        long lVal = Math.round(uiVal);
        sp().putLineComment("UI:" + lVal).ln();

        return;
    }

}
