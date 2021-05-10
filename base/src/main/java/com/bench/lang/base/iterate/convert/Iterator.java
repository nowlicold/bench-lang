/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.iterate.convert;

/**
 * 迭代
 * 
 * @author cold
 *
 * @version $Id: Iterator.java, v 0.1 2019年8月27日 下午1:10:59 cold Exp $
 */
public interface Iterator<E> {
	
	/**
	 * 是否还有下一个
	 * 
	 * @return
	 */
	boolean hasNext();

	/**
	 * 取下一个
	 * 
	 * @return
	 */
	E next();
}
