/*
 * vmd 2 xml converter
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd2xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import jp.sfjp.mikutoga.bin.parser.MmdFormatException;
import jp.sfjp.mikutoga.vmd.IllegalVmdDataException;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import jp.sfjp.mikutoga.vmd.model.binio.VmdExporter;
import jp.sfjp.mikutoga.vmd.model.binio.VmdLoader;
import jp.sourceforge.mikutoga.vmd.model.xml.Schema110820;
import jp.sourceforge.mikutoga.vmd.model.xml.VmdXmlExporter;
import jp.sourceforge.mikutoga.vmd.model.xml.Xml2VmdLoader;
import jp.sourceforge.mikutoga.xml.BotherHandler;
import jp.sourceforge.mikutoga.xml.SchemaUtil;
import jp.sourceforge.mikutoga.xml.TogaXmlException;
import jp.sourceforge.mikutoga.xml.XmlResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * VMD-XML間コンバータ本体。
 */
public class Vmd2XmlConv {

    private MotionFileType inTypes  = MotionFileType.NONE;
    private MotionFileType outTypes = MotionFileType.NONE;
    private String newLine = "\r\n";
    private String generator = null;
    private boolean isQuaternionMode = true;


    /**
     * コンストラクタ。
     */
    public Vmd2XmlConv(){
        super();
        return;
    }


    /**
     * ドキュメントビルダファクトリを初期化する。
     * @param builderFactory ドキュメントビルダファクトリ
     */
    private static void initBuilderFactory(
            DocumentBuilderFactory builderFactory ){
        builderFactory.setCoalescing(true);
        builderFactory.setExpandEntityReferences(true);
        builderFactory.setIgnoringComments(true);
        builderFactory.setIgnoringElementContentWhitespace(false);
        builderFactory.setNamespaceAware(true);
        builderFactory.setValidating(false);
        builderFactory.setXIncludeAware(false);

//      builderFactory.setFeature(name, value);
//      builderFactory.setAttribute(name, value);

        return;
    }

    /**
     * DOMビルダ生成。
     * @return DOMビルダ
     */
    private DocumentBuilder buildBuilder(){
        XmlResourceResolver resolver = new XmlResourceResolver();

        Schema schema;
        schema = SchemaUtil.newSchema(resolver, Schema110820.SINGLETON);

        DocumentBuilderFactory builderFactory =
                DocumentBuilderFactory.newInstance();
        initBuilderFactory(builderFactory);
        builderFactory.setSchema(schema);

        DocumentBuilder result;
        try{
            result = builderFactory.newDocumentBuilder();
        }catch(ParserConfigurationException e){
            assert false;
            throw new AssertionError(e);
        }
        result.setEntityResolver(resolver);
        result.setErrorHandler(BotherHandler.HANDLER);

        return result;
    }

    /**
     * 入力ファイル種別を設定する。
     * @param type ファイル種別
     * @throws IllegalArgumentException 具体的な種別を渡さなかった
     */
    public void setInType(MotionFileType type)
            throws IllegalArgumentException {
        if(type == null) throw new NullPointerException();
        if(type == MotionFileType.NONE) throw new IllegalArgumentException();
        this.inTypes = type;
        return;
    }

    /**
     * 入力ファイル種別を返す。
     * @return ファイル種別
     */
    public MotionFileType getInTypes(){
        return this.inTypes;
    }

    /**
     * 出力ファイル種別を設定する。
     * @param type ファイル種別
     * @throws IllegalArgumentException 具体的な種別を渡さなかった
     */
    public void setOutType(MotionFileType type)
            throws IllegalArgumentException {
        if(type == null) throw new NullPointerException();
        if(type == MotionFileType.NONE) throw new IllegalArgumentException();
        this.outTypes = type;
        return;
    }

    /**
     * 出力ファイル種別を返す。
     * @return ファイル種別
     */
    public MotionFileType getOutTypes(){
        return this.outTypes;
    }

    /**
     * XML出力用改行文字列を設定する。
     * @param newline 改行文字
     */
    public void setNewline(String newline){
        this.newLine = newline;
        return;
    }

    /**
     * XML出力用改行文字列を返す。
     * @return 改行文字
     */
    public String getNewline(){
        return this.newLine;
    }

    /**
     * ジェネレータ名を設定する。
     * @param generator ジェネレータ名。表示したくない場合はnull
     */
    public void setGenerator(String generator){
        this.generator = generator;
        return;
    }

    /**
     * ジェネレータ名を返す。
     * @return ジェネレータ名。非表示の場合はnullを返す。
     */
    public String getGenerator(){
        return this.generator;
    }

    /**
     * 回転情報をクォータニオン形式でXML出力するか設定する。
     * @param sw クォータニオン形式ならtrue、
     * YXZオイラー角で出力したければfalse。
     */
    public void setQuaterniomMode(boolean sw){
        this.isQuaternionMode = sw;
        return;
    }

    /**
     * 回転情報のXML出力形式を得る。
     * @return クォータニオン形式ならtrue、YXZオイラー角形式ならfalse。
     */
    public boolean isQuaterniomMode(){
        return this.isQuaternionMode;
    }

    /**
     * ファイル変換を行う。
     * @param is 入力ストリーム
     * @param os 出力ストリーム
     * @throws IOException 入力エラー
     * @throws MmdFormatException フォーマットエラー
     * @throws SAXException XMLエラー
     * @throws TogaXmlException XMLエラー
     * @throws IllegalVmdDataException 内部エラー
     */
    public void convert(InputStream is, OutputStream os)
            throws IOException,
                   MmdFormatException,
                   SAXException,
                   TogaXmlException,
                   IllegalVmdDataException {
        VmdMotion motion = readMotion(is);
        motion.frameSort();
        writeMotion(motion, os);
        return;
    }

    /**
     * モーションファイルを読み込む。
     * @param is 入力ストリーム
     * @return モーションデータ
     * @throws IOException 入力エラー
     * @throws MmdFormatException フォーマットエラー
     * @throws SAXException XMLエラー
     * @throws TogaXmlException XMLエラー
     */
    public VmdMotion readMotion(InputStream is)
            throws IOException,
                   MmdFormatException,
                   SAXException,
                   TogaXmlException {
        VmdMotion motion = null;

        if(this.inTypes.isVmd()){
            motion = vmdRead(is);
        }else if(this.inTypes.isXml()){
            motion = xmlRead(is);
        }else{
            throw new IllegalStateException();
        }

        return motion;
    }

    /**
     * モーションファイルを出力する。
     * @param motion モーションデータ
     * @param os 出力ストリーム
     * @throws IOException 出力エラー
     * @throws IllegalVmdDataException データの不備
     */
    public void writeMotion(VmdMotion motion, OutputStream os)
            throws IOException,
                   IllegalVmdDataException {
        if(this.outTypes.isVmd()){
            vmdOut(motion, os);
        }else if(this.outTypes.isXml()){
            xmlOut(motion, os);
        }else{
            throw new IllegalStateException();
        }

        return;
    }

    /**
     * VMDファイルからモーションデータを読み込む。
     * @param is 入力ストリーム
     * @return モーションデータ
     * @throws IOException 入力エラー
     * @throws MmdFormatException 不正なVMDファイルフォーマット
     */
    private VmdMotion vmdRead(InputStream is)
            throws IOException, MmdFormatException{
        VmdLoader loader = new VmdLoader();

        loader.setIgnoreName(true);
        loader.setRedundantCheck(false);

        VmdMotion motion = loader.load(is);

        return motion;
    }

    /**
     * XMLファイルからモーションデータを読み込む。
     * @param is 入力ストリーム
     * @return モーションデータ
     * @throws IOException 入力エラー
     * @throws SAXException XML構文エラー
     * @throws TogaXmlException 不正なXMLデータ
     */
    private VmdMotion xmlRead(InputStream is)
            throws IOException,
                   SAXException,
                   TogaXmlException {
        InputSource source = new InputSource(is);
        VmdMotion result = xmlRead(source);
        return result;
    }

    /**
     * XMLファイルからモーションデータを読み込む。
     * @param source 入力ソース
     * @return モーションデータ
     * @throws IOException 入力エラー
     * @throws SAXException XML構文エラー
     * @throws TogaXmlException 不正なXMLデータ
     */
    private VmdMotion xmlRead(InputSource source)
            throws IOException,
                   SAXException,
                   TogaXmlException {
        DocumentBuilder builder = buildBuilder();
        Xml2VmdLoader loader = new Xml2VmdLoader(builder);
        VmdMotion motion = loader.parse(source);
        return motion;
    }

    /**
     * モーションデータをVMDファイルに出力する。
     * @param motion モーションデータ
     * @param ostream 出力ストリーム
     * @throws IOException 出力エラー
     * @throws IllegalVmdDataException 不正なモーションデータ
     */
    private void vmdOut(VmdMotion motion, OutputStream ostream)
            throws IOException, IllegalVmdDataException{
        VmdExporter exporter = new VmdExporter();
        exporter.dumpVmdMotion(motion, ostream);
        ostream.close();
        return;
    }

    /**
     * モーションデータをXMLファイルに出力する。
     * @param motion モーションデータ
     * @param ostream 出力ストリーム
     * @throws IOException 出力エラー
     * @throws IllegalVmdDataException 不正なモーションデータ
     */
    private void xmlOut(VmdMotion motion, OutputStream ostream)
            throws IOException, IllegalVmdDataException{
        VmdXmlExporter exporter = new VmdXmlExporter(ostream);

        exporter.setNewLine(this.newLine);
        exporter.setGenerator(this.generator);
        exporter.setQuaternionMode(this.isQuaternionMode);

        exporter.putVmdXml(motion);

        exporter.close();

        return;
    }

}
