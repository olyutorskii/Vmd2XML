/*
 * flag xml exporter
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import jp.sfjp.mikutoga.typical.TypicalBone;
import jp.sfjp.mikutoga.vmd.model.IkSwitch;
import jp.sfjp.mikutoga.vmd.model.NumberedVmdFlag;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import jp.sfjp.mikutoga.xml.ProxyXmlExporter;

/**
 * フラグ情報のXMLエクスポーター。
 */
class FlagXmlExporter extends ProxyXmlExporter {

    private static final String MSG_MAYBE = "Perhaps : [{0}]";


    /**
     * コンストラクタ。
     * @param delegate 委譲先
     */
    FlagXmlExporter(VmdXmlExporter delegate) {
        super(delegate);
        return;
    }


    /**
     * boolean値をXSD:booleanに準拠した文字列に変換する。
     * @param bool boolean値
     * @return XSD:boolean文字列
     */
    private static String toXsdBoolean(boolean bool){
        String result;
        if(bool) result = "true";
        else     result = "false";
        return result;
    }


    /**
     * 各種モーションフラグを出力する。
     * @param vmdMotion モーションデータ
     * @throws IOException 出力エラー
     */
    void putFlagSequence(VmdMotion vmdMotion)
            throws IOException{
        ind().putSimpleSTag(VmdTag.FLAG_SEQUENCE.tag()).ln();

        pushNest();
        List<NumberedVmdFlag> list = vmdMotion.getNumberedFlagList();
        if( ! list.isEmpty() ) ln();
        for(NumberedVmdFlag flag : list){
            putFlagMotion(flag);
        }
        popNest();

        ind().putETag(VmdTag.FLAG_SEQUENCE.tag()).ln(2);

        return;
    }

    /**
     * フラグリストを出力する。
     * @param flag フラグモーション
     * @throws IOException 出力エラー
     */
    private void putFlagMotion(NumberedVmdFlag flag)
            throws IOException{
        int frameNo = flag.getFrameNumber();
        String showTxt = toXsdBoolean(flag.isModelShown());

        ind().putOpenSTag(VmdTag.FLAG_MOTION.tag()).sp();
        putIntAttr(XmlAttr.ATTR_FRAME, frameNo).sp();
        putAttr(XmlAttr.ATTR_SHOWMODEL, showTxt).sp();

        List<IkSwitch> ikList = flag.getIkSwitchList();
        if(ikList.isEmpty()){
            putCloseEmpty().ln(2);
        }else{
            putCloseSTag().ln(2);
            pushNest();
            for(IkSwitch ikSw : ikList){
                putIkSwitch(ikSw);
            }
            popNest();
            ind().putETag(VmdTag.FLAG_MOTION.tag()).ln(2);
        }

        return;
    }

    /**
     * IK ON/OFFリストを出力する。
     * @param ikSw IK ON/OFF スイッチ
     * @throws IOException 出力エラー
     */
    private void putIkSwitch(IkSwitch ikSw)
            throws IOException{
        String boneName = ikSw.getBoneName();
        String validTxt = toXsdBoolean(ikSw.isValid());

        ind().putLineComment(boneName);
        String globalName = TypicalBone.primary2global(boneName);
        if(globalName != null){
            String gname =
                    MessageFormat.format(MSG_MAYBE, globalName);
            sp(2).putLineComment(gname);
        }
        ln();

        ind().putOpenSTag(VmdTag.IK_SWITCH.tag()).sp();

        putAttr(XmlAttr.ATTR_NAME, boneName).sp();
        putAttr(XmlAttr.ATTR_VALID, validTxt).sp();

        putCloseEmpty().ln(2);

        return;
    }

}
