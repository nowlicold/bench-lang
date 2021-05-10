
package com.bench.lang.base.thread.utils;

/**
 * 线程池工具类
 * 
 * @author cold
 *
 * @version $Id: ThreadUtils.java, v 0.1 2018年7月18日 下午3:46:48 cold Exp $
 */
public final class ThreadUtils {

	public static void sleep(final long millis) {
		try {
			Thread.sleep(millis);
		} catch (final InterruptedException ex) {
			// ignore
		}
	}
}
