package com.bench.lang.base.repeat.utils;

import com.bench.lang.base.thread.utils.ThreadUtils;

/**
 * 重复操作工具类
 * 
 * @author cold
 *
 * @version $Id: RepeatUtils.java, v 0.1 2020年5月19日 上午11:42:00 cold Exp $
 */
public class RepeatUtils {

	/**
	 * 循环count次，每次执行完runable后，等待intervalMillSeconds 毫秒
	 * 
	 * @param count
	 * @param intervalMillSeconds
	 * @param repeat
	 */
	public static void repeat(int count, long intervalMillSeconds, Runnable runnable) {
		for (int i = 0; i < count; i++)
			runnable.run();
		if (intervalMillSeconds > 0) {
			ThreadUtils.sleep(intervalMillSeconds);
		}
	}

	/**
	 * 每次执行完runable后，等待intervalMillSeconds 毫秒，重复执行
	 * 
	 * @param intervalMillSeconds
	 * @param runnable
	 */
	public static void repeatInterval(long intervalMillSeconds, Runnable runnable) {
		repeat(Integer.MAX_VALUE, intervalMillSeconds, runnable);
	}

	/**
	 * 重复执行repeatCount次
	 * 
	 * @param intervalMillSeconds
	 * @param runnable
	 */
	public static void repeatCount(int count, Runnable runnable) {
		repeat(count, 0, runnable);
	}

}
