/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.payload.converter;

import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.payload.Payload;
import com.bench.lang.base.payload.PayloadConverter;
import com.bench.lang.base.string.utils.StringUtils;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Map转换
 * 
 * @author chenbug
 * 
 * @version $Id: Date_PayloadValueConverter.java, v 0.1 2014-8-6 上午10:59:39
 *          chenbug Exp $
 */
public class Map_PayloadValueConverter implements PayloadValueConverter<Map<String, Object>> {

	/**
	 * map元素
	 */
	public static final String MAP_ELEMENT_NAME = "map";

	@Override
	public void toXml(Payload payload, Element dataElement, String name, Map<String, Object> value) {
		// TODO Auto-generated method stub
		Element mapElm = dataElement.addElement(MAP_ELEMENT_NAME);
		for (Map.Entry<String, Object> valueEntry : value.entrySet()) {
			Element mapEntryElm = mapElm.addElement(PayloadConverter.ELEMENT_ENTRY);
			mapEntryElm.addAttribute(PayloadConverter.ATTRIBUTE_KEY, StringUtils.trim(ObjectUtils.toString(valueEntry.getKey())));
			//若包含换行则使用setText(为了适配map)，不包含则setAttribute
			if (StringUtils.indexOfAny(ObjectUtils.toString(valueEntry.getValue()), new String[] { "\r", "\n" }) > 0) {
				mapEntryElm.addElement("value").setText(StringUtils.trim(ObjectUtils.toString(valueEntry.getValue())));
			}else{
				mapEntryElm.addAttribute(PayloadConverter.ATTRIBUTE_VALUE, StringUtils.trim(ObjectUtils.toString(valueEntry.getValue())));
			}
		}

	}

	@Override
	public Map<String, Object> fromXml(Payload payload, Element dataElm, Element valueElm) {
		// TODO Auto-generated method stub
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<?> mapEntryElmList = valueElm.selectNodes(PayloadConverter.ELEMENT_ENTRY);
		for (Object entryElmObj : mapEntryElmList) {
			Element entryElm = (Element) entryElmObj;
			Element enryValueElm = entryElm.element("value");
			if (enryValueElm == null) {
				dataMap.put(StringUtils.trim(entryElm.attributeValue(PayloadConverter.ATTRIBUTE_KEY)),
						StringUtils.trim(entryElm.attributeValue(PayloadConverter.ATTRIBUTE_VALUE)));
			} else {
				dataMap.put(StringUtils.trim(entryElm.attributeValue(PayloadConverter.ATTRIBUTE_KEY)), StringUtils.trim(enryValueElm.getText()));
			}
		}
		return dataMap;
	}

	@Override
	public String getElementName() {
		// TODO Auto-generated method stub
		return MAP_ELEMENT_NAME;
	}

}
