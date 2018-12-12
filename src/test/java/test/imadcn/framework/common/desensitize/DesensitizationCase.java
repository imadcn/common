package test.imadcn.framework.common.desensitize;

import com.alibaba.fastjson.JSON;
import com.imadcn.framework.common.desensitize.DesensitionType;
import com.imadcn.framework.common.desensitize.Desensitization;
import com.imadcn.framework.common.desensitize.ValueDesensitizeFilter;
import org.testng.annotations.Test;

/**
 * 脱敏操作示例
 * @author Hinsteny
 * @version $ID: DesensitizationCase 2018-12-12 20:37 All rights reserved.$
 */
public class DesensitizationCase {

    @Test
    public void testDesensitizedPhone() {
        Person person = new Person();
        person.setName("无羡");
        person.setIdNo("519890199012120000");
        person.setPhone("18240156068");
        person.setCardNo("6214678286831572123456");
        person.setAddress("中国四川省成都市高新区");
        System.out.println(JSON.toJSONString(person, new ValueDesensitizeFilter()));
    }

    public static class Person {

        @Desensitization(type = DesensitionType.CUSTOM, attach = {"^(\\S+)(\\S{1})$", "*$2"})
        private String name;

        @Desensitization(type = DesensitionType.IDENTITYNO)
        private String idNo;

        @Desensitization(type = DesensitionType.PHONE)
        private String phone;

        @Desensitization(type = DesensitionType.BANKCARDNO)
        private String cardNo;

        @Desensitization(type = DesensitionType.TRUNCATE, attach = {"0", "3"})
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

}
