/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.object;

import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.date.utils.DateUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.sign.md5.utils.MD5Utils;
import com.bench.lang.base.string.utils.StringMaskUtils;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 
 * @author cold
 * 
 * @version $Id: ReflectionToStringBuilder.java, v 0.1 2013-12-16 下午4:03:18 cold Exp $
 */
public class ReflectionToStringBuilder extends org.apache.commons.lang3.builder.ReflectionToStringBuilder {

	private static final Class<?>[] IGNORE_CLASS = new Class<?>[] {};

	/**
	 * 隐藏的字段类型
	 */
	private String[] maskFieldNames;

	/**
	 * <p>
	 * Constructor.
	 * </p>
	 * 
	 * <p>
	 * If the style is <code>null</code>, the default style is used.
	 * </p>
	 * 
	 * @param object
	 *            the Object to build a <code>toString</code> for, must not be <code>null</code>
	 * @param style
	 *            the style of the <code>toString</code> to create, may be <code>null</code>
	 * @throws IllegalArgumentException
	 *             if the Object passed in is <code>null</code>
	 */
	public ReflectionToStringBuilder(Object object, ToStringStyle style) {
		super(object, style);
	}

	@Override
	protected boolean accept(Field field) {
		// TODO Auto-generated method stub
		boolean accept = super.accept(field);
		if (!accept) {
			return false;
		}
		if (ArrayUtils.contains(IGNORE_CLASS, field.getType())) {
			return false;
		}
		return true;
	}

	@Override
	protected Object getValue(Field field) throws IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		// 如果是字节输出，输出字节长度
		if (field.getType().equals(byte[].class)) {
			byte[] value = (byte[]) super.getValue(field);
			if (value != null) {
				return "'byte[] data,ignore output by bench,length=" + ArrayUtils.getLength(super.getValue(field)) + "，md5=" + MD5Utils.md5(value) + "'";
			} else {
				return null;
			}
		}
		if (field.getType().equals(Date.class)) {
			Date date = (Date) super.getValue(field);
			if (date != null) {
				return DateUtils.getDateExtraString(date);
			} else {
				return null;
			}
		}
		Object value = super.getValue(field);
		if (value == null) {
			return value;
		}
		if (ArrayUtils.contains(this.maskFieldNames, field.getName())) {

			return StringMaskUtils.maskAuto(ObjectUtils.toString(value));
		}
		return value;
	}

	public String[] getMaskFieldNames() {
		return maskFieldNames;
	}

	public void setMaskFieldNames(String[] maskFieldNames) {
		this.maskFieldNames = maskFieldNames;
	}
}
