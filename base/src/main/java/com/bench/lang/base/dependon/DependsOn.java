package com.bench.lang.base.dependon;

import java.util.List;

import com.bench.lang.base.order.Ordered;

/**
 * 依赖的
 * 
 * @author cold
 *
 * @version $Id: DependsOn.java, v 0.1 2020年5月11日 下午6:20:07 cold Exp $
 */
public interface DependsOn<T> extends Ordered {

	/**
	 * 返回依赖的类
	 * 
	 * @return
	 */
	List<Class<? extends T>> getDependsOn();

	/**
	 * 返回父
	 * 
	 * @return
	 */
	default Class<? extends T> getParent() {
		return null;
	}

	@Override
	default int order() {
		// TODO Auto-generated method stub
		return Ordered.DEFAULT_ORDER;
	}
}
