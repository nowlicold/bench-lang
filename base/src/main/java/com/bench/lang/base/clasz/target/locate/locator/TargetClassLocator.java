/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.clasz.target.locate.locator;

import com.bench.lang.base.clasz.target.locate.TargetClassLocateResult;
import com.bench.lang.base.instance.annotations.Singleton;

/**
 * 目标class定位，因为classd可能是被AOP代理了，需要能拿到真正的class
 * 
 * @author cold
 *
 * @version $Id: TargetClassLoader.java, v 0.1 2020年6月12日 上午9:11:22 cold Exp $
 */
@Singleton
public interface TargetClassLocator {

	/**
	 * 定位对象的真实class
	 * 
	 * @param object
	 * @param objectClass
	 * @return
	 */
	TargetClassLocateResult locate(Object object, Class<?> objectClass);
}
