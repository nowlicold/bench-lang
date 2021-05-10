/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.string.build;

import java.lang.reflect.Field;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.instance.BenchClassInstanceFactory;
import com.bench.lang.base.string.build.annotations.ToStringExclude;
import com.bench.lang.base.string.build.construct.ToStringConstructor;

/**
 * 
 * @author cold
 * 
 * @version $Id: ReflectionToStringBuilder.java, v 0.1 2013-12-16 下午4:03:18 cold Exp $
 */
public class ReflectionToStringBuilder extends org.apache.commons.lang3.builder.ReflectionToStringBuilder {

	private static final Class<?>[] IGNORE_CLASS = new Class<?>[] {};

 
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
		accept = field.getAnnotation(ToStringExclude.class) == null;
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
		Object value = super.getValue(field);

		// 如果为null，直接返回
		if (value == null) {
			return null;
		}

		// 查找Builder
		ToStringConstructor targetBuilder = null;
		for (ToStringConstructor builder : BenchClassInstanceFactory.getImplementsClassInstances(ToStringConstructor.class)) {
			if (builder.isSupport(field)) {
				targetBuilder = builder;
				break;
			}
		}
		// 如果没有builder，则返回默认值
		if (targetBuilder == null) {
			return value;
		}
		return targetBuilder.buildString(super.getObject(), field, value);

	}
}
