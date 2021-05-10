/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.combine.utils;

import java.util.List;

/**
 * 组合算法中,每种组合值访问
 * 
 * @author cold
 * 
 * @version $Id: CombineEachVisitor.java, v 0.1 2011-6-13 下午03:42:15 cold Exp
 *          $
 */
public interface CombineEachVisitor<T> {
	/**
	 * 访问每种组合值
	 * 
	 * @param singleCombineList
	 */
	public void visit(List<T> singleCombineList);
}
