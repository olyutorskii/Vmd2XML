/*
 * IK ON/OFF switch
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model;

import java.text.MessageFormat;

/**
 * IK ON/OFF の管理を行う。
 */
public class IkSwitch {

    private static final String MSG_TXT =
              "IKbone {0} : {1}";


    private String boneName = "";
    private boolean valid = true;


    /**
     * コンストラクタ。
     */
    public IkSwitch(){
        super();
        return;
    }


    /**
     * ボーン名を返す。
     * @return ボーン名
     */
    public String getBoneName(){
        return this.boneName;
    }

    /**
     * ボーン名を設定する。
     * @param boneNameArg ボーン名
     * @throws NullPointerException 引数がnull
     */
    public void setBoneName(String boneNameArg) throws NullPointerException{
        if(boneNameArg == null) throw new NullPointerException();
        this.boneName = boneNameArg;
        return;
    }

    /**
     * IK処理が有効か否か返す。
     * @return 有効ならtrue
     */
    public boolean isValid(){
        return this.valid;
    }

    /**
     * IK処理が有効か否か設定する。
     * @param validArg 有効ならtrue
     */
    public void setValid(boolean validArg){
        this.valid = validArg;
        return;
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String toString(){
        String msg;
        msg = MessageFormat.format(MSG_TXT, this.boneName, this.valid);
        return msg;
    }

}
