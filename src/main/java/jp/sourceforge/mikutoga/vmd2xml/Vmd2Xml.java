/*
 * vmd to xml converter main entry
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sourceforge.mikutoga.vmd2xml;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import jp.sourceforge.mikutoga.binio.FileUtils;
import jp.sourceforge.mikutoga.parser.MmdFormatException;
import jp.sourceforge.mikutoga.vmd.IllegalVmdDataException;
import jp.sourceforge.mikutoga.vmd.model.VmdMotion;
import jp.sourceforge.mikutoga.vmd.model.binio.VmdExporter;
import jp.sourceforge.mikutoga.vmd.model.binio.VmdLoader;
import jp.sourceforge.mikutoga.vmd.model.xml.VmdXmlExporter;
import jp.sourceforge.mikutoga.vmd.model.xml.VmdXmlResources;
import jp.sourceforge.mikutoga.vmd.model.xml.Xml2VmdLoader;
import jp.sourceforge.mikutoga.xml.TogaXmlException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * VMDモーションファイルとXMLとの間で変換を行うアプリケーション。
 */
public final class Vmd2Xml {

    private static final Class<?> THISCLASS;
    private static final String APPNAME;
    private static final String APPVER;
    private static final String APPLICENSE;
    private static final String APPURL;

    private static final int EXIT_OK     = 0;
    private static final int EXIT_IOERR  = 1;
    private static final int EXIT_ENVERR = 4;
    private static final int EXIT_OPTERR = 5;
    private static final int EXIT_INTERR = 6;
    private static final int EXIT_VMDERR = 7;
    private static final int EXIT_XMLERR = 8;

    private static final String ERRMSG_TXTONLY =
            "ERROR : {0}\n";
    private static final String ERRMSG_SAXPARSE =
            "ERROR : {0}\nline={1}, columun={2}\n";

    static{
        THISCLASS = Vmd2Xml.class;
        InputStream verDef =
                THISCLASS.getResourceAsStream("resources/version.properties");
        Properties verProps = new Properties();
        try{
            try{
                verProps.load(verDef);
            }finally{
                verDef.close();
            }
        }catch(IOException e){
            throw new ExceptionInInitializerError(e);
        }

        APPNAME    = verProps.getProperty("app.name");
        APPVER     = verProps.getProperty("app.version");
        APPLICENSE = verProps.getProperty("app.license");
        APPURL     = verProps.getProperty("app.url");

        new Vmd2Xml().hashCode();
    }


    /**
     * 隠しコンストラクタ。
     */
    private Vmd2Xml(){
        super();
        assert this.getClass().equals(THISCLASS);
        return;
    }


    /**
     * Mainエントリ。
     * @param args コマンドパラメータ
     */
    public static void main(String[] args){
        checkJRE();

        ArgInfo argInfo = ArgInfo.buildArgInfo(args);
        if(argInfo.hasOptionError()){
            String message = argInfo.getErrorMessage() + "\n"
                    + "(-h for help)\n";
            errExit(EXIT_OPTERR, message);
        }

        if(argInfo.isHelpMode()){
            putHelp();
            exit(EXIT_OK);
        }

        checkFiles(argInfo);

        String input  = argInfo.getInputFile();
        String output = argInfo.getOutputFile();

        InputStream is = null;
        OutputStream os = null;
        try{
            is = openInputStream(input);
            os = openTruncatedOutputStream(output);
        }catch(FileNotFoundException e){
            ioError(e);
        }catch(IOException e){
            ioError(e);
        }

        if(argInfo.isVmd2XmlMode()) vmd2xml(is, os, argInfo);
        else                        xml2vmd(is, os);

        exit(EXIT_OK);

        return;
    }

    /**
     * JREのバージョン判定を行う。
     * 不適切ならVMごと終了。
     */
    private static void checkJRE(){
        Package jrePackage = java.lang.Object.class.getPackage();
        if( ! jrePackage.isCompatibleWith("1.6")){
            errExit(EXIT_ENVERR, "You need JRE 1.6 or later.\n");
        }
        return;
    }

    /**
     * ヘルプメッセージを出力する。
     */
    private static void putHelp(){
        StringBuilder text = new StringBuilder();

        text.append(APPNAME).append(' ').append(APPVER).append('\n');
        text.append("  License : ").append(APPLICENSE).append('\n');
        text.append("  ").append(APPURL).append('\n');
        text.append('\n');
        text.append(ArgInfo.CMD_HELP);

        errprint(text);

        return;
    }

    /**
     * ファイルの各種状態を事前にチェックする。
     * @param argInfo コマンドライン引数
     */
    private static void checkFiles(ArgInfo argInfo){
        String input  = argInfo.getInputFile();
        String output = argInfo.getOutputFile();
        File iFile = new File(input);
        File oFile = new File(output);

        if( ! FileUtils.isExistsNormalFile(iFile) ){
            errExit(EXIT_IOERR, "Can't find input file:"
                    + iFile.getAbsolutePath() + '\n');
        }

        if(argInfo.isForceMode()){
            if(FileUtils.isExistsUnnormalFile(oFile)){
                errExit(EXIT_IOERR, oFile.getAbsolutePath()
                        + " is not file.\n");
            }
        }else if(oFile.exists()){
            errExit(EXIT_IOERR, oFile.getAbsolutePath()
                    + " already exists.\n"
                    + "If you want to overwrite, use -f.\n");
        }

        return;
    }

    /**
     * VMD->XML変換を行う。
     * @param istream 入力ストリーム
     * @param ostream 出力ストリーム
     * @param argInfo オプション設定
     */
    private static void vmd2xml(InputStream istream, OutputStream ostream,
                                ArgInfo argInfo ){
        try{
            vmd2xmlImpl(istream, ostream, argInfo);
        }catch(IOException e){
            ioError(e);
        }catch(MmdFormatException e){
            vmdError(e);
        }catch(IllegalVmdDataException e){
            internalError(e);
        }

        return;
    }

    /**
     * VMD->XML変換を行う。
     * @param istream 入力ストリーム
     * @param ostream 出力ストリーム
     * @param argInfo オプション設定
     * @throws IOException 入出力エラー
     * @throws MmdFormatException 不正なVMDファイル
     * @throws IllegalVmdDataException 不正なモーションデータ
     */
    private static void vmd2xmlImpl(InputStream istream, OutputStream ostream,
                                    ArgInfo argInfo )
            throws IOException, MmdFormatException, IllegalVmdDataException{
        VmdMotion motion;
        try{
            motion = vmdRead(istream);
        }finally{
            istream.close();
        }

        motion.frameSort();

        try{
            xmlOut(motion, ostream, argInfo);
        }finally{
            ostream.close();
        }

        return;
    }

    /**
     * XML->VMD変換を行う。
     * @param istream 入力ストリーム
     * @param ostream 出力ストリーム
     */
    private static void xml2vmd(InputStream istream, OutputStream ostream){
        try{
            xml2vmdImpl(istream, ostream);
        }catch(IOException e){
            ioError(e);
        }catch(SAXException e){
            xmlError(e);
        }catch(TogaXmlException e){
            xmlError(e);
        }catch(IllegalVmdDataException e){
            internalError(e);
        }

        return;
    }

    /**
     * XML->VMD変換を行う。
     * @param istream 入力ストリーム
     * @param ostream 出力ストリーム
     * @throws IOException 入出力エラー
     * @throws SAXException 不正なXMLファイル
     * @throws TogaXmlException 不正なXMLファイル
     * @throws IllegalVmdDataException 不正なVMDモーションデータ
     */
    private static void xml2vmdImpl(InputStream istream, OutputStream ostream)
            throws IOException,
                   SAXException,
                   TogaXmlException,
                   IllegalVmdDataException {
        InputSource source = new InputSource(istream);
        VmdMotion motion;
        try{
            motion = xmlRead(source);
        }finally{
            istream.close();
        }

        motion.frameSort();

        try{
            vmdOut(motion, ostream);
        }finally{
            ostream.close();
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
    private static VmdMotion vmdRead(InputStream is)
            throws IOException, MmdFormatException{
        VmdMotion vmdMotion = VmdLoader.load(is);
        return vmdMotion;
    }

    /**
     * モーションデータをXMLファイルに出力する。
     * @param motion モーションデータ
     * @param ostream 出力ストリーム
     * @param argInfo オプション設定
     * @throws IOException 出力エラー
     * @throws IllegalVmdDataException 不正なモーションデータ
     */
    private static void xmlOut(VmdMotion motion, OutputStream ostream,
                               ArgInfo argInfo)
            throws IOException, IllegalVmdDataException{
        VmdXmlExporter exporter = new VmdXmlExporter(ostream);

        exporter.setNewLine("\r\n");
        exporter.setGenerator(APPNAME + ' ' + APPVER);

        boolean isQuaternionMode = argInfo.isQuaternionMode();
        exporter.setQuaternionMode(isQuaternionMode);

        exporter.putVmdXml(motion);
        exporter.close();

        return;
    }

    /**
     * XMLファイルからモーションデータを読み込む。
     * @param source 入力ソース
     * @return モーションデータ
     * @throws IOException 入力エラー
     * @throws SAXException XML構文エラー
     * @throws TogaXmlException 不正なXMLデータ
     */
    private static VmdMotion xmlRead(InputSource source)
            throws IOException,
                   SAXException,
                   TogaXmlException {
        DocumentBuilder builder =
                VmdXmlResources.newBuilder(ValidationHandler.HANDLER);
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
    private static void vmdOut(VmdMotion motion, OutputStream ostream)
            throws IOException, IllegalVmdDataException{
        VmdExporter exporter = new VmdExporter(ostream);
        exporter.dumpVmdMotion(motion);
        ostream.close();
        return;
    }

    /**
     * 入力ストリームを得る。
     * @param fileName 入力ファイル名
     * @return 入力ストリーム
     * @throws FileNotFoundException 入力ファイルが見つからない。
     */
    private static InputStream openInputStream(String fileName)
            throws FileNotFoundException {
        File file = new File(fileName);
        InputStream result = new FileInputStream(file);
        result = new BufferedInputStream(result);
        return result;
    }

    /**
     * 出力ストリームを得る。
     * @param fileName 出力ファイル名
     * @return 出力ストリーム
     * @throws FileNotFoundException 出力ファイルが見つからない
     * @throws IOException 出力エラー
     */
    private static OutputStream openTruncatedOutputStream(String fileName)
            throws FileNotFoundException, IOException {
        File file = new File(fileName);
        FileUtils.trunc(file);
        OutputStream result = new FileOutputStream(file, false);
        result = new BufferedOutputStream(result);
        return result;
    }

    /**
     * 入出力エラー処理。
     * 例外を出力してVM終了する。
     * @param ex 例外
     */
    private static void ioError(IOException ex){
        errprint(ex);
        errprint('\n');
        exit(EXIT_IOERR);

        return;
    }

    /**
     * 内部エラー処理。
     * 例外を出力してVM終了する。
     * @param ex 例外
     */
    private static void internalError(Throwable ex){
        errprint(ex);
        errprint('\n');
        ex.printStackTrace(System.err);
        exit(EXIT_INTERR);

        return;
    }

    /**
     * VMDファイルフォーマットエラー処理。
     * 例外を出力してVM終了する。
     * @param ex 例外
     */
    private static void vmdError(MmdFormatException ex){
        errprint(ex);
        errprint('\n');
        ex.printStackTrace(System.err);
        exit(EXIT_VMDERR);

        return;
    }

    /**
     * XML構文エラー処理。
     * 例外を出力してVM終了する。
     * @param ex 例外
     */
    private static void xmlError(SAXException ex){
        if(ex instanceof SAXParseException){
            xmlError((SAXParseException)ex);
        }

        String txt = ex.getLocalizedMessage();
        String message = MessageFormat.format(ERRMSG_TXTONLY, txt);
        errprint(message);

        exit(EXIT_XMLERR);

        return;
    }

    /**
     * XML構文エラー処理。
     * 例外を出力してVM終了する。
     * @param ex 例外
     */
    private static void xmlError(SAXParseException ex){
        String txt = ex.getLocalizedMessage();
        int line = ex.getLineNumber();
        int col = ex.getColumnNumber();

        String message =
                MessageFormat.format(ERRMSG_SAXPARSE, txt, line, col);
        errprint(message);

        exit(EXIT_XMLERR);

        return;
    }

    /**
     * XML構文エラー処理。
     * 例外を出力してVM終了する。
     * @param ex 例外
     */
    private static void xmlError(TogaXmlException ex){
        String txt = ex.getLocalizedMessage();
        String message = MessageFormat.format(ERRMSG_TXTONLY, txt);
        errprint(message);

        exit(EXIT_XMLERR);

        return;
    }

    /**
     * VMを終了する。
     * @param code 終了コード
     */
    private static void exit(int code){
        System.exit(code);
        assert false;
        throw new AssertionError();
    }

    /**
     * 標準エラー出力に文字列出力を行う。
     * @param obj 文字列
     */
    @SuppressWarnings("PMD.SystemPrintln")
    private static void errprint(Object obj){
        System.err.print(obj.toString());
        return;
    }

    /**
     * エラーを表示した後VMを終了させる。
     * @param code 終了コード
     * @param text メッセージ
     */
    private static void errExit(int code, CharSequence text){
        errprint("ERROR:\n");
        errprint(text);
        exit(code);
        return;
    }

}
