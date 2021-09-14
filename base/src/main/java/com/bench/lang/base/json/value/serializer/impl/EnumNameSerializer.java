/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.value.serializer.impl;

import com.bench.lang.base.json.value.serializer.JSONSerializer;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 只取枚举的name，忽略其他属性
 * 
 * @author cold
 * 
 * @version $Id: EnumNameConverter.java, v 0.1 2012-6-25 下午12:31:14 cold Exp $
 */
public class EnumNameSerializer implements JSONSerializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.json.converter.JSONConverter#convertObjectValue(java .lang.String, java.lang.Object, java.lang.reflect.Field,
	 * com.bench.common.json.JsonConfig)
	 */
	@Override
	public Object serialize(Object value) {
		// TODO Auto-generated method stub
		if (value == null) {
			return null;
		}
		if (value instanceof Enum) {
			return ((Enum<?>) value).name();
		}

		if (value.getClass().isArray()) {
			List<String> valueList = new ArrayList<String>();
			for (int i = 0; i < ArrayUtils.getLength(value); i++) {
				Object eachValueObj = Array.get(value, i);
				valueList.add(eachValueObj == null ? null : ((Enum<?>) eachValueObj).name());
			}
			return valueList;
		}
		if (value instanceof Collection) {
			Collection<?> valueCollection = (Collection<?>) value;
			List<String> valueList = new ArrayList<String>();
			for (Object eachValueObj : valueCollection) {
				valueList.add(eachValueObj == null ? null : ((Enum<?>) eachValueObj).name());
			}
			return valueList;
		}
		return value;
	}

}
