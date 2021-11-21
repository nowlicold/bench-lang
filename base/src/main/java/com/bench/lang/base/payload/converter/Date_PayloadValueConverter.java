/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.payload.converter;
import com.bench.lang.base.date.utils.DateUtils;
import com.bench.lang.base.payload.Payload;
import com.bench.lang.base.payload.PayloadConverter;
import org.dom4j.Element;

import java.util.Date;

/**
 * 日期转换
 * 
 * @author cold
 * 
 * @version $Id: Date_PayloadValueConverter.java, v 0.1 2014-8-6 上午10:59:39
 *          cold Exp $
 */
public class Date_PayloadValueConverter implements PayloadValueConverter<Date> {

	@Override
	public void toXml(Payload payload, Element dataElement, String name, Date value) {
		// TODO Auto-generated method stub
		dataElement.addAttribute(PayloadConverter.ATTRIBUTE_VALUE, DateUtils.getLongDateExtraString(value));
	}

	@Override
	public Date fromXml(Payload payload, Element dataElm, Element valueElm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getElementName() {
		// TODO Auto-generated method stub
		return null;
	}

}
