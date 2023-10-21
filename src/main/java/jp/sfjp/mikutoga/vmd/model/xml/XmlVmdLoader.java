/*
 * xml 2 vmd loader
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

import java.io.IOException;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import jp.sfjp.mikutoga.xml.TogaXmlException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * XMLモーションファイルを読み込むためのローダ。
 */
public class XmlVmdLoader {

    private final XMLReader reader;
    private final XmlHandler handler = new XmlHandler();


    /**
     * コンストラクタ。
     *
     * <p>XMLリーダは名前空間をサポートしていなければならない。
     *
     * @param reader XMLリーダ
     * @throws NullPointerException 引数がnull
     */
    public XmlVmdLoader(XMLReader reader) throws NullPointerException{
        super();
        if(reader == null) throw new NullPointerException();
        this.reader = reader;
        return;
    }


    /**
     * XMLのパースを開始する。
     *
     * @param source XML入力
     * @return モーションデータ
     * @throws SAXException 構文エラー
     * @throws IOException 入力エラー
     * @throws TogaXmlException 構文エラー
     */
    public VmdMotion parse(InputSource source)
            throws SAXException, IOException, TogaXmlException{
        this.reader.setContentHandler(this.handler);

        try{
            this.reader.parse(source);
        }catch(SAXException e){
            Throwable cause = e.getCause();
            if(cause instanceof TogaXmlException){
                throw (TogaXmlException) cause;
            }
            throw e;
        }

        return this.handler.getVmdMotion();
    }

}
