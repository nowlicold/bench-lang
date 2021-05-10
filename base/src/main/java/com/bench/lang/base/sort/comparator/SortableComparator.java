/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.sort.comparator;

import java.util.Comparator;

import com.bench.lang.base.number.utils.NumberUtils;
import com.bench.lang.base.sort.Sortable;

/**
 * EnumBase基于message的比较器
 * 
 * @author cold
 * 
 * @version $Id: SortableComparator.java, v 0.1 2011-6-27 下午06:47:13 cold Exp $
 */
public class SortableComparator implements Comparator<Sortable> {

	/**
	 * 匹配模式，1升序，2降序
	 */
	private int compareMode;

	// 降序排序
	public static final SortableComparator ASC_INSTANCE = new SortableComparator(1);

	// 升序排序
	public static final SortableComparator DESC_INSTANCE = new SortableComparator(-1);

	private SortableComparator(int compareMode) {
		super();
		this.compareMode = compareMode;
	}

	public int compare(Sortable o1, Sortable o2) {
		// TODO Auto-generated method stub
		return compareMode * NumberUtils.compare(o1.sortValue(), o2.sortValue());
	}

}
