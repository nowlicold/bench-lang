package com.bench.lang.base.loader.merageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于List的可合并Loader<br>
 * 将多个List合并成一个List
 * 
 * @author cold
 *
 * @version $Id: MergeableListLoader.java, v 0.1 2020年5月19日 下午3:18:02 cold Exp $
 */
public interface MergeableListLoader<E> extends MergeableLoader<List<E>> {
	@Override
	default List<E> newLoadData() {
		// TODO Auto-generated method stub
		return new ArrayList<E>();
	}

	@Override
	default void mergeLoadData(List<E> fromLoadData, List<E> toLoadData) {
		// TODO Auto-generated method stub
		if (fromLoadData != null) {
			toLoadData.addAll(fromLoadData);
		}
	}
}
