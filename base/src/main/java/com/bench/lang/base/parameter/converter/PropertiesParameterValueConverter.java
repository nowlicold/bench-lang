/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.parameter.converter;

import com.bench.lang.base.parameter.ParameterTypeEnum;
import com.bench.lang.base.properties.utils.PropertiesUtils;

import java.util.Map;

/**
 * 
 * @author cold
 * 
 * @version $Id: PropertiesParameterValueConverter.java, v 0.1 2013年4月22日
 *          下午8:23:10 cold Exp $
 */
public class PropertiesParameterValueConverter implements ParameterValueConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.lang.parameter.converter.ParameterValueConverter#
	 * getSupportType()
	 */
	@Override
	public ParameterTypeEnum getSupportType() {
		// TODO Auto-generated method stub
		return ParameterTypeEnum.PROPERTIES;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.lang.parameter.converter.ParameterValueConverter#
	 * convertValue(java.lang.String)
	 */
	@Override
	public Object convertValue(String value, Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return PropertiesUtils.restoreMap(value);
	}

}
