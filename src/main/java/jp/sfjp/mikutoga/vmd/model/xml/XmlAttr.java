/*
 * xml attribution names
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

/**
 * VMD-XML 各種属性名。
 */
final class XmlAttr {

    static final String ATTR_VERSION         = "version";

    static final String ATTR_NAME            = "name";
    static final String ATTR_CONTENT         = "content";

    static final String ATTR_FRAME           = "frame";

    static final String ATTR_QX              = "qx";
    static final String ATTR_QY              = "qy";
    static final String ATTR_QZ              = "qz";
    static final String ATTR_QW              = "qw";

    static final String ATTR_X_POS           = "xPos";
    static final String ATTR_Y_POS           = "yPos";
    static final String ATTR_Z_POS           = "zPos";

    static final String ATTR_X_DEG           = "xDeg";
    static final String ATTR_Y_DEG           = "yDeg";
    static final String ATTR_Z_DEG           = "zDeg";

    static final String ATTR_X_RAD           = "xRad";
    static final String ATTR_Y_RAD           = "yRad";
    static final String ATTR_Z_RAD           = "zRad";

    static final String ATTR_X_VEC           = "xVec";
    static final String ATTR_Y_VEC           = "yVec";
    static final String ATTR_Z_VEC           = "zVec";

    static final String ATTR_R_COL           = "rCol";
    static final String ATTR_G_COL           = "gCol";
    static final String ATTR_B_COL           = "bCol";

    static final String ATTR_P1X             = "p1x";
    static final String ATTR_P1Y             = "p1y";
    static final String ATTR_P2X             = "p2x";
    static final String ATTR_P2Y             = "p2y";

    static final String ATTR_VERT_DEG        = "vertDeg";
    static final String ATTR_HAS_PERSPECTIVE = "hasPerspective";
    static final String ATTR_RANGE           = "range";

    static final String ATTR_FLEX            = "flex";

    static final String ATTR_MODE            = "mode";
    static final String ATTR_RAW_PARAM       = "rawParam";

    static final String ATTR_SHOWMODEL       = "showModel";
    static final String ATTR_VALID           = "valid";


    /**
     * 隠しコンストラクタ。
     */
    private XmlAttr(){
        assert false;
        throw new AssertionError();
    }

}
