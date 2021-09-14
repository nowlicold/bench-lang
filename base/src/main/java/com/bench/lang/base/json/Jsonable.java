/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 可json化的对象
 * 
 * @author cold
 *
 * @version $Id: Jsonable.java, v 0.1 2016年6月2日 上午10:35:58 cold Exp $
 */
public interface Jsonable {

	/**
	 * 转换为json节点
	 * 
	 * @return
	 */
	public JsonNode toJson();

}
