/*
 * custom error-handler for validation
 *
 * License : The MIT License
 * Copyright(c) 2011 MikuToga Partners
 */

package jp.sourceforge.mikutoga.vmd2xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * 妥当性検証用エラーハンドラ。
 * <p>XML SchemaによるXML文書検証を目的とするエラーハンドラ。
 */
final class ValidationHandler implements ErrorHandler {

    /**
     * 唯一のシングルトン。
     */
    static final ErrorHandler HANDLER = new ValidationHandler();

    /**
     * 隠しコンストラクタ。
     */
    private ValidationHandler(){
        super();
        return;
    }

    /**
     * {@inheritDoc}
     * @param exception {@inheritDoc}
     * @throws SAXException {@inheritDoc}
     */
    @Override
    public void error(SAXParseException exception) throws SAXException{
        throw exception;
    }

    /**
     * {@inheritDoc}
     * @param exception {@inheritDoc}
     * @throws SAXException {@inheritDoc}
     */
    @Override
    public void fatalError(SAXParseException exception) throws SAXException{
        throw exception;
    }

    /**
     * {@inheritDoc}
     * @param exception {@inheritDoc}
     * @throws SAXException {@inheritDoc}
     */
    @Override
    public void warning(SAXParseException exception) throws SAXException{
        throw exception;
    }

}
