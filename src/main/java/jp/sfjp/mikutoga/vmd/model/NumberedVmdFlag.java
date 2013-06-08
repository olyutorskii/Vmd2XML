/*
 * model presence switch
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jp.sfjp.mikutoga.vmd.AbstractNumbered;

/**
 * フレーム番号が付けられた各種モーションフラグの管理を行う。
 */
public class NumberedVmdFlag
        extends AbstractNumbered
        implements Iterable<IkSwitch> {

    private static final String MSG_TXT =
              "#{0} model precense : {1}";


    private boolean shown = true;
    private List<IkSwitch> ikSwList = new LinkedList<IkSwitch>();


    /**
     * コンストラクタ。
     * <p>モデル表示ありの状態で初期化される。
     */
    public NumberedVmdFlag(){
        super();
        return;
    }


    /**
     * モデルを表示するか否か返す。
     * @return 表示するならtrue
     */
    public boolean isModelShown(){
        return this.shown;
    }

    /**
     * モデルを表示するか否か設定する。
     * @param shownArg 表示するならtrue
     */
    public void setModelShown(boolean shownArg){
        this.shown = shownArg;
        return;
    }

    /**
     * 個別IKボーンフラグのリストを返す。
     * @return 個別IKボーンフラグのリスト
     */
    public List<IkSwitch> getIkSwitchList(){
        return this.ikSwList;
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public Iterator<IkSwitch> iterator(){
        return this.ikSwList.iterator();
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public String toString(){
        String msg;
        msg = MessageFormat.format(MSG_TXT, getFrameNumber(), this.shown);

        StringBuilder submsg = new StringBuilder(msg);
        for(IkSwitch sw : this.ikSwList){
            submsg.append('\n').append("\u0020").append(sw.toString());
        }

        return submsg.toString();
    }

}
