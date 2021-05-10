/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.string.build.construct.impl;

import java.lang.reflect.Field;
import java.sql.Date;

import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.instance.annotations.Singleton;
import com.bench.lang.base.sign.md5.utils.MD5Utils;
import com.bench.lang.base.string.build.construct.ToStringConstructor;

/**
 * 字节数组ToString
 * 
 * @author cold
 *
 * @version $Id: BytesToStringBuilder.java, v 0.1 2020年3月31日 下午12:33:28 cold Exp $
 */
@Singleton
public class DateToStringConstructor implements ToStringConstructor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.base.string.build.builder.ToStringBuilder#isSupport(java.lang.reflect.Field)
	 */
	@Override
	public boolean isSupport(Field field) {
		// TODO Auto-generated method stub
		return field.getType() == Date.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.base.string.build.builder.ToStringBuilder#buildString(java.lang.Object, java.lang.reflect.Field, java.lang.Object)
	 */
	@Override
	public String buildString(Object object, Field field, Object fieldValue) {
		// TODO Auto-generated method stub
		byte[] fieldBytesValue = (byte[]) fieldValue;
		return "'byte[] data,ignore output by bench,length=" + ArrayUtils.getLength(fieldBytesValue) + "，md5=" + MD5Utils.md5(fieldBytesValue) + "'";
	}
}
