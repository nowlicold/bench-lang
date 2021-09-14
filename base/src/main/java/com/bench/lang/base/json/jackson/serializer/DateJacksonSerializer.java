package com.bench.lang.base.json.jackson.serializer;

import com.bench.lang.base.date.utils.DateUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * json日期序列化
 * 
 * @author cold
 *
 * @version $Id: DateJacksonSerializer.java, v 0.1 2017年12月27日 下午12:22:32
 *          cold Exp $
 */
public class DateJacksonSerializer extends JsonSerializer<Date> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object,
	 * com.fasterxml.jackson.core.JsonGenerator,
	 * com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		gen.writeString(DateUtils.getNewFormatDateString((Date) value));
	}

}
