/*
 */

package testdata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jp.sourceforge.mikutoga.vmd2xml.MotionFileType;
import jp.sourceforge.mikutoga.vmd2xml.Vmd2XmlConv;

import static org.junit.Assert.*;

/**
 *
 */
public class CnvAssert {

    private CnvAssert(){
    }

    /**
     * テスト出力用テンポラリファイルの生成。
     * テスト終了時(VM終了時)に消える。
     * @return テンポラリファイル
     * @throws IOException エラー
     */
    public static File openTempFile() throws IOException{
        File file = File.createTempFile("vmd2xml", null);
        file.deleteOnExit();
        return file;
    }

    /**
     * XMLリソースをVMDに変換した結果がVMDリソースに等しいと表明する。
     * @param klass リソース元クラス
     * @param xmlResource XMLリソース名
     * @param expVmdResource VMDリソース名
     * @throws Exception エラー
     */
    public static void assertXml2Vmd(
            Class<?> klass,
            String xmlResource,
            String expVmdResource )
            throws Exception{
        InputStream xmlis =
                klass.getResourceAsStream(xmlResource);
        assertNotNull(xmlis);
        xmlis = new BufferedInputStream(xmlis);

        File destFile = openTempFile();
        OutputStream destOut;
        destOut = new FileOutputStream(destFile);
        destOut = new BufferedOutputStream(destOut);

        Vmd2XmlConv converter = new Vmd2XmlConv();
        converter.setInType(MotionFileType.XML_110820);
        converter.setOutType(MotionFileType.VMD);
        converter.setNewline("\n");

        converter.convert(xmlis, destOut);

        xmlis.close();
        destOut.close();

        assertSameFile(klass, expVmdResource, destFile);

        return;
    }

    /**
     * PMDリソースをXMLに変換した結果がXMLリソースに等しいと表明する。
     * @param klass リソース元クラス
     * @param pmdResource PMDリソース名
     * @param expXmlResource XMLリソース名
     * @throws Exception エラー
     */
    public static void assertVmd2Xml(
            Class<?> klass,
            String pmdResource,
            String expXmlResource )
            throws Exception{
        InputStream pmdis =
                klass.getResourceAsStream(pmdResource);
        assertNotNull(pmdis);
        pmdis = new BufferedInputStream(pmdis);

        File destFile = openTempFile();
        OutputStream destOut;
        destOut = new FileOutputStream(destFile);
        destOut = new BufferedOutputStream(destOut);

        Vmd2XmlConv converter = new Vmd2XmlConv();
        converter.setInType(MotionFileType.VMD);
        converter.setOutType(MotionFileType.XML_110820);
        converter.setNewline("\n");
        converter.setGenerator(null);

        converter.convert(pmdis, destOut);

        pmdis.close();
        destOut.close();

        assertSameFile(klass, expXmlResource, destFile);

        return;
    }

    /**
     * リソースとファイルの内容が等しいと表明する。
     * @param klass リソース元クラス
     * @param resourceName リソース名
     * @param resFile ファイル
     * @throws IOException 入力エラー
     */
    public static void assertSameFile(
            Class<?> klass,
            String resourceName,
            File resFile )
            throws IOException{
        InputStream expis =
                klass.getResourceAsStream(resourceName);
        assertNotNull(expis);

        InputStream resIn = new FileInputStream(resFile);

        try{
            assertSameStream(expis, resIn);
        }finally{
            expis.close();
            resIn.close();
        }

        return;
    }

    /**
     * 2つの入力ストリーム内容が等しいと表明する。
     * @param expIn 期待する入力ストリーム
     * @param resIn 結果入力ストリーム
     * @throws IOException 入力エラー
     */
    public static void assertSameStream(InputStream expIn, InputStream resIn)
            throws IOException{
        InputStream expis = new BufferedInputStream(expIn);
        InputStream resis = new BufferedInputStream(resIn);


        for(;;){
            int expCh = expis.read();
            int resCh = resis.read();

            assertEquals(expCh, resCh);

            if(expCh < 0) break;
        }

        return;
    }

}
