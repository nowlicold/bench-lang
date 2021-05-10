/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.combine.utils;

import java.util.List;

import com.bench.lang.base.string.build.ToStringObject;

/**
 * 合并实体
 * 
 * @author cold
 * 
 * @version $Id: CombineEntry.java, v 0.1 2011-6-13 下午06:37:59 cold Exp $
 */
public class CombineEntry<T> extends ToStringObject {

	/**
	 * 实体KEY
	 */
	private Object key;

	/**
	 * 选项列表
	 */
	private List<T> choiceList;

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public List<T> getChoiceList() {
		return choiceList;
	}

	public void setChoiceList(List<T> choiceList) {
		this.choiceList = choiceList;
	}

}
