/*
 * lighting listener from XML
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

import java.util.List;
import jp.sfjp.mikutoga.math.MkVec3D;
import jp.sfjp.mikutoga.vmd.model.LuminousColor;
import jp.sfjp.mikutoga.vmd.model.LuminousMotion;
import jp.sfjp.mikutoga.vmd.model.ShadowMode;
import jp.sfjp.mikutoga.vmd.model.ShadowMotion;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import org.xml.sax.Attributes;

/**
 * 照明・シャドウ関連のXML要素出現イベントを受信する。
 */
class SaxLightingListener extends SaxVmdListener{

    private LuminousMotion currentLuminous = null;


    /**
     * コンストラクタ。
     */
    SaxLightingListener(){
        super();
        return;
    }


    /**
     * {@inheritDoc}
     * @param tag {@inheritDoc}
     * @param attr {@inheritDoc}
     */
    @Override
    void openTag(VmdTag tag, Attributes attr){
        switch(tag){
        case LUMINOUS_ACT:
            openLumiAct(attr);
            break;
        case LUMI_COLOR:
            openLumiColor(attr);
            break;
        case LUMI_DIRECTION:
            openLumiDirection(attr);
            break;
        case SHADOW_ACT:
            openShadowAct(attr);
            break;
        default:
            break;
        }

        return;
    }

    /**
     * {@inheritDoc}
     * @param tag {@inheritDoc}
     */
    @Override
    void closeTag(VmdTag tag){
        if(tag == VmdTag.LUMINOUS_ACT){
            closeLumiAct();
        }
        return;
    }

    /**
     * lumiAct要素開始の通知。
     * @param attr 属性群
     */
    private void openLumiAct(Attributes attr){
        this.currentLuminous = new LuminousMotion();

        int frameNo = SaxAttr.getIntAttr(attr, XmlAttr.ATTR_FRAME);
        this.currentLuminous.setFrameNumber(frameNo);

        return;
    }

    /**
     * lumiAct要素終了の通知。
     */
    private void closeLumiAct(){
        VmdMotion motion = getVmdMotion();
        List<LuminousMotion> lumiList = motion.getLuminousMotionList();
        lumiList.add(this.currentLuminous);

        this.currentLuminous = null;

        return;
    }

    /**
     * lumiColor要素開始の通知。
     * @param attr 属性群
     */
    private void openLumiColor(Attributes attr){
        LuminousColor color = this.currentLuminous.getColor();

        float rCol = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_R_COL);
        float gCol = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_G_COL);
        float bCol = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_B_COL);

        color.setColR(rCol);
        color.setColG(gCol);
        color.setColB(bCol);

        return;
    }

    /**
     * lumiDirection要素開始の通知。
     * @param attr 属性群
     */
    private void openLumiDirection(Attributes attr){
        MkVec3D vec = this.currentLuminous.getDirection();

        float xVec = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_X_VEC);
        float yVec = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_Y_VEC);
        float zVec = SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_Z_VEC);

        vec.setXVal(xVec);
        vec.setYVal(yVec);
        vec.setZVal(zVec);

        return;
    }

    /**
     * shadowAct要素開始の通知。
     * @param attr 属性群
     */
    private void openShadowAct(Attributes attr){
        ShadowMotion shadowMotion = new ShadowMotion();

        int frameNo = SaxAttr.getIntAttr(attr, XmlAttr.ATTR_FRAME);
        shadowMotion.setFrameNumber(frameNo);

        float rawParam =
                SaxAttr.getFloatAttr(attr, XmlAttr.ATTR_RAW_PARAM);
        shadowMotion.setRawScopeParam(rawParam);

        String modeAttr =
                SaxAttr.getStringAttr(attr, XmlAttr.ATTR_MODE);
        ShadowMode mode = ShadowMode.valueOf(modeAttr);
        shadowMotion.setShadowMode(mode);

        VmdMotion motion = getVmdMotion();
        List<ShadowMotion> shadowList = motion.getShadowMotionList();
        shadowList.add(shadowMotion);

        return;
    }

}
