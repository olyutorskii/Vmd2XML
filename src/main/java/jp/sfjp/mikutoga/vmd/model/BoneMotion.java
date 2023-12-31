/*
 * bone motion
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model;

import java.text.MessageFormat;
import jp.sfjp.mikutoga.math.MkPos3D;
import jp.sfjp.mikutoga.math.MkQuat;
import jp.sfjp.mikutoga.vmd.AbstractNumbered;

/**
 * ボーンのモーション情報。
 *
 * <p>ボーン名、ボーン位置、ボーン回転量、及び補間カーブ情報を持つ。
 */
public class BoneMotion extends AbstractNumbered {

    private static final String MSG_TXT =
              "bone name : [{0}] #{1}\n"
            + "rotation {2} R-Bezier {3}\n"
            + "position {4}\n"
            + "{5}";


    private String boneName;

    private final MkQuat rotation = new MkQuat();
    private final BezierParam intpltRotation = new BezierParam();

    private final MkPos3D position = new MkPos3D();
    private final PosCurve posCurve = new PosCurve();


    /**
     * コンストラクタ。
     */
    public BoneMotion(){
        super();
        return;
    }


    /**
     * ボーン名を返す。
     *
     * @return ボーン名
     */
    public String getBoneName(){
        return this.boneName;
    }

    /**
     * ボーン名を設定する。
     *
     * @param boneName ボーン名
     */
    public void setBoneName(String boneName){
        this.boneName = boneName;
        return;
    }

    /**
     * ボーン回転量を返す。
     *
     * @return ボーン回転量
     */
    public MkQuat getRotation(){
        return this.rotation;
    }

    /**
     * ボーン回転の補間曲線情報を返す。
     *
     * @return ボーン回転の補間曲線情報
     */
    public BezierParam getIntpltRotation(){
        return this.intpltRotation;
    }

    /**
     * ボーン位置を返す。
     *
     * @return ボーン位置
     */
    public MkPos3D getPosition(){
        return this.position;
    }

    /**
     * ボーン位置移動の補間情報を返す。
     *
     * @return ボーン位置移動の補間情報
     */
    public PosCurve getPosCurve(){
        return this.posCurve;
    }

    /**
     * このモーションが暗黙の位置情報を持つか判定する。
     *
     * <p>ボーン位置が原点(0,0,0)にあり、
     * XYZ3軸の移動補間カーブがデフォルト直線補間の場合、
     * 暗黙の位置情報と見なされる。
     *
     * <p>MMDは、位置情報を持たないボーンのモーションに対し
     * 便宜的にこの暗黙の位置情報を割り当てる。
     *
     * <p>通常の位置モーションが暗黙の位置情報と一致する場合もありうる。
     *
     * @return 暗黙の位置情報であるならtrue
     */
    public boolean hasImplicitPosition(){
        if(this.position.isOriginPoint() && this.posCurve.isDefaultLinear()){
            return true;
        }
        return false;
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
                this.boneName,
                getFrameNumber(),
                this.rotation, this.intpltRotation,
                this.position, this.posCurve );
        return msg;
    }

}
