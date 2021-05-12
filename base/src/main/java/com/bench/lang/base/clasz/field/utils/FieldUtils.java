package com.bench.lang.base.clasz.field.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;

import com.bench.lang.base.clasz.field.ClassNullField;
import com.bench.lang.base.clasz.utils.ClassUtils;
import com.bench.lang.base.error.enums.CommonErrorCodeEnum;
import com.bench.lang.base.exception.BenchRuntimeException;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * Class对应的Field工具类
 * 
 * @author cold
 * 
 * @version $Id: FieldUtils.java, v 0.1 2009-8-18 下午06:10:13 cold Exp $
 */
public class FieldUtils {
	public static final FieldUtils INSTANCE = new FieldUtils();

	private static Field NULL_FIELD = null;
	static {
		try {
			NULL_FIELD = ClassNullField.class.getDeclaredField("NULL_FIELD");
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "初始化ClassUtils异常", e);
		}
	}

	private static Map<Class<?>, Map<String, Field>> wthSuperClassEachFieldCacheMap = new ConcurrentHashMap<Class<?>, Map<String, Field>>();

	private static Map<Class<?>, Set<Field>> wthSuperClassAllFieldCacheMap = new ConcurrentHashMap<Class<?>, Set<Field>>();

	public static List<Class<?>> getFieldParameterizedClass(Class<?> objectClass, Field field) {
		// 如果是List类型，得到其Generic的类型
		Type genericType = field.getGenericType();
		if (genericType == null)
			return null;
		// 如果是泛型参数的类型
		if (genericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) genericType;
			List<Class<?>> returnList = new ArrayList<Class<?>>();
			for (Type type : pt.getActualTypeArguments()) {
				if (type instanceof Class<?>) {
					returnList.add((Class<?>) type);
				} else if (type instanceof ParameterizedType) {
					ParameterizedType childPt = (ParameterizedType) type;
					if (childPt.getRawType() instanceof Class<?>) {
						returnList.add((Class<?>) childPt.getRawType());
					}
				} else if (type instanceof TypeVariable) {
					TypeVariable<?> childPt = (TypeVariable<?>) type;
					if (childPt.getBounds().length > 0) {
						if (childPt.getBounds()[0] instanceof ParameterizedType) {
							ParameterizedType boundType = (ParameterizedType) childPt.getBounds()[0];
							if (boundType.getRawType() instanceof Class) {
								Class<?> targetClass = ClassUtils.getParameterizedType(objectClass, (Class<?>) boundType.getRawType());
								if (targetClass != null) {
									returnList.add(targetClass);
								}
							}
						}
					}

				}
			}
			return returnList;
		}
		return new ArrayList<Class<?>>(0);
	}

	/**
	 * @param field
	 * @return
	 */
	public static Type[] getFieldParameterizedType(Field field) {
		// 如果是List类型，得到其Generic的类型
		Type genericType = field.getGenericType();
		if (genericType == null)
			return null;
		// 如果是泛型参数的类型
		if (genericType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) genericType;
			return pt.getActualTypeArguments();
		}
		return null;
	}

	/**
	 * 获取clazz中field的ParameterizedType，如List内的属性类型
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Type[] getFieldParameterizedType(Class<?> clazz, String fieldName) {
		Field field = getFieldSafe(clazz, fieldName);
		if (field == null) {
			return null;
		}
		return getFieldParameterizedType(field);
	}

	/**
	 * 安全的获取静态属性值
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Object getStaticFieldValueSafe(Class<?> clazz, String fieldName) {
		try {
			return getStaticFieldValue(clazz, fieldName);
		} catch (NoSuchFieldException e) {
			return null;
		} catch (IllegalArgumentException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "获取静态属性值异常,class=" + clazz + ",fieldName=" + fieldName, e);
		}
	}

	/**
	 * 获取静态属性值
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getStaticFieldValue(Class<?> clazz, String fieldName) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field field = getField(clazz, fieldName);
		field.setAccessible(true);
		return field.get(null);
	}

	public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
		Map<String, Field> fieldMap = wthSuperClassEachFieldCacheMap.get(clazz);
		if (fieldMap == null) {
			fieldMap = new ConcurrentHashMap<String, Field>();
			wthSuperClassEachFieldCacheMap.put(clazz, fieldMap);
		}
		Field field = fieldMap.get(fieldName);
		if (field != null) {
			if (field == NULL_FIELD) {
				throw new NoSuchFieldException("无法找到field，class:" + clazz + "，field：" + fieldName);
			}
			return field;
		}
		try {
			field = clazz.getDeclaredField(fieldName);
			fieldMap.put(fieldName, field);
			return field;
		} catch (NoSuchFieldException e) {
			Class<?> parentClass = clazz.getSuperclass();
			if (parentClass == null) {
				fieldMap.put(fieldName, NULL_FIELD);
				throw new NoSuchFieldException("无法找到field，class:" + clazz + "，field：" + fieldName);
			}
			return getField(parentClass, fieldName);
		}
	}

	public static Set<Field> getAllField(Class<?> clazz) {
		Set<Field> fieldSet = wthSuperClassAllFieldCacheMap.get(clazz);
		if (fieldSet != null) {
			return fieldSet;
		}
		fieldSet = new HashSet<Field>();
		CollectionUtils.addAll(fieldSet, clazz.getDeclaredFields());
		for (Class<?> parentClass : ClassUtils.getSuperclasses(clazz)) {
			CollectionUtils.addAll(fieldSet, parentClass.getDeclaredFields());
		}
		return fieldSet;
	}

	/**
	 * 递归获取Field，如果在当前类中无法找到，则到父类中查找，如果找不到，则返回null，不抛出异常
	 * 
	 * @param clasz
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static Field getFieldSafe(Class<?> clasz, String fieldName) {
		try {
			return getField(clasz, fieldName);
		} catch (NoSuchFieldException e) {
			return null;
		}

	}

	/**
	 * 递归获取Field，如果在当前类中无法找到，则到父类中查找
	 * 
	 * @param clasz
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static List<Field> getAllField(Class<?> clasz, Class<?> fieldType) {
		List<Field> returnList = new ArrayList<Field>();
		for (Field field : getAllField(clasz)) {
			if (field.getType() == fieldType) {
				returnList.add(field);
			}
		}
		return returnList;
	}

	public static boolean hasField(Class<?> clasz, String fieldName) {
		try {
			return getField(clasz, fieldName) != null;
		} catch (NoSuchFieldException e) {
			return false;
		}
	}

	public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
		Field field = null;
		try {
			field = getField(obj.getClass(), fieldName);
		} catch (NoSuchFieldException e) {
			throw new BenchRuntimeException("无法找到属性,object=" + obj + ",fieldName=" + fieldName, e);
		}
		try {
			field.setAccessible(true);
			field.set(obj, fieldValue);
		} catch (Exception e) {
			throw new BenchRuntimeException("设置属性值异常,object=" + obj + ",fieldName=" + fieldName, e);
		}
	}

	/**
	 * 设置属性值
	 * 
	 * @param obj
	 * @param field
	 * @param fieldValue
	 */
	public static void setFieldValueSafe(Object obj, Field field, Object fieldValue) {
		try {
			field.setAccessible(true);
			field.set(obj, fieldValue);
		} catch (Exception e) {
			return;
		}
	}

	public static void setStaticFieldValue(Class<?> objClass, String fieldName, Object fieldValue) {
		Field field = null;
		try {
			field = getField(objClass, fieldName);
		} catch (NoSuchFieldException e) {
			throw new BenchRuntimeException("无法找到属性,objClass=" + objClass + ",fieldName=" + fieldName, e);
		}
		try {
			field.setAccessible(true);
			field.set(null, fieldValue);
		} catch (Exception e) {
			throw new BenchRuntimeException("设置属性值异常,objClass=" + objClass + ",fieldName=" + fieldName, e);
		}
	}

	/**
	 * 返回属性值
	 * 
	 * @param field
	 * @param obj
	 * @return
	 */
	public static Object getFieldValue(Field field, Object obj) {
		try {
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
			throw new BenchRuntimeException("获取属性值异常,field=" + field + ",obj=" + obj, e);
		}
	}

	/**
	 * 获取属性值
	 * 
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object object, String fieldName) {
		if (object == null) {
			return null;
		}
		Field field = getFieldSafe(object.getClass(), fieldName);
		if (field != null) {
			try {
				field.setAccessible(true);
				return field.get(object);
			} catch (Exception e) {
				throw new BenchRuntimeException("获取属性值异常,fieldName=" + fieldName + ",object=" + object, e);
			}
		}
		return null;
	}

	/**
	 * 根据getter方法获取属性名
	 * 
	 * @param getterMethodName
	 * @return
	 */
	public static String getFieldNameByGetterMethod(String getterMethodName) {
		if (StringUtils.startsWith(getterMethodName, "get")) {
			return StringUtils.toCamelCase(getterMethodName.substring(3));
		}
		if (StringUtils.startsWith(getterMethodName, "is")) {
			return StringUtils.toCamelCase(getterMethodName.substring(2));
		}
		return null;
	}

	/**
	 * 获取getter属性 <br>
	 * 如果当前方法是是getter方法，则返回对应的field，否则返回null
	 * 
	 * @param method
	 */
	public static Field getGetterField(Method method) {
		if (method.getParameterCount() != 0) {
			return null;
		}
		if (method.getReturnType() == void.class) {
			return null;
		}

		// 如果是get开头
		if (StringUtils.startsWith(method.getName(), "get")) {
			String fieldName = StringUtils.substringAfter(method.getName(), "get");
			fieldName = StringUtils.toFirstCharLowerCase(fieldName);
			Field field = FieldUtils.getFieldSafe(method.getDeclaringClass(), fieldName);
			return field;
		} else if (StringUtils.startsWith(method.getName(), "is")) {
			String fieldName = StringUtils.substringAfter(method.getName(), "is");
			fieldName = StringUtils.toFirstCharLowerCase(fieldName);
			Field field = FieldUtils.getFieldSafe(method.getDeclaringClass(), fieldName);
			if (field != null && (field.getType() == boolean.class || field.getType() == Boolean.class)) {
				return field;
			}
			return null;
		}
		return null;
	}

	/**
	 * 获取setter属性 <br>
	 * 如果当前方法是是setter方法，则返回对应的field，否则返回null
	 * 
	 * @param method
	 */
	public static Field getSetterField(Method method) {
		// 只有一个参数
		if (method.getParameterCount() != 1) {
			return null;
		}
		// setter没有返回值
		if (method.getReturnType() != void.class) {
			return null;
		}

		// 如果是set开头
		if (StringUtils.startsWith(method.getName(), "set")) {
			String fieldName = StringUtils.substringAfter(method.getName(), "set");
			fieldName = StringUtils.toFirstCharLowerCase(fieldName);
			Field field = FieldUtils.getFieldSafe(method.getDeclaringClass(), fieldName);
			return field;
		}
		return null;
	}

}
