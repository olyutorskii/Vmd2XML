/*
 * motion & stage act
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import jp.sfjp.mikutoga.vmd.FrameNumbered;
import jp.sfjp.mikutoga.vmd.VmdUniq;

/**
 * モーション及び演出情報。
 */
public class VmdMotion {

    private static final String MSG_TXT =
          "model name : {0}\n"
        + "bone#{1} morph#{2} camera#{3} luminous#{4} shadow#{5} flag#{6}";


    private String modelName = VmdUniq.MODELNAME_STAGEACT;

    private final Map<String, List<BoneMotion>>  bonePartMap;
    private final Map<String, List<MorphMotion>> morphPartMap;

    private final List<CameraMotion>    cameraMotionList;
    private final List<LuminousMotion>  luminousMotionList;
    private final List<ShadowMotion>    shadowMotionList;
    private final List<NumberedVmdFlag> flagList;


    /**
     * コンストラクタ。
     */
    public VmdMotion(){
        super();

        this.bonePartMap  = new LinkedHashMap<>();
        this.morphPartMap = new LinkedHashMap<>();

        this.cameraMotionList   = new LinkedList<>();
        this.luminousMotionList = new LinkedList<>();
        this.shadowMotionList   = new LinkedList<>();
        this.flagList           = new LinkedList<>();

        return;
    }


    /**
     * モデル名を返す。
     *
     * @return モデル名
     */
    public String getModelName(){
        return this.modelName;
    }

    /**
     * モデル名を設定する。
     *
     * <p>このモーションがモデルモーションかステージ演出情報かは、
     * このモデル名で判別される。
     *
     * @param modelName モデル名
     * @throws NullPointerException 引数がnull
     * @see jp.sfjp.mikutoga.vmd.VmdUniq#MODELNAME_STAGEACT
     */
    public void setModelName(String modelName) throws NullPointerException{
        if(modelName == null) throw new NullPointerException();
        this.modelName = modelName;
        return;
    }

    /**
     * モデルモーションか否か判別する。
     *
     * <p>判別は特殊なモデル名を持つか否かで決定される。
     * @return モデルモーションならtrue
     *
     * @see jp.sfjp.mikutoga.vmd.VmdUniq#MODELNAME_STAGEACT
     */
    public boolean isModelMotion(){
        if(VmdUniq.isStageActName(this.modelName)){
            return false;
        }

        return true;
    }

    /**
     * フラグモーションが存在するか否か判定する。
     *
     * <p>フラグモーションは、
     * MMD Ver7.40以降のVMDフォーマットでなければ記録できない。
     *
     * @return 存在するならtrue
     */
    public boolean hasFlagMotion(){
        if(this.flagList.isEmpty()) return false;
        return true;
    }

    /**
     * 順序保証されたボーンモーションマップを返す。
     *
     * @return ボーンモーションマップ
     * @see java.util.LinkedHashMap
     */
    public Map<String, List<BoneMotion>> getBonePartMap(){
        return this.bonePartMap;
    }

    /**
     * 順序保証されたモーフモーションマップを返す。
     *
     * @return モーフモーションマップ
     * @see java.util.LinkedHashMap
     */
    public Map<String, List<MorphMotion>> getMorphPartMap(){
        return this.morphPartMap;
    }

    /**
     * カメラモーションのリストを返す。
     *
     * @return カメラモーションのリスト
     */
    public List<CameraMotion> getCameraMotionList(){
        return this.cameraMotionList;
    }

    /**
     * 照明モーションのリストを返す。
     *
     * @return 照明モーションのリスト
     */
    public List<LuminousMotion> getLuminousMotionList(){
        return this.luminousMotionList;
    }

    /**
     * シャドウモーションのリストを返す。
     *
     * @return シャドウモーションのリスト
     */
    public List<ShadowMotion> getShadowMotionList(){
        return this.shadowMotionList;
    }

    /**
     * 各種フレーム番号付きフラグのリストを返す。
     *
     * @return フレーム番号付きフラグのリスト
     */
    public List<NumberedVmdFlag> getNumberedFlagList(){
        return this.flagList;
    }

    /**
     * ボーンモーションを追加する。
     * 追加順は保持される。
     *
     * @param motion ボーンモーション
     * @see java.util.LinkedHashMap
     */
    public void addBoneMotion(BoneMotion motion){
        String name = motion.getBoneName();

        List<BoneMotion> list = this.bonePartMap.get(name);
        if(list == null){
            list = new LinkedList<>();
            this.bonePartMap.put(name, list);
        }

        list.add(motion);

        return;
    }

    /**
     * モーフモーションを追加する。
     * 追加順は保持される。
     *
     * @param motion モーフモーション
     * @see java.util.LinkedHashMap
     */
    public void addMorphMotion(MorphMotion motion){
        String name = motion.getMorphName();

        List<MorphMotion> list = this.morphPartMap.get(name);
        if(list == null){
            list = new LinkedList<>();
            this.morphPartMap.put(name, list);
        }

        list.add(motion);

        return;
    }

    /**
     * 各データをフレーム番号順に昇順ソートする。
     */
    public void frameSort(){
        for(List<BoneMotion> list : this.bonePartMap.values()){
            Collections.sort(list, FrameNumbered.COMPARATOR);
        }

        for(List<MorphMotion> list : this.morphPartMap.values()){
            Collections.sort(list, FrameNumbered.COMPARATOR);
        }

        Collections.sort(this.cameraMotionList,   FrameNumbered.COMPARATOR);
        Collections.sort(this.luminousMotionList, FrameNumbered.COMPARATOR);
        Collections.sort(this.shadowMotionList,   FrameNumbered.COMPARATOR);
        Collections.sort(this.flagList,           FrameNumbered.COMPARATOR);

        return;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String toString(){
        int boneNo = 0;
        for(List<BoneMotion> motionList : this.bonePartMap.values()){
            boneNo += motionList.size();
        }

        int morphNo = 0;
        for(List<MorphMotion> motionList : this.morphPartMap.values()){
            morphNo += motionList.size();
        }

        int cameraNo   = this.cameraMotionList  .size();
        int luminousNo = this.luminousMotionList.size();
        int shadowNo   = this.shadowMotionList  .size();
        int flagNo     = this.flagList          .size();

        String msg;
        msg = MessageFormat.format(MSG_TXT,
                this.modelName,
                boneNo, morphNo, cameraNo, luminousNo, shadowNo, flagNo );

        return msg;
    }

}
