/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson.deserializer;

import com.bench.lang.base.enums.utils.EnumUtils;
import com.bench.lang.base.string.utils.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.bench.common.enums.EnumBase;
import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;

import java.io.IOException;

/**
 * 
 * @author chenbug
 *
 * @version $Id: EnumBaseJacksonDeserializer.java, v 0.1 2017年12月27日 下午12:22:21 chenbug Exp $
 */
public class EnumBaseJacksonDeserializer extends AbstractJacksonDeserializer<EnumBase> {

	public EnumBaseJacksonDeserializer(BeanDescription beanDescription) {
		super(beanDescription);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml .jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public EnumBase deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		JsonToken token = p.getCurrentToken();
		String enumName = null;
		if (token == JsonToken.VALUE_STRING) {
			enumName = p.getText().trim();
		} else {
			token = p.nextToken();
			while (token != JsonToken.END_OBJECT) {
				if (token == JsonToken.VALUE_STRING && p.getCurrentName().equals("name")) {
					enumName = p.getText();
				}
				token = p.nextToken();
			}
		}
		// -换成_，因为java枚举名不支持-
		enumName = StringUtils.replace(enumName, StringUtils.SUB_SIGN, StringUtils.UNDERSCORE_SIGN);
		Enum enumObj = EnumUtils.valueOfIgnoreCase((Class<Enum>) this.beanDescription.getType().getRawClass(), enumName);
		if (enumObj != null) {
			return (EnumBase) enumObj;
		}
		throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "无法找到枚举值，class=" + this.beanDescription.getType().getRawClass() + ",name=" + enumName);
	}

}
