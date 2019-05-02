package com.imadcn.framework.common.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * @author Hinsteny
 * @version $ID: DelegateXMLStreamWriter 2019-05-02 15:30 All rights reserved.$
 */
public final class CustomXMLStreamWriter extends DelegatingXMLStreamWriter {

    private boolean cData = false;

    public CustomXMLStreamWriter(XMLStreamWriter writer) {
        super(writer);
    }

    public void setcData(boolean cData) {
        this.cData = cData;
    }

    /**
     * Write text to the output
     *
     * @param text the value to write
     */
    @Override
    public void writeCharacters(String text) throws XMLStreamException {
        if (!"".equals(text) && cData) {
            super.writeCData(text);
        } else {
            super.writeCharacters(text);
        }
    }

}
