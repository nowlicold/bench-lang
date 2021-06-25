/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.sort;

import com.bench.common.enums.EnumBase;

/**
 * 可排序的枚举
 * 
 * @author cold
 *
 * @version $Id: SortableEnumBase.java, v 0.1 2016年5月11日 上午10:11:04 cold Exp
 *          $
 */
public interface Sortable extends EnumBase {

	/**
	 * 返回排序值
	 * 
	 * @return
	 */
	public int sortValue();

}
