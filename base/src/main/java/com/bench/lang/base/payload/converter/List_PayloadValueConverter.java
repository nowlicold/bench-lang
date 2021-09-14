/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.payload.converter;

import com.bench.lang.base.parameter.Parameter;
import com.bench.lang.base.payload.Payload;
import com.bench.lang.base.payload.PayloadConverter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合转换
 * 
 * @author chenbug
 * 
 * @version $Id: Date_PayloadValueConverter.java, v 0.1 2014-8-6 上午10:59:39
 *          chenbug Exp $
 */
public class List_PayloadValueConverter implements PayloadValueConverter<List<Object>> {

	/**
	 * list元素
	 */
	public static final String LIST_ELEMENT_NAME = "list";

	@Override
	public void toXml(Payload payload, Element dataElement, String name, List<Object> value) {
		// TODO Auto-generated method stub
		Element listElm = dataElement.addElement(LIST_ELEMENT_NAME);
		for (Object singleValue : value) {
			if (singleValue == null) {
				Element valueElm = listElm.addElement(PayloadConverter.ELEMENT_VALUE);
				valueElm.setText(StringUtils.trim(ObjectUtils.toString(singleValue)));
				continue;
			}
			if (singleValue instanceof Parameter) {
				Element valueElm = listElm.addElement(Parameter_PayloadValueConverter.PARAMETER_ELEMENT_NAME);
				Parameter_PayloadValueConverter.setParameterToXml((Parameter) singleValue, valueElm);
				continue;
			} else {
				Element valueElm = listElm.addElement(PayloadConverter.ELEMENT_VALUE);
				valueElm.setText(StringUtils.trim(ObjectUtils.toString(singleValue)));
				continue;
			}
		}

	}

	@Override
	public List<Object> fromXml(Payload payload, Element dataElm, Element valueElm) {
		// TODO Auto-generated method stub
		List<Object> valueList = new ArrayList<Object>();
		List<?> valueElmList = valueElm.selectNodes(PayloadConverter.ELEMENT_VALUE);
		for (Object valueElmObj : valueElmList) {
			valueList.add(StringUtils.trim(((Element) valueElmObj).getText()));
		}
		List<?> parameterElmList = valueElm
				.selectNodes(Parameter_PayloadValueConverter.PARAMETER_ELEMENT_NAME);
		for (Object valueElmObj : parameterElmList) {
			valueList.add(Parameter_PayloadValueConverter.parseParameterFromXml((Element) valueElmObj));
		}
		return valueList;
	}

	@Override
	public String getElementName() {
		// TODO Auto-generated method stub
		return LIST_ELEMENT_NAME;
	}

}
