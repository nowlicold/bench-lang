package com.bench.lang.base.enabled;

/**
 * 是否有效
 * 
 * @author cold
 *
 * @version $Id: Enabled.java, v 0.1 2020年5月21日 下午6:00:52 cold Exp $
 */
public interface Enabled {

	default boolean isEnabled() {
		return true;
	}
}
