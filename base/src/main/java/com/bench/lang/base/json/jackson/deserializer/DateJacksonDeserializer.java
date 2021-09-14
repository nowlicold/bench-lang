/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson.deserializer;

import com.bench.lang.base.date.utils.DateUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * 
 * @author cold
 *
 * @version $Id: DateJacksonDeserializer.java, v 0.1 2017年12月27日 下午12:22:21 cold Exp $
 */
public class DateJacksonDeserializer extends JsonDeserializer<Date> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml .jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		JsonToken t = p.getCurrentToken();
		if (t == JsonToken.VALUE_STRING) {
			String str = p.getText().trim();
			return DateUtils.parseDateNewFormat(str);
		}
		return null;
	}

}
