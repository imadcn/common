package com.imadcn.framework.common.desensitize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要脱敏的对象
 * @author Hinsteny
 * @version $ID: Desensitization 2018-12-12 20:12 All rights reserved.$
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Desensitization {

    /**
     * 脱敏规则类型
     * @return
     */
    DesensitionType type();

    /**
     * 附加值, 自定义正则表达式等
     * @return
     */
    String[] attach() default "";

}
