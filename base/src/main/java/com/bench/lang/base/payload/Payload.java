package com.bench.lang.base.payload;
import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;
import com.bench.common.model.BaseModel;
import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.bean.utils.PropertyUtils;
import com.bench.lang.base.bool.utils.BooleanUtils;
import com.bench.lang.base.clasz.field.utils.FieldUtils;
import com.bench.lang.base.date.utils.DateUtils;
import com.bench.lang.base.json.jackson.JacksonUtils;
import com.bench.lang.base.list.utils.ListUtils;
import com.bench.lang.base.map.utils.MapUtils;
import com.bench.lang.base.money.Money;
import com.bench.lang.base.number.utils.NumberUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.parameter.Parameter;
import com.bench.lang.base.string.utils.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;

/**
 * 消息数据载体
 * 
 * @author chenbug
 * 
 * @version $Id: MessagePayload.java, v 0.1 2014-7-21 下午6:11:13 chenbug Exp $
 */
public class Payload extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8233487843920084679L;

	/**
	 * 消息数据
	 */
	protected Map<String, Object> datas = new HashMap<String, Object>();

	/**
	 * 返回所有包含nameStartsWith开头的name集合
	 * 
	 * @param nameStartsWith
	 * @return
	 */
	public List<String> getNamesStartsWith(String nameStartsWith) {
		List<String> returnList = new ArrayList<String>();
		for (String name : datas.keySet()) {
			if (StringUtils.startsWith(name, nameStartsWith)) {
				returnList.add(name);
			}
		}
		return returnList;
	}

	/**
	 * 转换为StringMap
	 * 
	 * @return
	 */
	public Map<String, String> toStringMap() {
		return MapUtils.convert(this.datas);
	}

	/**
	 * 返回所有包含keyContains 的name集合
	 * 
	 * @param keyContains
	 * @return
	 */
	public List<String> getNamesContains(String keyContains) {
		List<String> returnList = new ArrayList<String>();
		for (String name : datas.keySet()) {
			if (StringUtils.contains(name, keyContains)) {
				returnList.add(name);
			}
		}
		return returnList;
	}

	/**
	 * 返回所有以nameEndsWith结尾 的name集合
	 * 
	 * @param nameEndsWith
	 * @return
	 */
	public List<String> getNamesEndsWith(String nameEndsWith) {
		List<String> returnList = new ArrayList<String>();
		for (String name : datas.keySet()) {
			if (StringUtils.endsWith(name, nameEndsWith)) {
				returnList.add(name);
			}
		}
		return returnList;
	}

	/**
	 * 返回所有符合PayloadNameFilter的name集合
	 * 
	 * @param filter
	 * @return
	 */
	public List<String> getNamesEndsWith(com.bench.lang.base.payload.PayloadNameFilter filter) {
		List<String> returnList = new ArrayList<String>();
		for (String name : datas.keySet()) {
			if (filter.accept(name)) {
				returnList.add(name);
			}
		}
		return returnList;
	}

	/**
	 * 将值设置到Object
	 * 
	 * @param object
	 * @param ignoreNullValue
	 */
	public void putVallueToObject(Object object, boolean ignoreNullValue) {
		if (object == null) {
			return;
		}

		try {
			for (java.lang.reflect.Field field : FieldUtils.getAllField(object.getClass())) {
				if (!datas.containsKey(field.getName())) {
					continue;
				}
				Object value = datas.get(field.getName());
				if (value == null) {
					if (!ignoreNullValue) {
						FieldUtils.setFieldValue(object, field.getName(), null);
					}
					continue;
				}
				if (field.getType() == Money.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getMoney(field.getName()));
					continue;
				} else if (field.getType() == Date.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getDate(field.getName()));
					continue;
				} else if (field.getType() == Long.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getLong(field.getName()));
					continue;
				} else if (field.getType() == long.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getLong(field.getName()));
					continue;
				} else if (field.getType() == Integer.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getInt(field.getName()));
					continue;
				} else if (field.getType() == int.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getInt(field.getName()));
					continue;
				} else if (field.getType() == Double.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getDouble(field.getName()));
					continue;
				} else if (field.getType() == double.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getDouble(field.getName()));
					continue;
				} else if (field.getType() == Short.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getShort(field.getName()));
					continue;
				} else if (field.getType() == short.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getShort(field.getName()));
					continue;
				} else if (field.getType() == Float.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getFloat(field.getName()));
					continue;
				} else if (field.getType() == float.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getFloat(field.getName()));
					continue;
				} else if (field.getType() == String.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getString(field.getName()));
					continue;
				} else if (field.getType() == Boolean.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getBoolean(field.getName()));
					continue;
				} else if (field.getType() == boolean.class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getBoolean(field.getName()));
					continue;
				} else if (field.getType() == byte[].class) {
					FieldUtils.setFieldValue(object, field.getName(), this.getBytes(field.getName()));
					continue;
				} else if (field.getType().isEnum()) {
					FieldUtils.setFieldValue(object, field.getName(), this.getEnum(field.getName(), (Class) field.getType()));
					continue;
				}
				// 其他待添加
			}
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "放入对象到payload异常,object=" + object, e);
		}
	}

	/**
	 * 放置对象，注意，放置的对象将以JSON方式存储，使用时再反序列化成JSON，如果使用泛型，不要使用Object类型的
	 * 
	 * @param name
	 * @param object
	 */
	public void putObject(String name, Object object) {
		String jsonString = JacksonUtils.toJson(object);
		this.put(name, jsonString);
	}

	/**
	 * 获取对象，注意确保对象存储时用的JSON格式
	 * 
	 * @param name
	 * @param objectClass
	 * @return
	 */
	public <T> T getObject(String name, Class<T> objectClass) {
		String jsonString = this.getString(name);
		return StringUtils.isEmpty(jsonString) ? null : JacksonUtils.parseJson(jsonString, objectClass);
	}

	/**
	 * 设置数据的Field,将Object的属性放置到里面了,只能放置基本的数据类型，其他无法识别的类型将被忽略，返回忽略的字段
	 * 
	 * @param object
	 * @param ignoreFields
	 * @param ignoreNullFieldValue
	 */
	public String[] putObjectFields(Object object, String[] ignoreFields, boolean ignoreNullFieldValue) {
		if (object == null) {
			return null;
		}
		List<String> unknownValueTypeFieldNames = new ArrayList<String>();
		try {
			Map<?, ?> properties = PropertyUtils.describe(object);
			for (Map.Entry<?, ?> entry : properties.entrySet()) {
				String fieldName = ObjectUtils.toString(entry.getKey());
				if (StringUtils.equals(fieldName, "class")) {
					continue;
				}
				if (ignoreFields != null && ArrayUtils.contains(ignoreFields, fieldName)) {
					continue;
				}

				Object fieldValue = entry.getValue();
				// 空值处理
				if (fieldValue == null) {
					if (ignoreNullFieldValue) {
						continue;
					} else {
						datas.put(fieldName, null);
					}
					continue;
				}
				if (fieldValue instanceof Money) {
					this.put(fieldName, (Money) fieldValue);
					continue;
				} else if (fieldValue instanceof Date) {
					this.put(fieldName, (Date) fieldValue);
					continue;
				} else if (fieldValue instanceof Long) {
					this.put(fieldName, (Long) fieldValue);
					continue;
				} else if (fieldValue instanceof Integer) {
					this.put(fieldName, (Integer) fieldValue);
					continue;
				} else if (fieldValue instanceof Double) {
					this.put(fieldName, (Double) fieldValue);
					continue;
				} else if (fieldValue instanceof Short) {
					this.put(fieldName, (Short) fieldValue);
					continue;
				} else if (fieldValue instanceof Float) {
					this.put(fieldName, (Float) fieldValue);
					continue;
				} else if (fieldValue instanceof String) {
					this.put(fieldName, (String) fieldValue);
					continue;
				} else if (fieldValue instanceof Boolean) {
					this.put(fieldName, (Boolean) fieldValue);
					continue;
				} else if (fieldValue instanceof byte[]) {
					this.put(fieldName, (byte[]) fieldValue);
					continue;
				} else if (fieldValue.getClass().isEnum()) {
					this.put(fieldName, (Enum<?>) fieldValue);
					continue;
				} else {
					unknownValueTypeFieldNames.add(fieldName);
				}
				// 其他待添加
			}
			return unknownValueTypeFieldNames.toArray(new String[unknownValueTypeFieldNames.size()]);
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "放入对象到payload异常,object=" + object, e);
		}
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void put(String name, String value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void putAsJson(String name, Object value) {
		datas.put(name, JacksonUtils.toJson(value));
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	public JsonNode getAsJson(String name) {
		return JacksonUtils.parseJson(getString(name));
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void put(String name, byte[] value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void put(String name, Money value) {
		datas.put(name, value == null ? null : value.toString());
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void put(String name, long value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void put(String name, int value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void put(String name, Enum<?> value) {
		datas.put(name, ObjectUtils.toString(value));
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void put(String name, float value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void put(String name, double value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void put(String name, Date value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void put(String name, boolean value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void put(String name, short value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void put(String name, Map<String, String> value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void putStrings(String name, List<String> value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void putLongs(String name, List<Long> value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void putIntegers(String name, List<Integer> value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void putDoubles(String name, List<Double> value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 * @param value
	 */
	public void put(String name, List<Long> value) {
		datas.put(name, value);
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	public String getString(String name) {
		return ObjectUtils.toString(datas.get(name));
	}

	/**
	 * 获取二进制数据
	 * 
	 * @param name
	 * @return
	 */
	public byte[] getBytes(String name) {
		Object value = datas.get(name);
		if (value != null && value instanceof byte[]) {
			return (byte[]) value;
		}
		return null;
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	public long getLong(String name) {
		return NumberUtils.toLong(datas.get(name));
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	public int getInt(String name) {
		return NumberUtils.toInt(datas.get(name));
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	public float getFloat(String name) {
		return NumberUtils.toFloat(datas.get(name));
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	public double getDouble(String name) {
		return NumberUtils.toDouble(datas.get(name));
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	public Date getDate(String name) {
		return DateUtils.parseLongDateExtraString(ObjectUtils.toString(datas.get(name)));
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	public boolean getBoolean(String name) {
		return BooleanUtils.toBoolean(datas.get(name));
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	public short getShort(String name) {
		return NumberUtils.toShort(datas.get(name));
	}

	public Money getMoney(String name) {
		Object value = datas.get(name);
		return value == null ? null : new Money(value.toString());
	}

	public <T extends Enum<T>> T getEnum(String name, Class<T> enumClass) {
		Object value = datas.get(name);
		return value == null ? null : Enum.valueOf(enumClass, value.toString());
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getMap(String name) {
		Object value = datas.get(name);
		if (value != null && value instanceof Map) {
			return (Map<String, String>) value;
		}
		return null;
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	public List<String> getStrings(String name) {
		Object value = datas.get(name);
		if (value != null && value instanceof List) {
			return ListUtils.toStringList((List<?>) value);
		}
		return null;
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	public List<Long> getLongs(String name) {
		Object value = datas.get(name);
		if (value != null && value instanceof List) {
			return ListUtils.toLong((List<?>) value);
		}
		return null;
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	public List<Integer> getIntegers(String name) {
		Object value = datas.get(name);
		if (value != null && value instanceof List) {
			return ListUtils.toInteger((List<?>) value);
		}
		return null;
	}

	/**
	 * 设置数据
	 * 
	 * @param name
	 */
	public List<Double> getDoubles(String name) {
		Object value = datas.get(name);
		if (value != null && value instanceof List) {
			return ListUtils.toDouble((List<?>) value);
		}
		return null;
	}

	public boolean containsKey(String name) {
		return datas.containsKey(name);
	}

	/**
	 * 设置数据<br>
	 * 注：如果是普通的键值对，则直接使用map即可，如果是含有全局命名的，则使用Parameter
	 * 
	 * @param key
	 * @param value
	 */
	public void putParameters(String key, List<Parameter> value) {
		datas.put(key, value);
	}

	/**
	 * 设置数据<br>
	 * 注：如果是普通的键值对，则直接使用map即可，如果是含有全局命名的，则使用Parameter
	 * 
	 * @param value
	 */
	public void put(Parameter value) {
		datas.put(value.getName(), value);
	}

	/**
	 * 获取数据<br>
	 * 注：如果是普通的键值对，则直接使用map即可，如果是含有全局命名的，则使用Parameter
	 * 
	 * @param key
	 */
	public Parameter getParameter(String key) {
		return (Parameter) (datas.get(key));
	}

	/**
	 * 设置数据<br>
	 * 注：如果是普通的键值对，则直接使用map即可，如果是含有全局命名的，则使用Parameter
	 * 
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public List<Parameter> getParameters(String key) {
		return (List<Parameter>) datas.get(key);
	}

	/**
	 * 
	 * @param map
	 */
	public void putStringMap(Map<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			this.put(entry.getKey(), entry.getValue());
		}
	}

}
