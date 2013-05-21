/*
 * xml comment strings
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

/**
 * 各種XMLコメント内容。
 */
final class XmlComment {

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


    /**
     * 隠しコンストラクタ。
     */
    private XmlComment(){
        assert false;
        throw new AssertionError();
    }

}
