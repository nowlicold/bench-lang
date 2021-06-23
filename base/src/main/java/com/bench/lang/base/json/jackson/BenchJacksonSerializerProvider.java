/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson;

import com.bench.lang.base.clasz.field.utils.FieldUtils;
import com.bench.lang.base.json.jackson.annotations.serializer.MultiValueAnnotationJsonSerializer;
import com.bench.lang.base.json.jackson.annotations.serializer.ValueAnnotationSerializer;
import com.bench.lang.base.json.jackson.annotations.serializer.ValueAnnotationSerializerFactory;
import com.bench.lang.base.string.utils.StringUtils;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 
 * @author chenbug
 *
 * @version $Id: BenchJacksonSerializerProvider.java, v 0.1 2016年3月2日 上午10:21:20
 *          chenbug Exp $
 */
public class BenchJacksonSerializerProvider extends DefaultSerializerProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5316857232800743198L;

	public BenchJacksonSerializerProvider() {
		super();
	}

	public BenchJacksonSerializerProvider(BenchJacksonSerializerProvider src) {
		super(src);
	}

	protected BenchJacksonSerializerProvider(SerializerProvider src, SerializationConfig config, SerializerFactory f) {
		super(src, config, f);
	}

	@Override
	public DefaultSerializerProvider copy() {
		if (getClass() != BenchJacksonSerializerProvider.class) {
			return super.copy();
		}
		return new BenchJacksonSerializerProvider(this);
	}

	@Override
	public BenchJacksonSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
		return new BenchJacksonSerializerProvider(this, config, jsf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fasterxml.jackson.databind.SerializerProvider#findValueSerializer
	 * (com.fasterxml.jackson.databind.JavaType,
	 * com.fasterxml.jackson.databind.BeanProperty)
	 */
	@Override
	public JsonSerializer<Object> findValueSerializer(JavaType valueType, BeanProperty property) throws JsonMappingException {
		JsonSerializer<Object> ser = getSpecialSerializer(property);
		if (ser != null) {
			return ser;
		}
		return super.findValueSerializer(valueType, property);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.SerializerProvider#
	 * findPrimaryPropertySerializer(com.fasterxml.jackson.databind.JavaType,
	 * com.fasterxml.jackson.databind.BeanProperty)
	 */
	@Override
	public JsonSerializer<Object> findPrimaryPropertySerializer(JavaType valueType, BeanProperty property) throws JsonMappingException {
		// TODO Auto-generated method stub
		JsonSerializer<Object> ser = getSpecialSerializer(property);
		if (ser != null) {
			return ser;
		}
		return super.findPrimaryPropertySerializer(valueType, property);
	}

	private JsonSerializer<Object> getSpecialSerializer(BeanProperty property) {
		// 按注解转换
		try {
			String filedName = FieldUtils.getFieldNameByGetterMethod(property.getMember().getName());
			if (!StringUtils.isEmpty(filedName)) {
				Field field = FieldUtils.getField(property.getMember().getDeclaringClass(), filedName);
				List<ValueAnnotationSerializer<?>> handlerList = ValueAnnotationSerializerFactory.getHandlers(field);
				if (handlerList.size() > 0) {
					return new MultiValueAnnotationJsonSerializer(property, handlerList);
				}
			}
		} catch (NoSuchFieldException e) {

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.SerializerProvider#
	 * findPrimaryPropertySerializer(java.lang.Class,
	 * com.fasterxml.jackson.databind.BeanProperty)
	 */
	@Override
	public JsonSerializer<Object> findPrimaryPropertySerializer(Class<?> valueType, BeanProperty property) throws JsonMappingException {
		// TODO Auto-generated method stub
		JsonSerializer<Object> ser = getSpecialSerializer(property);
		if (ser != null) {
			return ser;
		}
		return super.findPrimaryPropertySerializer(valueType, property);
	}

}
