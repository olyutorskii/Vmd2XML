/*
 * morph motion
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model;

import java.text.MessageFormat;
import jp.sfjp.mikutoga.vmd.AbstractNumbered;

/**
 * モーフ情報。
 * <p>通常、モーフ量は0.0以上1.0以下の値をとる。
 */
public class MorphMotion extends AbstractNumbered {

    private static final String MSG_TXT =
            "morph name : [{0}] #{1} flex = {2}";


    private String morphName = "";
    private float flex = 0.0f;


    /**
     * コンストラクタ。
     */
    public MorphMotion(){
        super();
        return;
    }


    /**
     * モーフ名を設定する。
     * @param morphName モーフ名
     */
    public void setMorphName(String morphName) {
        this.morphName = morphName;
        return;
    }

    /**
     * モーフ名を返す。
     * @return モーフ名
     */
    public String getMorphName() {
        return this.morphName;
    }

    /**
     * モーフ変量を設定する。
     * @param flex 変量
     */
    public void setFlex(float flex) {
        this.flex = flex;
        return;
    }

    /**
     * モーフ変量を返す。
     * @return 変量
     */
    public float getFlex() {
        return this.flex;
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String toString(){
        String msg;
        msg = MessageFormat.format(MSG_TXT,
                this.morphName, getFrameNumber(), this.flex );
        return msg;
    }

}
