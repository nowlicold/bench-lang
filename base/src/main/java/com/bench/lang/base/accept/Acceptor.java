/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.accept;

/**
 * 接收器
 * 
 * @author cold
 * 
 * @version $Id: Visitor.java, v 0.1 2012-12-22 上午11:01:16 cold Exp $
 */
public interface Acceptor<T> {

	public boolean accept(T t);
}
