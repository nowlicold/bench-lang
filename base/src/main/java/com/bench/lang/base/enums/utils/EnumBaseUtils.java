package com.bench.lang.base.enums.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.clasz.method.utils.MethodUtils;
import com.bench.lang.base.clasz.utils.BenchClassUtils;
import com.bench.lang.base.enums.EnumBase;
import com.bench.lang.base.error.enums.CommonErrorCodeEnum;
import com.bench.lang.base.exception.BenchRuntimeException;
import com.bench.lang.base.list.utils.ListUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * 枚举工具类
 * 
 * @author cold
 * @version $Id: EnumBaseUtils.java,v 0.1 2009-1-21 下午07:31:23 cold Exp $
 */
public class EnumBaseUtils {

	public static EnumBaseUtils INSTANCE = new EnumBaseUtils();

	private static final Map<Class<?>, Map<String, String>> cachedNameMessageMap = new HashMap<Class<?>, Map<String, String>>();

	private static final Map<Class<?>, Map<String, String>> cachedMessageNameMap = new HashMap<Class<?>, Map<String, String>>();

	private static final Map<Class<?>, Map<String, EnumBase>> cachedNameEnumBaseMap = new HashMap<Class<?>, Map<String, EnumBase>>();

	private static final Map<Class<?>, Map<String, EnumBase>> cachedMessageEnumBaseMap = new HashMap<Class<?>, Map<String, EnumBase>>();

	private static final Map<Class<?>, Map<Number, String>> cachedValueMessageMap = new HashMap<Class<?>, Map<Number, String>>();

	private static final Map<Class<?>, Map<Number, EnumBase>> cachedValueEnumBaseMap = new HashMap<Class<?>, Map<Number, EnumBase>>();

	private static final Map<Class<?>, Set<EnumBase>> cachedNameEnumBaseList = new HashMap<Class<?>, Set<EnumBase>>();

	private static final Map<String, Class<?>> enumClassMap = new HashMap<String, Class<?>>();

	private static Set<Class<? extends EnumBase>> ALL_IMPLEMENTS_ENUM_BASE_CLASS_SET = null;
	private static Object initAllImplementsEnumBaseClassSetLock = new Object();

	public static Set<Class<? extends EnumBase>> getAllImplementsEnumBaseEnum() {
		if (ALL_IMPLEMENTS_ENUM_BASE_CLASS_SET != null) {
			return ALL_IMPLEMENTS_ENUM_BASE_CLASS_SET;
		}
		synchronized (initAllImplementsEnumBaseClassSetLock) {
			if (ALL_IMPLEMENTS_ENUM_BASE_CLASS_SET != null) {
				return ALL_IMPLEMENTS_ENUM_BASE_CLASS_SET;
			}
			Set<Class<? extends EnumBase>> tmpSet = new HashSet<Class<? extends EnumBase>>();
			for (Class<?> clasz : BenchClassUtils.getClasses()) {
				if (clasz.isEnum() && EnumBase.class.isAssignableFrom(clasz)) {
					tmpSet.add((Class<? extends EnumBase>) clasz);
				}
			}
			ALL_IMPLEMENTS_ENUM_BASE_CLASS_SET = tmpSet;
		}
		return ALL_IMPLEMENTS_ENUM_BASE_CLASS_SET;
	}

	/**
	 * 获取当前枚举的前一个枚举,按照value排序
	 * 
	 * @return
	 */
	public static <T extends EnumBase> T getPreviousByValue(T t) {
		T previous = null;
		for (Object enumObj : EnumSet.allOf((Class<? extends Enum>) t.getClass())) {
			EnumBase enumBase = (EnumBase) enumObj;
			if (enumBase.value().doubleValue() < t.value().doubleValue()) {
				if (previous == null) {
					previous = (T) enumBase;
				} else if (previous.value().doubleValue() < enumBase.value().doubleValue()) {
					previous = (T) enumBase;
				}
			}
		}
		return previous;
	}

	/**
	 * 获取当前枚举的下一个枚举,按照value排序
	 * 
	 * @return
	 */
	public static <T extends EnumBase> T getNextByValue(T t) {
		T previous = null;
		for (Object enumObj : EnumSet.allOf((Class<? extends Enum>) t.getClass())) {
			EnumBase enumBase = (EnumBase) enumObj;
			if (enumBase.value().doubleValue() > t.value().doubleValue()) {
				if (previous == null) {
					previous = (T) enumBase;
				} else if (previous.value().doubleValue() > enumBase.value().doubleValue()) {
					previous = (T) enumBase;
				}
			}
		}
		return previous;
	}

	public static Map<String, String> newNameMessageMap(Class<?> enumClass) {
		return new LinkedHashMap<String, String>(getNameMessageMap(enumClass));
	}

	public static Class<?> getClass(String enumClassName) {
		Class<?> clasz = enumClassMap.get(enumClassName);
		if (clasz == null) {
			if (StringUtils.indexOf(enumClassName, ".") > 0) {
				try {
					clasz = Class.forName(enumClassName);
					enumClassMap.put(enumClassName, clasz);
					return clasz;
				} catch (ClassNotFoundException e) {
					throw new BenchRuntimeException("无法找到类异常，类名：" + enumClassName);
				}
			} else {
				for (Class<?> implementsClass : getAllImplementsEnumBaseEnum()) {
					if (StringUtils.equals(implementsClass.getSimpleName(), enumClassName)) {
						return implementsClass;
					}
				}
			}
			throw new BenchRuntimeException("无法找到类异常，类名：" + enumClassName);

		}
		return clasz;
	}

	/**
	 * @param enumClassName
	 * @return
	 */
	public static Object[] enumValues(String enumClassName) {
		Class enumClass = getClass(enumClassName);
		if (enumClass != null) {
			EnumSet set = EnumSet.allOf(enumClass);
			return set.toArray();
		}
		return null;
	}

	public static Map<String, String> newNameMessageMapByClassName(String enumClassName) {
		return new LinkedHashMap<String, String>(getNameMessageMap(getClass(enumClassName)));
	}

	public static Map<String, String> getNameMessageMap(String enumClass) {
		return getNameMessageMap(getClass(enumClass));
	}

	public static Map<String, String> getNameMessageMap(Class enumClass) {
		Map<String, String> nameMessageMap = cachedNameMessageMap.get(enumClass);
		if (nameMessageMap == null) {
			EnumSet<?> enumSet = EnumSet.allOf(enumClass);
			nameMessageMap = new LinkedHashMap<String, String>();
			for (Object obj : enumSet) {
				EnumBase enumObj = (EnumBase) obj;
				nameMessageMap.put(enumObj.name(), enumObj.message());
			}
			cachedNameMessageMap.put(enumClass, nameMessageMap);
		}
		return nameMessageMap;
	}

	public static Map<String, String> getMessageNameMap(String enumClass) {
		return getMessageNameMap(getClass(enumClass));
	}

	public static Map<String, String> getMessageNameMap(Class enumClass) {
		Map<String, String> messageNameMap = cachedMessageNameMap.get(enumClass);
		if (messageNameMap == null) {
			EnumSet<?> enumSet = EnumSet.allOf(enumClass);
			messageNameMap = new LinkedHashMap<String, String>();
			for (Object obj : enumSet) {
				EnumBase enumObj = (EnumBase) obj;
				messageNameMap.put(enumObj.message(), enumObj.name());
			}
			cachedMessageNameMap.put(enumClass, messageNameMap);
		}
		return messageNameMap;
	}

	public static Map<String, EnumBase> newNameEnumBaseMap(Class<?> enumClass) {
		return new LinkedHashMap<String, EnumBase>(getNameEnumBaseMap(enumClass));
	}

	public static Map<String, EnumBase> getNameEnumBaseMap(String enumClass) {
		return getNameEnumBaseMap(getClass(enumClass));
	}

	@SuppressWarnings("unchecked")
	public static Map<String, EnumBase> getNameEnumBaseMap(Class enumClass) {
		Map<String, EnumBase> nameEnumBaseMap = cachedNameEnumBaseMap.get(enumClass);
		if (nameEnumBaseMap == null) {
			EnumSet enumSet = EnumSet.allOf(enumClass);
			nameEnumBaseMap = new LinkedHashMap<String, EnumBase>();
			for (Object obj : enumSet) {
				EnumBase enumObj = (EnumBase) obj;
				nameEnumBaseMap.put(enumObj.name(), enumObj);
			}
			cachedNameEnumBaseMap.put(enumClass, nameEnumBaseMap);
		}
		return nameEnumBaseMap;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, EnumBase> getMessageEnumBaseMap(Class enumClass) {
		Map<String, EnumBase> messageEnumBaseMap = cachedMessageEnumBaseMap.get(enumClass);
		if (messageEnumBaseMap == null) {
			EnumSet enumSet = EnumSet.allOf(enumClass);
			messageEnumBaseMap = new LinkedHashMap<String, EnumBase>();
			for (Object obj : enumSet) {
				EnumBase enumObj = (EnumBase) obj;
				messageEnumBaseMap.put(enumObj.message(), enumObj);
			}
			cachedMessageEnumBaseMap.put(enumClass, messageEnumBaseMap);
		}
		return messageEnumBaseMap;
	}

	public static Map<Number, String> newValueMessageMap(Class<?> enumClass) {
		return new LinkedHashMap<Number, String>(getValueMessageMap(enumClass));
	}

	public static Set<EnumBase> getEnumBases(String enumClass) {
		return getEnumBases(getClass(enumClass));
	}

	/**
	 * 返回枚举类的所有实例
	 * 
	 * @param enumClass
	 * @return
	 */
	public static <T extends Enum<T>> List<T> values(Class<T> enumClass) {
		return EnumUtils.values(enumClass);
	}

	/**
	 * 根据枚举名前缀获取枚举列表
	 * 
	 * @param enumList
	 * @param enumNamePrefix
	 * @return
	 */
	public static <T extends Enum<T>> List<T> getEnumByNamePrefix(List<T> enumList, String enumNamePrefix) {
		return EnumUtils.getEnumByNamePrefix(enumList, enumNamePrefix);
	}

	/**
	 * valueOf多个name返回集合
	 * 
	 * @param enumClass
	 * @param names
	 * @return
	 */
	public static <T extends Enum<T>> List<T> valueOf(Class<T> enumClass, String[] names, boolean safe) {
		return EnumUtils.valueOf(enumClass, names, safe);
	}

	private static <T extends Enum<T>> List<T> messageOf(Class<T> enumClass, String[] messages, boolean safe) {
		List<T> returnList = new ArrayList<T>();
		if (ArrayUtils.isEmpty(messages)) {
			return returnList;
		}
		Map<String, EnumBase> getMessageEnumMap = getMessageEnumBaseMap(enumClass);
		for (String message : messages) {
			T enumBase = (T) getMessageEnumMap.get(message);
			if (enumBase == null && !safe) {
				throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "无法找到枚举，message=" + message + ",class=" + enumClass);
			} else if (enumBase != null) {
				returnList.add(enumBase);
			}
		}
		return returnList;
	}

	/**
	 * valueOf多个name返回集合
	 * 
	 * @param enumClass
	 * @param names
	 * @return
	 */
	public static <T extends Enum<T>> List<T> valueOf(Class<T> enumClass, String[] names) {
		return valueOf(enumClass, names, false);
	}

	public static <T extends Enum<T>> List<T> messageOf(Class<T> enumClass, String[] names) {
		return messageOf(enumClass, names, false);
	}

	/**
	 * valueOf多个name返回集合
	 * 
	 * @param enumClass
	 * @param names
	 * @return
	 */
	public static <T extends Enum<T>> T[] valueOfArray(Class<T> enumClass, String[] names) {
		return ListUtils.toArray(enumClass, valueOf(enumClass, names, false));
	}

	public static <T extends Enum<T>> T[] messageOfArray(Class<T> enumClass, String[] names) {
		return ListUtils.toArray(enumClass, messageOf(enumClass, names, false));
	}

	/**
	 * 安全的valueOf多个name返回集合
	 * 
	 * @param enumClass
	 * @param names
	 * @return
	 */
	public static <T extends Enum<T>> List<T> valueOfSafe(Class<T> enumClass, String[] names) {
		return valueOf(enumClass, names, true);
	}

	public static <T extends Enum<T>> List<T> messageOfSafe(Class<T> enumClass, String[] names) {
		return messageOf(enumClass, names, true);
	}

	public static <T extends Enum<T>> List<T> messageOfSafe(Class<T> enumClass, List<String> names) {
		return messageOf(enumClass, names.toArray(new String[names.size()]), true);
	}

	/**
	 * valueOf多个name返回集合
	 * 
	 * @param enumClass
	 * @param names
	 * @return
	 */
	public static <T extends Enum<T>> T valueOf(Class<T> enumClass, String name) {
		return EnumUtils.valueOf(enumClass, name);
	}

	/**
	 * valueOf多个name返回集合
	 * 
	 * @param enumClass
	 * @param names
	 * @return
	 */
	public static <T extends Enum<T>> T valueOfIgnoreCase(Class<T> enumClass, String name) {
		return EnumUtils.valueOfIgnoreCase(enumClass, name);
	}

	public static <T extends Enum<T>> T messageOf(Class<T> enumClass, String message) {
		if (StringUtils.isEmpty(message)) {
			return null;
		}

		for (T enumObj : EnumSet.allOf(enumClass)) {
			try {
				if (StringUtils.equals(ObjectUtils.toString(MethodUtils.invokeMethod(enumObj, "message", null)), message)) {
					return enumObj;
				}
			} catch (Exception e) {
				throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "查找枚举异常，message=" + message + ",class=" + enumClass, e);
			}
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public static Map<Number, String> getValueMessageMap(Class enumClass) {
		Map<Number, String> valueMessageMap = cachedValueMessageMap.get(enumClass);
		if (valueMessageMap == null) {
			EnumSet enumSet = EnumSet.allOf(enumClass);
			valueMessageMap = new LinkedHashMap<Number, String>();
			for (Object obj : enumSet) {
				EnumBase enumObj = (EnumBase) obj;
				valueMessageMap.put(enumObj.value(), enumObj.message());
			}
			cachedValueMessageMap.put(enumClass, valueMessageMap);
		}
		return valueMessageMap;
	}

	@SuppressWarnings("unchecked")
	public static Map<Number, String> getValueMessageMap(String className) {
		return getValueMessageMap(getClass(className));
	}

	public static Map<Number, EnumBase> newValueEnumBaseMap(Class<?> enumClass) {
		return new LinkedHashMap<Number, EnumBase>(getValueEnumBaseMap(enumClass));
	}

	@SuppressWarnings("unchecked")
	public static Map<Number, EnumBase> getValueEnumBaseMap(Class enumClass) {
		Map<Number, EnumBase> nameEnumBaseMap = cachedValueEnumBaseMap.get(enumClass);
		if (nameEnumBaseMap == null) {
			EnumSet enumSet = EnumSet.allOf(enumClass);
			nameEnumBaseMap = new LinkedHashMap<Number, EnumBase>();
			for (Object obj : enumSet) {
				EnumBase enumObj = (EnumBase) obj;
				nameEnumBaseMap.put(enumObj.value(), enumObj);
			}
			cachedValueEnumBaseMap.put(enumClass, nameEnumBaseMap);
		}
		return nameEnumBaseMap;
	}

	public static List<EnumBase> toEnumBases(Class<?> enumClass) {
		return new ArrayList<EnumBase>(getEnumBases(enumClass));
	}

	@SuppressWarnings("unchecked")
	private static Set<EnumBase> getEnumBases(Class enumClass) {
		Set<EnumBase> enumBaseSet = cachedNameEnumBaseList.get(enumClass);
		if (enumBaseSet == null) {
			EnumSet enumSet = EnumSet.allOf(enumClass);
			enumBaseSet = new HashSet<EnumBase>();
			for (Object obj : enumSet) {
				EnumBase enumObj = (EnumBase) obj;
				enumBaseSet.add(enumObj);
			}
			cachedNameEnumBaseList.put(enumClass, enumBaseSet);
		}
		return enumBaseSet;
	}

	/**
	 * 
	 * @param enumClass
	 * @return
	 */
	public static <T extends Enum<T>> Map<String, T> toMessageMap(Class<T> enumClass) {
		Map<String, T> map = new HashMap<String, T>();
		EnumSet<T> enumSet = EnumSet.allOf(enumClass);
		for (T obj : enumSet) {
			map.put(((EnumBase) obj).message(), obj);
		}
		return map;
	}

	public static EnumBase numberValueOf(Class<?> enumType, Number value) {
		return getValueEnumBaseMap(enumType).get(value);
	}

	/**
	 * @param enumList
	 * @return
	 */
	public static List<String> toName(Collection<? extends Enum<?>> enumList) {
		return EnumUtils.toName(enumList);
	}

	/**
	 * @param enumList
	 * @return
	 */
	public static List<String> toEnumBaseName(Collection<? extends EnumBase> enumList) {
		if (enumList == null)
			return null;

		List<String> enumNameList = new ArrayList<String>();
		for (EnumBase enumObj : enumList) {
			enumNameList.add(enumObj.name());
		}
		return enumNameList;
	}

	/**
	 * @param enumList
	 * @return
	 */
	public static List<String> toMessage(Collection<? extends EnumBase> enumList) {
		if (enumList == null)
			return null;

		List<String> enumNameList = new ArrayList<String>();
		for (EnumBase enumObj : enumList) {
			enumNameList.add(enumObj.message());
		}
		return enumNameList;
	}

	public static List<String> toMessage(EnumBase[] enumList) {
		if (enumList == null)
			return null;

		List<String> enumNameList = new ArrayList<String>();
		for (EnumBase enumObj : enumList) {
			enumNameList.add(enumObj.message());
		}
		return enumNameList;
	}

	/**
	 * 转换成value列表
	 * 
	 * @param <T>
	 * @param enumList
	 * @return
	 */
	public static <T extends EnumBase> List<Number> toValue(Collection<T> enumList) {
		if (enumList == null)
			return null;

		List<Number> enumNameList = new ArrayList<Number>();
		for (EnumBase enumObj : enumList) {
			enumNameList.add(enumObj.value());
		}
		return enumNameList;
	}

	public static <T extends Enum<T>> List<T> toEnum(Class<T> enumClass, Collection<String> enumNameList) {
		return EnumUtils.toEnum(enumClass, enumNameList);
	}

	public static <T extends Enum<T>> List<T> toEnum(Class<T> enumClass, String[] enumNameList) {
		return EnumUtils.toEnum(enumClass, enumNameList);
	}

	/**
	 * 转换为Name
	 * 
	 * @param enumObj
	 * @return
	 */
	public static String toName(Enum<?> enumObj) {
		return EnumUtils.toName(enumObj);
	}

	/**
	 * @param enumList
	 * @return
	 */
	public static List<String> toName(Enum<?>[] enumList) {
		return EnumUtils.toName(enumList);
	}

}
