/*
 * xml symbols
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sourceforge.mikutoga.vmd.model.xml;

/**
 * XML 各種シンボル名。
 */
final class XmlSyms {

    static final String TOP_COMMENT =
            "  MikuMikuDance\n    motion-data(*.vmd) on XML";

    static final String QUATERNION_COMMENT =
          "  bone-rotation has Quaternion parameters [boneRotQuat]\n"
        + "  or YXZ-Euler angles [boneRotEyxz].\n"
        + "  Quaternion form is strongly recommended"
        +   " if you are data-exchanging.";

    static final String BEZIER_COMMENT =
          "  motion-interpolation is described with Bezier-cubic-curve.\n"
        + "  implicit bezier curve point : P0=(0,0) P3=(127,127)\n"
        + "  defLinear : MMD default linear curve."
        + " P1=(20,20) P2=(107,107) [DEFAULT]\n"
        + "  defEaseInOut : MMD default ease-in-out curve."
        + " P1=(64,0) P2=(64,127)";

    static final String CAMERA_COMMENT =
          "  camera-rotation has polar-coordinates parameters.\n"
        + "  xRad = -radian(UI_X) [latitude]\n"
        + "  yRad =  radian(UI_Y) [longitude]\n"
        + "  zRad =  radian(UI_Z) [roll]\n"
        + "  range = -(UI_RANGE)";

    static final String SHADOW_COMMENT =
         "  UI_VALUE = EFFECTIVE_RANGE * 100 ???\n"
        +"  rawParam = 0.1 - (UI_VALUE / 1.0E+5)\n\n"
        +"  NONE   : no self-shadow\n"
        +"  MODE_1 : reduce shadow-quality suddenly at range\n"
        +"  MODE_2 : reduce shadow-quality gradually with range";

    static final String TAG_VMD_MOTION      = "vmdMotion";
    static final String TAG_META            = "meta";
    static final String TAG_MODEL_NAME      = "modelName";
    static final String TAG_BONE_M_SEQUENCE = "boneMotionSequence";
    static final String TAG_BONE_PART       = "bonePart";
    static final String TAG_BONE_MOTION     = "boneMotion";
    static final String TAG_BONE_POSITION   = "bonePosition";
    static final String TAG_BONE_ROT_QUAT   = "boneRotQuat";
    static final String TAG_BONE_ROT_EYXZ   = "boneRotEyxz";
    static final String TAG_BEZIER          = "bezier";
    static final String TAG_DEF_LINEAR      = "defLinear";
    static final String TAG_DEF_EASE_IN_OUT = "defEaseInOut";
    static final String TAG_MORPH_SEQUENCE  = "morphSequence";
    static final String TAG_MORPH_PART      = "morphPart";
    static final String TAG_MORPH_MOTION    = "morphMotion";
    static final String TAG_CAMERA_SEQUENCE = "cameraSequence";
    static final String TAG_CAMERA_MOTION   = "cameraMotion";
    static final String TAG_CAMERA_TARGET   = "cameraTarget";
    static final String TAG_CAMERA_ROTATION = "cameraRotation";
    static final String TAG_CAMERA_RANGE    = "cameraRange";
    static final String TAG_PROJECTION      = "projection";
    static final String TAG_LUMI_SEQUENCE   = "luminousSequence";
    static final String TAG_LUMINOUS_ACT    = "luminousAct";
    static final String TAG_LUMI_COLOR      = "lumiColor";
    static final String TAG_LUMI_DIRECTION  = "lumiDirection";
    static final String TAG_SHADOW_SEQUENCE = "shadowSequence";
    static final String TAG_SHADOW_ACT      = "shadowAct";

    static final String ATTR_VERSION         = "version";
    static final String ATTR_CONTENT         = "content";
    static final String ATTR_FRAME           = "frame";
    static final String ATTR_NAME            = "name";
    static final String ATTR_P1X             = "p1x";
    static final String ATTR_P1Y             = "p1y";
    static final String ATTR_P2X             = "p2x";
    static final String ATTR_P2Y             = "p2y";
    static final String ATTR_QX              = "qx";
    static final String ATTR_QY              = "qy";
    static final String ATTR_QZ              = "qz";
    static final String ATTR_QW              = "qw";
    static final String ATTR_X_DEG           = "xDeg";
    static final String ATTR_Y_DEG           = "yDeg";
    static final String ATTR_Z_DEG           = "zDeg";
    static final String ATTR_X_POS           = "xPos";
    static final String ATTR_Y_POS           = "yPos";
    static final String ATTR_Z_POS           = "zPos";
    static final String ATTR_FLEX            = "flex";
    static final String ATTR_RANGE           = "range";
    static final String ATTR_X_RAD           = "xRad";
    static final String ATTR_Y_RAD           = "yRad";
    static final String ATTR_Z_RAD           = "zRad";
    static final String ATTR_VERT_DEG        = "vertDeg";
    static final String ATTR_HAS_PERSPECTIVE = "hasPerspective";
    static final String ATTR_X_VEC           = "xVec";
    static final String ATTR_Y_VEC           = "yVec";
    static final String ATTR_Z_VEC           = "zVec";
    static final String ATTR_R_COL           = "rCol";
    static final String ATTR_G_COL           = "gCol";
    static final String ATTR_B_COL           = "bCol";
    static final String ATTR_MODE            = "mode";
    static final String ATTR_RAW_PARAM       = "rawParam";


    /**
     * 隠しコンストラクタ。
     */
    private XmlSyms(){
        assert false;
        throw new AssertionError();
    }

}
