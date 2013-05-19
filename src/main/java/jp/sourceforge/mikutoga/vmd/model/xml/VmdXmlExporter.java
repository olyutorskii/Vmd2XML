/*
 * vmd-xml exporter
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sourceforge.mikutoga.vmd.model.xml;

import java.io.IOException;
import java.io.OutputStream;
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
import jp.sourceforge.mikutoga.xml.BasicXmlExporter;
import jp.sourceforge.mikutoga.xml.XmlResourceResolver;

/**
 * VMDモーションデータをXMLへエクスポートする。
 */
public class VmdXmlExporter extends BasicXmlExporter {

    private static final String XML_DECL =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";

    private static final String XSINS = "xsi";


    private boolean isQuaternionMode = true;
    private String generator = "";

    private final CameraXmlExporter cameraXmlExporter;
    private final LightingXmlExpoter lightingExporter;


    /**
     * コンストラクタ。
     * 文字エンコーディングはUTF-8が用いられる。
     * @param ostream 出力ストリーム
     */
    public VmdXmlExporter(OutputStream ostream){
        super(ostream);
        this.cameraXmlExporter = new CameraXmlExporter(this);
        this.lightingExporter = new LightingXmlExpoter(this);
        return;
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
     * @throws IOException 出力エラー
     * @throws IllegalVmdDataException 不正なモーションデータを検出
     */
    public void putVmdXml(VmdMotion vmdMotion)
            throws IOException, IllegalVmdDataException{
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
        ind().putRawText(XML_DECL).ln(2);

        ind().putBlockComment(XmlSyms.TOP_COMMENT).ln(2);

        ind().putOpenSTag(XmlSyms.TAG_VMD_MOTION).ln();
        pushNest();
        ind().putAttr("xmlns", Schema110820.NS_VMDXML).ln();
        ind().putAttr("xmlns:" + XSINS, XmlResourceResolver.NS_XSD).ln();

        ind().putRawText(XSINS).putRawText(":schemaLocation=")
             .putRawCh('"');
        putRawText(Schema110820.NS_VMDXML).ln();
        ind().sp(2).putRawText(Schema110820.SCHEMA_VMDXML)
             .putRawCh('"')
             .ln();

        ind().putAttr(XmlSyms.ATTR_VERSION, Schema110820.VER_VMDXML).ln();
        popNest();
        putCloseSTag().ln(2);

        if(this.generator != null && this.generator.length() > 0){
            ind().putOpenSTag(XmlSyms.TAG_META).sp();
            putAttr(XmlSyms.ATTR_NAME, "generator").sp();
            putAttr(XmlSyms.ATTR_CONTENT, this.generator).sp();
            putCloseEmpty().ln(2);
        }

        if(vmdMotion.isModelMotion()){
            putModelName(vmdMotion);
            putBoneMotionSequence(vmdMotion);
            putMorphSequence(vmdMotion);
        }else{
            this.cameraXmlExporter.putCameraSequence(vmdMotion);
            this.lightingExporter.putLuminousSequence(vmdMotion);
            this.lightingExporter.putShadowSequence(vmdMotion);
        }

        ind().putETag(XmlSyms.TAG_VMD_MOTION).ln(2);
        ind().putLineComment("EOF").ln();

        return;
    }

    /**
     * 位置移動補間カーブを出力する。
     * @param posCurve 移動補間情報
     * @throws IOException 出力エラー
     */
    private void putPositionCurve(PosCurve posCurve)
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
    private void putBezierCurve(BezierParam bezier)
            throws IOException{
        if(bezier.isDefaultLinear()){
            putSimpleEmpty(XmlSyms.TAG_DEF_LINEAR);
        }else if(bezier.isDefaultEaseInOut()){
            putSimpleEmpty(XmlSyms.TAG_DEF_EASE_IN_OUT);
        }else{
            putOpenSTag(XmlSyms.TAG_BEZIER).sp();
            putIntAttr(XmlSyms.ATTR_P1X, bezier.getP1x()).sp();
            putIntAttr(XmlSyms.ATTR_P1Y, bezier.getP1y()).sp();
            putIntAttr(XmlSyms.ATTR_P2X, bezier.getP2x()).sp();
            putIntAttr(XmlSyms.ATTR_P2Y, bezier.getP2y()).sp();
            putCloseEmpty();
        }
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
        ind().putOpenSTag(XmlSyms.TAG_MODEL_NAME).sp();
        putAttr(XmlSyms.ATTR_NAME, modelName).sp();
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
            ind().putBlockComment(XmlSyms.QUATERNION_COMMENT);
            ind().putBlockComment(XmlSyms.BEZIER_COMMENT);
        }

        ind().putSimpleSTag(XmlSyms.TAG_BONE_M_SEQUENCE).ln();

        pushNest();
        if( ! boneMap.isEmpty() ) ln();
        for(Map.Entry<String, List<BoneMotion>> entry : boneMap.entrySet()){
            putBonePart(entry.getKey(), entry.getValue());
        }
        popNest();

        ind().putETag(XmlSyms.TAG_BONE_M_SEQUENCE).ln(2);

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
            sp(2).putLineComment("Perhaps : [" + globalName + "]");
        }
        ln();

        ind().putOpenSTag(XmlSyms.TAG_BONE_PART).sp();
        putAttr(XmlSyms.ATTR_NAME, boneName).sp();
        putCloseSTag().ln(2);

        pushNest();
        for(BoneMotion bone : list){
            putBoneMotion(bone);
        }
        popNest();

        ind().putETag(XmlSyms.TAG_BONE_PART).ln(2);

        return;
    }

    /**
     * ボーンモーションを出力する。
     * @param boneMotion ボーンモーション
     * @throws IOException 出力エラー
     */
    private void putBoneMotion(BoneMotion boneMotion)
            throws IOException{
        ind().putOpenSTag(XmlSyms.TAG_BONE_MOTION).sp();
        int frameNo = boneMotion.getFrameNumber();
        putIntAttr(XmlSyms.ATTR_FRAME, frameNo).sp();
        putCloseSTag().ln();

        pushNest();
        putBonePosition(boneMotion);
        if(this.isQuaternionMode){
            putBoneRotQuat(boneMotion);
        }else{
            putBoneRotEyxz(boneMotion);
        }
        popNest();

        ind().putETag(XmlSyms.TAG_BONE_MOTION).ln(2);

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

        ind().putOpenSTag(XmlSyms.TAG_BONE_POSITION).sp();
        MkPos3D position = boneMotion.getPosition();
        float xPos = (float) position.getXpos();
        float yPos = (float) position.getYpos();
        float zPos = (float) position.getZpos();
        putFloatAttr(XmlSyms.ATTR_X_POS, xPos).sp();
        putFloatAttr(XmlSyms.ATTR_Y_POS, yPos).sp();
        putFloatAttr(XmlSyms.ATTR_Z_POS, zPos).sp();

        PosCurve posCurve = boneMotion.getPosCurve();
        if(posCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();

            pushNest();
            putPositionCurve(posCurve);
            popNest();

            ind().putETag(XmlSyms.TAG_BONE_POSITION).ln();
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

        ind().putOpenSTag(XmlSyms.TAG_BONE_ROT_QUAT).ln();
        pushNest();
        ind().putFloatAttr(XmlSyms.ATTR_QX, (float) rotation.getQ1()).ln();
        ind().putFloatAttr(XmlSyms.ATTR_QY, (float) rotation.getQ2()).ln();
        ind().putFloatAttr(XmlSyms.ATTR_QZ, (float) rotation.getQ3()).ln();
        ind().putFloatAttr(XmlSyms.ATTR_QW, (float) rotation.getQW()).ln();
        popNest();
        ind();

        if(rotCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();
            pushNest();
            ind();
            putBezierCurve(rotCurve);
            ln();
            popNest();
            ind().putETag(XmlSyms.TAG_BONE_ROT_QUAT).ln();
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

        ind().putOpenSTag(XmlSyms.TAG_BONE_ROT_EYXZ).ln();
        pushNest();
        ind().putFloatAttr(XmlSyms.ATTR_X_DEG, xDeg).ln();
        ind().putFloatAttr(XmlSyms.ATTR_Y_DEG, yDeg).ln();
        ind().putFloatAttr(XmlSyms.ATTR_Z_DEG, zDeg).ln();
        popNest();
        ind();

        if(rotCurve.isDefaultLinear()){
            putCloseEmpty().ln();
        }else{
            putCloseSTag().ln();
            pushNest();
            ind();
            putBezierCurve(rotCurve);
            ln();
            popNest();
            ind().putETag(XmlSyms.TAG_BONE_ROT_EYXZ).ln();
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
        ind().putSimpleSTag(XmlSyms.TAG_MORPH_SEQUENCE).ln();

        pushNest();
        Map<String, List<MorphMotion>> listMap = vmdMotion.getMorphPartMap();
        if( ! listMap.isEmpty() ) ln();
        putMorphPartList(listMap);
        popNest();

        ind().putETag(XmlSyms.TAG_MORPH_SEQUENCE).ln(2);

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
                sp(2).putLineComment("Perhaps : [" + globalName + "]");
            }
            ln();

            ind().putOpenSTag(XmlSyms.TAG_MORPH_PART).sp();
            putAttr(XmlSyms.ATTR_NAME, morphName).sp();
            putCloseSTag().ln();

            pushNest();
            for(MorphMotion morph : list){
                putMorphMotion(morph);
            }
            popNest();

            ind().putETag(XmlSyms.TAG_MORPH_PART).ln(2);
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
        ind().putOpenSTag(XmlSyms.TAG_MORPH_MOTION).sp();

        int frameNo = morphMotion.getFrameNumber();
        float flex = morphMotion.getFlex();

        putIntAttr(XmlSyms.ATTR_FRAME, frameNo).sp();
        putFloatAttr(XmlSyms.ATTR_FLEX, flex).sp();

        putCloseEmpty().ln();

        return;
    }

}
