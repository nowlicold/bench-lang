package com.bench.lang.base.math.utils;

import com.bench.lang.base.math.Percentage;

/**
 * 百分比
 * 
 * @author cold 2009-12-20 下午07:12:49
 * 
 */
public class PercentageUtils {

	public static final PercentageUtils INSTANCE = new PercentageUtils();

	public static Percentage cal(Number a, Number b) {
		return new Percentage(a.doubleValue() / b.doubleValue());
	}

}
