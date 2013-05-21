/*
 * command line exception
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd2xml;

/**
 * コマンドライン引数の不備による異常系。
 */
@SuppressWarnings("serial")
class CmdLineException extends Exception{

    /**
     * コンストラクタ。
     * @param message {@inheritDoc}
     */
    CmdLineException(String message) {
        super(message);
        return;
    }

}
