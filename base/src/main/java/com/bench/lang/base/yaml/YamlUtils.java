package com.bench.lang.base.yaml;

import com.bench.lang.base.json.jackson.BenchJacksonSerializerProvider;
import com.bench.lang.base.json.jackson.BenchJacksonVisibilityChecker;
import com.bench.lang.base.json.jackson.annotations.serializer.BenchFilterProvider;
import com.bench.lang.base.json.jackson.deserializer.BenchJacksonSimpleDeserializers;
import com.bench.lang.base.json.jackson.deserializer.DateJacksonDeserializer;
import com.bench.lang.base.json.jackson.deserializer.EnumBaseJacksonDeserializer;
import com.bench.lang.base.json.jackson.introspector.BenchScriptAnnotationIntrospector;
import com.bench.lang.base.json.jackson.serializer.DateJacksonSerializer;
import com.bench.lang.base.json.jackson.serializer.EnumBaseJacksonSerializer;
import com.bench.lang.base.json.jackson.serializer.MoneyJacksonSerializer;
import com.bench.lang.base.money.Money;
import com.bench.lang.base.string.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import com.bench.common.enums.EnumBase;
import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;

import java.io.StringWriter;
import java.util.Date;

public class YamlUtils {

	private static YAMLFactory yamlFactory = new YAMLFactory();

	private static YAMLMapper mapper = new YAMLMapper();

	static {
		SimpleModule simpleModule = new SimpleModule(SimpleModule.class.getName(), Version.unknownVersion());

		BenchJacksonSimpleDeserializers benchJacksonSimpleDeserializers = new BenchJacksonSimpleDeserializers();
		simpleModule.setDeserializers(benchJacksonSimpleDeserializers);
		benchJacksonSimpleDeserializers.addDeserializerClass(EnumBase.class, EnumBaseJacksonDeserializer.class);
		simpleModule.addSerializer(Money.class, new MoneyJacksonSerializer());
		simpleModule.addSerializer(EnumBase.class, new EnumBaseJacksonSerializer());
		simpleModule.addSerializer(Date.class, new DateJacksonSerializer());
		simpleModule.addDeserializer(Date.class, new DateJacksonDeserializer());
		mapper.registerModule(simpleModule);
		mapper.setAnnotationIntrospector(new BenchScriptAnnotationIntrospector());
		mapper.setSerializerProvider(new BenchJacksonSerializerProvider());
		mapper.setVisibility(new BenchJacksonVisibilityChecker(Visibility.DEFAULT));
		mapper.setFilterProvider(new BenchFilterProvider());
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

	}

	/**
	 * 解析成jsonNode
	 * 
	 * @param content
	 * @return
	 */
	public static JsonNode parse(String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		try {
			YAMLParser yamlParser = yamlFactory.createParser(content);
			return mapper.readTree(yamlParser);
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "解析yaml文本异常,content=" + content, e);
		}
	}

	/**
	 * 解析成对象T
	 * 
	 * @param content
	 * @return
	 */
	public static <T> T parse(String content, Class<T> clasz) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		try {
			YAMLParser yamlParser = yamlFactory.createParser(content);
			return mapper.readValue(yamlParser, clasz);
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "解析yaml文本异常,content=" + content, e);
		}
	}

	/**
	 * 转换成字符串对象
	 * 
	 * @param object
	 * @return
	 */
	public static String toYamlString(Object object) {
		if (object == null) {
			return null;
		}
		try (StringWriter sw = new StringWriter();) {
			mapper.writeValue(sw, object);
			return sw.toString();
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "转成yaml文本异常,object=" + object, e);
		}
	}
}
