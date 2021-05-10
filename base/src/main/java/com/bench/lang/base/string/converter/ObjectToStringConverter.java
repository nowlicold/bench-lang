/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.string.converter;

import com.bench.lang.base.object.utils.ObjectUtils;

/**
 * 
 * @author cold
 * 
 * @version $Id: ObjectToStringConverter.java, v 0.1 2013-1-17 下午2:35:55 cold Exp $
 */
public class ObjectToStringConverter<T> implements ToStringConverter<T> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.lang.base.ToStringConverter#convert(java.lang.Object)
	 */
	@Override
	public String convert(T t) {
		// TODO Auto-generated method stub
		return ObjectUtils.toString(t);
	}

}
