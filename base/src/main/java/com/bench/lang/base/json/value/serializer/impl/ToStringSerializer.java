/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.value.serializer.impl;

import com.bench.lang.base.json.value.serializer.JSONSerializer;
import com.bench.lang.base.object.utils.ObjectUtils;

/**
 * 将对象toString的converter
 * 
 * @author cold
 * 
 * @version $Id: ToStringConverter.java, v 0.1 2012-6-25 下午12:31:14 cold Exp
 *          $
 */
public class ToStringSerializer implements JSONSerializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bench.common.json.converter.JSONConverter#convertObjectValue(java
	 * .lang.String, java.lang.Object, java.lang.reflect.Field,
	 * com.bench.common.json.JsonConfig)
	 */
	@Override
	public Object serialize(Object value) {
		// TODO Auto-generated method stub
		return ObjectUtils.toString(value);
	}

}
