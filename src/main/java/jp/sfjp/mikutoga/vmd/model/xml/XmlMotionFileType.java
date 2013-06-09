/*
 * MMD motion xml file types.
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

/**
 * XMLファイルスキーマ種別。
 */
public enum XmlMotionFileType {

    /**
     * XMLファイル(自動判別)。
     * <p>読み込み時のスキーマ判別は自動。
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

}
