/*
 * tags of vmd xml file
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * XML要素名一覧。
 */
enum VmdTag {

    VMD_MOTION     ("vmdMotion"),
    META           ("meta"),
    MODEL_NAME     ("modelName"),

    BONE_M_SEQUENCE("boneMotionSequence"),
    BONE_PART      ("bonePart"),
    BONE_MOTION    ("boneMotion"),
    BONE_POSITION  ("bonePosition"),
    BONE_ROT_QUAT  ("boneRotQuat"),
    BONE_ROT_EYXZ  ("boneRotEyxz"),

    MORPH_SEQUENCE ("morphSequence"),
    MORPH_PART     ("morphPart"),
    MORPH_MOTION   ("morphMotion"),

    FLAG_SEQUENCE  ("flagSequence"),
    FLAG_MOTION    ("flagMotion"),
    IK_SWITCH      ("ikSwitch"),

    CAMERA_SEQUENCE("cameraSequence"),
    CAMERA_MOTION  ("cameraMotion"),
    CAMERA_TARGET  ("cameraTarget"),
    CAMERA_ROTATION("cameraRotation"),
    CAMERA_RANGE   ("cameraRange"),
    PROJECTION     ("projection"),

    LUMI_SEQUENCE  ("luminousSequence"),
    LUMINOUS_ACT   ("luminousAct"),
    LUMI_COLOR     ("lumiColor"),
    LUMI_DIRECTION ("lumiDirection"),

    SHADOW_SEQUENCE("shadowSequence"),
    SHADOW_ACT     ("shadowAct"),

    BEZIER         ("bezier"),
    DEF_LINEAR     ("defLinear"),
    DEF_EASE_IN_OUT("defEaseInOut"),

    ;


    private static final Map<String, VmdTag> NAME_MAP =
            new HashMap<String, VmdTag>();

    static{
        for(VmdTag tag : values()){
            NAME_MAP.put(tag.tag(), tag);
        }
    }


    private final String tagName;


    /**
     * コンストラクタ。
     * @param tagName 要素名
     */
    private VmdTag(String tagName){
        this.tagName = tagName.intern();
        return;
    }


    /**
     * XML要素名から列挙子を得る。
     * @param name 要素名
     * @return 列挙子。合致する物がなければnull。
     */
    static VmdTag parse(String name){
        VmdTag result;
        result = NAME_MAP.get(name);
        return result;
    }


    /**
     * XML要素名を返す。
     * @return 要素名
     */
    String tag(){
        return this.tagName;
    }

}
