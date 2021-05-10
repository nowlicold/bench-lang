package com.bench.lang.base.model.basic;

import com.bench.lang.base.string.build.ToStringObject;

/**
 * 最小最大值
 * 
 * @author cold
 *
 * @version $Id: MinMax.java, v 0.1 2019年7月16日 下午12:07:24 cold Exp $
 */
public class MinMax extends ToStringObject {

	private int min;

	private boolean includeMin = true;

	private int max;

	private boolean includeMax = true;

	public MinMax(int min, int max) {
		super();
		this.min = min;
		this.max = max;
	}

	public MinMax() {
		super();
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public boolean isIncludeMin() {
		return includeMin;
	}

	public void setIncludeMin(boolean includeMin) {
		this.includeMin = includeMin;
	}

	public boolean isIncludeMax() {
		return includeMax;
	}

	public void setIncludeMax(boolean includeMax) {
		this.includeMax = includeMax;
	}
}
