/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.override.loader;

/**
 * 加载所有重载的子类
 * 
 * @author cold
 *
 * @version $Id: OverrideTypesLoader.java, v 0.1 2016年6月27日 下午6:22:55 cold Exp $
 */
public interface OverrideTypesLoader {

	/**
	 * 加载所有重载的子类
	 * 
	 * @return
	 */
	Class<?>[] load();

	/**
	 * 是否包含子类
	 * 
	 * @return
	 */
	default boolean includeChildClass() {
		return true;
	}
}
