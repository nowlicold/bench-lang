/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.set.utils;

import java.util.HashSet;
import java.util.Set;

import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.collection.utils.CollectionUtils;

/**
 * Set工具类
 * 
 * @author cold
 * 
 * @version $Id: SetUtils.java, v 0.1 2013-5-9 下午4:20:11 cold Exp $
 */
public class SetUtils extends org.apache.commons.collections.SetUtils {

	public static final SetUtils INSTANCE = new SetUtils();

	/**
	 * 转换到Set集合
	 * 
	 * @param ts
	 * @return
	 */
	@SafeVarargs
	public static <T> Set<T> toSet(T... ts) {
		Set<T> retList = new HashSet<T>();
		if (ArrayUtils.isEmpty(ts)) {
			return retList;
		}
		CollectionUtils.addAll(retList, ts);
		return retList;
	}

	/**
	 * 是否包含
	 * 
	 * @param set
	 * @param t
	 * @return
	 */
	public static <T> boolean contains(Set<T> set, T t) {
		return set == null ? false : set.contains(t);
	}
}
