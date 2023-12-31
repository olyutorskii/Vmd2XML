/*
 * xml resources for VMD-XML
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 110820形式XML各種リソースの定義。
 */
public final class Schema110820{

    /** XML名前空間識別子。 */
    public static final String NS_VMDXML =
            "http://mikutoga.sourceforge.jp/xml/ns/vmdxml/110820";
    /** XMLスキーマURI名。 */
    public static final String SCHEMA_VMDXML =
            "http://mikutoga.sourceforge.jp/xml/xsd/vmdxml-110820.xsd";
    /** 定義の版数。 */
    public static final String VER_VMDXML =
            "110820";
    /** ローカルなスキーマファイル名。 */
    public static final String LOCAL_SCHEMA_VMDXML =
            "resources/vmdxml-110820.xsd";

    /** schema URI. */
    public static final URI URI_SCHEMA_VMDXML = URI.create(SCHEMA_VMDXML);
    /** local resource URI. */
    public static final URI RES_SCHEMA_VMDXML;

    private static final Class<?> THISCLASS = Schema110820.class;

    static{
        try{
            RES_SCHEMA_VMDXML =
                    THISCLASS.getResource(LOCAL_SCHEMA_VMDXML).toURI();
        }catch(URISyntaxException e){
            throw new ExceptionInInitializerError(e);
        }
    }


    /**
     * コンストラクタ。
     */
    private Schema110820(){
        assert false;
    }

}
