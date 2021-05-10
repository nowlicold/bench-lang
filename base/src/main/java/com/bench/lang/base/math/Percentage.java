package com.bench.lang.base.math;

import com.bench.lang.base.math.utils.MathUtils;
import com.bench.lang.base.math.utils.PercentageUtils;

/**
 * 百分比
 * 
 * @author cold 2009-12-20 下午07:12:49
 * 
 */
public class Percentage {

	private Number rate;

	public Percentage(Number rate) {
		super();
		this.rate = rate;
	}

	public static Percentage cal(Number a, Number b) {
		return PercentageUtils.cal(a, b);
	}

	/**
	 * 小数点后保留几位
	 * 
	 * @param reserve
	 * @return
	 */
	public String toString(int scale) {
		return MathUtils.percentage(rate, scale);

	}

	/**
	 * @return the rate
	 */
	public Number getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(Number rate) {
		this.rate = rate;
	}
 
}
