/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.object;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.bench.lang.base.string.build.ToStringBuilder;

/**
 * 解决boolean不能进入final的问题
 * 
 * @author cold
 * 
 * @version $Id: LongObject, v 0.1 2011-6-13 下午08:31:49 cold Exp $
 */
public class BooleanObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1117316519954926945L;
	private boolean value;

	public BooleanObject(boolean value) {
		super();
		this.value = value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setBoolean(boolean value) {
		this.value = value;
	}

	/**
	 * @return
	 */
	public boolean booleanValue() {
		return this.value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
