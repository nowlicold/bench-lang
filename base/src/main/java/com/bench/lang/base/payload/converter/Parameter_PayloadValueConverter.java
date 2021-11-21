/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.payload.converter;
import com.bench.lang.base.parameter.Parameter;
import com.bench.lang.base.parameter.ParameterTypeEnum;
import com.bench.lang.base.payload.Payload;
import com.bench.lang.base.payload.PayloadConverter;
import org.dom4j.Element;

/**
 * 参数转换
 * 
 * @author cold
 * 
 * @version $Id: Date_PayloadValueConverter.java, v 0.1 2014-8-6 上午10:59:39
 *          cold Exp $
 */
public class Parameter_PayloadValueConverter implements PayloadValueConverter<Parameter> {

	private static final String ATTRIBUTE_GLOBAL_NAME = "globalName";

	/**
	 * parameter元素
	 */
	public static final String PARAMETER_ELEMENT_NAME = "parameter";

	@Override
	public void toXml(Payload payload, Element dataElement, String name, Parameter value) {
		// TODO Auto-generated method stub
		Element parameterElm = dataElement.addElement(PARAMETER_ELEMENT_NAME);
		setParameterToXml(value, parameterElm);
	}

	public static void setParameterToXml(Parameter value, Element parameterElm) {
		parameterElm.addAttribute(ATTRIBUTE_GLOBAL_NAME, value.getGlobalName());
		parameterElm.addAttribute(PayloadConverter.ATTRIBUTE_NAME, value.getName());
		parameterElm.addAttribute(PayloadConverter.ATTRIBUTE_TYPE, value.getType().name());
		parameterElm.addCDATA(value.getValue());
	}

	@Override
	public Parameter fromXml(Payload payload, Element dataElm, Element valueElm) {
		// TODO Auto-generated method stub
		return parseParameterFromXml(valueElm);
	}

	public static Parameter parseParameterFromXml(Element valueElm) {
		Parameter parameter = new Parameter();
		parameter.setGlobalName(valueElm.attributeValue(ATTRIBUTE_GLOBAL_NAME));
		parameter.setName(valueElm.attributeValue(PayloadConverter.ATTRIBUTE_NAME));
		parameter
				.setType(ParameterTypeEnum.valueOf(valueElm.attributeValue(PayloadConverter.ATTRIBUTE_TYPE)));
		parameter.setValue(valueElm.getText());
		return parameter;
	}

	@Override
	public String getElementName() {
		// TODO Auto-generated method stub
		return PARAMETER_ELEMENT_NAME;
	}

}
