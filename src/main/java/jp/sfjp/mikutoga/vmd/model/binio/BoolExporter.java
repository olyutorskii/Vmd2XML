/*
 * boolean information exporter
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.binio;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import jp.sfjp.mikutoga.bin.export.BinaryExporter;
import jp.sfjp.mikutoga.bin.export.IllegalTextExportException;
import jp.sfjp.mikutoga.vmd.VmdConst;
import jp.sfjp.mikutoga.vmd.model.IkSwitch;
import jp.sfjp.mikutoga.vmd.model.NumberedVmdFlag;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;

/**
 * フラグ情報のエクスポーター。
 * <p>MikuMikuDance Ver7.40以降でサポート
 */
class BoolExporter extends BinaryExporter{

    private static final byte[] FDFILLER =
        { (byte)0x00, (byte)0xfd };


    /**
     * コンストラクタ。
     * @param stream 出力ストリーム
     */
    BoolExporter(OutputStream stream){
        super(stream);
        return;
    }


    /**
     * フラグ情報を出力する。
     * @param motion モーションデータ
     * @throws IOException 出力エラー
     * @throws IllegalTextExportException 不正な文字列が指定された。
     */
    void dumpNumberedFlagMotion(VmdMotion motion)
            throws IOException, IllegalTextExportException {
        List<NumberedVmdFlag> list = motion.getNumberedFlagList();

        if(list.isEmpty()) return;

        int count = list.size();
        dumpLeInt(count);

        for(NumberedVmdFlag flag : list){
            int frameNo = flag.getFrameNumber();
            dumpLeInt(frameNo);

            byte showModel;
            if(flag.isModelShown()) showModel = 0x01;
            else                    showModel = 0x00;
            dumpByte(showModel);

            dumpIkSwitch(flag);
        }


        return;
    }

    /**
     * IK有効フラグを出力する。
     * @param flag フラグ情報
     * @throws IOException 出力エラー
     * @throws IllegalTextExportException 不正な文字列が指定された。
     */
    private void dumpIkSwitch(NumberedVmdFlag flag)
            throws IOException, IllegalTextExportException {
        List<IkSwitch> swList = flag.getIkSwitchList();
        int swNo = swList.size();
        dumpLeInt(swNo);

        for(IkSwitch ikSwitch : swList){
            String boneName = ikSwitch.getBoneName();
            dumpFixedW31j(boneName, VmdConst.IKSWBONENAME_MAX, FDFILLER);

            byte ikValid;
            if(ikSwitch.isValid()) ikValid = 0x01;
            else                   ikValid = 0x00;
            dumpByte(ikValid);
        }

        return;
    }

}
