package com.bench.lang.base.loader.merageable;

import com.bench.lang.base.loader.Loader;

/**
 * 可合并的Loader，表示会加载多个LOAD_DATA，然后合并成 一个LOAD_DATA
 * 
 * @author cold
 *
 * @version $Id: MergeableLoader.java, v 0.1 2020年5月19日 下午3:18:02 cold Exp $
 */
public interface MergeableLoader<LOAD_DATA> extends Loader<LOAD_DATA> {

	/**
	 * 构造一个新的加载数据
	 * 
	 * @return
	 */
	LOAD_DATA newLoadData();

	/**
	 * 合并加载的数据
	 * 
	 * @param fromLoadData
	 * @param toLoadData
	 */
	void mergeLoadData(LOAD_DATA fromLoadData, LOAD_DATA toLoadData);
}
