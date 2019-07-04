/*
 * position interpolation curve
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 3次元位置移動のモーション補間曲線情報。
 *
 * <p>XYZ3軸それぞれに対応するベジェ曲線を3本持つ。
 */
public class PosCurve implements Iterable<BezierParam> {

    private static final int IT_SZ = 3;
    private static final String MSG_TXT =
            "X-Bezier {0}\n" + "Y-Bezier {1}\n" + "Z-Bezier {2}";


    private final BezierParam intpltXpos = new BezierParam();
    private final BezierParam intpltYpos = new BezierParam();
    private final BezierParam intpltZpos = new BezierParam();


    /**
     * コンストラクタ。
     */
    public PosCurve(){
        super();
        return;
    }


    /**
     * {@inheritDoc}
     *
     * <p>X軸、Y軸、Z軸の順で補間曲線情報を列挙する。
     *
     * @return {@inheritDoc} 3要素を返す列挙子
     */
    @Override
    public Iterator<BezierParam> iterator(){
        List<BezierParam> list;
        list = new ArrayList<BezierParam>(IT_SZ);
        list.add(this.intpltXpos);
        list.add(this.intpltYpos);
        list.add(this.intpltZpos);
        return list.iterator();
    }

    /**
     * ボーンX軸移動の補間曲線情報を返す。
     *
     * @return ボーンX軸移動の補間曲線情報
     */
    public BezierParam getIntpltXpos(){
        return this.intpltXpos;
    }

    /**
     * ボーンY軸移動の補間曲線情報を返す。
     *
     * @return ボーンY軸移動の補間曲線情報
     */
    public BezierParam getIntpltYpos(){
        return this.intpltYpos;
    }

    /**
     * ボーンZ軸移動の補間曲線情報を返す。
     *
     * @return ボーンZ軸移動の補間曲線情報
     */
    public BezierParam getIntpltZpos(){
        return this.intpltZpos;
    }

    /**
     * 3軸ともMMDデフォルトの直線補間か判定する。
     *
     * @return 3軸ともMMDデフォルトの直線補間ならtrue
     */
    public boolean isDefaultLinear(){
        if( ! this.intpltXpos.isDefaultLinear() ) return false;
        if( ! this.intpltYpos.isDefaultLinear() ) return false;
        if( ! this.intpltZpos.isDefaultLinear() ) return false;

        return true;
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
                this.intpltXpos, this.intpltYpos, this.intpltZpos );

        return msg;
    }

}
