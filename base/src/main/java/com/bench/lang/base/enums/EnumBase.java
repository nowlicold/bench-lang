package com.bench.lang.base.enums;

import java.lang.reflect.Field;
import java.util.Objects;

import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * 枚举接口
 * 
 * @author cold
 * @version $Id: EnumBase.java,v 0.1 2008-12-30 上午10:22:20 cold Exp $
 */
public interface EnumBase {

	/**
	 * 获取枚举名
	 * 
	 * @return
	 */
	public String name();

	/**
	 * 获取枚举消息
	 * 
	 * @return
	 */
	public default String message() {
		// 优先获取“message”字段值
		Field field = FieldUtils.getField(this.getClass(), "message", true);
		if (field == null) {
			return name();
		}
		try {
			return Objects.toString(field.get(this));
		} catch (Exception e) {
			// ignore
		}
		return name();
	}

	/**
	 * 获取枚举值
	 * 
	 * @return
	 */
	public default Number value() {
		// 优先获取“value”字段值
		Field field = FieldUtils.getField(this.getClass(), "value", true);
		if (field == null) {
			return null;
		}
		try {
			return (Number) (field.get(this));
		} catch (Exception e) {
			// ignore
		}
		return null;
	}
}
