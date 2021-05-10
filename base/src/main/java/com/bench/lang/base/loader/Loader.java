package com.bench.lang.base.loader;

import com.bench.lang.base.enabled.Enabled;
import com.bench.lang.base.loader.exceptions.LoadException;
import com.bench.lang.base.order.Ordered;

/**
 * 加载器
 * 
 * @author cold
 *
 * @version $Id: Loader.java, v 0.1 2020年5月19日 下午3:18:02 cold Exp $
 */
public interface Loader<LOAD_DATA> extends Ordered, Enabled {

	/**
	 * 加载数据
	 * 
	 * @return
	 */
	LOAD_DATA load() throws LoadException;

	@Override
	default int order() {
		// TODO Auto-generated method stub
		return Ordered.DEFAULT_ORDER;
	}

}
