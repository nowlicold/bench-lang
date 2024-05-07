package com.bench.lang.base.json.jackson;

import com.bench.lang.base.bool.utils.BooleanUtils;
import com.bench.lang.base.date.utils.DateUtils;
import com.bench.lang.base.json.jackson.annotations.serializer.BenchFilterProvider;
import com.bench.lang.base.json.jackson.deserializer.BenchJacksonSimpleDeserializers;
import com.bench.lang.base.json.jackson.deserializer.DateJacksonDeserializer;
import com.bench.lang.base.json.jackson.deserializer.EnumBaseJacksonDeserializer;
import com.bench.lang.base.json.jackson.deserializer.MoneyJacksonDeserializer;
import com.bench.lang.base.json.jackson.introspector.BenchScriptAnnotationIntrospector;
import com.bench.lang.base.json.jackson.serializer.DateJacksonSerializer;
import com.bench.lang.base.json.jackson.serializer.EnumBaseJacksonSerializer;
import com.bench.lang.base.json.jackson.serializer.MoneyJacksonSerializer;
import com.bench.lang.base.model.basic.MinMax;
import com.bench.lang.base.money.Money;
import com.bench.lang.base.object.creater.ObjectCreater;
import com.bench.lang.base.string.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.bench.common.enums.EnumBase;
import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;
import org.apache.commons.lang3.RandomUtils;

import java.io.StringWriter;
import java.util.*;

/**
 * jackson工具类<br>
 * 查找顺序：<br>
 * 1、注解<br>
 * 2、类型<br>
 * 以下情况会加到到json数据<br>
 * 1、含有共有的get或者is方法<br>
 * 2、增加ScriptName和ScriptMethod的方法<br>
 * 
 * @author cold
 *
 * @version $Id: JackSonUtils.java, v 0.1 2016年2月29日 下午5:41:19 cold Exp $
 */
public class SnakeCaseJacksonUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();

	static {
		SimpleModule simpleModule = new SimpleModule(SimpleModule.class.getName(), Version.unknownVersion());
		BenchJacksonSimpleDeserializers benchJacksonSimpleDeserializers = new BenchJacksonSimpleDeserializers();
		simpleModule.setDeserializers(benchJacksonSimpleDeserializers);
		benchJacksonSimpleDeserializers.addDeserializerClass(EnumBase.class, EnumBaseJacksonDeserializer.class);
		simpleModule.addDeserializer(Date.class, new DateJacksonDeserializer());
		simpleModule.addDeserializer(Money.class, new MoneyJacksonDeserializer());

		simpleModule.addSerializer(Money.class, new MoneyJacksonSerializer());
		simpleModule.addSerializer(EnumBase.class, new EnumBaseJacksonSerializer());
		simpleModule.addSerializer(Date.class, new DateJacksonSerializer());

		objectMapper.registerModule(simpleModule);
		objectMapper.setAnnotationIntrospector(new BenchScriptAnnotationIntrospector());
		objectMapper.setSerializerProvider(new BenchJacksonSerializerProvider());
		objectMapper.setVisibility(new BenchJacksonVisibilityChecker(Visibility.DEFAULT));
		objectMapper.setFilterProvider(new BenchFilterProvider());
		objectMapper.setSerializationInclusion(Include.NON_NULL);

		// 忽略未知的属性
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		// 下划线风格
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	}

	public static String toJson(Object object) {
		return toJson(object, false);
	}

	public static String toJson(Object object, boolean pretty) {
		if (object == null) {
			return null;
		}
		try (StringWriter sw = new StringWriter();) {
			if (pretty) {
				objectMapper.writerWithDefaultPrettyPrinter().writeValue(sw, object);
			} else {
				objectMapper.writeValue(sw, object);
			}

			return sw.toString();
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "构造json字符串异常,object=" + object, e);
		}
	}

	/**
	 * 转换成json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static JsonNode toJsonNode(Object object) {
		return objectMapper.valueToTree(object);
	}

	/**
	 * @param object
	 * @param objectClass
	 * @return
	 */
	public static <T> T parseJson(JsonNode object, Class<T> objectClass) {
		try {
			return objectMapper.treeToValue(object, objectClass);
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "解析json字符串到java对象异常,json=" + object + ",objectClass=" + objectClass, e);
		}
	}

	/**
	 * @param node
	 * @param fieldName
	 * @return
	 * @deprecated 使用getStringValue
	 */
	public static String getFieldValueAsStringSafe(JsonNode node, String fieldName) {
		JsonNode fieldNode = node.get(fieldName);
		return toString(fieldNode);
	}

	/**
	 * @param node
	 * @param fieldName
	 * @return
	 */
	public static String getStringValue(JsonNode node, String fieldName) {
		JsonNode fieldNode = node.get(fieldName);
		return toString(fieldNode);
	}

	/**
	 * @param node
	 * @param fieldName
	 * @return
	 */
	public static Date getDateValue(JsonNode node, String fieldName) {
		String value = getStringValue(node, fieldName);
		return StringUtils.isEmpty(value) ? null : DateUtils.parseDateNewFormat(value);
	}

	public static Integer getIntegerValue(JsonNode node, String fieldName) {
		String value = getStringValue(node, fieldName);
		return StringUtils.isEmpty(value) ? null : Integer.parseInt(value);
	}

	public static int getIntValue(JsonNode node, String fieldName, int defaultValue) {
		String value = getStringValue(node, fieldName);
		return StringUtils.isEmpty(value) ? defaultValue : Integer.parseInt(value);
	}

	public static Double getDoubleValue(JsonNode node, String fieldName) {
		String value = getStringValue(node, fieldName);
		return StringUtils.isEmpty(value) ? null : Double.parseDouble(value);
	}

	public static double getDoubleValue(JsonNode node, String fieldName, double defaultValue) {
		String value = getStringValue(node, fieldName);
		return StringUtils.isEmpty(value) ? defaultValue : Double.parseDouble(value);
	}

	public static Long getLongValue(JsonNode node, String fieldName) {
		String value = getStringValue(node, fieldName);
		return StringUtils.isEmpty(value) ? null : Long.parseLong(value);
	}

	public static long getLongValue(JsonNode node, String fieldName, long defaultValue) {
		String value = getStringValue(node, fieldName);
		return StringUtils.isEmpty(value) ? defaultValue : Long.parseLong(value);
	}

	public static Boolean getBooleanValue(JsonNode node, String fieldName) {
		String value = getStringValue(node, fieldName);
		return StringUtils.isEmpty(value) ? null : BooleanUtils.toBooleanObject(value);
	}

	public static boolean getBoolValue(JsonNode node, String fieldName, boolean defaultValue) {
		String value = getStringValue(node, fieldName);
		return StringUtils.isEmpty(value) ? defaultValue : BooleanUtils.toBoolean(value, defaultValue);
	}

	private static String toString(JsonNode fieldNode) {
		return fieldNode == null || fieldNode instanceof NullNode ? null : fieldNode.asText();
	}

	/**
	 * 设置属性值
	 * 
	 * @param node
	 * @param fieldName
	 * @param value
	 */
	public static void setNoneNullValue(ObjectNode node, String fieldName, Integer value) {
		if (value == null) {
			return;
		}
		node.put(fieldName, value);
	}

	/**
	 * 设置属性值
	 * 
	 * @param node
	 * @param fieldName
	 * @param value
	 */
	public static void setNoneNullValue(ObjectNode node, String fieldName, Double value) {
		if (value == null) {
			return;
		}
		node.put(fieldName, value);
	}

	/**
	 * 设置属性值
	 * 
	 * @param node
	 * @param fieldName
	 * @param value
	 */
	public static void setNoneNullValue(ObjectNode node, String fieldName, Long value) {
		if (value == null) {
			return;
		}
		node.put(fieldName, value);
	}

	/**
	 * 设置属性值
	 * 
	 * @param node
	 * @param fieldName
	 * @param value
	 */
	public static void setNoneNullValue(ObjectNode node, String fieldName, Boolean value) {
		if (value == null) {
			return;
		}
		node.put(fieldName, value);
	}

	/**
	 * 设置属性值
	 * 
	 * @param node
	 * @param fieldName
	 * @param values
	 */
	public static void setStringValues(ObjectNode node, String fieldName, List<String> values) {
		ArrayNode arrayNode = node.putArray(fieldName);
		if (values != null) {
			for (String value : values) {
				arrayNode.add(value);
			}
		}
	}

	/**
	 * 设置属性值
	 * 
	 * @param node
	 * @param fieldName
	 * @param values
	 */
	public static void setStringValues(ObjectNode node, String fieldName, String[] values) {
		ArrayNode arrayNode = node.putArray(fieldName);
		if (values != null) {
			for (String value : values) {
				arrayNode.add(value);
			}
		}
	}

	/**
	 * 设置属性值
	 * 
	 * @param node
	 * @param fieldName
	 * @param value
	 */
	public static void setDateValue(ObjectNode node, String fieldName, Date value) {
		node.put(fieldName, DateUtils.getNewFormatDateString(value));
	}

	/**
	 * 设置属性值
	 * 
	 * @param node
	 * @param fieldName
	 * @param values
	 */
	public static void setIntegerValues(ObjectNode node, String fieldName, List<Integer> values) {
		ArrayNode arrayNode = node.putArray(fieldName);
		if (values != null) {
			for (Integer value : values) {
				arrayNode.add(value);
			}
		}
	}

	/**
	 * 设置属性值
	 * 
	 * @param node
	 * @param fieldName
	 * @param values
	 */
	public static void setLongValues(ObjectNode node, String fieldName, List<Long> values) {
		ArrayNode arrayNode = node.putArray(fieldName);
		if (values != null) {
			for (Long value : values) {
				arrayNode.add(value);
			}
		}
	}

	/**
	 * 设置属性值
	 * 
	 * @param node
	 * @param fieldName
	 * @param valueMap
	 */
	public static void setStringMapValue(ObjectNode node, String fieldName, Map<String, String> valueMap) {
		ObjectNode objectNode = node.putObject(fieldName);
		if (valueMap != null) {
			for (Map.Entry<String, String> entry : valueMap.entrySet()) {
				objectNode.put(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * 将json转成clasz实例
	 * 
	 * @param content
	 * @param clasz
	 * @return
	 */
	public static <T> T parseJson(String content, Class<T> clasz) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		try {
			return objectMapper.readValue(content, clasz);
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "解析json字符串异常,content=" + content, e);
		}
	}

	public static List<String> getStringValues(JsonNode node, String fieldName) {
		JsonNode dataNode = node.get(fieldName);
		if (dataNode == null) {
			return null;
		}
		if (dataNode instanceof NullNode) {
			return null;
		}
		List<String> values = new ArrayList<String>();
		ArrayNode arrayNode = (ArrayNode) dataNode;
		for (int i = 0; i < arrayNode.size(); i++) {
			values.add(toString(arrayNode.get(i)));
		}
		return values;
	}

	/**
	 * 得到字符串的kv Map
	 * 
	 * @param node
	 * @param fieldName
	 * @return
	 */
	public static Map<String, String> getStringMapValue(JsonNode node, String fieldName) {
		JsonNode objectNode = node.get(fieldName);
		if (objectNode == null) {
			return null;
		}
		if (objectNode instanceof NullNode) {
			return null;
		}
		return getStringMapValue(objectNode);
	}

	/**
	 * 得到字符串的kv Map
	 * 
	 * @param node
	 * @return
	 */
	public static Map<String, String> getStringMapValue(JsonNode node) {
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator<Map.Entry<String, JsonNode>> nodeIterator = node.fields();
		while (nodeIterator.hasNext()) {
			Map.Entry<String, JsonNode> entry = nodeIterator.next();
			returnMap.put(entry.getKey(), toString(entry.getValue()));
		}
		return returnMap;
	}

	public static JsonNode parseJson(String content) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		try {
			return objectMapper.readTree(content);
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "解析json字符串异常,content=" + content, e);
		}
	}

	/**
	 * 将json字符串转换为kvmap
	 * 
	 * @param content
	 * @return
	 */
	public static Map<String, String> toStringMap(String content) {
		JsonNode jsonNode = SnakeCaseJacksonUtils.parseJson(content);
		return toMap(jsonNode);
	}

	public static Map<String, String> toMap(JsonNode jsonNode) {
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator<Map.Entry<String, JsonNode>> ite = jsonNode.fields();
		while (ite.hasNext()) {
			Map.Entry<String, JsonNode> child = ite.next();
			returnMap.put(child.getKey(), child.getValue().asText());
		}
		return returnMap;
	}

	/**
	 * 返回objectMapper
	 * 
	 * @return
	 */
	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	/**
	 * 创建对象节点
	 * 
	 * @return
	 */
	public static ObjectNode createObjectNode() {
		return objectMapper.createObjectNode();
	}

	/**
	 * 创建数组节点
	 * 
	 * @return
	 */
	public static ArrayNode createArrayNode() {
		return objectMapper.createArrayNode();
	}

	/**
	 * 返回jsonFactory
	 * 
	 * @return
	 */
	public static JsonFactory getFactory() {
		return objectMapper.getFactory();
	}

	/**
	 * 将当前object加入数组
	 * 
	 * @param arrayNode
	 * @param value
	 */
	public static void add(ArrayNode arrayNode, Object value) {
		arrayNode.addPOJO(value);
	}

	/**
	 * 
	 * @param rootNode
	 * @param key
	 * @param value
	 */
	public static void add(ObjectNode rootNode, String key, Object value) {
		rootNode.putPOJO(key, value);
	}

	/**
	 * 
	 * @param parentNode
	 * @param currentDimension
	 * @param lengths
	 * @param creater
	 */
	private static <OBJ extends Object> void createChildMultiDimensionArrayNode(ArrayNode parentNode, int currentDimension, List<MinMax> lengths,
			ObjectCreater<OBJ> creater) {
		MinMax length = lengths.get(currentDimension);
		int arrayLength = RandomUtils.nextInt(length.getMin(), length.getMax());
		for (int i = 0; i < arrayLength; i++) {
			// 如果是最后一个维度，则生成Object列表
			if (lengths.size() == currentDimension + 1) {
				SnakeCaseJacksonUtils.add(parentNode, creater.create());
				continue;
			}
			ArrayNode childArrayNode = SnakeCaseJacksonUtils.createArrayNode();
			parentNode.add(childArrayNode);
			int childDimension = currentDimension + 1;
			if (childDimension < lengths.size()) {
				createChildMultiDimensionArrayNode(childArrayNode, childDimension, lengths, creater);
			}
		}
	}

	/**
	 * 产生一个ArrayNode，维度为dimensionLength的长度，每个维度元素为minMax个
	 * 
	 * @param dimensionLength
	 * @param creater
	 * @return
	 */
	public static <OBJ extends Object> ArrayNode createMultiDimensionArrayNode(List<MinMax> dimensionLength, ObjectCreater<OBJ> creater) {
		ArrayNode rootNode = SnakeCaseJacksonUtils.createArrayNode();
		createChildMultiDimensionArrayNode(rootNode, 0, dimensionLength, creater);
		return rootNode;
	}
}
