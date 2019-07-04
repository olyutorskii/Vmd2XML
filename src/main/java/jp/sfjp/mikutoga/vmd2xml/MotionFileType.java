/*
 * MMD motion file types.
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd2xml;

import jp.sfjp.mikutoga.vmd.model.xml.XmlMotionFileType;

/**
 * モーションファイル種別。
 */
public enum MotionFileType {

    /**
     * 不明。
     */
    NONE,

    /**
     * MikuMikuDance ver7 前後で読み書きが可能なVMDファイル。
     */
    VMD,

    /**
     * XMLファイル(自動判別)。
     *
     * <p>読み込み時のスキーマ判別は自動。
     *
     * <p>書き込み時のスキーマは最新。
     */
    XML_AUTO,

    /**
     * スキーマ
     * http://mikutoga.sourceforge.jp/xml/xsd/vmdxml-110820.xsd
     * で定義されたXMLファイル。
     */
    XML_110820,

    /**
     * スキーマ
     * http://mikutoga.sourceforge.jp/xml/xsd/vmdxml-130609.xsd
     * で定義されたXMLファイル。
     * MikuMikuDance Ver7.40対応。
     */
    XML_130609,

    ;


    /**
     * コンストラクタ。
     */
    MotionFileType(){
        return;
    }


    /**
     * ファイル種別をXMLファイル種別に変換する。
     *
     * <p>未定義の場合はXML_AUTOを返す。
     *
     * @return XMLファイル種別
     */
    public XmlMotionFileType toXmlType(){
        XmlMotionFileType result;

        switch(this){
        case XML_110820:
            result = XmlMotionFileType.XML_110820;
            break;
        case XML_130609:
            result = XmlMotionFileType.XML_130609;
            break;
        case XML_AUTO:
            result = XmlMotionFileType.XML_AUTO;
            break;
        default:
            result = XmlMotionFileType.XML_AUTO;
            break;
        }

        return result;
    }

    /**
     * ファイル種別がXMLか判定する。
     *
     * @return XMLならtrue
     */
    public boolean isXml(){
        switch(this){
        case XML_AUTO:
        case XML_110820:
        case XML_130609:
            return true;
        default:
            break;
        }

        return false;
    }

    /**
     * ファイル種別がVMDか判定する。
     *
     * @return VMDならtrue
     */
    public boolean isVmd(){
        if(this == VMD) return true;
        return false;
    }

}
