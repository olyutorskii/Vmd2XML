/*
 * command line argument info
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd2xml;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

/**
 * コマンドラインオプション情報。
 */
final class OptInfo {

    private static final String EOL_LF = "\n";
    private static final String EOL_CRLF = "\r\n";
    private static final String EOL_DEFAULT = EOL_LF;

    private static final String FORMAT_VMD       = "vmd";
    private static final String FORMAT_XML       = "xml";
    private static final String FORMAT_XML110820 = "xml110820";

    private static final String SFX_VMD = ".vmd";
    private static final String SFX_XML = ".xml";

    private static final String NL_LF   =   "lf";
    private static final String NL_CRLF = "crlf";

    private static final String ERRMSG_UNKNOWN =
            "Unknown option : {0}";
    private static final String ERRMSG_MOREARG =
            "You need option arg with : {0}";
    private static final String ERRMSG_INTYPE =
            "You must specify input format with -iform.";
    private static final String ERRMSG_OUTTYPE =
            "You must specify output format with -oform.";
    private static final String ERRMSG_NOINFILE =
            "You must specify input file with -i.";
    private static final String ERRMSG_NOOUTFILE =
            "You must specify output file with -o.";
    private static final String ERRMSG_INVFORM =
            "Unknown format : \"{0}\" must be \"vmd\" or \"xml\" "
            + "or \"xml110820\"";
    private static final String ERRMSG_INVNL =
            "Unknown newline : \"{0}\" must be \"lf\" or \"crlf\"";
    private static final String ERRMSG_INVBOOL =
            "Unknown switch : \"{0}\" must be \"on\" or \"off\"";


    private boolean needHelp = false;
    private MotionFileType inTypes  = MotionFileType.NONE;
    private MotionFileType outTypes = MotionFileType.NONE;
    private String inFilename = null;
    private String outFilename = null;
    private boolean overwrite = false;
    private String newline = EOL_DEFAULT;
    private String generator = Vmd2Xml.GENERATOR;
    private boolean isQuaternionMode = true;


    /**
     * コンストラクタ。
     */
    private OptInfo(){
        super();
        return;
    }


    /**
     * フォーマット種別指定子をデコードする。
     * @param arg 文字列
     * @return デコード結果。
     * @throws CmdLineException 不正なフォーマット種別
     */
    private static MotionFileType decodeFormatType(String arg)
            throws CmdLineException{
        MotionFileType result;

        if      (FORMAT_VMD.equals(arg)){
            result = MotionFileType.VMD;
        }else if(FORMAT_XML.equals(arg)){
            result = MotionFileType.XML_110820;
        }else if(FORMAT_XML110820.equals(arg)){
            result = MotionFileType.XML_110820;
        }else{
            String errMsg = MessageFormat.format(ERRMSG_INVFORM, arg);
            throw new CmdLineException(errMsg);
        }

        return result;
    }

    /**
     * 改行文字指定子をデコードする。
     * @param arg 文字列
     * @return デコード結果。
     * @throws CmdLineException 不正なフォーマット種別
     */
    private static String decodeNewline(String arg)
            throws CmdLineException{
        String result;

        if      (NL_LF.equals(arg)){
            result = EOL_LF;
        }else if(NL_CRLF.equals(arg)){
            result = EOL_CRLF;
        }else{
            String errMsg = MessageFormat.format(ERRMSG_INVNL, arg);
            throw new CmdLineException(errMsg);
        }

        return result;
    }

    /**
     * ブール指定子をデコードする。
     * @param arg 文字列
     * @return デコード結果。
     * @throws CmdLineException 不正なフォーマット種別
     */
    private static boolean decodeBoolean(String arg)
            throws CmdLineException{
        boolean result;

        if(   "on"  .equals(arg)
           || "true".equals(arg)
           || "yes" .equals(arg) ){
            result = true;
        }else if(   "off"  .equals(arg)
                 || "false".equals(arg)
                 || "no"   .equals(arg) ){
            result = false;
        }else{
            String errMsg = MessageFormat.format(ERRMSG_INVBOOL, arg);
            throw new CmdLineException(errMsg);
        }

        return result;
    }

    /**
     * ファイル名からファイル種別を類推する。
     * <p>拡張子が「.vmd」ならVMDファイル、「.xml」ならXMLファイル。
     * @param fileName ファイル名
     * @return ファイル種別。識別不可ならNONE。
     */
    private static MotionFileType getFileType(String fileName){
        MotionFileType result = MotionFileType.NONE;
        if(fileName == null) return result;

        String lower = fileName.toLowerCase(Locale.ROOT);
        if     (lower.endsWith(SFX_VMD)) result = MotionFileType.VMD;
        else if(lower.endsWith(SFX_XML)) result = MotionFileType.XML_110820;

        return result;
    }

    /**
     * ヘルプ要求があるかコマンドライン列を調べる。
     * @param cmds コマンドライン列
     * @return ヘルプ要求があればtrue
     */
    private static boolean hasHelp(List<CmdLine> cmds){
        for(CmdLine cmd : cmds){
            OptSwitch opt = cmd.getOptSwitch();
            if(opt == OptSwitch.OPT_HELP){
                return true;
            }
        }
        return false;
    }

    /**
     * 個別のオプション情報を格納する。
     * @param opt オプション識別子
     * @param exArg 引数。なければnull
     * @param result オプション情報格納先
     * @throws CmdLineException 不正なコマンドライン
     */
    private static void storeOptInfo(OptSwitch opt,
                                       String exArg,
                                       OptInfo result )
            throws CmdLineException{
        switch(opt){
        case OPT_FORCE:
            result.overwrite = true;
            break;
        case OPT_QUAT:
            result.isQuaternionMode = true;
            break;
        case OPT_EYXZ:
            result.isQuaternionMode = false;
            break;
        case OPT_INFILE:
            result.inFilename = exArg;
            break;
        case OPT_OUTFILE:
            result.outFilename = exArg;
            break;
        case OPT_NEWLINE:
            result.newline = decodeNewline(exArg);
            break;
        case OPT_IFORM:
            MotionFileType itype = decodeFormatType(exArg);
            result.inTypes  = itype;
            break;
        case OPT_OFORM:
            MotionFileType otype = decodeFormatType(exArg);
            result.outTypes  = otype;
            break;
        case OPT_GENOUT:
            boolean genout = decodeBoolean(exArg);
            if(genout) result.generator = Vmd2Xml.GENERATOR;
            else       result.generator = null;
            break;
        default:
            break;
        }

        return;
    }

    /**
     * コマンドラインを解析する。
     * @param args コマンドライン
     * @return オプション情報
     * @throws CmdLineException 不正なコマンドライン
     */
    static OptInfo parseOption(String... args) throws CmdLineException{
        List<CmdLine> cmdLines = CmdLine.parse(args);

        OptInfo result = new OptInfo();

        if(hasHelp(cmdLines)){
            result.needHelp = true;
            return result;
        }

        checkCmdLineList(cmdLines);

        for(CmdLine cmd : cmdLines){
            OptSwitch opt = cmd.getOptSwitch();

            List<String> optArgs = cmd.getOptArgs();
            String exArg1 = null;
            if(optArgs.size() >= 2){
                exArg1 = optArgs.get(1);
            }

            storeOptInfo(opt, exArg1, result);
        }

        fixFormat(result);

        checkResult(result);

        return result;
    }

    /**
     * 単純なコマンドラインエラーを検出する。
     * <p>検出項目は未知のオプションおよび不正な引数の個数
     * @param cmdLines コマンドライン
     * @throws CmdLineException 異常系
     */
    private static void checkCmdLineList(List<CmdLine> cmdLines)
            throws CmdLineException{
        for(CmdLine cmd : cmdLines){
            List<String> optArgs = cmd.getOptArgs();
            assert ! optArgs.isEmpty();

            String optTxt = optArgs.get(0);
            assert optTxt != null;

            OptSwitch opt = cmd.getOptSwitch();
            if(opt == null){
                String errMsg =
                        MessageFormat.format(ERRMSG_UNKNOWN, optTxt);
                throw new CmdLineException(errMsg);
            }

            int exArgNum = opt.getExArgNum();
            if(optArgs.size() != 1 + exArgNum){
                String errMsg =
                        MessageFormat.format(ERRMSG_MOREARG, optTxt);
                throw new CmdLineException(errMsg);
            }
        }

        return;
    }

    /**
     * ファイルフォーマット情報の推測を行う。
     * @param result オプション情報
     */
    private static void fixFormat(OptInfo result){
        if(result.inTypes == MotionFileType.NONE){
            result.inTypes = getFileType(result.inFilename);
        }

        if(result.outTypes == MotionFileType.NONE){
            result.outTypes = getFileType(result.outFilename);
        }

        return;
    }

    /**
     * オプション整合性の事後検査。
     * @param result オプション情報
     * @throws CmdLineException 不正なオプション設定
     */
    private static void checkResult(OptInfo result)
            throws CmdLineException{
        if(result.getInFilename() == null){
            throw new CmdLineException(ERRMSG_NOINFILE);
        }

        if(result.getOutFilename() == null){
            throw new CmdLineException(ERRMSG_NOOUTFILE);
        }

        if(result.getInFileType()  == MotionFileType.NONE){
            throw new CmdLineException(ERRMSG_INTYPE);
        }

        if(result.getOutFileType()  == MotionFileType.NONE){
            throw new CmdLineException(ERRMSG_OUTTYPE);
        }

        return;
    }


    /**
     * ヘルプ表示が必要か否か判定する。
     * @return 必要ならtrue
     */
    boolean needHelp(){
        return this.needHelp;
    }

    /**
     * 入力ファイル種別を返す。
     * @return 入力ファイル種別
     */
    MotionFileType getInFileType(){
        return this.inTypes;
    }

    /**
     * 出力ファイル種別を返す。
     * @return 出力ファイル種別
     */
    MotionFileType getOutFileType(){
        return this.outTypes;
    }

    /**
     * 入力ファイル名を返す。
     * @return 入力ファイル名
     */
    String getInFilename(){
        return this.inFilename;
    }

    /**
     * 出力ファイル名を返す。
     * @return 出力ファイル名
     */
    String getOutFilename(){
        return this.outFilename;
    }

    /**
     * 上書きモードか否か返す。
     * @return 上書きモードならtrue
     */
    boolean overwriteMode(){
        return this.overwrite;
    }

    /**
     * XML改行文字を返す。
     * @return 改行文字
     */
    String getNewline(){
        return this.newline;
    }

    /**
     * ジェネレータ名を返す。
     * @return ジェネレータ名。表示したくない時はnull
     */
    String getGenerator(){
        return this.generator;
    }

    /**
     * 回転情報のXML出力形式を得る。
     * @return クォータニオン形式ならtrue、YXZオイラー角形式ならfalse。
     */
    boolean isQuaterniomMode(){
        return this.isQuaternionMode;
    }

}
