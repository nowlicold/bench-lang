/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.order.comparator;

import java.util.Comparator;

import com.bench.lang.base.number.utils.NumberUtils;
import com.bench.lang.base.order.Ordered;

/**
 * 
 * @author cold
 *
 * @version $Id: OrderedComparator.java, v 0.1 2018年3月13日 下午3:15:56 cold Exp $
 */
public class OrderedComparator implements Comparator<Ordered> {

	public static final OrderedComparator INSTANCE = new OrderedComparator();

	@Override
	public int compare(Ordered o1, Ordered o2) {
		// TODO Auto-generated method stub
		return NumberUtils.compare(o1.order(), o2.order());
	}
}
