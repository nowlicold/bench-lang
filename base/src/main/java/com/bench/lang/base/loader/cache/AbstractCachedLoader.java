/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.loader.cache;

import java.util.Date;

import com.bench.lang.base.date.utils.DateUtils;
import com.bench.lang.base.loader.exceptions.LoadException;
import com.bench.lang.base.loader.merageable.MergeableChannelLoader;

/**
 * 抽象的缓存Loader<Br>
 * 当加载异常时，使用缓存的数据<br>
 * 
 * @author cold
 *
 * @version $Id: AbstractCachedLoader.java, v 0.1 2020年5月19日 下午3:12:14 cold Exp $
 */
public abstract class AbstractCachedLoader<CHANNEL, LOAD_DATA> implements MergeableChannelLoader<CHANNEL, LOAD_DATA> {

	/**
	 * 缓存数据
	 */
	protected LOAD_DATA cachedLoadData;

	/**
	 * 缓存开始时间
	 */
	private Date cachedStartDate;

	/**
	 * 返回缓存数据的过期时间，默认小于0表示永久有效
	 * 
	 * @return
	 */
	protected int getCacheExpireSeconds() {
		// 默认不缓存
		return 0;
	}

	/**
	 * 当异常时，是否使用缓存的数据，默认为true
	 * 
	 * @return
	 */
	protected boolean usingCachedDataWhenException() {
		return true;
	}

	/**
	 * 当加载异常时<br>
	 * 如果异常发生后，不想使用缓存数据，则需要再次抛出LoadException ，中断整个加载流程
	 * 
	 * @param e
	 * @param needRefresh
	 * 
	 */
	protected abstract void whenLoadException(Exception e) throws LoadException;

	@Override
	public LOAD_DATA load() throws LoadException {
		// TODO Auto-generated method stub
		/***************************************
		 * 是否需要刷新<Br>
		 * 1、如果缓存开始时间为空<Br>
		 * 2、如果缓存开始时间不为空，并且设置了缓存失效时间，并且缓存已经过期了，则需要刷新<Br>
		 ***************************************/
		boolean needreRefresh = cachedStartDate == null
				|| cachedStartDate != null && this.getCacheExpireSeconds() >= 0 && DateUtils.getDiffSeconds(new Date(), cachedStartDate) > this.getCacheExpireSeconds();
		if (!needreRefresh) {
			return cachedLoadData;
		}
		try {
			cachedLoadData = mergedLoad();
			cachedStartDate = new Date();
			return cachedLoadData;
		} catch (Exception e) {
			// 触发异常处理
			whenLoadException(e);
			// 如果异常时使用缓存数据，则返回缓存数据
			if (usingCachedDataWhenException()) {
				return cachedLoadData;
			}
			// 否则返回null
			else {
				return null;
			}
		}
	}

}
