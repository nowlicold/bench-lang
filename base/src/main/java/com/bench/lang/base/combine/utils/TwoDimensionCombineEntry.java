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
public class TwoDimensionCombineEntry<T, E> extends ToStringObject {

	/**
	 * 1维拖
	 */
	private List<T> firstList;

	/**
	 * 1维胆
	 */
	private List<T> firstDanList;

	/**
	 * 2维拖
	 */
	private List<E> secondList;

	/**
	 * 2维胆
	 */
	private List<E> secondDanList;

 
	public List<T> getFirstList() {
		return firstList;
	}

	public void setFirstList(List<T> firstList) {
		this.firstList = firstList;
	}

	public List<E> getSecondList() {
		return secondList;
	}

	public void setSecondList(List<E> secondList) {
		this.secondList = secondList;
	}

	public List<T> getFirstDanList() {
		return firstDanList;
	}

	public void setFirstDanList(List<T> firstDanList) {
		this.firstDanList = firstDanList;
	}

	public List<E> getSecondDanList() {
		return secondDanList;
	}

	public void setSecondDanList(List<E> secondDanList) {
		this.secondDanList = secondDanList;
	}
}
