/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.object;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.bench.lang.base.string.build.ToStringBuilder;

/**
 * 解决 long不能进入final的问题
 * 
 * @author cold
 * 
 * @version $Id: LongObject, v 0.1 2011-6-13 下午08:31:49 cold Exp $
 */
public class LongObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2986362823909056313L;

	private long value;

	public LongObject(long value) {
		super();
		this.value = value;
	}

	public long increase() {
		value++;
		return value;
	}

	public long decrease() {
		value--;
		return value;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public long add(long value) {
		this.value += value;
		return this.value;
	}

	public long substract(long value) {
		this.value -= value;
		return this.value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
