/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.parameter.converter;


import com.bench.lang.base.json.jackson.JacksonUtils;
import com.bench.lang.base.parameter.ParameterTypeEnum;

import java.util.Map;

/**
 * 
 * @author chenbug
 * 
 * @version $Id: JsonParameterValueConverter.java, v 0.1 2013年4月22日 下午8:22:08
 *          chenbug Exp $
 */
public class JsonParameterValueConverter implements ParameterValueConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.lang.parameter.converter.ParameterValueConverter#
	 * getSupportType()
	 */
	@Override
	public ParameterTypeEnum getSupportType() {
		// TODO Auto-generated method stub
		return ParameterTypeEnum.JSON;
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
		return JacksonUtils.parseJson(value);

	}

}
