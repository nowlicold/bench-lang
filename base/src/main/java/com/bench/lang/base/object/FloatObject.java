package com.bench.lang.base.object;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.bench.lang.base.string.build.ToStringBuilder;

/**
 * 
 * 
 * @author cold
 * 
 * @version $Id: FloatObject.java, v 0.1 2011-11-15 下午10:21:40 cold Exp $
 */
public class FloatObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7801784632902502404L;
	private float value;

	public FloatObject(float value) {
		super();
		this.value = value;
	}

	public float increase() {
		value++;
		return value;
	}

	public float decrease() {
		value--;
		return value;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public float add(float value) {
		this.value += value;
		return this.value;
	}

	public float substract(float value) {
		this.value -= value;
		return this.value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
