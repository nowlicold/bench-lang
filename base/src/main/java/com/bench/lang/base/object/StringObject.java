/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.object;

import java.io.Serializable;

/**
 * 解决 String不能进入final的问题
 * 
 * @author cold
 * 
 * @version $Id: StringObject, v 0.1 2011-6-13 下午08:31:49 cold Exp $
 */
public class StringObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8609512141704934505L;
	private String value;

	public StringObject() {

	}

	public StringObject(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value;
	}
}
