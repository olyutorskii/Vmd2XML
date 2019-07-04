/*
 * shadow motion
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model;

import java.text.MessageFormat;
import jp.sfjp.mikutoga.vmd.AbstractNumbered;

/**
 * 影(セルフシャドウ)演出情報。
 *
 * <p>カメラからの距離情報(幾何距離の100倍？)による影演出対象の範囲指定は、
 * MMDのスライダUI上では0から9999までが指定可能。
 *
 * <p>MMDのスライダUI値SからVMDファイル上の生パラメターへの変換式は、
 * 「 0.1 - (S / 1.0E+5) 」
 * となる。
 */
public strictfp class ShadowMotion extends AbstractNumbered {

    /**
     * デフォルトの影描画モード。
     */
    public static final ShadowMode DEF_MODE = ShadowMode.MODE_1;

    /**
     * デフォルトの範囲指定生パラメータ。
     *
     * <p>MMDのスライダUI値「8875」にほぼ相当。
     */
    public static final double DEF_SCOPE = 0.01125;
    private static final int DEF_UIVAL = 8875;

    private static final double OFFSET = 0.1;
    private static final double SCALE = 1.0E+5;

    private static final String MSG_TXT =
            "#{0} shadow mode : {1} rawparam={2}";

    static{
        assert (float)(OFFSET - (DEF_UIVAL / SCALE)) == (float)DEF_SCOPE;
    }


    private ShadowMode shadowMode = DEF_MODE;
    private double rawScopeParam = DEF_SCOPE;


    /**
     * コンストラクタ。
     */
    public ShadowMotion(){
        super();
        return;
    }


    /**
     * VMDファイル上の生パラメータ数値による演出対象範囲指定を、
     * MMDのUI上の距離情報(カメラからの幾何距離×100倍？)に変換する。
     *
     * @param param 生パラメータ
     * @return MMDのスライダUI上の距離情報
     */
    public static double rawParamToScope(double param){
        double result;
        result = OFFSET - param;
        result *= SCALE;
        return result;
    }

    /**
     * MMDのUI上の距離情報(カメラからの幾何距離×100倍？)を、
     * VMDファイル上の生パラメータ数値に変換する。
     *
     * @param scope MMDのスライダUI上の距離情報
     * @return 生パラメータ
     */
    public static double scopeToRawParam(double scope){
        double result;
        result = scope / SCALE;
        result = OFFSET - result;
        return result;
    }


    /**
     * 影演出の範囲指定の生パラメータを設定する。
     *
     * @param rawScopeParam 生パラメータ
     */
    public void setRawScopeParam(double rawScopeParam) {
        this.rawScopeParam = rawScopeParam;
        return;
    }

    /**
     * 影演出の範囲指定の生パラメータを返す。
     *
     * @return 生パラメータ
     */
    public double getRawScopeParam() {
        return this.rawScopeParam;
    }

    /**
     * 影演出の範囲指定のスライダUI値を設定する。
     *
     * @param scope スライダUI値
     */
    public void setScope(double scope){
        float rawVal = (float) scopeToRawParam(scope);
        setRawScopeParam(rawVal);
        return;
    }

    /**
     * 影演出の範囲指定のスライダUI値を返す。
     *
     * @return スライダUI値
     */
    public double getScope(){
        double rawVal = getRawScopeParam();
        double scope = rawParamToScope(rawVal);
        return scope;
    }

    /**
     * 影描画モードを設定する。
     *
     * @param shadowMode 影描画モード
     * @throws NullPointerException 引数がnull
     */
    public void setShadowMode(ShadowMode shadowMode)
            throws NullPointerException{
        if(shadowMode == null) throw new NullPointerException();
        this.shadowMode = shadowMode;
        return;
    }

    /**
     * 影描画モードを返す。
     *
     * @return 影描画モード
     */
    public ShadowMode getShadowMode(){
        return this.shadowMode;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String toString(){
        String msg;
        msg = MessageFormat.format(MSG_TXT,
                getFrameNumber(),
                this.shadowMode,
                Double.toString(this.rawScopeParam) );
        return msg;
    }

}
