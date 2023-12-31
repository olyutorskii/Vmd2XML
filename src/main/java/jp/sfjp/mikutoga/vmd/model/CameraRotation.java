/*
 * camera rotation
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model;

import java.text.MessageFormat;

/**
 * 左手系空間でターゲットの周りを回るカメラの回転情報。
 * いずれもカメラ姿勢ではなくカメラ運動量を示すため、
 * 回転量0と2Πの区別には意味がある。
 *
 * <p>latitudeはターゲットから見たカメラの仰俯角(≒緯度)。
 * 単位はラジアン。
 * Y軸回転量が0の時のZ正軸がY正軸へ倒れる方向が正回転。
 * (MMDのUIとは符号が逆になるので注意)
 * 仰俯角が0の場合、
 * カメラはターゲットに対しXZ平面(水平)と平行な箇所に位置する。
 *
 * <p>longitudeはY軸周りの回転量(≒経度)。
 * 単位はラジアン。
 * X正軸がZ正軸へ倒れる方向が正回転。(ボーン回転と逆)
 * 仰俯角およびY軸回転量が0の場合、
 * カメラレンズはZ軸-∞方向からZ軸+∞方向を向く。
 *
 * <p>rollはレンズをターゲットを向けたカメラのロール回転量。
 * 単位はラジアン。
 * 仰俯角とY軸回転量が0の時にY正軸がX正軸に倒れる方向が正回転。
 * 仰俯角およびロール回転量が0の場合、カメラ上部はY軸+∞の方を向く。
 */
public class CameraRotation {

    private static final String MSG_TXT =
            "latitude={0} longitude={1} roll={2}";


    private double latitude;
    private double longitude;
    private double roll;


    /**
     * コンストラクタ。
     */
    public CameraRotation(){
        super();
        return;
    }


    /**
     * ターゲットから見たカメラの仰俯角(≒緯度)を返す。
     *
     * @return ターゲットから見たカメラの仰俯角(≒緯度)
     */
    public double getLatitude(){
        return this.latitude;
    }

    /**
     * ターゲットから見たカメラの仰俯角(≒緯度)を設定する。
     *
     * @param latitude ターゲットから見たカメラの仰俯角(≒緯度)
     */
    public void setLatitude(double latitude){
        this.latitude = latitude;
        return;
    }

    /**
     * Y軸周りの回転量(≒経度)を返す。
     *
     * @return Y軸周りの回転量(≒経度)
     */
    public double getLongitude(){
        return this.longitude;
    }

    /**
     * Y軸周りの回転量(≒経度)を設定する。
     *
     * @param longitude Y軸周りの回転量(≒経度)
     */
    public void setLongitude(double longitude){
        this.longitude = longitude;
        return;
    }

    /**
     * レンズをターゲットを向けたカメラのロール回転量を返す。
     *
     * @return レンズをターゲットを向けたカメラのロール回転量
     */
    public double getRoll(){
        return this.roll;
    }

    /**
     * レンズをターゲットを向けたカメラのロール回転量を設定する。
     *
     * @param roll レンズをターゲットを向けたカメラのロール回転量
     */
    public void setRoll(double roll){
        this.roll = roll;
        return;
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
                this.latitude, this.longitude, this.roll );
        return msg;
    }

}
