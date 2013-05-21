/*
 * MMD motion file types.
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd2xml;

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
     * スキーマ
     * http://mikutoga.sourceforge.jp/xml/xsd/vmdxml-110820.xsd
     * で定義されたXMLファイル。
     */
    XML_110820,

    ;


    /**
     * コンストラクタ。
     */
    private MotionFileType(){
        return;
    }

    /**
     * ファイル種別がXMLか判定する。
     * @return XMLならtrue
     */
    public boolean isXml(){
        if(this == XML_110820) return true;
        return false;
    }

    /**
     * ファイル種別がVMDか判定する。
     * @return VMDならtrue
     */
    public boolean isVmd(){
        if(this == VMD) return true;
        return false;
    }

}
