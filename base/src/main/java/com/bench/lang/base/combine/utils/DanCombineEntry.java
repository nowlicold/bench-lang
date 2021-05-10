/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.combine.utils;

import java.util.List;

import com.bench.lang.base.string.build.ToStringObject;

/**
 * 二维组合实体
 * 
 * @author cold
 * 
 * @version $Id: TwoDimensionCombineEntry.java, v 0.1 2011-6-13 下午06:37:59
 *          cold Exp $
 */
public class DanCombineEntry<T> extends ToStringObject {

	/**
	 * 普通集合
	 */
	private List<T> objectList;

	/**
	 *胆集合
	 */
	private List<T> danObjectList;

	public List<T> getDanObjectList() {
		return danObjectList;
	}

	public void setDanObjectList(List<T> danObjectList) {
		this.danObjectList = danObjectList;
	}

	public List<T> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<T> objectList) {
		this.objectList = objectList;
	}

}
