/*
 * command argument information
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sourceforge.mikutoga.vmd2xml;

import java.util.Arrays;
import java.util.Iterator;

/**
 * コマンドライン引数情報。
 */
final class ArgInfo {

    /** ヘルプ文字列。 */
    public static final String CMD_HELP =
          "-h       : put help massage\n\n"
        + "-vmd2xml : convert *.vmd to *.xml\n"
        + "-xml2vmd : convert *.xml to *.vmd\n\n"
        + "-i file  : specify input file\n"
        + "-o file  : specify output file\n"
        + "-f       : force overwriting\n\n"
        + "-quat    : Quaternion output mode [default]\n"
        + "-eyxz    : YXZ-Euler output mode\n";

    private static final String ERRMSG_UNKNOWN = "Unknown option : ";
    private static final String ERRMSG_INCOMP_I = "You need -i argument.";
    private static final String ERRMSG_INCOMP_O = "You need -o argument.";
    private static final String ERRMSG_EXCLUSIVE =
            "You must specify -vmd2xml or -xml2vmd.";
    private static final String ERRMSG_NOINPUT =
            "You must specify input file with -i.";
    private static final String ERRMSG_NOOUTPUT =
            "You must specify output file with -o.";


    private String inputFile = null;
    private String outputFile = null;
    private boolean vmd2xml = false;
    private boolean xml2vmd = false;
    private boolean force = false;
    private boolean helpMode = false;
    private boolean quaternionMode = true;

    private boolean hasOptionError = false;
    private String errorMessage = null;


    /**
     * コンストラクタ。
     */
    private ArgInfo(){
        super();
        return;
    }


    /**
     * コマンドライン引数を解析する。
     * @param args コマンドライン引数
     * @return 解析結果
     */
    public static ArgInfo buildArgInfo(String[] args){
        return buildArgInfo(Arrays.asList(args));
    }

    /**
     * コマンドライン引数を解析する。
     * @param args コマンドライン引数
     * @return 解析結果
     */
    public static ArgInfo buildArgInfo(Iterable<String> args){
        return buildArgInfo(args.iterator());
    }

    /**
     * コマンドライン引数を解析する。
     * @param argIter コマンドライン引数
     * @return 解析結果
     */
    public static ArgInfo buildArgInfo(Iterator<String> argIter){
        ArgInfo result = new ArgInfo();

        while(argIter.hasNext()){
            String arg = argIter.next();
            OptSwitch option = OptSwitch.parse(arg);
            if(option == null){
                result.setOptionError(ERRMSG_UNKNOWN + arg);
                break;
            }

            result.parseOption(option, argIter);

            if(result.hasOptionError()) break;
            if(result.isHelpMode()) break;
        }

        result.checkOptionError();

        return result;
    }

    /**
     * 単一もしくは引数を持つオプション種別を解析する。
     * @param argIter コマンドライン引数
     * @param option オプション種別
     */
    private void parseOption(OptSwitch option, Iterator<String> argIter){
        switch(option){
        case HELP:
            this.helpMode = true;
            break;
        case VMD2XML:
            this.vmd2xml = true;
            this.xml2vmd = false;
            break;
        case XML2VMD:
            this.vmd2xml = false;
            this.xml2vmd = true;
            break;
        case FORCE:
            this.force = true;
            break;
        case INPUTFILE:
            if( ! argIter.hasNext() ){
                this.setOptionError(ERRMSG_INCOMP_I);
                break;
            }
            this.inputFile = argIter.next();
            break;
        case OUTPUTFILE:
            if( ! argIter.hasNext() ){
                this.setOptionError(ERRMSG_INCOMP_O);
                break;
            }
            this.outputFile = argIter.next();
            break;
        case QUATERNION:
            this.quaternionMode = true;
            break;
        case EULERYXZ:
            this.quaternionMode = false;
            break;
        default:
            assert false;
            throw new AssertionError();
        }

        return;
    }

    /**
     * オプション指定の整合性をチェックする。
     * <p>必要に応じてエラーの有無とメッセージが登録される。
     */
    private void checkOptionError(){
        if( hasOptionError() || isHelpMode() ) return;

        if( ( ! isVmd2XmlMode() ) && ( ! isXml2VmdMode() ) ){
            setOptionError(ERRMSG_EXCLUSIVE);
        }else if(getInputFile() == null){
            setOptionError(ERRMSG_NOINPUT);
        }else if(getOutputFile() == null){
            setOptionError(ERRMSG_NOOUTPUT);
        }

        return;
    }

    /**
     * オプションエラーを設定する。
     * @param text メッセージ
     */
    private void setOptionError(CharSequence text){
        if(text == null){
            this.errorMessage = null;
            this.hasOptionError = false;
        }else{
            this.errorMessage = text.toString();
            this.hasOptionError = true;
        }

        return;
    }

    /**
     * 入力ファイル名を返す。
     * @return 入力ファイル名
     */
    public String getInputFile(){
        return this.inputFile;
    }

    /**
     * 出力ファイル名を返す。
     * @return 出力ファイル名
     */
    public String getOutputFile(){
        return this.outputFile;
    }

    /**
     * 強制上書きモードか判定を返す。
     * @return 強制上書きモードならtrue
     */
    public boolean isForceMode(){
        return this.force;
    }

    /**
     * VMD-XML変換モードか判定を返す。
     * @return VMD-XML変換モードならtrue
     */
    public boolean isVmd2XmlMode(){
        return this.vmd2xml;
    }

    /**
     * XML-VMD変換モードか判定を返す。
     * @return XML-VMD変換モードならtrue
     */
    public boolean isXml2VmdMode(){
        return this.xml2vmd;
    }

    /**
     * ヘルプモードか否か判定を返す。
     * @return ヘルプモードならtrue
     */
    public boolean isHelpMode(){
        return this.helpMode;
    }

    /**
     * クォータニオン出力モードか否か判定を返す。
     * @return クォータニオン出力モードならtrue
     */
    public boolean isQuaternionMode(){
        return this.quaternionMode;
    }

    /**
     * コマンドライン解析中にエラーが検出されたか判定する。
     * @return エラーが検出されればtrue
     */
    public boolean hasOptionError(){
        return this.hasOptionError;
    }

    /**
     * コマンドライン解析中のエラーメッセージを返す。
     * @return エラーメッセージ。エラーが無ければnull
     */
    public String getErrorMessage(){
        return this.errorMessage;
    }

    /**
     * オプション列挙子。
     */
    private static enum OptSwitch implements Iterable<String> {

        HELP       ("-h", "-help", "-?"),
        VMD2XML    ("-vmd2xml"),
        XML2VMD    ("-xml2vmd"),
        FORCE      ("-f"),
        INPUTFILE  ("-i"),
        OUTPUTFILE ("-o"),
        QUATERNION ("-quat"),
        EULERYXZ   ("-eyxz"),
        ;

        private final Iterable<String> optLines;

        /**
         * コンストラクタ。
         * @param optLines オプション文字列候補群。
         */
        private OptSwitch(String... optLines){
            this.optLines = Arrays.asList(optLines);
            return;
        }

        /**
         * 文字列候補群を返す。
         * @return 文字列候補群
         */
        @Override
        public Iterator<String> iterator(){
            return this.optLines.iterator();
        }

        /**
         * 文字列に合致する列挙子を返す。
         * @param arg 文字列
         * @return 列挙子。見つからなければnull
         */
        private static OptSwitch parse(String arg){
            for(OptSwitch opt : values()){
                for(String optLine : opt){
                    if(optLine.equals(arg)) return opt;
                }
            }
            return null;
        }

    }

}
