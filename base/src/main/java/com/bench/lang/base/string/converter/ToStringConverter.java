package com.bench.lang.base.string.converter;

/**
 * 将对象转换成String的Converter
 * 
 * @author cold
 * 
 * @version $Id: ToStringConverter.java, v 0.1 2013-1-17 下午1:51:59 cold Exp $
 */
public interface ToStringConverter<T> {

	public String convert(T t);

}
