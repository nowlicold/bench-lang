/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.object;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.bench.lang.base.string.build.ToStringBuilder;

/**
 * 解决int、long不能进入final的问题
 * 
 * @author cold
 * 
 * @version $Id: IntegerObject.java, v 0.1 2011-6-13 下午08:31:49 cold Exp $
 */
public class IntegerObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3046458870011222692L;
	private int value;

	public IntegerObject(int value) {
		super();
		this.value = value;
	}

	public int increase() {
		value++;
		return value;
	}

	public int decrease() {
		value--;
		return value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int add(int value) {
		this.value += value;
		return this.value;
	}

	public int substract(int value) {
		this.value -= value;
		return this.value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
