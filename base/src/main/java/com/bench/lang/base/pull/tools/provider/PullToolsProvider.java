package com.bench.lang.base.pull.tools.provider;

import java.util.Map;

import com.bench.lang.base.order.Ordered;

/**
 * PullTool工具提供者
 * 
 * @author cold
 *
 * @version $Id: PullToolsProvider.java, v 0.1 2020年4月16日 下午3:20:36 cold Exp $
 */
public interface PullToolsProvider extends Ordered {

	/**
	 * 提供工具
	 * 
	 * @return
	 */
	Map<String, Object> provide();

	@Override
	default int order() {
		// TODO Auto-generated method stub
		return Ordered.DEFAULT_ORDER;
	}
}
