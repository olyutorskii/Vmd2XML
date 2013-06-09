/*
 * motion flag listener from XML
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

import java.util.List;
import jp.sfjp.mikutoga.vmd.model.IkSwitch;
import jp.sfjp.mikutoga.vmd.model.NumberedVmdFlag;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import jp.sfjp.mikutoga.xml.SaxAttr;
import org.xml.sax.Attributes;

/**
 * モーションフラグ関連のXML要素出現イベントを受信する。
 */
class SaxFlagListener extends SaxVmdListener {

    private NumberedVmdFlag currentFlagMotion = null;


    /**
     * コンストラクタ。
     */
    SaxFlagListener(){
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
        case FLAG_MOTION:
            openFlagMotion(attr);
            break;
        case IK_SWITCH:
            openIkSwitch(attr);
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
        if(tag == VmdTag.FLAG_MOTION){
            closeFlagMotion();
        }
        return;
    }

    /**
     * flagMotion要素開始の通知。
     * @param attr 属性群
     */
    private void openFlagMotion(Attributes attr){
        this.currentFlagMotion = new NumberedVmdFlag();

        int frameNo = SaxAttr.getIntAttr(attr, XmlAttr.ATTR_FRAME);
        boolean showModel =
                SaxAttr.getBooleanAttr(attr, XmlAttr.ATTR_SHOWMODEL);

        this.currentFlagMotion.setFrameNumber(frameNo);
        this.currentFlagMotion.setModelShown(showModel);

        return;
    }

    /**
     * flagMotion要素終了の通知。
     */
    private void closeFlagMotion(){
        VmdMotion motion = getVmdMotion();
        List<NumberedVmdFlag> flagList = motion.getNumberedFlagList();
        flagList.add(this.currentFlagMotion);

        this.currentFlagMotion = null;

        return;
    }

    /**
     * ikSwitch要素開始の通知。
     * @param attr 属性群
     */
    private void openIkSwitch(Attributes attr){
        String boneName = SaxAttr.getStringAttr(attr, XmlAttr.ATTR_NAME);
        boolean valid =
                SaxAttr.getBooleanAttr(attr, XmlAttr.ATTR_VALID);

        IkSwitch ikSw = new IkSwitch();
        ikSw.setBoneName(boneName);
        ikSw.setValid(valid);

        List<IkSwitch> ikList = this.currentFlagMotion.getIkSwitchList();
        ikList.add(ikSw);

        return;
    }

}
