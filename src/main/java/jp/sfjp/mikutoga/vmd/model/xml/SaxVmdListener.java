/*
 * VMD-SAX element listsner
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

import jp.sfjp.mikutoga.vmd.model.BezierParam;
import jp.sfjp.mikutoga.vmd.model.PosCurve;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import org.xml.sax.Attributes;

/**
 * XML要素出現の通知受信部の共通実装。
 */
class SaxVmdListener {

    private VmdMotion vmdMotion;

    private PosCurve currentPosCurve;
    private int axisIdx;

    private BezierParam currentBezParam;


    /**
     * コンストラクタ。
     */
    protected SaxVmdListener(){
        super();

        this.vmdMotion = null;

        this.currentPosCurve = null;
        this.axisIdx = -1;

        this.currentBezParam = null;

        return;
    }


    /**
     * XML要素開始の通知。
     * @param tag 要素種別
     * @param attr 属性群
     */
    void openTag(VmdTag tag, Attributes attr){
        switch(tag){
        case BEZIER:
            openBezier(attr);
            break;
        case DEF_LINEAR:
            openDefLinear();
            break;
        case DEF_EASE_IN_OUT:
            openDefEaseInOut();
            break;
        default:
            break;
        }

        return;
    }

    /**
     * XML要素終了の通知。
     * @param tag 要素種別
     */
    void closeTag(VmdTag tag){
        return;
    }

    /**
     * ビルド対象オブジェクトの登録。
     * @param motion ビルド対象オブジェクト
     * @throws NullPointerException 引数がnull
     */
    void setVmdMotion(VmdMotion motion) throws NullPointerException{
        if(motion == null) throw new NullPointerException();
        this.vmdMotion = motion;
        return;
    }

    /**
     * ビルド対象オブジェクトの取得。
     * @return ビルド対象オブジェクト。未登録の場合はnull。
     */
    protected VmdMotion getVmdMotion(){
        return this.vmdMotion;
    }

    /**
     * ビルド対象の位置補間曲線情報を受け取る。
     * @param curve 位置補間曲線情報
     */
    protected void setCurrentPosCurve(PosCurve curve){
        this.currentPosCurve = curve;
        this.axisIdx = 0;

        this.currentBezParam = null;

        return;
    }

    /**
     * ビルド対象の単一補間曲線情報を受け取る。
     * @param bez 補間曲線情報
     */
    protected void setCurrentBezierParam(BezierParam bez){
        this.currentBezParam = bez;

        this.currentPosCurve = null;
        this.axisIdx = -1;

        return;
    }

    /**
     * ビルド対象の補間曲線情報を返す。
     * @return 補間曲線情報
     */
    private BezierParam getTargetBezierParam(){
        if(this.currentBezParam != null){
            return this.currentBezParam;
        }

        if(this.currentPosCurve == null){
            assert false;
            throw new AssertionError();
        }

        BezierParam result;

        switch(this.axisIdx){
        case 0:
            result = this.currentPosCurve.getIntpltXpos();
            break;
        case 1:
            result = this.currentPosCurve.getIntpltYpos();
            break;
        case 2:
            result = this.currentPosCurve.getIntpltZpos();
            break;
        default:
            assert false;
            throw new AssertionError();
        }

        this.axisIdx++;

        return result;
    }

    /**
     * ベジェ補間曲線情報を構築。
     * @param p1x P1-x
     * @param p1y P1-y
     * @param p2x P2-x
     * @param p2y P2-y
     */
    protected void putBezier(byte p1x, byte p1y, byte p2x, byte p2y){
        BezierParam bez = getTargetBezierParam();

        bez.setP1(p1x, p1y);
        bez.setP2(p2x, p2y);

        return;
    }

    /**
     * bezier要素開始の通知。
     * @param attr 属性群
     */
    protected void openBezier(Attributes attr){
        byte p1x = SaxXsdUtil.getByteAttr(attr, XmlAttr.ATTR_P1X);
        byte p1y = SaxXsdUtil.getByteAttr(attr, XmlAttr.ATTR_P1Y);
        byte p2x = SaxXsdUtil.getByteAttr(attr, XmlAttr.ATTR_P2X);
        byte p2y = SaxXsdUtil.getByteAttr(attr, XmlAttr.ATTR_P2Y);

        putBezier(p1x, p1y, p2x, p2y);

        return;
    }

    /**
     * defLinear要素開始の通知。
     */
    protected void openDefLinear(){
        byte p1x = BezierParam.DEF_P1X;
        byte p1y = BezierParam.DEF_P1Y;
        byte p2x = BezierParam.DEF_P2X;
        byte p2y = BezierParam.DEF_P2Y;

        putBezier(p1x, p1y, p2x, p2y);

        return;
    }

    /**
     * defEaseInOut要素開始の通知。
     */
    protected void openDefEaseInOut(){
        byte p1x = BezierParam.EIO_P1X;
        byte p1y = BezierParam.EIO_P1Y;
        byte p2x = BezierParam.EIO_P2X;
        byte p2y = BezierParam.EIO_P2Y;

        putBezier(p1x, p1y, p2x, p2y);

        return;
    }

}
