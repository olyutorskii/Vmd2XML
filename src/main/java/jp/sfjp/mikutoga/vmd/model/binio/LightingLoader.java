/*
 * lighting information builder
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.binio;

import java.util.List;
import jp.sfjp.mikutoga.bin.parser.MmdFormatException;
import jp.sfjp.mikutoga.bin.parser.ParseStage;
import jp.sfjp.mikutoga.math.MkVec3D;
import jp.sfjp.mikutoga.vmd.model.LuminousColor;
import jp.sfjp.mikutoga.vmd.model.LuminousMotion;
import jp.sfjp.mikutoga.vmd.model.ShadowMode;
import jp.sfjp.mikutoga.vmd.model.ShadowMotion;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import jp.sfjp.mikutoga.vmd.parser.VmdLightingHandler;

/**
 * ライティング情報のビルダ。
 */
class LightingLoader implements VmdLightingHandler {

    private final List<LuminousMotion> luminousMotionList;
    private final List<ShadowMotion> shadowMotionList;

    private LuminousMotion currentLuminousMotion;
    private ShadowMotion currentShadowMotion;


    /**
     * コンストラクタ。
     * @param vmdMotion モーションデータの格納先。
     */
    LightingLoader(VmdMotion vmdMotion){
        super();

        this.luminousMotionList = vmdMotion.getLuminousMotionList();
        this.shadowMotionList   = vmdMotion.getShadowMotionList();

        this.currentLuminousMotion = null;
        this.currentShadowMotion = null;

        return;
    }


    /**
     * 照明ループか否か判定する。
     * @param stage 判定対象
     * @return 照明ループならtrue
     */
    private static boolean isLuminousList(ParseStage stage){
        if(stage == VmdLightingHandler.LUMINOUS_LIST) return true;
        return false;
    }

    /**
     * シャドウループか否か判定する。
     * @param stage 判定対象
     * @return シャドウループならtrue
     */
    private static boolean isShadowList(ParseStage stage){
        if(stage == VmdLightingHandler.SHADOW_LIST) return true;
        return false;
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
        if(isLuminousList(stage)){
            this.currentLuminousMotion = new LuminousMotion();
        }else if(isShadowList(stage)){
            this.currentShadowMotion = new ShadowMotion();
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
        if(isLuminousList(stage)){
            this.luminousMotionList.add(this.currentLuminousMotion);
            this.currentLuminousMotion = new LuminousMotion();
        }else if(isShadowList(stage)){
            this.shadowMotionList.add(this.currentShadowMotion);
            this.currentShadowMotion = new ShadowMotion();
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
        if(isLuminousList(stage)){
            this.currentLuminousMotion = null;
        }else if(isShadowList(stage)){
            this.currentShadowMotion = null;
        }
        return;
    }

    /**
     * {@inheritDoc}
     * @param keyFrameNo {@inheritDoc}
     * @throws MmdFormatException {@inheritDoc}
     */
    @Override
    public void vmdLuminousMotion(int keyFrameNo) throws MmdFormatException{
        this.currentLuminousMotion.setFrameNumber(keyFrameNo);
        return;
    }

    /**
     * {@inheritDoc}
     * @param rVal {@inheritDoc}
     * @param gVal {@inheritDoc}
     * @param bVal {@inheritDoc}
     * @throws MmdFormatException {@inheritDoc}
     */
    @Override
    public void vmdLuminousColor(float rVal, float gVal, float bVal)
            throws MmdFormatException{
        LuminousColor color = this.currentLuminousMotion.getColor();
        color.setColR(rVal);
        color.setColG(gVal);
        color.setColB(bVal);
        return;
    }

    /**
     * {@inheritDoc}
     * @param xVec {@inheritDoc}
     * @param yVec {@inheritDoc}
     * @param zVec {@inheritDoc}
     * @throws MmdFormatException {@inheritDoc}
     */
    @Override
    public void vmdLuminousDirection(float xVec, float yVec, float zVec)
            throws MmdFormatException{
        MkVec3D direction = this.currentLuminousMotion.getDirection();
        direction.setXVal(xVec);
        direction.setYVal(yVec);
        direction.setZVal(zVec);
        return;
    }

    /**
     * {@inheritDoc}
     * @param keyFrameNo {@inheritDoc}
     * @throws MmdFormatException {@inheritDoc}
     */
    @Override
    public void vmdShadowMotion(int keyFrameNo) throws MmdFormatException{
        this.currentShadowMotion.setFrameNumber(keyFrameNo);
        return;
    }

    /**
     * {@inheritDoc}
     * @param shadowMode {@inheritDoc}
     * @throws MmdFormatException {@inheritDoc}
     */
    @Override
    public void vmdShadowMode(byte shadowMode) throws MmdFormatException{
        ShadowMode mode = ShadowMode.decode(shadowMode);
        this.currentShadowMotion.setShadowMode(mode);
        return;
    }

    /**
     * {@inheritDoc}
     * @param shadowScope {@inheritDoc}
     * @throws MmdFormatException {@inheritDoc}
     */
    @Override
    public void vmdShadowScopeRaw(float shadowScope)
            throws MmdFormatException{
        this.currentShadowMotion.setRawScopeParam(shadowScope);
        return;
    }

}
