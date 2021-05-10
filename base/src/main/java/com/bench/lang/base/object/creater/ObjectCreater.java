/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.object.creater;

/**
 * 对象创建器
 * 
 * @author cold
 *
 * @version $Id: ObjectCreater.java, v 0.1 2018年10月25日 下午5:31:54 cold Exp $
 */
public interface ObjectCreater<OBJ extends Object> {

	/**
	 * 访问
	 * 
	 * @param object
	 * @return
	 */
	public OBJ create();
}
