/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.instance.comparator;

import java.util.Comparator;

import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.classreading.MetadataReader;

import com.bench.lang.base.number.utils.NumberUtils;
import com.bench.lang.base.order.Ordered;
import com.bench.lang.base.order.annotations.Order;

/**
 * MetadataReader中Order注解比较器
 * 
 * @author cold
 *
 * @version $Id: MetadataReaderOrderComparator.java, v 0.1 2020年4月1日 下午7:17:06 cold Exp $
 */
public class MetadataReaderOrderComparator implements Comparator<MetadataReader> {

	public static final MetadataReaderOrderComparator INSTANCE = new MetadataReaderOrderComparator();

	@Override
	public int compare(MetadataReader o1, MetadataReader o2) {
		// TODO Auto-generated method stub
		MergedAnnotation<Order> order1 = o1.getAnnotationMetadata().getAnnotations().get(Order.class);
		MergedAnnotation<Order> order2 = o2.getAnnotationMetadata().getAnnotations().get(Order.class);
		int orderValue1 = order1.isPresent() ? order1.getInt("value") : Ordered.DEFAULT_ORDER;
		int orderValue2 = order2.isPresent() ? order2.getInt("value") : Ordered.DEFAULT_ORDER;
		return NumberUtils.compare(orderValue1, orderValue2);
	}

}
