package com.bench.lang.base.loader.merageable.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bench.lang.base.clasz.utils.ClassUtils;
import com.bench.lang.base.instance.BenchInstanceFactory;
import com.bench.lang.base.instance.annotations.Singleton;
import com.bench.lang.base.loader.exceptions.LoadException;
import com.bench.lang.base.loader.merageable.MergeableChannelLoader;

/**
 * 抽象的基于Map的可合并加载器管理
 * 
 * @author cold
 *
 * @version $Id: AbstractMapBasedMerableLoadManager.java, v 0.1 2020年5月18日 下午7:05:04 cold Exp $
 */
@Singleton
public class AbstractMapBasedMerableLoadManager<LOADER extends MergeableChannelLoader<?, Map<String, String>>> {

	/**
	 * 加载器集合
	 */
	protected List<LOADER> loaders;

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected AbstractMapBasedMerableLoadManager() {
		super();
		loaders = new ArrayList<LOADER>();
		Class<?> loaderClass = ClassUtils.getParameterizedType(this.getClass(), MergeableChannelLoader.class);
		BenchInstanceFactory.getAll(loaderClass).forEach(p -> loaders.add((LOADER) p));
	}

	public Map<String, String> load() throws LoadException {
		Map<String, String> returnMap = new HashMap<String, String>();
		for (LOADER loader : loaders) {
			Map<String, String> currentLoaded = loader.load();
			if (currentLoaded != null) {
				returnMap.putAll(currentLoaded);
			}
		}
		return returnMap;
	}
}
