/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.payload.converter;

import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.payload.Payload;
import com.bench.lang.base.string.utils.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * 默认转换转换
 * 
 * @author cold
 * 
 * @version $Id: Default_PayloadValueConverter.java, v 0.1 2014-8-6 上午10:59:39
 *          cold Exp $
 */
public class Default_PayloadValueConverter implements PayloadValueConverter<Object> {

	@Override
	public void toXml(Payload payload, Element dataElement, String name, Object value) {
		// TODO Auto-generated method stub
		String stringValue = ObjectUtils.toString(value);
		if (StringUtils.indexOfAny(stringValue, StringUtils.LINE_SPLITER) < 0) {
			dataElement.addAttribute("value", stringValue);
		} else {
			dataElement.addCDATA(stringValue);
		}
	}

	@Override
	public Object fromXml(Payload payload, Element dataElm, Element valueElm) {
		// TODO Auto-generated method stub
		Attribute valueAttr = dataElm.attribute("value");
		if (valueAttr != null) {
			return valueAttr.getValue();
		} else {
			return dataElm.getText();
		}

	}

	@Override
	public String getElementName() {
		// TODO Auto-generated method stub
		return null;
	}
}
