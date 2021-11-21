/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.payload.converter;


import com.bench.lang.base.parameter.Parameter;
import com.bench.lang.base.string.utils.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author cold
 * 
 * @version $Id: PayloadValueConverterManager.java, v 0.1 2014-8-6 上午11:02:09
 *          cold Exp $
 */
public class PayloadValueConverterManager {

	/**
	 * 转换器map,这个必须在底层框架注册，否则跨系统传递时，其他系统无法转换
	 */
	private static Map<Class<?>, PayloadValueConverter<?>> converterMap = new HashMap<Class<?>, PayloadValueConverter<?>>();

	/**
	 * 默认转换器
	 */
	private static PayloadValueConverter<Object> defaultPayloadConverter = null;
	static {
		converterMap.put(Date.class, new Date_PayloadValueConverter());
		converterMap.put(Map.class, new Map_PayloadValueConverter());
		converterMap.put(List.class, new List_PayloadValueConverter());
		converterMap.put(Parameter.class, new Parameter_PayloadValueConverter());
		converterMap.put(byte[].class, new Bytes_PayloadValueConverter());

		defaultPayloadConverter = new Default_PayloadValueConverter();
	}

	/**
	 * 设置默认转换器
	 * 
	 * @param defaultPayloadConverter
	 */
	public static void setDefaultPayloadConverter(PayloadValueConverter<Object> defaultPayloadConverter) {
		PayloadValueConverterManager.defaultPayloadConverter = defaultPayloadConverter;
	}

	/**
	 * 获取默认转换器
	 * 
	 * @return
	 */
	public static PayloadValueConverter<Object> getDefaultPayloadConverter() {
		return defaultPayloadConverter;
	}

	/**
	 * 注册转换器
	 * 
	 * @param clasz
	 * @param converter
	 */
	public static <T> void register(Class<T> clasz, PayloadValueConverter<T> converter) {
		converterMap.put(clasz, converter);
	}

	/**
	 * 获取转换器
	 * 
	 * @param clasz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PayloadValueConverter<Object> getConverter(Class<?> clasz) {
		for (Map.Entry<Class<?>, PayloadValueConverter<?>> entry : converterMap.entrySet()) {
			if (entry.getKey().isAssignableFrom(clasz)) {
				return (PayloadValueConverter<Object>) entry.getValue();
			}
		}
		return null;
	}

	/**
	 * 获取转换器
	 * 
	 * @param elementName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PayloadValueConverter<Object> getConverter(String elementName) {
		for (PayloadValueConverter<?> converter : converterMap.values()) {
			if (!StringUtils.isEmpty(converter.getElementName())
					&& StringUtils.equals(converter.getElementName(), elementName)) {
				return (PayloadValueConverter<Object>) converter;
			}
		}
		return null;
	}
}
