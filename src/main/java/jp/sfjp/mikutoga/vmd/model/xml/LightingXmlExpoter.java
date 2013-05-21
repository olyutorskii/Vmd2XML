/*
 * lighting xml exporter
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

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
     * @param delegate 委譲先
     */
    LightingXmlExpoter(XmlExporter delegate) {
        super(delegate);
        return;
    }


    /**
     * 照明演出データを出力する。
     * @param vmdMotion 演出データ
     * @throws IOException 出力エラー
     */
    void putLuminousSequence(VmdMotion vmdMotion)
            throws IOException{
        ind().putSimpleSTag(VmdTag.LUMI_SEQUENCE.tag()).ln();

        pushNest();
        List<LuminousMotion> list = vmdMotion.getLuminousMotionList();
        if( ! list.isEmpty() ) ln();
        for(LuminousMotion luminous : list){
            putLuminousMotion(luminous);
        }
        popNest();

        ind().putETag(VmdTag.LUMI_SEQUENCE.tag()).ln(2);

        return;
    }

    /**
     * 照明モーションを出力する。
     * @param luminousMotion 照明モーション
     * @throws IOException 出力エラー
     */
    private void putLuminousMotion(LuminousMotion luminousMotion)
            throws IOException{
        ind().putOpenSTag(VmdTag.LUMINOUS_ACT.tag()).sp();

        int frameNo = luminousMotion.getFrameNumber();
        putIntAttr(XmlAttr.ATTR_FRAME, frameNo).sp();

        putCloseSTag().ln();

        LuminousColor color = luminousMotion.getColor();
        MkVec3D vector = luminousMotion.getDirection();

        pushNest();
        putLuminousColor(color);
        putLuminousDirection(vector);
        popNest();

        ind().putETag(VmdTag.LUMINOUS_ACT.tag()).ln(2);

        return;
    }

    /**
     * 光源色情報を出力する。
     * @param color 光源色
     * @throws IOException 出力エラー
     */
    private void putLuminousColor(LuminousColor color)
            throws IOException{
        ind().putOpenSTag(VmdTag.LUMI_COLOR.tag()).sp();

        float colR = color.getColR();
        float colG = color.getColG();
        float colB = color.getColB();

        putFloatAttr(XmlAttr.ATTR_R_COL, colR).sp();
        putFloatAttr(XmlAttr.ATTR_G_COL, colG).sp();
        putFloatAttr(XmlAttr.ATTR_B_COL, colB).sp();

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
        ind().putOpenSTag(VmdTag.LUMI_DIRECTION.tag()).sp();

        float xVec = (float) vector.getXVal();
        float yVec = (float) vector.getYVal();
        float zVec = (float) vector.getZVal();

        putFloatAttr(XmlAttr.ATTR_X_VEC, xVec).sp();
        putFloatAttr(XmlAttr.ATTR_Y_VEC, yVec).sp();
        putFloatAttr(XmlAttr.ATTR_Z_VEC, zVec).sp();

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
            ind().putBlockComment(XmlComment.SHADOW_COMMENT);
        }

        ind().putSimpleSTag(VmdTag.SHADOW_SEQUENCE.tag()).ln();

        pushNest();
        for(ShadowMotion shadow : list){
            putShadowMotion(shadow);
        }
        popNest();

        ind().putETag(VmdTag.SHADOW_SEQUENCE.tag()).ln(2);

        return;
    }

    /**
     * シャドウモーションを出力する。
     * @param shadowMotion シャドウモーション
     * @throws IOException 出力エラー
     */
    private void putShadowMotion(ShadowMotion shadowMotion)
            throws IOException{
        ind().putOpenSTag(VmdTag.SHADOW_ACT.tag()).sp();

        int frameNo = shadowMotion.getFrameNumber();
        ShadowMode mode = shadowMotion.getShadowMode();
        float rawParam = (float) shadowMotion.getRawScopeParam();

        putIntAttr(XmlAttr.ATTR_FRAME, frameNo).sp();
        putAttr(XmlAttr.ATTR_MODE, mode.name()).sp();
        putFloatAttr(XmlAttr.ATTR_RAW_PARAM, rawParam).sp();

        putCloseEmpty();

        double uiVal = ShadowMotion.rawParamToScope(rawParam);
        long lVal = Math.round(uiVal);
        sp().putLineComment("UI:" + lVal).ln();

        return;
    }

}
