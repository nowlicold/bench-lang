package com.bench.lang.base.enums.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.exception.BenchRuntimeException;
import com.bench.lang.base.list.utils.ListUtils;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * 枚举工具类
 * 
 * @author cold
 * @version $Id: EnumUtils.java,v 0.1 2009-1-21 下午07:31:23 cold Exp $
 */
public class EnumUtils extends org.apache.commons.lang3.EnumUtils {

	public static EnumUtils INSTANCE = new EnumUtils();

	/**
	 * @param enumClassName
	 * @return
	 */
	public static Object[] enumValues(String enumClassName) {
		Class enumClass = null;
		try {
			enumClass = Class.forName(enumClassName);
		} catch (ClassNotFoundException e) {
			throw new BenchRuntimeException("无法找到类:" + enumClassName);
		}
		EnumSet set = EnumSet.allOf(enumClass);
		return set.toArray();
	}

	/**
	 * 返回枚举类的所有实例
	 * 
	 * @param enumClass
	 * @return
	 */
	public static <T extends Enum<T>> List<T> values(Class<T> enumClass) {
		return new ArrayList<T>(EnumSet.allOf(enumClass));
	}

	/**
	 * 根据枚举名前缀获取枚举列表
	 * 
	 * @param enumList
	 * @param enumNamePrefix
	 * @return
	 */
	public static <T extends Enum<T>> List<T> getEnumByNamePrefix(List<T> enumList, String enumNamePrefix) {
		List<T> returnList = new ArrayList<T>();
		for (T enumObject : enumList) {
			if (StringUtils.startsWith(enumObject.name(), enumNamePrefix)) {
				returnList.add(enumObject);
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
	public static <T extends Enum<T>> List<T> valueOf(Class<T> enumClass, String[] names, boolean safe) {
		List<T> returnList = new ArrayList<T>();
		if (ArrayUtils.isEmpty(names)) {
			return returnList;
		}
		for (String name : names) {
			name = StringUtils.trimWithLine(name);
			T enumObj = null;
			if (safe) {
				try {
					enumObj = Enum.valueOf(enumClass, name);
				} catch (Exception e) {

				}
			} else {
				enumObj = Enum.valueOf(enumClass, name);
			}
			if (enumObj != null) {
				returnList.add(enumObj);
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

	/**
	 * valueOf多个name返回集合
	 * 
	 * @param enumClass
	 * @param names
	 * @return
	 */
	public static <T extends Enum<T>> T valueOf(Class<T> enumClass, String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		try {
			return Enum.valueOf(enumClass, name);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * valueOf多个name返回集合
	 * 
	 * @param enumClass
	 * @param names
	 * @return
	 */
	public static <T extends Enum<T>> T valueOfIgnoreCase(Class<T> enumClass, String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		for (T enumObj : EnumSet.allOf(enumClass)) {
			if (StringUtils.equalsIgnoreCase(enumObj.name(), name)) {
				return enumObj;
			}
		}
		return null;
	}

	/**
	 * @param enumList
	 * @return
	 */
	public static List<String> toName(Collection<? extends Enum<?>> enumList) {
		if (enumList == null)
			return null;

		List<String> enumNameList = new ArrayList<String>();
		for (Enum<?> enumObj : enumList) {
			enumNameList.add(enumObj.name());
		}
		return enumNameList;
	}

	public static <T extends Enum<T>> List<T> toEnum(Class<T> enumClass, Collection<String> enumNameList) {
		if (enumNameList == null)
			return null;

		List<T> enumList = new ArrayList<T>();
		for (String enumName : enumNameList) {
			enumList.add(Enum.valueOf(enumClass, enumName));
		}
		return enumList;
	}

	public static <T extends Enum<T>> List<T> toEnum(Class<T> enumClass, String[] enumNameList) {
		if (enumNameList == null)
			return null;

		List<T> enumList = new ArrayList<T>();
		for (String enumName : enumNameList) {
			enumList.add(Enum.valueOf(enumClass, enumName));
		}
		return enumList;
	}

	/**
	 * 转换为Name
	 * 
	 * @param enumObj
	 * @return
	 */
	public static String toName(Enum<?> enumObj) {
		return enumObj == null ? null : enumObj.name();
	}

	/**
	 * @param enumList
	 * @return
	 */
	public static List<String> toName(Enum<?>[] enumList) {
		if (enumList == null)
			return null;

		List<String> enumNameList = new ArrayList<String>();
		for (Enum<?> enumObj : enumList) {
			enumNameList.add(enumObj.name());
		}
		return enumNameList;
	}

}
