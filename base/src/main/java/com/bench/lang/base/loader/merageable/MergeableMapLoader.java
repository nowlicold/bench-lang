package com.bench.lang.base.loader.merageable;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于Map的可合并Loader<br>
 * 将多个Map合并成一个Map
 * 
 * @author cold
 *
 * @version $Id: MergeableMapLoader.java, v 0.1 2020年5月19日 下午3:18:02 cold Exp $
 */
public interface MergeableMapLoader<K, V> extends MergeableLoader<Map<K, V>> {

	@Override
	default Map<K, V> newLoadData() {
		// TODO Auto-generated method stub
		return new HashMap<K, V>();
	}

	@Override
	default void mergeLoadData(Map<K, V> fromLoadData, Map<K, V> toLoadData) {
		// TODO Auto-generated method stub
		if (fromLoadData != null) {
			toLoadData.putAll(fromLoadData);
		}
	}
}
