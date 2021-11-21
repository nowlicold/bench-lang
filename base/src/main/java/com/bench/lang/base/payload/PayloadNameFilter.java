package com.bench.lang.base.payload;

/**
 * 
 * 
 * @author cold
 *
 * @version $Id: PayloadNameFilter.java, v 0.1 2015年6月30日 下午3:18:01 cold Exp
 *          $
 */
public interface PayloadNameFilter {

	/**
	 * 是否接受该数据名
	 * 
	 * @param name
	 * @return
	 */
	public boolean accept(String name);
}
