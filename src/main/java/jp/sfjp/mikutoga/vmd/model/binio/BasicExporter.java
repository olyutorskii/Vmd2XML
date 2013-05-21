/*
 * bone motion & morph exporter
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.binio;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import jp.sfjp.mikutoga.bin.export.BinaryExporter;
import jp.sfjp.mikutoga.bin.export.IllegalTextExportException;
import jp.sfjp.mikutoga.math.MkPos3D;
import jp.sfjp.mikutoga.math.MkQuat;
import jp.sfjp.mikutoga.vmd.VmdConst;
import jp.sfjp.mikutoga.vmd.VmdUniq;
import jp.sfjp.mikutoga.vmd.model.BezierParam;
import jp.sfjp.mikutoga.vmd.model.BoneMotion;
import jp.sfjp.mikutoga.vmd.model.MorphMotion;
import jp.sfjp.mikutoga.vmd.model.PosCurve;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;

/**
 * ボーンモーション及びモーフ情報のエクスポーター。
 */
class BasicExporter extends BinaryExporter {

    private static final int BZ_SIZE = 4;           // 4byte Bezier parameter
    private static final int BZXYZR_SIZE = BZ_SIZE * 4; // XYZR Bezier
    private static final int BZ_REDUNDANT = 4;          // redundant spare
    private static final int BZTOTAL_SIZE = BZXYZR_SIZE * BZ_REDUNDANT;

    private static final Charset CS_ASCII = Charset.forName("US-ASCII");

    // '\0' * 5byte の版もあり
    private static final String HEADFILLER = "\u0000" + "JKLM";

    private static final byte[] FDFILLER =
        { (byte)0x00, (byte)0xfd };
    private static final  byte[] INTPLT_FILLER = {
        (byte) 0x01,  // 0x00の版もあり。
        (byte) 0x00,
        (byte) 0x00,
    };


    private final byte[] motionIntplt = new byte[BZTOTAL_SIZE];
    private final ByteBuffer intpltBuf;
    private final ByteBuffer rdBuf;


    /**
     * コンストラクタ。
     * @param stream 出力ストリーム
     */
    BasicExporter(OutputStream stream){
        super(stream);

        this.intpltBuf = ByteBuffer.wrap(this.motionIntplt);
        ByteBuffer buf = ByteBuffer.wrap(this.motionIntplt, 0, BZXYZR_SIZE);
        this.rdBuf = buf.asReadOnlyBuffer();

        return;
    }


    /**
     * ヘッダ情報を出力する。
     * @throws IOException 出力エラー
     */
    void dumpHeader() throws IOException{
        byte[] header = (VmdConst.MAGIC_TXT + HEADFILLER).getBytes(CS_ASCII);
        assert header.length == VmdConst.HEADER_LENGTH;

        dumpByteArray(header);

        return;
    }

    /**
     * モデル名を出力する。
     * <p>演出データのモデル名には
     * 便宜的に
     * {@link jp.sfjp.mikutoga.vmd.VmdUniq#MODELNAME_STAGEACT}
     * が使われる。
     * @param motion モーションデータ
     * @throws IOException 出力エラー
     * @throws IllegalTextExportException 不正なモデル名の出現
     */
    void dumpModelName(VmdMotion motion)
            throws IOException, IllegalTextExportException{
        String modelName = motion.getModelName();
        if(modelName == null) modelName = VmdUniq.MODELNAME_STAGEACT;

        dumpFixedW31j(modelName, VmdConst.MODELNAME_MAX, FDFILLER);

        return;
    }

    /**
     * ボーンモーション情報を出力する。
     * @param motion モーションデータ
     * @throws IOException 出力エラー
     * @throws IllegalTextExportException 不正なボーン名の出現
     */
    void dumpBoneMotion(VmdMotion motion)
            throws IOException, IllegalTextExportException{
        Map<String, List<BoneMotion>> map = motion.getBonePartMap();

        List<BoneMotion> bmotionList = new LinkedList<BoneMotion>();
        for(List<BoneMotion> eachList : map.values()){
            bmotionList.addAll(eachList);
        }
        dumpLeInt(bmotionList.size());

        for(BoneMotion boneMotion : bmotionList){
            String boneName = boneMotion.getBoneName();
            dumpFixedW31j(boneName, VmdConst.BONENAME_MAX, FDFILLER);

            int frame = boneMotion.getFrameNumber();
            dumpLeInt(frame);

            MkPos3D position = boneMotion.getPosition();
            dumpBonePosition(position);

            MkQuat rotation = boneMotion.getRotation();
            dumpBoneRotation(rotation);

            dumpBoneInterpolation(boneMotion);
        }

        return;
    }

    /**
     * ボーン位置情報を出力する。
     * @param position ボーン位置情報
     * @throws IOException 出力エラー
     */
    private void dumpBonePosition(MkPos3D position)
            throws IOException{
        float xPos = (float) position.getXpos();
        float yPos = (float) position.getYpos();
        float zPos = (float) position.getZpos();

        dumpLeFloat(xPos);
        dumpLeFloat(yPos);
        dumpLeFloat(zPos);

        return;
    }

    /**
     * ボーン回転情報を出力する。
     * @param rotation ボーン回転情報
     * @throws IOException 出力エラー
     */
    private void dumpBoneRotation(MkQuat rotation)
            throws IOException{
        float qx = (float) rotation.getQ1();
        float qy = (float) rotation.getQ2();
        float qz = (float) rotation.getQ3();
        float qw = (float) rotation.getQW();

        dumpLeFloat(qx);
        dumpLeFloat(qy);
        dumpLeFloat(qz);
        dumpLeFloat(qw);

        return;
    }

    /**
     * ボーンモーションの補間情報を出力する。
     * @param boneMotion ボーンモーション
     * @throws IOException 出力エラー
     */
    private void dumpBoneInterpolation(BoneMotion boneMotion)
            throws IOException{
        PosCurve posCurve = boneMotion.getPosCurve();
        BezierParam xCurve = posCurve.getIntpltXpos();
        BezierParam yCurve = posCurve.getIntpltYpos();
        BezierParam zCurve = posCurve.getIntpltZpos();
        BezierParam rCurve = boneMotion.getIntpltRotation();

        this.intpltBuf.clear();

        this.intpltBuf.put(xCurve.getP1x());
        this.intpltBuf.put(yCurve.getP1x());
        this.intpltBuf.put(zCurve.getP1x());
        this.intpltBuf.put(rCurve.getP1x());

        this.intpltBuf.put(xCurve.getP1y());
        this.intpltBuf.put(yCurve.getP1y());
        this.intpltBuf.put(zCurve.getP1y());
        this.intpltBuf.put(rCurve.getP1y());

        this.intpltBuf.put(xCurve.getP2x());
        this.intpltBuf.put(yCurve.getP2x());
        this.intpltBuf.put(zCurve.getP2x());
        this.intpltBuf.put(rCurve.getP2x());

        this.intpltBuf.put(xCurve.getP2y());
        this.intpltBuf.put(yCurve.getP2y());
        this.intpltBuf.put(zCurve.getP2y());
        this.intpltBuf.put(rCurve.getP2y());

        assert this.intpltBuf.position() == BZXYZR_SIZE;

        redundantCopy();

        dumpByteArray(this.motionIntplt);

        return;
    }

    /**
     * 補間情報冗長部の組み立て。
     * <p>※ MMDの版によって若干出力内容が異なる。
     */
    private void redundantCopy(){
        this.intpltBuf.position(BZXYZR_SIZE);

        for(int lack = 1; lack < BZ_REDUNDANT; lack++){
            this.rdBuf.position(lack);
            this.intpltBuf.put(this.rdBuf);
            this.intpltBuf.put(INTPLT_FILLER, 0, lack);
        }

        assert this.intpltBuf.position() == BZTOTAL_SIZE;

        return;
    }

    /**
     * モーフ情報を出力する。
     * @param motion モーションデータ
     * @throws IOException 出力エラー
     * @throws IllegalTextExportException 不正なモーフ名の出現
     */
    void dumpMorphMotion(VmdMotion motion)
            throws IOException, IllegalTextExportException{
        Map<String, List<MorphMotion>> map = motion.getMorphPartMap();

        List<MorphMotion> morphList = new LinkedList<MorphMotion>();
        for(List<MorphMotion> eachList : map.values()){
            morphList.addAll(eachList);
        }
        dumpLeInt(morphList.size());

        for(MorphMotion morphMotion : morphList){
            String morphName = morphMotion.getMorphName();
            dumpFixedW31j(morphName, VmdConst.MORPHNAME_MAX, FDFILLER);

            int frame = morphMotion.getFrameNumber();
            dumpLeInt(frame);

            float flex = morphMotion.getFlex();
            dumpLeFloat(flex);
        }

        return;
    }

}
