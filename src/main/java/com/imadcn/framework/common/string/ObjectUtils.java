package com.imadcn.framework.common.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hinsteny
 * @version $ID: ObjectUtils 2019-05-02 11:01 All rights reserved.$
 */
public class ObjectUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtils.class);

    /**
     * 判断class类型是否为基础类型的包装引用类型
     * [byte, short, int, long, float, double, boolean, char]
     *                        ||||||
     * [Byte, Short, Integer, Long, Float, Double, Boolean, Character]
     * @param cls 被判断的类型
     * @return
     */
    public static boolean isPrimitive(Class cls) {
        try {
            return cls.isPrimitive() ? true : ((Class<?>) cls.getField("TYPE").get(null)).isPrimitive();
        } catch (IllegalAccessException | NoSuchFieldException e) {
//            LOGGER.warn("judge class type is primitive exception", e);
        }
        return false;
    }

}
