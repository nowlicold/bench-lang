package com.bench.lang.base.object;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.bench.lang.base.string.build.ToStringBuilder;

/**
 * 
 * 
 * @author cold
 * 
 * @version $Id: DoubleObject.java, v 0.1 2011-11-15 下午10:21:40 cold Exp $
 */
public class DoubleObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7844050559869347450L;

	private double value;

	public DoubleObject(double value) {
		super();
		this.value = value;
	}

	public double increase() {
		value++;
		return value;
	}

	public double decrease() {
		value--;
		return value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double add(double value) {
		this.value += value;
		return this.value;
	}

	public double substract(double value) {
		this.value -= value;
		return this.value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
