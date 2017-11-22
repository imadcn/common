package com.imadcn.framework.common.reflect;

import java.lang.reflect.Field;

public class ReflectUtil {

	public static Object getClassDeclaredField(Object obj, String filedText) throws Exception {
		Field field = obj.getClass().getDeclaredField(filedText);

		if (field == null) {
			return null;
		}

		field.setAccessible(true);
		return field.get(obj);
	}

	public static Object getFieldValue(Object obj, String fieldName) throws IllegalArgumentException, IllegalAccessException {
		Object result = null;
		Field field = ReflectUtil.getField(obj, fieldName);

		if (field != null) {
			field.setAccessible(true);
			result = field.get(obj);
		}

		return result;
	}

	public static Object getSuperclassDeclaredField(Object obj, String filedText) throws Exception {
		Field field = obj.getClass().getSuperclass().getDeclaredField(filedText);

		if (field == null) {
			return null;
		}

		field.setAccessible(true);
		return field.get(obj);
	}

	/**
	 * 利用反射设置指定对象的指定属性为指定的值
	 * 
	 * @param obj 目标对象
	 * @param fieldName 目标属性
	 * @param fieldValue 目标值
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void setFieldValue(Object obj, String fieldName, String fieldValue) throws IllegalArgumentException, IllegalAccessException {
		Field field = getField(obj, fieldName);
		
		if (field != null) {
			field.setAccessible(true);
			field.set(obj, fieldValue);
		}
	}

	public static Field getField(Object obj, String fieldName) {
		Field field = null;

		for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				break;
			} catch (NoSuchFieldException e) {
				// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
			}
		}

		return field;
	}

}