/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.payload.converter;

import com.bench.lang.base.base64.utils.BASE64Utils;
import com.bench.lang.base.payload.Payload;
import org.dom4j.Element;

/**
 * 日期转换
 * 
 * @author cold
 * 
 * @version $Id: Date_PayloadValueConverter.java, v 0.1 2014-8-6 上午10:59:39
 *          cold Exp $
 */
public class Bytes_PayloadValueConverter implements PayloadValueConverter<byte[]> {

	public static final String BYTES_ELEMENT_NAME = "bytes";

	@Override
	public void toXml(Payload payload, Element dataElement, String name, byte[] value) {
		// TODO Auto-generated method stub
		dataElement.addElement(BYTES_ELEMENT_NAME).addCDATA(BASE64Utils.encode(value));
	}

	@Override
	public byte[] fromXml(Payload payload, Element dataElm, Element valueElm) {
		// TODO Auto-generated method stub
		return BASE64Utils.decodeToBytes(valueElm.getText());
	}

	@Override
	public String getElementName() {
		// TODO Auto-generated method stub
		return BYTES_ELEMENT_NAME;
	}

}
