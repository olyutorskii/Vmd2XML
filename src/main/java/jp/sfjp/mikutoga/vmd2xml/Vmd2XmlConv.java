/*
 * vmd 2 xml converter
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd2xml;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import jp.sfjp.mikutoga.bin.parser.MmdFormatException;
import jp.sfjp.mikutoga.vmd.IllegalVmdDataException;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import jp.sfjp.mikutoga.vmd.model.binio.VmdExporter;
import jp.sfjp.mikutoga.vmd.model.binio.VmdLoader;
import jp.sfjp.mikutoga.vmd.model.xml.VmdXmlExporter;
import jp.sfjp.mikutoga.vmd.model.xml.XmlMotionFileType;
import jp.sfjp.mikutoga.vmd.model.xml.XmlVmdLoader;
import jp.sfjp.mikutoga.xml.TogaXmlException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * VMD-XML間コンバータ本体。
 */
public class Vmd2XmlConv {

    /** デフォルトエンコーディング。 */
    private static final Charset CS_UTF8 = Charset.forName("UTF-8");


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
     * 入力ファイル種別を設定する。
     *
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
     *
     * @return ファイル種別
     */
    public MotionFileType getInTypes(){
        return this.inTypes;
    }

    /**
     * 出力ファイル種別を設定する。
     *
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
     *
     * @return ファイル種別
     */
    public MotionFileType getOutTypes(){
        return this.outTypes;
    }

    /**
     * XML出力用改行文字列を設定する。
     *
     * @param newline 改行文字
     */
    public void setNewline(String newline){
        this.newLine = newline;
        return;
    }

    /**
     * XML出力用改行文字列を返す。
     *
     * @return 改行文字
     */
    public String getNewline(){
        return this.newLine;
    }

    /**
     * ジェネレータ名を設定する。
     *
     * @param generator ジェネレータ名。表示したくない場合はnull
     */
    public void setGenerator(String generator){
        this.generator = generator;
        return;
    }

    /**
     * ジェネレータ名を返す。
     *
     * @return ジェネレータ名。非表示の場合はnullを返す。
     */
    public String getGenerator(){
        return this.generator;
    }

    /**
     * 回転情報をクォータニオン形式でXML出力するか設定する。
     *
     * @param sw クォータニオン形式ならtrue、
     *     YXZオイラー角で出力したければfalse。
     */
    public void setQuaterniomMode(boolean sw){
        this.isQuaternionMode = sw;
        return;
    }

    /**
     * 回転情報のXML出力形式を得る。
     *
     * @return クォータニオン形式ならtrue、YXZオイラー角形式ならfalse。
     */
    public boolean isQuaterniomMode(){
        return this.isQuaternionMode;
    }

    /**
     * ファイル変換を行う。
     *
     * <p>XML入力の場合は{@link #convert(InputSource, OutputStream)}を
     * 推奨する。
     *
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
     * ファイル変換を行う。
     *
     * <p>VMD入力の場合は{@link InputStream}に
     * バイトストリームが直接設定されていなければならない。
     *
     * <p>XML入力の場合は{@link InputStream}に
     * URL(systemId)のみの設定を推奨する。
     *
     * @param source 入力ソース
     * @param os 出力ストリーム
     * @throws IOException 入力エラー
     * @throws MmdFormatException フォーマットエラー
     * @throws SAXException XMLエラー
     * @throws TogaXmlException XMLエラー
     * @throws IllegalVmdDataException 内部エラー
     */
    public void convert(InputSource source, OutputStream os)
            throws IOException,
                   MmdFormatException,
                   SAXException,
                   TogaXmlException,
                   IllegalVmdDataException {
        VmdMotion motion = readMotion(source);
        motion.frameSort();
        writeMotion(motion, os);
        return;
    }

    /**
     * モーションファイルを読み込む。
     *
     * <p>XML読み込みの場合は、
     * こちらより{@link #readMotion(InputSource)}版を推奨する。
     *
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
        InputSource source = new InputSource(is);

        VmdMotion motion;
        try{
            motion = readMotion(source);
        }finally{
            is.close();
        }

        return motion;
    }

    /**
     * モーションファイルを読み込む。
     *
     * <p>VMD入力の場合は、{@link InputStream}に
     * 納められたバイトストリームかSystemId-URLから読み込む。
     *
     * @param source 入力ソース
     * @return モーションデータ
     * @throws IOException 入力エラー
     * @throws MmdFormatException フォーマットエラー
     * @throws SAXException XMLエラー
     * @throws TogaXmlException XMLエラー
     */
    public VmdMotion readMotion(InputSource source)
            throws IOException,
                   MmdFormatException,
                   SAXException,
                   TogaXmlException {
        VmdMotion motion = null;

        if(this.inTypes.isVmd()){
            InputStream is = XmlInputUtil.openInputSource(source);
            try{
                motion = vmdRead(is);
            }finally{
                is.close();
            }
        }else if(this.inTypes.isXml()){
            motion = xmlRead(source);
        }else{
            throw new IllegalStateException();
        }

        return motion;
    }

    /**
     * モーションファイルを出力する。
     *
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
     *
     * <p>入力ストリームは最後に閉じられる。
     *
     * @param is 入力ストリーム
     * @return モーションデータ
     * @throws IOException 入力エラー
     * @throws MmdFormatException 不正なVMDファイルフォーマット
     */
    private VmdMotion vmdRead(InputStream is)
            throws IOException, MmdFormatException{
        VmdLoader loader = new VmdLoader();

        loader.setRedundantCheck(false);

        VmdMotion motion = loader.load(is);

        return motion;
    }

    /**
     * XMLファイルからモーションデータを読み込む。
     *
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
        XMLReader reader = XmlInputUtil.buildReader(this.inTypes);
        XmlVmdLoader loader = new XmlVmdLoader(reader);

        VmdMotion motion = loader.parse(source);

        return motion;
    }

    /**
     * モーションデータをVMDファイルに出力する。
     *
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
     *
     * @param motion モーションデータ
     * @param ostream 出力ストリーム
     * @throws IOException 出力エラー
     * @throws IllegalVmdDataException 不正なモーションデータ
     */
    private void xmlOut(VmdMotion motion, OutputStream ostream)
            throws IOException, IllegalVmdDataException{
        VmdXmlExporter exporter = new VmdXmlExporter();

        XmlMotionFileType xmlType = this.outTypes.toXmlType();
        exporter.setXmlFileType(xmlType);
        exporter.setNewLine(this.newLine);
        exporter.setGenerator(this.generator);
        exporter.setQuaternionMode(this.isQuaternionMode);

        Writer writer;
        writer = new OutputStreamWriter(ostream, CS_UTF8);
        writer = new BufferedWriter(writer);

        exporter.putVmdXml(motion, writer);

        exporter.close();

        return;
    }

}
