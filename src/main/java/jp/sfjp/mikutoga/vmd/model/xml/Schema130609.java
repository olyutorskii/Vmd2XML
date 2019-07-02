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
 * 130609形式XML各種リソースの定義。
 * <p>MikuMikuDance Ver7.40 の新VMDファイルフォーマット対応。
 */
public final class Schema130609{

    /** XML名前空間識別子。 */
    public static final String NS_VMDXML =
            "http://mikutoga.sourceforge.jp/xml/ns/vmdxml/130609";
    /** XMLスキーマURI名。 */
    public static final String SCHEMA_VMDXML =
            "http://mikutoga.sourceforge.jp/xml/xsd/vmdxml-130609.xsd";
    /** 定義の版数。 */
    public static final String VER_VMDXML =
            "130609";
    /** ローカルなスキーマファイル名。 */
    public static final String LOCAL_SCHEMA_VMDXML =
            "resources/vmdxml-130609.xsd";

    public static final URI URI_SCHEMA_VMDXML = URI.create(SCHEMA_VMDXML);
    public static final URI RES_SCHEMA_VMDXML;

    private static final Class<?> THISCLASS = Schema130609.class;

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
    private Schema130609(){
        assert false;
    }

}
