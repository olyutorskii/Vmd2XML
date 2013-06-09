/*
 * vmd-xml exporter
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import jp.sfjp.mikutoga.math.EulerYXZ;
import jp.sfjp.mikutoga.math.MkPos3D;
import jp.sfjp.mikutoga.math.MkQuat;
import jp.sfjp.mikutoga.typical.TypicalBone;
import jp.sfjp.mikutoga.typical.TypicalMorph;
import jp.sfjp.mikutoga.vmd.IllegalVmdDataException;
import jp.sfjp.mikutoga.vmd.VmdUniq;
import jp.sfjp.mikutoga.vmd.model.BezierParam;
import jp.sfjp.mikutoga.vmd.model.BoneMotion;
import jp.sfjp.mikutoga.vmd.model.MorphMotion;
import jp.sfjp.mikutoga.vmd.model.PosCurve;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import jp.sfjp.mikutoga.xml.BasicXmlExporter;
import jp.sfjp.mikutoga.xml.XmlResourceResolver;

/**
 * VMDモーションデータをXMLへエクスポートする。
 */
public class VmdXmlExporter extends BasicXmlExporter {

    private static final String XML_VER = "1.0";
    private static final String XML_ENC = "UTF-8";
    private static final String XML_DECL =
              "<?xml version=\"" + XML_VER
            + "\" encoding=\"" + XML_ENC
            + "\" ?>";

    private static final String XSINS = "xsi";

    private static final String MSG_MAYBE = "Perhaps : [{0}]";


    private XmlMotionFileType xmlType = XmlMotionFileType.XML_110820;

    private boolean isQuaternionMode = true;
    private String generator = "";

    private final CameraXmlExporter cameraXmlExporter;
    private final LightingXmlExpoter lightingExporter;
    private final ExtraXmlExporter extraExporter;
    private final FlagXmlExporter flagXmlExporter;


    /**
     * コンストラクタ。
     */
    public VmdXmlExporter(){
        super();

        this.cameraXmlExporter = new CameraXmlExporter(this);
        this.lightingExporter  = new LightingXmlExpoter(this);
        this.extraExporter     = new ExtraXmlExporter(this);
        this.flagXmlExporter   = new FlagXmlExporter(this);

        return;
    }


    /**
     * 出力XMLファイル種別を設定する。
     * @param type ファイル種別
     */
    public void setXmlFileType(XmlMotionFileType type){
        switch(type){
        case XML_110820:
        case XML_130609:
            this.xmlType = type;
            break;
        case XML_AUTO:
            this.xmlType = XmlMotionFileType.XML_130609;
            break;
        default:
            assert false;
            throw new AssertionError();
        }

        assert this.xmlType == XmlMotionFileType.XML_110820
            || this.xmlType == XmlMotionFileType.XML_130609;

        return;
    }

    /**
     * 出力XMLファイル種別を返す。
     * @return ファイル種別
     */
    public XmlMotionFileType getXmlFileType(){
        return this.xmlType;
    }

    /**
     * ボーン回転量をクォータニオンで出力するか否か設定する。
     * <p>デフォルトではtrue
     * @param mode trueだとクォータニオン、falseだとオイラー角で出力される。
     */
    public void setQuaternionMode(boolean mode){
        this.isQuaternionMode = mode;
    }

    /**
     * ボーン回転量をクォータニオンで出力するか否か返す。
     * @return クォータニオンで出力するならtrue
     */
    public boolean isQuaternionMode(){
        return this.isQuaternionMode;
    }

    /**
     * Generatorメタ情報を設定する。
     * @param generatorArg Generatorメタ情報。nullならXML出力しない。
     */
    public void setGenerator(String generatorArg){
        this.generator = generatorArg;
        return;
    }

    /**
     * Generatorメタ情報を取得する。
     * @return Generatorメタ情報。XML出力しないならnullを返す。
     */
    public String getGenerator(){
        return this.generator;
    }

    /**
     * VMDモーションデータをXML形式で出力する。
     * @param vmdMotion VMDモーションデータ
     * @param xmlOut 出力先
     * @throws IOException 出力エラー
     * @throws IllegalVmdDataException 不正なモーションデータを検出
     */
    public void putVmdXml(VmdMotion vmdMotion, Appendable xmlOut)
            throws IOException, IllegalVmdDataException{
        setAppendable(xmlOut);

        try{
            putVmdXmlImpl(vmdMotion);
        }finally{
            flush();
        }
        return;
    }

    /**
     * VMDモーションデータをXML形式で出力する。
     * @param vmdMotion VMDモーションデータ
     * @throws IOException 出力エラー
     * @throws IllegalVmdDataException 不正なモーションデータを検出
     */
    private void putVmdXmlImpl(VmdMotion vmdMotion)
            throws IOException, IllegalVmdDataException{
        putVmdRootOpen();

        putGenerator();

        if(vmdMotion.isModelMotion()){
            putModelName(vmdMotion);
            putBoneMotionSequence(vmdMotion);
            putMorphSequence(vmdMotion);
            if(this.xmlType == XmlMotionFileType.XML_130609){
                this.flagXmlExporter.putFlagSequence(vmdMotion);
            }
        }else{
            this.cameraXmlExporter.putCameraSequence(vmdMotion);
            this.lightingExporter.putLuminousSequence(vmdMotion);
            this.lightingExporter.putShadowSequence(vmdMotion);
        }

        ind().putETag(VmdTag.VMD_MOTION.tag()).ln(2);
        ind().putLineComment("EOF").ln();

        return;
    }

    /**
     * ルート要素がオープンするまでの各種宣言を出力する。
     * @throws IOException 出力エラー
     */
    private void putVmdRootOpen() throws IOException{
        ind().putRawText(XML_DECL).ln(2);

        ind().putBlockComment(XmlComment.TOP_COMMENT).ln(2);

        String namespace;
        String schemaUrl;
        String schemaVer;

        switch(this.xmlType){
        case XML_110820:
            namespace = Schema110820.NS_VMDXML;
            schemaUrl = Schema110820.SCHEMA_VMDXML;
            schemaVer = Schema110820.VER_VMDXML;
            break;
        case XML_130609:
            namespace = Schema130609.NS_VMDXML;
            schemaUrl = Schema130609.SCHEMA_VMDXML;
            schemaVer = Schema130609.VER_VMDXML;
            break;
        default:
            assert false;
            throw new AssertionError();
        }

        ind().putOpenSTag(VmdTag.VMD_MOTION.tag()).ln();
        pushNest();
        ind().putAttr("xmlns", namespace).ln();
        ind().putAttr("xmlns:" + XSINS, XmlResourceResolver.NS_XSD).ln();

        ind().putRawText(XSINS).putRawText(":schemaLocation=")
             .putRawCh('"');
        putRawText(namespace).ln();
        ind().sp(2).putRawText(schemaUrl)
             .putRawCh('"')
             .ln();

        ind().putAttr(XmlAttr.ATTR_VERSION, schemaVer).ln();
        popNest();
        putCloseSTag().ln(2);

        return;
    }

    /**
     * ジェネレータ名を出力する。
     * @throws IOException 出力エラー
     */
    private void putGenerator() throws IOException{
        String genTxt = getGenerator();
        if(genTxt == null) return;
        if(genTxt.isEmpty()) return;

        ind().putOpenSTag(VmdTag.META.tag()).sp();
        putAttr(XmlAttr.ATTR_NAME, "generator").sp();
        putAttr(XmlAttr.ATTR_CONTENT, genTxt).sp();
        putCloseEmpty().ln(2);

        return;
    }

    /**
     * モデル名を出力する。
     * @param vmdMotion モーションデータ
     * @throws IOException 出力エラー
     */
    private void putModelName(VmdMotion vmdMotion)
            throws IOException{
        String modelName = vmdMotion.getModelName();

        String modelComm;
        if(modelName != null && modelName.length() > 0){
            modelComm = modelName;
        }else{
            modelComm = "[NAMELESS]";
        }

        ind().putLineComment(modelComm).ln();
        ind().putOpenSTag(VmdTag.MODEL_NAME.tag()).sp();
        putAttr(XmlAttr.ATTR_NAME, modelName).sp();
        putCloseEmpty().ln(2);

        return;
    }

    /**
     * ボーンモーションデータを出力する。
     * @param vmdMotion モーションデータ
     * @throws IOException 出力エラー
     */
    private void putBoneMotionSequence(VmdMotion vmdMotion)
            throws IOException{
        Map<String, List<BoneMotion>> boneMap = vmdMotion.getBonePartMap();

        if( ! boneMap.isEmpty() ){
            ind().putBlockComment(XmlComment.QUATERNION_COMMENT);
            ind().putBlockComment(XmlComment.BEZIER_COMMENT);
        }

        ind().putSimpleSTag(VmdTag.BONE_M_SEQUENCE.tag()).ln();

        pushNest();
        if( ! boneMap.isEmpty() ) ln();
        for(Map.Entry<String, List<BoneMotion>> entry : boneMap.entrySet()){
            putBonePart(entry.getKey(), entry.getValue());
        }
        popNest();

        ind().putETag(VmdTag.BONE_M_SEQUENCE.tag()).ln(2);

        return;
    }

    /**
     * ボーン別モーションデータを出力する。
     * @param boneName ボーン名
     * @param list ボーンモーションのリスト
     * @throws IOException 出力エラー
     */
    private void putBonePart(String boneName, List<BoneMotion> list)
            throws IOException{
        ind().putLineComment(boneName);
        String globalName = TypicalBone.primary2global(boneName);
        if(globalName != null){
            String gname =
                    MessageFormat.format(MSG_MAYBE, globalName);
            sp(2).putLineComment(gname);
        }
        ln();

        ind().putOpenSTag(VmdTag.BONE_PART.tag()).sp();
        putAttr(XmlAttr.ATTR_NAME, boneName).sp();
        putCloseSTag().ln(2);

        pushNest();
        for(BoneMotion bone : list){
            putBoneMotion(bone);
        }
        popNest();

        ind().putETag(VmdTag.BONE_PART.tag()).ln(2);

        return;
    }

    /**
     * ボーンモーションを出力する。
     * @param boneMotion ボーンモーション
     * @throws IOException 出力エラー
     */
    private void putBoneMotion(BoneMotion boneMotion)
            throws IOException{
        ind().putOpenSTag(VmdTag.BONE_MOTION.tag()).sp();
        int frameNo = boneMotion.getFrameNumber();
        putIntAttr(XmlAttr.ATTR_FRAME, frameNo).sp();
        putCloseSTag().ln();

        pushNest();
        putBonePosition(boneMotion);
        if(isQuaternionMode()){
            putBoneRotQuat(boneMotion);
        }else{
            putBoneRotEyxz(boneMotion);
        }
        popNest();

        ind().putETag(VmdTag.BONE_MOTION.tag()).ln(2);

        return;
    }

    /**
     * ボーン位置を出力する。
     * @param boneMotion ボーンモーション
     * @throws IOException 出力エラー
     */
    private void putBonePosition(BoneMotion boneMotion)
            throws IOException{
        if(boneMotion.hasImplicitPosition()){
            return;
        }

        ind().putOpenSTag(VmdTag.BONE_POSITION.tag()).sp();

        MkPos3D position = boneMotion.getPosition();

        float xPos = (float) position.getXpos();
        float yPos = (float) position.getYpos();
        float zPos = (float) position.getZpos();

        putFloatAttr(XmlAttr.ATTR_X_POS, xPos).sp();
        putFloatAttr(XmlAttr.ATTR_Y_POS, yPos).sp();
        putFloatAttr(XmlAttr.ATTR_Z_POS, zPos).sp();

        PosCurve posCurve = boneMotion.getPosCurve();
        if(posCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();

            pushNest();
            this.extraExporter.putPositionCurve(posCurve);
            popNest();

            ind().putETag(VmdTag.BONE_POSITION.tag()).ln();
        }

        return;
    }

    /**
     * ボーン回転を出力する。
     * @param boneMotion ボーンモーション
     * @throws IOException 出力エラー
     */
    private void putBoneRotQuat(BoneMotion boneMotion)
            throws IOException{
        MkQuat rotation = boneMotion.getRotation();
        BezierParam rotCurve = boneMotion.getIntpltRotation();

        ind().putOpenSTag(VmdTag.BONE_ROT_QUAT.tag()).ln();
        pushNest();

        float qx = (float) rotation.getQ1();
        float qy = (float) rotation.getQ2();
        float qz = (float) rotation.getQ3();
        float qw = (float) rotation.getQW();

        ind().putFloatAttr(XmlAttr.ATTR_QX, qx).ln();
        ind().putFloatAttr(XmlAttr.ATTR_QY, qy).ln();
        ind().putFloatAttr(XmlAttr.ATTR_QZ, qz).ln();
        ind().putFloatAttr(XmlAttr.ATTR_QW, qw).ln();

        popNest();
        ind();

        if(rotCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();
            pushNest();
            ind();
            this.extraExporter.putBezierCurve(rotCurve);
            ln();
            popNest();
            ind().putETag(VmdTag.BONE_ROT_QUAT.tag()).ln();
        }

        return;
    }

    /**
     * ボーン回転を出力する。
     * @param boneMotion ボーンモーション
     * @throws IOException 出力エラー
     */
    private void putBoneRotEyxz(BoneMotion boneMotion)
            throws IOException{
        MkQuat rotation = boneMotion.getRotation();
        BezierParam rotCurve = boneMotion.getIntpltRotation();

        EulerYXZ euler = new EulerYXZ();
        rotation.toEulerYXZ(euler);
        float xDeg = (float)StrictMath.toDegrees(euler.getXRot());
        float yDeg = (float)StrictMath.toDegrees(euler.getYRot());
        float zDeg = (float)StrictMath.toDegrees(euler.getZRot());

        ind().putOpenSTag(VmdTag.BONE_ROT_EYXZ.tag()).ln();
        pushNest();
        ind().putFloatAttr(XmlAttr.ATTR_X_DEG, xDeg).ln();
        ind().putFloatAttr(XmlAttr.ATTR_Y_DEG, yDeg).ln();
        ind().putFloatAttr(XmlAttr.ATTR_Z_DEG, zDeg).ln();
        popNest();
        ind();

        if(rotCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();
            pushNest();
            ind();
            this.extraExporter.putBezierCurve(rotCurve);
            ln();
            popNest();
            ind().putETag(VmdTag.BONE_ROT_EYXZ.tag()).ln();
        }

        return;
    }

    /**
     * モーフデータを出力する。
     * @param vmdMotion モーションデータ
     * @throws IOException 出力エラー
     */
    private void putMorphSequence(VmdMotion vmdMotion)
            throws IOException{
        ind().putSimpleSTag(VmdTag.MORPH_SEQUENCE.tag()).ln();

        pushNest();
        Map<String, List<MorphMotion>> listMap = vmdMotion.getMorphPartMap();
        if( ! listMap.isEmpty() ) ln();
        putMorphPartList(listMap);
        popNest();

        ind().putETag(VmdTag.MORPH_SEQUENCE.tag()).ln(2);

        return;
    }

    /**
     * 箇所別モーフデータを出力する。
     * @param listMap モーフデータの名前付きリストマップ
     * @throws IOException 出力エラー
     */
    private void putMorphPartList(Map<String, List<MorphMotion>> listMap)
            throws IOException{
        for(Map.Entry<String, List<MorphMotion>> entry : listMap.entrySet()){
            String morphName       = entry.getKey();
            List<MorphMotion> list = entry.getValue();

            if(VmdUniq.isBaseMorphName(morphName)) continue;

            ind().putLineComment(morphName);
            String globalName = TypicalMorph.primary2global(morphName);
            if(globalName != null){
                String gname =
                        MessageFormat.format(MSG_MAYBE, globalName);
                sp(2).putLineComment(gname);
            }
            ln();

            ind().putOpenSTag(VmdTag.MORPH_PART.tag()).sp();
            putAttr(XmlAttr.ATTR_NAME, morphName).sp();
            putCloseSTag().ln();

            pushNest();
            for(MorphMotion morph : list){
                putMorphMotion(morph);
            }
            popNest();

            ind().putETag(VmdTag.MORPH_PART.tag()).ln(2);
        }

        return;
    }

    /**
     * 個別のモーフモーションを出力する。
     * @param morphMotion モーフモーション
     * @throws IOException 出力エラー
     */
    private void putMorphMotion(MorphMotion morphMotion)
            throws IOException{
        ind().putOpenSTag(VmdTag.MORPH_MOTION.tag()).sp();

        int frameNo = morphMotion.getFrameNumber();
        float flex = morphMotion.getFlex();

        putIntAttr(XmlAttr.ATTR_FRAME, frameNo).sp();
        putFloatAttr(XmlAttr.ATTR_FLEX, flex).sp();

        putCloseEmpty().ln();

        return;
    }

}
