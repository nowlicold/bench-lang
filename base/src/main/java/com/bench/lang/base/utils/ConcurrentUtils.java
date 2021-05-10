package com.bench.lang.base.utils;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * concurrent工具类
 * 
 * @author cold
 *
 * @version $Id: ConcurrentUtils.java, v 0.1 2020年4月8日 下午3:19:43 cold Exp $
 */
public class ConcurrentUtils {

	/**
	 * 创建一个ConcurrentHashSet
	 * 
	 * @param <K>
	 * @return
	 */
	public static <K> Set<K> newConcurrentHashSet() {
		return Collections.newSetFromMap(new ConcurrentHashMap<K, Boolean>(16));
	}

}
