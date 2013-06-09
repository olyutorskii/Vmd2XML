/*
 * extra common xml exporter
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

import java.io.IOException;
import jp.sfjp.mikutoga.vmd.model.BezierParam;
import jp.sfjp.mikutoga.vmd.model.PosCurve;
import jp.sfjp.mikutoga.xml.ProxyXmlExporter;
import jp.sfjp.mikutoga.xml.XmlExporter;

/**
 * XML出力機構の共通部。
 * <p>主にベジェ補間パラメータ出力部。
 */
class ExtraXmlExporter extends ProxyXmlExporter {

    /**
     * コンストラクタ。
     * @param delegate 委譲先
     */
    ExtraXmlExporter(XmlExporter delegate){
        super(delegate);
        return;
    }


    /**
     * 位置移動補間カーブを出力する。
     * @param posCurve 移動補間情報
     * @throws IOException 出力エラー
     */
    void putPositionCurve(PosCurve posCurve)
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
     * ベジェ曲線による補間曲線情報を出力する。
     * @param bezier ベジェ曲線
     * @throws IOException 出力エラー
     */
    void putBezierCurve(BezierParam bezier)
            throws IOException{
        if(bezier.isDefaultLinear()){
            putSimpleEmpty(VmdTag.DEF_LINEAR.tag());
        }else if(bezier.isDefaultEaseInOut()){
            putSimpleEmpty(VmdTag.DEF_EASE_IN_OUT.tag());
        }else{
            putOpenSTag(VmdTag.BEZIER.tag()).sp();

            byte p1x = bezier.getP1x();
            byte p1y = bezier.getP1y();
            byte p2x = bezier.getP2x();
            byte p2y = bezier.getP2y();

            putIntAttr(XmlAttr.ATTR_P1X, p1x).sp();
            putIntAttr(XmlAttr.ATTR_P1Y, p1y).sp();
            putIntAttr(XmlAttr.ATTR_P2X, p2x).sp();
            putIntAttr(XmlAttr.ATTR_P2Y, p2y).sp();

            putCloseEmpty();
        }
        return;
    }

}
