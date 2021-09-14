/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson.serializer;

import com.bench.lang.base.clasz.field.utils.FieldUtils;
import com.bench.lang.base.json.user.MoneyLevelString;
import com.bench.lang.base.money.Money;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 
 * @author cold
 *
 * @version $Id: MoneyJacksonSerializer.java, v 0.1 2016年2月29日 下午5:33:42 cold Exp $
 */
public class MoneyJacksonSerializer extends JsonSerializer<Money> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object, org.codehaus.jackson.JsonGenerator, org.codehaus.jackson.map.SerializerProvider)
	 */
	@Override
	public void serialize(Money value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		if (jgen.getCurrentValue() != null) {
			try {
				Field f = FieldUtils.getField(jgen.getOutputContext().getCurrentValue().getClass(), jgen.getOutputContext().getCurrentName());
				if (f != null && f.getAnnotation(MoneyLevelString.class) != null) {
					jgen.writeString(((Money) value).toLevelString());
					return;
				}
			} catch (NoSuchFieldException e) {

			}
		}
		jgen.writeNumber(value.getAmount());
	}
}
