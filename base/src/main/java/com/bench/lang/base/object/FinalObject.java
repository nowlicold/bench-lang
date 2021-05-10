/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.object;

/**
 * 解决 object不能进入final的问题
 * 
 * @author cold
 *
 * @version $Id: FinalObject.java, v 0.1 2016年4月7日 下午5:30:22 cold Exp $
 */
public class FinalObject<T> {

	private T object;

	public FinalObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FinalObject(T object) {
		super();
		// TODO Auto-generated constructor stub
		this.object = object;
	}

	/**
	 * @return Returns the object.
	 */
	public T getObject() {
		return object;
	}

	/**
	 * @param object
	 *            The object to set.
	 */
	public void setObject(T object) {
		this.object = object;
	}

}
