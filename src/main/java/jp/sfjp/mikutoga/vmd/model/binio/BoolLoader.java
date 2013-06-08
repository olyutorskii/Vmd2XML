/*
 * boolean information builder
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.binio;

import java.util.List;
import jp.sfjp.mikutoga.bin.parser.MmdFormatException;
import jp.sfjp.mikutoga.bin.parser.ParseStage;
import jp.sfjp.mikutoga.vmd.model.IkSwitch;
import jp.sfjp.mikutoga.vmd.model.NumberedVmdFlag;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import jp.sfjp.mikutoga.vmd.parser.VmdBoolHandler;

/**
 * フラグ情報のビルダ。
 * <p>MikuMikuDance Ver7.40以降でサポート
 */
public class BoolLoader implements VmdBoolHandler{

    private final List<NumberedVmdFlag> flagList;

    private NumberedVmdFlag currentFlag;
    private IkSwitch currentSwitch;

    /**
     * コンストラクタ。
     * @param vmdMotion モーションデータの格納先。
     */
    BoolLoader(VmdMotion vmdMotion){
        super();
        this.flagList = vmdMotion.getNumberedFlagList();
        return;
    }


    /**
     * {@inheritDoc}
     * @param stage {@inheritDoc}
     * @param loops {@inheritDoc}
     * @throws MmdFormatException {@inheritDoc}
     */
    @Override
    public void loopStart(ParseStage stage, int loops)
            throws MmdFormatException{
        if(stage == VmdBoolHandler.MODELSIGHT_LIST){
            this.currentFlag = new NumberedVmdFlag();
        }else if(stage == VmdBoolHandler.IKSW_LIST){
            this.currentSwitch = new IkSwitch();
        }

        return;
    }

    /**
     * {@inheritDoc}
     * @param stage {@inheritDoc}
     * @throws MmdFormatException {@inheritDoc}
     */
    @Override
    public void loopNext(ParseStage stage)
            throws MmdFormatException{
        if(stage == VmdBoolHandler.MODELSIGHT_LIST){
            this.flagList.add(this.currentFlag);
            this.currentFlag = new NumberedVmdFlag();
        }else if(stage == VmdBoolHandler.IKSW_LIST){
            List<IkSwitch> swList = this.currentFlag.getIkSwitchList();
            swList.add(this.currentSwitch);
            this.currentSwitch = new IkSwitch();
        }

        return;
    }

    /**
     * {@inheritDoc}
     * @param stage {@inheritDoc}
     * @throws MmdFormatException {@inheritDoc}
     */
    @Override
    public void loopEnd(ParseStage stage)
            throws MmdFormatException{
        if(stage == VmdBoolHandler.MODELSIGHT_LIST){
            this.currentFlag = null;
        }else if(stage == VmdBoolHandler.IKSW_LIST){
            this.currentSwitch = null;
        }

        return;
    }

    /**
     * {@inheritDoc}
     * @param show {@inheritDoc}
     * @param keyFrameNo {@inheritDoc}
     * @throws MmdFormatException {@inheritDoc}
     */
    @Override
    public void vmdModelSight(boolean show, int keyFrameNo)
            throws MmdFormatException {
        this.currentFlag.setModelShown(show);
        this.currentFlag.setFrameNumber(keyFrameNo);
        return;
    }

    /**
     * {@inheritDoc}
     * @param boneName {@inheritDoc}
     * @param validIk {@inheritDoc}
     * @param keyFrameNo {@inheritDoc}
     * @throws MmdFormatException {@inheritDoc}
     */
    @Override
    public void vmdIkSwitch(String boneName,
                              boolean validIk,
                              int keyFrameNo )
            throws MmdFormatException {
        this.currentSwitch.setBoneName(boneName);
        this.currentSwitch.setValid(validIk);
        return;
    }

}
