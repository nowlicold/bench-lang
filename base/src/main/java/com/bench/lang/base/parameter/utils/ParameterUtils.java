package com.bench.lang.base.parameter.utils;


import com.bench.lang.base.parameter.Parameter;
import com.bench.lang.base.parameter.ParameterTypeEnum;
import com.bench.lang.base.parameter.converter.CommonParameterValueConverter;
import com.bench.lang.base.parameter.converter.JsonParameterValueConverter;
import com.bench.lang.base.parameter.converter.ParameterValueConverter;
import com.bench.lang.base.parameter.converter.PropertiesParameterValueConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 参数工具类
 * 
 * @author cold
 * 
 * @version $Id: ParameterUtils.java, v 0.1 2013-1-12 下午2:03:05 cold Exp $
 */
public class ParameterUtils {

	private static final Logger log = LoggerFactory.getLogger(ParameterUtils.class);

	private static Map<ParameterTypeEnum, ParameterValueConverter> paramterValueConverterMap = new HashMap<ParameterTypeEnum, ParameterValueConverter>();

	static {
		paramterValueConverterMap.put(ParameterTypeEnum.COMMON, new CommonParameterValueConverter());
		paramterValueConverterMap.put(ParameterTypeEnum.JSON, new JsonParameterValueConverter());
		paramterValueConverterMap.put(ParameterTypeEnum.PROPERTIES, new PropertiesParameterValueConverter());
	}

	/**
	 * 注册Converter
	 * 
	 * @param converter
	 */
	public static void registerConverter(ParameterValueConverter converter) {
		paramterValueConverterMap.put(converter.getSupportType(), converter);
	}

	/**
	 * 转换参数值
	 * 
	 * @param value
	 * @param type
	 * @param parameters
	 * @return
	 */
	public static Object convertValue(String value, ParameterTypeEnum type, Map<String, Object> parameters) {
		ParameterValueConverter converter = paramterValueConverterMap.get(type);
		if (converter != null) {
			return converter.convertValue(value, parameters);
		}
		log.error("不支持的类型");
		return value;
	}

	/**
	 * 从集合中移除相同名字的参数，返回移除的参数集合
	 * 
	 * @param parameters
	 * @return
	 */
	public static List<Parameter> removeSameName(List<Parameter> parameters) {
		if (parameters == null)
			return null;
		Set<String> parameterNameSet = new HashSet<String>();
		List<Parameter> removedList = new ArrayList<Parameter>();
		for (Parameter parameter : parameters) {
			if (parameterNameSet.contains(parameter.getName())) {
				removedList.add(parameter);
			} else {
				parameterNameSet.add(parameter.getName());
			}
		}
		parameters.removeAll(removedList);
		return removedList;
	}
}
