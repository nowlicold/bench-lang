package com.bench.lang.base.parameter.converter;


import com.bench.lang.base.parameter.ParameterTypeEnum;

import java.util.Map;

/**
 * 参数值转换器
 * 
 * @author cold
 * 
 * @version $Id: ParameterValueConverter.java, v 0.1 2013年4月22日 下午7:44:56
 *          cold Exp $
 */
public interface ParameterValueConverter {

	/**
	 * 得到支持的类型
	 * 
	 * @return
	 */
	public ParameterTypeEnum getSupportType();

	/**
	 * 转换value为对象
	 * 
	 * @param value
	 * @param parameters
	 * @return
	 */
	public Object convertValue(String value, Map<String, Object> parameters);
}
