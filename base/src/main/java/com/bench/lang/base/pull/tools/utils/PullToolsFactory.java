/*
 * mywebsite.com Inc.
 * Copyright (c) 2004-2005 All Rights Reserved.
 */
package com.bench.lang.base.pull.tools.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bench.lang.base.instance.BenchClassInstanceFactory;
import com.bench.lang.base.instance.BenchInstanceFactory;
import com.bench.lang.base.instance.annotations.Singleton;
import com.bench.lang.base.pull.tools.annotations.PullTool;
import com.bench.lang.base.pull.tools.provider.PullToolsProvider;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * 
 * PullTool工厂
 * 
 * @author cold
 */
@Singleton
public class PullToolsFactory {

	private Map<String, Object> commonToolMap;

	public static final PullToolsFactory getInstance() {
		return BenchInstanceFactory.getInstance(PullToolsFactory.class);
	}

	/**
	 * 构造函数
	 */
	public PullToolsFactory() {
		commonToolMap = new HashMap<String, Object>();
		// 放入单例工具类
		List<Object> pullToolList = BenchClassInstanceFactory.getAnnotatedClassInstances(PullTool.class);
		pullToolList.forEach(p -> {
			commonToolMap.put(StringUtils.toFirstCharLowerCase(p.getClass().getSimpleName()), p);
		});

		List<? extends PullToolsProvider> pullToolsProviderList = BenchClassInstanceFactory.getImplementsClassInstances(PullToolsProvider.class);
		pullToolsProviderList.forEach(p -> {
			commonToolMap.putAll(p.provide());
		});
	}

	/**
	 * 返回通用的工具Map
	 * 
	 * @return
	 */
	public Map<String, Object> getCommonToolMap() {
		return commonToolMap;
	}

}
