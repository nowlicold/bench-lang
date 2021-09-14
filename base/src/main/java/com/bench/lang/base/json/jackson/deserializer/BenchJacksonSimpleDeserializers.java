/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson.deserializer;

import com.bench.lang.base.clasz.utils.ClassUtils;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.type.ClassKey;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author cold
 *
 * @version $Id: BenchJacksonSimpleDeserializers.java, v 0.1 2017年12月27日
 *          下午1:56:01 cold Exp $
 */
public class BenchJacksonSimpleDeserializers extends SimpleDeserializers {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5003032842006337812L;

	private Map<Class<?>, Class<? extends JsonDeserializer<?>>> deserializerClassMap = new HashMap<Class<?>, Class<? extends JsonDeserializer<?>>>();

	public <T> void addDeserializerClass(Class<?> forClass, Class<? extends JsonDeserializer<?>> deserClass) {
		deserializerClassMap.put(forClass, deserClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.module.SimpleDeserializers#
	 * findEnumDeserializer(java.lang.Class,
	 * com.fasterxml.jackson.databind.DeserializationConfig,
	 * com.fasterxml.jackson.databind.BeanDescription)
	 */
	@Override
	public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
		// TODO Auto-generated method stub
		if (_classMappings == null) {
			return null;
		}
		ClassKey key = new ClassKey(type);
		JsonDeserializer<?> deser = _classMappings.get(key);
		if (deser == null) {
			Class<? extends JsonDeserializer<?>> deserClass = deserializerClassMap.get(type);
			if (deserClass != null) {
				return ClassUtils.newInstance(deserClass, new Class<?>[] { BeanDescription.class }, new Object[] { beanDesc });
			}
			// If not direct match, maybe super-class match?
			Class<?> superClass = type.getSuperclass();
			while (superClass != null) {
				key.reset(superClass);
				deser = _classMappings.get(key);
				if (deser != null) {
					return deser;
				}

				deserClass = deserializerClassMap.get(superClass);
				if (deserClass != null) {
					return ClassUtils.newInstance(deserClass, new Class<?>[] { BeanDescription.class }, new Object[] { beanDesc });
				}

				superClass = superClass.getSuperclass();
			}

			for (Class<?> interfaceCalss : type.getInterfaces()) {
				key.reset(interfaceCalss);
				deser = _classMappings.get(key);
				if (deser != null) {
					return deser;
				}
				deserClass = deserializerClassMap.get(interfaceCalss);
				if (deserClass != null) {
					return ClassUtils.newInstance(deserClass, new Class<?>[] { BeanDescription.class }, new Object[] { beanDesc });
				}
			}

			if (_hasEnumDeserializer && type.isEnum()) {
				deser = _classMappings.get(new ClassKey(Enum.class));
			}
		}

		return deser;
	}

}
