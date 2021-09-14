/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson.deserializer;

import com.bench.lang.base.money.Money;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 
 * @author cold
 *
 * @version $Id: MoneyJacksonDeserializer.java, v 0.1 2017年12月27日 下午12:22:21 cold Exp $
 */
public class MoneyJacksonDeserializer extends JsonDeserializer<Money> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml .jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public Money deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		String str = p.getText().trim();
		if (!StringUtils.isEmpty(str)) {
			return new Money(str);
		}
		return null;
	}

}
