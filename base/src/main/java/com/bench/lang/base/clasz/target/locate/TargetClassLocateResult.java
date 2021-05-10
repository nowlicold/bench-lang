package com.bench.lang.base.clasz.target.locate;

import com.bench.lang.base.string.build.ToStringObject;

/**
 * 目标class定位结果
 * 
 * @author cold
 *
 * @version $Id: TargetClassLocateResult.java, v 0.1 2020年6月12日 上午9:51:58 cold Exp $
 */
public class TargetClassLocateResult extends ToStringObject {

	private Class<?> targetClass;

	private Object targetObject;

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	public Object getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}

}
