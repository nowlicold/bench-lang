/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.iterate.convert;

/**
 * 以迭代的方式将FROM转换为TO
 * 
 * @author cold
 *
 * @version $Id: ConvertIterator.java, v 0.1 2019年8月27日 下午1:10:59 cold Exp $
 */
public interface ConvertIterator<FROM, TO> extends Iterator<TO> {

	/**
	 * 将FROM转换为TO
	 * 
	 * @param from
	 * @return
	 */
	TO convert(FROM from);

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
	TO next();
}
