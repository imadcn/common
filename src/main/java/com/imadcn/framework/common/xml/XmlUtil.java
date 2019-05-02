package com.imadcn.framework.common.xml;

import com.imadcn.framework.common.string.ObjectUtils;
import java.io.ByteArrayOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于JAXB(Java Architecture for XML Binding)实现的对象与xml转化工具类
 * @author Hinsteny
 * @version $ID: XmlUtil 2019-05-02 14:46 All rights reserved.$
 */
public final class XmlUtil {

    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    private static final String XML_DEFAULT_ENCODING = "UTF-8";

    /**
     *
     * 将POJO（Plain Ordinary Java Object）转换为xml对象
     * <br>
     * a. 针对对象属性值为null时, 默认生成的xml是不包含该属性节点的, 如果需要生成一个值为空的节点, 可以在注解{@link XmlElement}的属性中配置(nillable = true)就能生成空标签,
     *    然后标签属性会包含(xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:nil="true"); 再如果只想生成空标签, 不要前面的熟悉内容, 针对String类型的变量, 可以设置
     *    属性值为空字符串("")即可, 推荐使用前者;
     *
     * @param pojo 要被转化的对象
     * @param hasHeader 最后生成的xml是否包含头, <?xml version="1.0" encoding="utf-8"?>
     * @param hasCData  最后生成的xml节点值是否被 <![CDATA[xxx]]> 包裹, 在对象属性的内容中包含被xml禁止使用的字符时, 可以用它进行避免解析;
     * @return
     * @throws JAXBException
     * @throws XMLStreamException
     */
    public static String transformPojoToXml(Object pojo, boolean hasHeader, boolean hasCData) throws JAXBException, XMLStreamException {
        if (null == pojo || ObjectUtils.isPrimitive((pojo).getClass()) || String.class == pojo.getClass()) {
            return null;
        }
        ByteArrayOutputStream op = new ByteArrayOutputStream();
        CustomXMLStreamWriter customXMLStreamWriter = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(pojo.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            XMLStreamWriter streamWriter = xof.createXMLStreamWriter(op);

            customXMLStreamWriter = new CustomXMLStreamWriter(streamWriter);
            customXMLStreamWriter.setcData(hasCData);

            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, !hasHeader);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, XML_DEFAULT_ENCODING);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(pojo, customXMLStreamWriter);
        } finally {
            if (null != customXMLStreamWriter) {
                try {
                    customXMLStreamWriter.flush();
                    customXMLStreamWriter.close();
                } catch (Exception e) {
                    logger.warn("close stream Exception", e);
                }
            }
        }
        return op.toString();
    }


}
