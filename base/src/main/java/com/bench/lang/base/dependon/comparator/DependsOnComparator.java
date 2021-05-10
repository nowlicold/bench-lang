/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.dependon.comparator;

import java.util.Comparator;
import java.util.List;

import com.bench.lang.base.dependon.DependsOn;
import com.bench.lang.base.dependon.utils.DependsOnUtils;
import com.bench.lang.base.number.utils.NumberUtils;

/**
 * 
 * @author cold
 *
 * @version $Id: DependsOnComparator.java, v 0.1 2020年5月21日 下午2:54:35 cold Exp $
 */
public class DependsOnComparator<T extends DependsOn<T>> implements Comparator<T> {

	private List<T> objectList;

	/**
	 * @param objectList
	 */
	public DependsOnComparator(List<T> objectList) {
		super();
		this.objectList = objectList;
	}

	@Override
	public int compare(T o1, T o2) {
		// TODO Auto-generated method stub
		// 比较依赖关系
		List<T> objectDependsOn1 = DependsOnUtils.getAllDependsOn(o1, objectList);
		if (objectDependsOn1.contains(o2)) {
			return 1;
		}

		List<T> objectDependsOn2 = DependsOnUtils.getAllDependsOn(o2, objectList);
		if (objectDependsOn2.contains(o1)) {
			return -1;
		}

		return NumberUtils.compare(o1.order(), o2.order());
	}

}
