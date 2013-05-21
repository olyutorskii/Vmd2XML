/*
 * xml 2 vmd SAX Handler
 *
 * License : The MIT License
 * Copyright(c) 2013 MikuToga Partners
 */

package jp.sfjp.mikutoga.vmd.model.xml;

import jp.sfjp.mikutoga.vmd.model.VmdMotion;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * XMLモーションファイルパース用SAXハンドラ。
 * <p>下位リスナに各種通知が振り分けられる。
 */
class XmlHandler implements ContentHandler{

    private VmdMotion vmdMotion;

    private String nspfx = "";
    private String nsuri = null;

    private final SaxVmdListener motionListener;
    private final SaxVmdListener cameraListener;
    private final SaxVmdListener lightListener;

    private SaxVmdListener currentListener = null;


    /**
     * コンストラクタ。
     */
    XmlHandler(){
        super();

        this.motionListener = new SaxMotionListener();
        this.cameraListener = new SaxCameraListener();
        this.lightListener  = new SaxLightingListener();

        return;
    }


    /**
     * ビルド対象のモーションを返す。
     * @return ビルド対象のモーション
     */
    VmdMotion getVmdMotion(){
        return this.vmdMotion;
    }

    /**
     * {@inheritDoc}
     * @throws SAXException {@inheritDoc}
     */
    @Override
    public void startDocument() throws SAXException{
        this.vmdMotion = new VmdMotion();

        this.motionListener.setVmdMotion(this.vmdMotion);
        this.cameraListener.setVmdMotion(this.vmdMotion);
        this.lightListener .setVmdMotion(this.vmdMotion);

        return;
    }

    /**
     * {@inheritDoc}
     * @throws SAXException {@inheritDoc}
     */
    @Override
    public void endDocument() throws SAXException{
        assert this.vmdMotion != null;
        return;
    }

    /**
     * {@inheritDoc}
     * @param prefix {@inheritDoc}
     * @param uri {@inheritDoc}
     * @throws SAXException {@inheritDoc}
     */
    @Override
    public void startPrefixMapping(String prefix, String uri)
            throws SAXException {
        if(Schema110820.NS_VMDXML.equals(uri)){
            this.nspfx = prefix;
            this.nsuri = uri;
        }
        return;
    }

    /**
     * {@inheritDoc}
     * @param prefix {@inheritDoc}
     * @throws SAXException {@inheritDoc}
     */
    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        if(prefix.equals(this.nspfx)){
            this.nspfx = "";
            this.nsuri = null;
        }
        return;
    }

    /**
     * {@inheritDoc}
     * @param uri {@inheritDoc}
     * @param localName {@inheritDoc}
     * @param qName {@inheritDoc}
     * @param attr {@inheritDoc}
     * @throws SAXException {@inheritDoc}
     */
    @Override
    public void startElement(String uri,
                               String localName,
                               String qName,
                               Attributes attr)
            throws SAXException {
        if( ! this.nsuri.equals(uri) ) return;

        VmdTag tag = VmdTag.parse(localName);
        if(tag == null) return;

        if(tag == VmdTag.MODEL_NAME){
            String modelName =
                    SaxXsdUtil.getStringAttr(attr, XmlAttr.ATTR_NAME);
            this.vmdMotion.setModelName(modelName);
            return;
        }

        switchListener(tag);
        if(this.currentListener != null){
            this.currentListener.openTag(tag, attr);
        }

        return;
    }

    /**
     * タグ出現に従い通知リスナを切り替える。
     * @param tag タグ種別
     */
    private void switchListener(VmdTag tag){
        switch(tag){
        case BONE_M_SEQUENCE:
        case MORPH_SEQUENCE:
            this.currentListener = this.motionListener;
            break;
        case CAMERA_SEQUENCE:
            this.currentListener = this.cameraListener;
            break;
        case LUMI_SEQUENCE:
        case SHADOW_SEQUENCE:
            this.currentListener = this.lightListener;
            break;
        default:
            break;
        }

        return;
    }

    /**
     * {@inheritDoc}
     * @param uri {@inheritDoc}
     * @param localName {@inheritDoc}
     * @param qName {@inheritDoc}
     * @throws SAXException {@inheritDoc}
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if( ! this.nsuri.equals(uri) ) return;

        VmdTag tag = VmdTag.parse(localName);
        if(tag == null) return;

        if(this.currentListener != null){
            this.currentListener.closeTag(tag);
        }

        return;
    }

    /**
     * {@inheritDoc}
     * @param locator {@inheritDoc}
     */
    @Override
    public void setDocumentLocator(Locator locator){
        return;
    }

    /**
     * {@inheritDoc}
     * @param target {@inheritDoc}
     * @param data {@inheritDoc}
     * @throws SAXException {@inheritDoc}
     */
    @Override
    public void processingInstruction(String target, String data)
            throws SAXException {
        return;
    }

    /**
     * {@inheritDoc}
     * @param ch {@inheritDoc}
     * @param start {@inheritDoc}
     * @param length {@inheritDoc}
     * @throws SAXException {@inheritDoc}
     */
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        return;
    }

    /**
     * {@inheritDoc}
     * @param ch {@inheritDoc}
     * @param start {@inheritDoc}
     * @param length {@inheritDoc}
     * @throws SAXException {@inheritDoc}
     */
    @Override
    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
        return;
    }

    /**
     * {@inheritDoc}
     * @param name {@inheritDoc}
     * @throws SAXException {@inheritDoc}
     */
    @Override
    public void skippedEntity(String name) throws SAXException{
        return;
    }

}
