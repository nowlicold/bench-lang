/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.parameter.converter;


import com.bench.lang.base.parameter.ParameterTypeEnum;

import java.util.Map;

/**
 * 
 * @author chenbug
 * 
 * @version $Id: CommonParameterValueConverter.java, v 0.1 2013年4月22日 下午8:18:08
 *          chenbug Exp $
 */
public class CommonParameterValueConverter implements ParameterValueConverter {

	@Override
	public ParameterTypeEnum getSupportType() {
		// TODO Auto-generated method stub
		return ParameterTypeEnum.COMMON;
	}

	@Override
	public Object convertValue(String value, Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return value;
	}

}
