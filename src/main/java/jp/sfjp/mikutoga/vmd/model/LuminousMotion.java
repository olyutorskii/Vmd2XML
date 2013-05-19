/*
 * luminous motion
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model;

import java.text.MessageFormat;
import jp.sfjp.mikutoga.math.MkVec3D;
import jp.sfjp.mikutoga.vmd.AbstractNumbered;

/**
 * 光源演出情報。
 * <p>照明方向は、
 * ワールド座標原点から伸びる方向ベクトルとして記述される。
 * この方向ベクトルに向けて、無限遠の光源から照明が当たる。
 * <p>MMDのスライダUI上では
 * 方向ベクトル各軸成分の定義域は-1.0以上+1.0以下だが、
 * さらに絶対値の大きな値を指定することも可能。
 * <p>方向ベクトルの長さは演出上の意味を持たないが、
 * キーフレーム間の照明方向の補間に影響を及ぼすかもしれない。
 * <p>方向ベクトルが零ベクトル(0,0,0)の場合、MMDでは全ポリゴンに影が落ちる。
 */
public class LuminousMotion extends AbstractNumbered {

    /** デフォルトのX成分。 */
    public static final double DEF_VECX = -0.5;
    /** デフォルトのY成分。 */
    public static final double DEF_VECY = -1.0;
    /** デフォルトのZ成分。 */
    public static final double DEF_VECZ = +0.5;

    private static final String MSG_TXT =
            "#{0} luminous color : {1} direction : {2}";


    private final LuminousColor color = new LuminousColor();
    private final MkVec3D direction = new MkVec3D();


    /**
     * コンストラクタ。
     */
    public LuminousMotion(){
        super();
        this.direction.setVector(DEF_VECX, DEF_VECY, DEF_VECZ);
        return;
    }


    /**
     * 光源の色情報を返す。
     * @return 光源の色情報
     */
    public LuminousColor getColor(){
        return this.color;
    }

    /**
     * 光源からの照射方向情報を返す。
     * @return 光源からの照射方向情報
     */
    public MkVec3D getDirection(){
        return this.direction;
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String toString(){
        String msg;
        msg = MessageFormat.format(MSG_TXT,
                getFrameNumber(), this.color, this.direction );
        return msg;
    }

}
