/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.math.permutation;

import java.util.List;

/**
 * 排列算法中,每种排列值访问
 * 
 * @author cold
 * 
 * @version $Id: PermutationEachVisitor.java, v 0.1 2011-6-13 下午03:42:15 cold
 *          Exp $
 */
public interface PermutationEachVisitor<T> {
	/**
	 * 访问每种排列值
	 * 
	 * @param singleCombineList
	 */
	public void visit(List<T> singleCombineList);
}
