package test.imadcn.framework.common.xml;

import com.imadcn.framework.common.xml.XmlUtil;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLStreamException;
import org.testng.annotations.Test;

/**
 * @author Hinsteny
 * @version $ID: XmlUtilTest 2019-05-02 16:52 All rights reserved.$
 */
public class XmlUtilTest {

    /**
     * 对象转化为xml方法测试
     */
    @Test
    public void testTransformPojoToXml() throws JAXBException, XMLStreamException {
        TransReq transReq = buildTransReq();
        String xml = XmlUtil.transformPojoToXml(transReq, true, true);
        System.out.println(xml);
    }

    private TransReq buildTransReq() {
        TransReq transReq = new TransReq();
        TransHeader transHeader = new TransHeader();
        transHeader.setSysMsgLen("10000");
        transReq.setHeader(transHeader);
        TransBody transBody = new TransBody();
//        transBody.setResponse("");
        transReq.setBody(transBody);

        return transReq;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "Transaction")
    public static class TransReq {

        @XmlElement(name = "Transaction_Header")
        private TransHeader header;

        @XmlElement(name = "Transaction_Body", nillable = true)
        private TransBody body;

        public TransHeader getHeader() {
            return header;
        }

        public void setHeader(TransHeader header) {
            this.header = header;
        }

        public TransBody getBody() {
            return body;
        }

        public void setBody(TransBody body) {
            this.body = body;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class TransHeader {

        /**
         * 服务版本号--当前默认为01
         */
        @XmlElement(name = "SYS_TX_VRSN")
        private String sysTxVrsn = "01";

        /**
         * 应用报文长度---计算
         */
        @XmlElement(name = "SYS_MSG_LEN")
        private String sysMsgLen = "0";

        public String getSysTxVrsn() {
            return sysTxVrsn;
        }

        public void setSysTxVrsn(String sysTxVrsn) {
            this.sysTxVrsn = sysTxVrsn;
        }

        public String getSysMsgLen() {
            return sysMsgLen;
        }

        public void setSysMsgLen(String sysMsgLen) {
            this.sysMsgLen = sysMsgLen;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class TransBody {

        /**
         * 返回信息
         */
        @XmlElement(name = "response", nillable = true)
        private String response;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }

}
