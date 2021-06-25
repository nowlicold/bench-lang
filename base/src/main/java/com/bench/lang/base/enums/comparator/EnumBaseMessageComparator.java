/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.enums.comparator;

import com.bench.common.enums.EnumBase;

import java.util.Comparator;

/**
 * EnumBase基于message的比较器
 * 
 * @author cold
 * 
 * @version $Id: EnumBaseMessageComparator.java, v 0.1 2011-6-27 下午06:47:13
 *          cold Exp $
 */
public class EnumBaseMessageComparator implements Comparator<EnumBase> {

	/**
	 * 匹配模式，1升序，2降序
	 */
	private int compareMode;

	// 降序排序
	public static final EnumBaseMessageComparator ASC_INSTANCE = new EnumBaseMessageComparator(1);

	// 升序排序
	public static final EnumBaseMessageComparator DESC_INSTANCE = new EnumBaseMessageComparator(-1);

	private EnumBaseMessageComparator(int compareMode) {
		super();
		this.compareMode = compareMode;
	}

	public int compare(EnumBase o1, EnumBase o2) {
		// TODO Auto-generated method stub
		return compareMode * (o1.message().compareTo(o2.message()));
	}

}
