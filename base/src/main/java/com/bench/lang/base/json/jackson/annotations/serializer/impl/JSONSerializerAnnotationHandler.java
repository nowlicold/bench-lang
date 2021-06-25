/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson.annotations.serializer.impl;

import com.bench.lang.base.json.jackson.annotations.serializer.ValueAnnotationSerializer;
import com.bench.lang.base.json.value.serializer.JSONSerializer;
import com.bench.lang.base.json.value.serializer.Serializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于JSONSerializer的值处理
 * 
 * @author chenbug
 *
 * @version $Id: JSONSerializerAnnotationHandler.java, v 0.1 2016年3月2日 下午5:24:35 chenbug Exp $
 */
public class JSONSerializerAnnotationHandler implements ValueAnnotationSerializer<Serializer> {

	private Map<Class<?>, JSONSerializer> converterCacheMap = new ConcurrentHashMap<Class<?>, JSONSerializer>();

	public synchronized JSONSerializer getConverter(Class<?> converterClass) {
		JSONSerializer converter = converterCacheMap.get(converterClass);
		if (converter == null) {
			synchronized (converterCacheMap) {
				converter = converterCacheMap.get(converterClass);
				if (converter == null) {
					try {
						converter = (JSONSerializer) converterClass.newInstance();
					} catch (Exception e) {
						throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "实例化类异常,class=" + converterClass, e);
					}
					converterCacheMap.put(converterClass, (JSONSerializer) converter);
				}
			}

		}
		return converter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.json.jackson.annotations.handler.ValueAnnotationHandler #handle(java.lang.Object, com.fasterxml.jackson.databind.BeanProperty)
	 */
	@Override
	public Object handle(Object value, JsonGenerator gen, SerializerProvider serializers, BeanProperty beanProperty) {
		// TODO Auto-generated method stub
		Serializer annotation = beanProperty.getAnnotation(Serializer.class);
		return getConverter(annotation.value()).serialize(value);
	}

}
