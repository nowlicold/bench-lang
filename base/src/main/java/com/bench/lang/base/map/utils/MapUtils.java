/**
 * 
 */
package com.bench.lang.base.map.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;
import org.apache.commons.collections4.map.LRUMap;

import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.bean.utils.PropertyUtils;
import com.bench.lang.base.collection.utils.CollectionUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.properties.utils.PropertiesUtils;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * Map工具类
 * 
 * @author cold 2009-10-30 下午10:29:07
 * 
 */
public class MapUtils extends org.apache.commons.collections.MapUtils {

	public static final MapUtils INSTANCE = new MapUtils();

	/**
	 * 创建同步的LRUMap
	 * 
	 * @param maxSize
	 * @return
	 */
	public static <K, V> Map<K, V> newSynchronizedLRUMap(int maxSize) {
		return Collections.synchronizedMap(new LRUMap<K, V>(maxSize));
	}

	/**
	 * 创建同步的Map
	 * 
	 * @param maxSize
	 * @return
	 */
	public static <K, V> Map<K, V> newSynchronizeMap() {
		return Collections.synchronizedMap(new HashMap<K, V>());
	}

	public static final Map<Object, Object> newMap() {
		return new HashMap<Object, Object>();
	}

	/**
	 * @param keyPrefix
	 * @param fromMap
	 * @param targetMap
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final void putAllWithKeyPrefix(String keyPrefix, Map fromMap, Map targetMap) {
		fromMap.forEach((k, v) -> {
			targetMap.put(keyPrefix + k, v);
		});
	}

	/**
	 * 忽略key的大小写来获取map中的value
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static <V> V getIgnoreCase(Map<String, V> map, String key) {
		if (map.containsKey(key)) {
			return map.get(key);
		}
		for (Map.Entry<String, V> entry : map.entrySet()) {
			if (StringUtils.equalsIgnoreCase(entry.getKey(), key)) {
				return entry.getValue();
			}
		}
		return null;
	}

	/**
	 * 设置相同的value
	 * 
	 * @param map
	 * @param value
	 */
	public static <K extends Comparable<? super K>, V> void setSameValue(Map<K, V> map, V value) {
		for (K k : map.keySet()) {
			map.put(k, value);
		}
	}

	/**
	 * 按key排序
	 * 
	 * @param list
	 */
	public static <K extends Comparable<? super K>, V> Map<K, V> sortMapByKey(Map<K, V> map) {
		if (map == null) {
			return null;
		}
		Map<K, V> linkedMap = new LinkedHashMap<K, V>();
		List<K> keyList = new ArrayList<K>(map.keySet());
		CollectionUtils.sort(keyList);
		for (K k : keyList) {
			linkedMap.put(k, map.get(k));
		}
		return linkedMap;
	}

	/**
	 * 按key排序
	 * 
	 * @param list
	 */
	public static <K extends Comparable<? super K>, V> Map<K, V> sortMapByKey(Map<K, V> map, Comparator<K> c) {
		if (map == null) {
			return null;
		}
		Map<K, V> linkedMap = new LinkedHashMap<K, V>();
		List<K> keyList = new ArrayList<K>(map.keySet());
		CollectionUtils.sort(keyList, c);
		for (K k : keyList) {
			linkedMap.put(k, map.get(k));
		}
		return linkedMap;
	}

	public static final Map<Object, Object> convertToMap(List<Object> objectList, String keyFieldName) {
		if (objectList == null) {
			return null;
		}
		Map<Object, Object> returnMap = new HashMap<Object, Object>();
		for (Object object : objectList) {
			try {
				returnMap.put(PropertyUtils.getProperty(object, keyFieldName), object);
			} catch (Exception e) {
				throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"将对象集合转换为Map异常,keyFieldName=" + keyFieldName, e);
			}
		}
		return returnMap;
	}

	public static final Map<Object, Object> newLinkedMap() {
		return new LinkedHashMap<Object, Object>();
	}

	/**
	 * 返回1个新的map,该值为将map2合并到map1,不合并已经存在的key
	 * 
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static <K, V> Map<K, V> combineFilterSameKeys(Map<K, V> map1, Map<K, V> map2) {
		if (map2 == null) {
			return new HashMap<K, V>(map1);
		}
		if (map1 == null) {
			return new HashMap<K, V>(map2);
		}
		Map<K, V> returnMap = new HashMap<K, V>(map1);
		for (Map.Entry<K, V> entry : map2.entrySet()) {
			if (!map1.containsKey(entry.getKey())) {
				returnMap.put(entry.getKey(), entry.getValue());
			}
		}
		return returnMap;
	}

	/**
	 * 讲map2放入到map1中，如果key已经存在，则忽略
	 * 
	 * @param map1
	 * @param map2
	 */
	public static <K, V> void putAllIgnoreSameKey(Map<K, V> map1, Map<K, V> map2) {
		if (map2 == null) {
			return;
		}
		for (Map.Entry<K, V> entry : map2.entrySet()) {
			if (!map1.containsKey(entry.getKey())) {
				map1.put(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * 根据值获取key
	 * 
	 * @param map
	 * @param v
	 * @return
	 */
	public static <K, V> List<K> getKeyByValue(Map<K, V> map, V v) {
		List<K> returnList = new ArrayList<K>();
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (Objects.equals(entry.getValue(), v)) {
				if (!returnList.contains(entry.getKey()))
					returnList.add(entry.getKey());
			}
		}
		return returnList;
	}

	/**
	 * 返回map容量
	 * 
	 * @param map
	 * @return
	 */
	public static final int getSize(Map<?, ?> map) {
		return map == null ? 0 : map.size();
	}

	public static Map<String, String> convert(Map<?, ?> map) {
		return convert(map, false);
	}

	/**
	 * 转换map成字符串Map
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, String> convert(Map<?, ?> map, boolean fitlerEmpty) {
		/**
		 * 本地签名验证
		 */
		Map<String, String> dataMap = new HashMap<String, String>();
		// 获得POST 过来参数设置到新的params中
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			// 过滤
			String key = ObjectUtils.toString(entry.getKey());
			if (entry.getValue() == null) {
				dataMap.put(key, null);
				continue;
			}
			if (entry.getValue().getClass().isArray()) {

				String[] values = (String[]) entry.getValue();
				StringBuffer valueBuf = new StringBuffer();
				for (String value : values) {
					if (!StringUtils.isEmpty(value)) {
						valueBuf.append(value).append(",");
					}
				}
				if (valueBuf.length() > 1) {
					valueBuf = valueBuf.deleteCharAt(valueBuf.length() - 1);
				}
				dataMap.put(key, valueBuf.toString());
			} else {
				dataMap.put(key, ObjectUtils.toString(entry.getValue()));
			}
		}

		return dataMap;
	}

	/**
	 * 移除key
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param keyList
	 * @return
	 */
	public static <K, V> List<V> removeKeys(Map<K, V> map, List<K> keyList) {
		List<V> removeValueList = new ArrayList<V>();
		if (keyList == null) {
			return removeValueList;
		}
		for (K key : keyList) {
			removeValueList.add(map.remove(key));
		}
		return removeValueList;
	}

	/**
	 * 移除key,返回移除的kv Map
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param keyList
	 * @return
	 */
	public static <K, V> Map<K, V> remove(Map<K, V> map, List<K> keyList) {
		Map<K, V> returnMap = new HashMap<K, V>();
		if (keyList == null) {
			return returnMap;
		}
		for (K key : keyList) {
			returnMap.put(key, map.remove(key));
		}
		return returnMap;
	}

	/**
	 * 移除key,返回移除的kv Map
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param keyList
	 * @return
	 */
	public static <K, V> Map<K, V> remove(Map<K, V> map, K[] keys) {
		Map<K, V> returnMap = new HashMap<K, V>();
		if (keys == null) {
			return returnMap;
		}
		for (K key : keys) {
			returnMap.put(key, map.remove(key));
		}
		return returnMap;
	}

	/**
	 * 移除key
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param keyList
	 * @return
	 */
	public static <K, V> List<V> removeKeys(Map<K, V> map, Set<K> keyList) {
		List<V> removeValueList = new ArrayList<V>();
		if (keyList == null) {
			return removeValueList;
		}
		for (K key : keyList) {
			removeValueList.add(map.remove(key));
		}
		return removeValueList;
	}

	/**
	 * 移除key
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param keyList
	 * @return
	 */
	@SafeVarargs
	public static <K, V> List<V> removeKeys(Map<K, V> map, K... keyList) {
		List<V> removeValueList = new ArrayList<V>();
		if (keyList == null) {
			return removeValueList;
		}
		for (K key : keyList) {
			removeValueList.add(map.remove(key));
		}
		return removeValueList;
	}

	/**
	 * 移除不存在的key
	 * 
	 * @param map
	 * @param existedKey
	 * @return
	 */
	public static <K, V> List<V> removeNotExistsKeys(Map<K, V> map, List<K> existedKeyList) {
		List<K> removeKeyList = new ArrayList<K>();
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (!existedKeyList.contains(entry.getKey())) {
				removeKeyList.add(entry.getKey());
			}
		}
		return removeKeys(map, removeKeyList);
	}

	/**
	 * 解析字符串为Map<br>
	 * 字符串格式 :key1+kvJoin+value1+pairJoin
	 */
	public static Map<String, String> parseSimpleKeyValue(String values) {
		return parseSimpleKeyValue(values, ",", "=");
	}

	/**
	 * 解析字符串为Map<br>
	 * 字符串格式 :key1+kvJoin+value1+pairJoin
	 * 
	 * @param values
	 * @return
	 */
	public static Map<String, String> parseSimpleKeyValue(String values, String pairJoin, String kvJoin) {
		if (values == null)
			return null;
		Map<String, String> returnMap = new HashMap<String, String>();
		for (String keyValueString : StringUtils.splitByWholeSeparator(values, pairJoin)) {
			String[] keyValueArray = StringUtils.splitByWholeSeparator(keyValueString, kvJoin);
			if (ArrayUtils.getLength(keyValueArray) != 2) {
				continue;
			}
			returnMap.put(keyValueArray[0], keyValueArray[1]);
		}
		return returnMap;
	}

	/**
	 * 使用已有map构造一个treeMap
	 * 
	 * @param map
	 * @return
	 */
	public static <K, V> Map<K, V> newTreeMap(Map<K, V> map) {
		return new TreeMap<K, V>(map);
	}

	/**
	 * 获取第一个字符串，如果是值是String，则直接返回，如果值是数组，则返回第0个
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static String getFirstString(final Map<?, ?> map, final Object key) {
		Object value = map.get(key);
		if (value == null) {
			return null;
		}
		if (value.getClass().isArray()) {
			if (Array.getLength(value) > 0) {
				return ObjectUtils.toString(Array.get(value, 0));
			} else {
				return null;
			}
		} else if (value instanceof List) {
			List<?> valueList = (List<?>) value;
			if (valueList.size() > 0) {
				return ObjectUtils.toString(valueList.get(0));
			} else {
				return null;
			}
		} else {
			return ObjectUtils.toString(value);
		}
	}

	/**
	 * 将Key转换为大写
	 * 
	 * @param map
	 * @return
	 */
	public static <T> Map<String, T> convertKeyUpperCase(Map<String, T> map) {
		Map<String, T> returnMap = new HashMap<String, T>();
		for (Map.Entry<String, T> entry : map.entrySet()) {
			returnMap.put(StringUtils.toUpperCase(entry.getKey()), entry.getValue());
		}
		return returnMap;
	}

	/**
	 * @param map
	 * @param keyConverter
	 * @param valueConverter
	 * @return
	 */
	public static <K, V> Map<K, V> convert(Map<?, ?> map, PropertiesUtils.KeyConverter<K> keyConverter, PropertiesUtils.ValueConverter<V> valueConverter) {
		Map<K, V> returnMap = new HashMap<K, V>();
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			returnMap.put(keyConverter.convert(entry.getKey()), valueConverter.convert(entry.getValue()));
		}
		return returnMap;
	} // Map decorators
		// -----------------------------------------------------------------------

	/**
	 * 将Map转成String，用joiner连接
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param joiner
	 * @return
	 */
	public static <K, V> String toString(Map<K, V> map, String joinner) {
		if (getSize(map) == 0) {
			return StringUtils.EMPTY_STRING;
		}
		StringBuffer buf = new StringBuffer();
		map.forEach((k, v) -> {
			buf.append(k).append("=").append(v).append(joinner);
		});
		buf.delete(buf.length() - joinner.length(), buf.length());
		return buf.toString();
	}
}