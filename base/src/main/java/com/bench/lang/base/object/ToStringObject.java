package com.bench.lang.base.object;

/**
 * 转换成字符串对象
 * 
 * @author cold
 * 
 * @version $Id: ToStringObject.java, v 0.1 2014-9-9 下午6:49:05 cold Exp $
 */
public class ToStringObject {

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
