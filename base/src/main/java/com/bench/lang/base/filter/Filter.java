/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.filter;

/**
 * 过滤器
 * 
 * @author cold
 *
 * @version $Id: Filter.java, v 0.1 2018年10月25日 下午5:31:54 cold Exp $
 */
public interface Filter<T extends Object> {

	/**
	 * 是否接收
	 * 
	 * @param object
	 * @return
	 */
	public boolean accept(T object);
}
