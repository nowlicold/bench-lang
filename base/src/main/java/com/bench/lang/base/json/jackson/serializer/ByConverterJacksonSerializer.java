/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.jackson.serializer;

import com.bench.lang.base.json.value.serializer.JSONSerializer;
import com.bench.lang.base.json.value.serializer.Serializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;

import java.io.IOException;

/**
 * 
 * @author chenbug
 *
 * @version $Id: ByConvererJacksonSerializer.java, v 0.1 2016年3月1日 下午6:28:45
 *          chenbug Exp $
 */
public class ByConverterJacksonSerializer extends JsonSerializer<Object> {

	private Serializer converterAnno;

	private Annotated member;

	private JSONSerializer converter;

	public ByConverterJacksonSerializer(Serializer converterAnno, Annotated member) {
		super();
		this.converterAnno = converterAnno;
		this.member = member;
		try {
			converter = converterAnno.value().newInstance();
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "不正确的converterAnno，converterAnno=" + converterAnno, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object,
	 * com.fasterxml.jackson.core.JsonGenerator,
	 * com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		Object convertedValue = converter.serialize(value);
		gen.writeObject(convertedValue);
	}

	/**
	 * @return Returns the converterAnno.
	 */
	public Serializer getConverterAnno() {
		return converterAnno;
	}

	/**
	 * @return Returns the member.
	 */
	public Annotated getMember() {
		return member;
	}

	/**
	 * @return Returns the converter.
	 */
	public JSONSerializer getConverter() {
		return converter;
	}

}
