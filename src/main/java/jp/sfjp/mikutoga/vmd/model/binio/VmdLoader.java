/*
 * VMD file loader
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.binio;

import java.io.IOException;
import java.io.InputStream;
import jp.sfjp.mikutoga.bin.parser.MmdFormatException;
import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import jp.sfjp.mikutoga.vmd.parser.VmdParser;

/**
 * VMDモーションファイルを読み込むためのバイナリローダ。
 */
public class VmdLoader {

    private static final String ERR_TRYLOAD = "try loading first.";
    private static final String ERR_LOADED  = "has been loaded.";


    private boolean loaded = false;
    private boolean hasMoreData = true;

    private boolean ignoreName = true;
    private boolean redundantCheck = false;


    /**
     * コンストラクタ。
     */
    public VmdLoader(){
        super();
        return;
    }


    /**
     * 正常パース時に読み残したデータがあったか判定する。
     * @return 読み残したデータがあればtrue
     * @throws IllegalStateException まだパースを試みていない。
     */
    public boolean hasMoreData() throws IllegalStateException{
        if( ! this.loaded ) throw new IllegalStateException(ERR_TRYLOAD);
        return this.hasMoreData;
    }

    /**
     * カメラ・ライティングデータのパースを試みるか否かの判断で、
     * 特殊モデル名判定を無視するか否か設定する。
     * デフォルトではモデル名を無視。
     * <p>※MMDVer7.30前後のVMD出力不具合を回避したい場合は、
     * オフにするとパースに成功する場合がある。
     * @param mode モデル名を無視してパースを強行するならtrue
     */
    public void setIgnoreName(boolean mode){
        this.ignoreName = mode;
        return;
    }

    /**
     * ボーンモーション補間情報冗長部のチェックを行うか否か設定する。
     * デフォルトではチェックを行わない。
     * <p>※MMDVer7.30前後のVMD出力不具合を回避したい場合は、
     * オフにするとパースに成功する場合がある。
     * @param mode チェックさせたければtrue
     */
    public void setRedundantCheck(boolean mode){
        this.redundantCheck = mode;
        return;
    }

    /**
     * VMDファイルの読み込みを行いモーション情報を返す。
     * 1インスタンスにつき一度しかロードできない。
     * @param source VMDファイル入力ソース
     * @return モーション情報
     * @throws IOException 入力エラー
     * @throws MmdFormatException VMDファイルフォーマットの異常を検出
     * @throws IllegalStateException このインスタンスで再度のロードを試みた。
     */
    public VmdMotion load(InputStream source)
            throws IOException,
                   MmdFormatException,
                   IllegalStateException {
        if(this.loaded) throw new IllegalStateException(ERR_LOADED);

        VmdMotion motion = new VmdMotion();

        VmdParser parser = new VmdParser(source);

        parser.setIgnoreName(this.ignoreName);
        parser.setRedundantCheck(this.redundantCheck);

        BasicLoader basicBuilder       = new BasicLoader(motion);
        CameraLoader cameraBuilder     = new CameraLoader(motion);
        LightingLoader lightingBuilder = new LightingLoader(motion);

        parser.setBasicHandler(basicBuilder);
        parser.setCameraHandler(cameraBuilder);
        parser.setLightingHandler(lightingBuilder);

        try{
            parser.parseVmd();
            this.hasMoreData = basicBuilder.hasMoreData();
        }finally{
            this.loaded = true;
        }

        return motion;
    }

}
