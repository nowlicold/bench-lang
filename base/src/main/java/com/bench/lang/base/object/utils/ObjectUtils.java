package com.bench.lang.base.object.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.bean.utils.PropertyUtils;
import com.bench.lang.base.clasz.field.utils.FieldUtils;
import com.bench.lang.base.clasz.utils.ClassUtils;
import com.bench.lang.base.error.enums.CommonErrorCodeEnum;
import com.bench.lang.base.exception.BenchRuntimeException;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * @author cold
 * @version $Id: ObjectUtils.java,v 0.1 2009-5-21 上午12:13:44 cold Exp $
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

	public static final ObjectUtils INSTANCE = new ObjectUtils();
	/*
	 * ========================================================================= = ==
	 */
	/* 常量和singleton。 */
	/*
	 * ========================================================================= = ==
	 */

	/**
	 * 用于表示<code>null</code>的常量。
	 * 
	 * <p>
	 * 例如，<code>HashMap.get(Object)</code>方法返回<code>null</code>有两种可能： 值不存在或值为 <code>null</code>。而这个singleton可用来区别这两种情形。
	 * </p>
	 * 
	 * <p>
	 * 另一个例子是，<code>Hashtable</code>的值不能为<code>null</code>。
	 * </p>
	 */
	public static final Object NULL = new Serializable() {
		private static final long serialVersionUID = 7092611880189329093L;

		private Object readResolve() {
			return NULL;
		}
	};

	/**
	 * 是否为null
	 * 
	 * @param object
	 * @return
	 */
	public static final boolean isNull(Object object) {
		return object == null;
	}

	/**
	 * 如果对象为<code>null</code>，则返回指定默认对象，否则返回对象本身。
	 * 
	 * @param object
	 * @param defaultValue
	 * @return
	 */
	public static <T> T defaultWhenNull(T object, T defaultValue) {
		return (object != null) ? object : defaultValue;
	}

	/**
	 * @param list
	 * @return
	 */
	public static boolean equalsList(List<?> list) {
		if (list == null)
			return false;
		if (list.size() == 0)
			return true;

		Object firstObject = list.get(0);
		for (Object object : list) {
			if (!ObjectUtils.equals(firstObject, object)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * ========================================================================= = ==
	 */
	/* Hashcode函数。 */
	/*                                                                              */
	/* 以下方法用来取得对象的hash code。 */
	/*
	 * ========================================================================= = ==
	 */

	/**
	 * 取得对象的hash值, 如果对象为<code>null</code>, 则返回<code>0</code>。
	 * 
	 * <p>
	 * 此方法可以正确地处理多维数组。
	 * </p>
	 * 
	 * @param object
	 *            对象
	 * 
	 * @return hash值
	 */
	public static int hashCode(Object object) {
		return ArrayUtils.hashCode(object);
	}

	/**
	 * 取得对象的原始的hash值, 如果对象为<code>null</code>, 则返回<code>0</code>。
	 * 
	 * <p>
	 * 该方法使用<code>System.identityHashCode</code>来取得hash值，该值不受对象本身的 <code>hashCode</code>方法的影响。
	 * </p>
	 * 
	 * @param object
	 *            对象
	 * 
	 * @return hash值
	 */
	public static int identityHashCode(Object object) {
		return (object == null) ? 0 : System.identityHashCode(object);
	}

	/*
	 * ========================================================================= = ==
	 */
	/* 取得对象的identity。 */
	/*
	 * ========================================================================= = ==
	 */

	/**
	 * 取得对象自身的identity，如同对象没有覆盖<code>toString()</code>方法时， <code>Object.toString()</code>的原始输出。
	 * 
	 * <pre>
	 * ObjectUtil.identityToString(null)          = null
	 * ObjectUtil.identityToString(&quot;&quot;)            = &quot;java.lang.String@1e23&quot;
	 * ObjectUtil.identityToString(Boolean.TRUE)  = &quot;java.lang.Boolean@7fa&quot;
	 * ObjectUtil.identityToString(new int[0])    = &quot;int[]@7fa&quot;
	 * ObjectUtil.identityToString(new Object[0]) = &quot;java.lang.Object[]@7fa&quot;
	 * </pre>
	 * 
	 * @param object
	 *            对象
	 * 
	 * @return 对象的identity，如果对象是<code>null</code>，则返回<code>null</code>
	 */
	public static String identityToString(Object object) {
		if (object == null) {
			return null;
		}

		return appendIdentityToString(null, object).toString();
	}

	/**
	 * 取得对象自身的identity，如同对象没有覆盖<code>toString()</code>方法时， <code>Object.toString()</code>的原始输出。
	 * 
	 * <pre>
	 * ObjectUtil.identityToString(null, &quot;NULL&quot;)            = &quot;NULL&quot;
	 * ObjectUtil.identityToString(&quot;&quot;, &quot;NULL&quot;)              = &quot;java.lang.String@1e23&quot;
	 * ObjectUtil.identityToString(Boolean.TRUE, &quot;NULL&quot;)    = &quot;java.lang.Boolean@7fa&quot;
	 * ObjectUtil.identityToString(new int[0], &quot;NULL&quot;)      = &quot;int[]@7fa&quot;
	 * ObjectUtil.identityToString(new Object[0], &quot;NULL&quot;)   = &quot;java.lang.Object[]@7fa&quot;
	 * </pre>
	 * 
	 * @param object
	 *            对象
	 * @param nullStr
	 *            如果对象为<code>null</code>，则返回该字符串
	 * 
	 * @return 对象的identity，如果对象是<code>null</code>，则返回指定字符串
	 */
	public static String identityToString(Object object, String nullStr) {
		if (object == null) {
			return nullStr;
		}

		return appendIdentityToString(null, object).toString();
	}

	/**
	 * 将对象自身的identity――如同对象没有覆盖<code>toString()</code>方法时， <code>Object.toString()</code>的原始输出――追加到<code>StringBuffer</code>中。
	 * 
	 * <pre>
	 * ObjectUtil.appendIdentityToString(*, null)            = null
	 * ObjectUtil.appendIdentityToString(null, &quot;&quot;)           = &quot;java.lang.String@1e23&quot;
	 * ObjectUtil.appendIdentityToString(null, Boolean.TRUE) = &quot;java.lang.Boolean@7fa&quot;
	 * ObjectUtil.appendIdentityToString(buf, Boolean.TRUE)  = buf.append(&quot;java.lang.Boolean@7fa&quot;)
	 * ObjectUtil.appendIdentityToString(buf, new int[0])    = buf.append(&quot;int[]@7fa&quot;)
	 * ObjectUtil.appendIdentityToString(buf, new Object[0]) = buf.append(&quot;java.lang.Object[]@7fa&quot;)
	 * </pre>
	 * 
	 * @param buffer
	 *            <code>StringBuffer</code>对象，如果是<code>null</code>，则创建新的
	 * @param object
	 *            对象
	 * 
	 * @return <code>StringBuffer</code>对象，如果对象为<code>null</code>，则返回 <code>null</code>
	 */
	public static StringBuffer appendIdentityToString(StringBuffer buffer, Object object) {
		if (object == null) {
			return null;
		}

		if (buffer == null) {
			buffer = new StringBuffer();
		}

		buffer.append(ClassUtils.getClassNameForObject(object));

		return buffer.append('@').append(Integer.toHexString(identityHashCode(object)));
	}

	/*
	 * ========================================================================= = ==
	 */
	/* 比较对象的类型。 */
	/*
	 * ========================================================================= = ==
	 */

	/**
	 * 检查两个对象是否属于相同类型。<code>null</code>将被看作任意类型。
	 * 
	 * @param object1
	 *            对象1
	 * @param object2
	 *            对象2
	 * 
	 * @return 如果两个对象有相同的类型，则返回<code>true</code>
	 */
	public static boolean isSameType(Object object1, Object object2) {
		if ((object1 == null) || (object2 == null)) {
			return true;
		}

		return object1.getClass().equals(object2.getClass());
	}

	/*
	 * ========================================================================= = ==
	 */
	/* toString方法。 */
	/*
	 * ========================================================================= = ==
	 */

	/**
	 * 取得对象的<code>toString()</code>的值，如果对象为<code>null</code>，则返回空字符串 <code>""</code>。
	 * 
	 * <pre>
	 * ObjectUtil.toString(null)         = &quot;&quot;
	 * ObjectUtil.toString(&quot;&quot;)           = &quot;&quot;
	 * ObjectUtil.toString(&quot;bat&quot;)        = &quot;bat&quot;
	 * ObjectUtil.toString(Boolean.TRUE) = &quot;true&quot;
	 * ObjectUtil.toString([1, 2, 3])    = &quot;[1, 2, 3]&quot;
	 * </pre>
	 * 
	 * @param object
	 *            对象
	 * 
	 * @return 对象的<code>toString()</code>的返回值，或空字符串<code>""</code>
	 */
	public static String toString(Object object) {
		return (object == null) ? StringUtils.EMPTY_STRING : (object.getClass().isArray() ? ArrayUtils.toString(object) : object.toString());
	}

	/**
	 * 取得对象的<code>toString()</code>的值，如果对象为<code>null</code>，则返回指定字符串。
	 * 
	 * <pre>
	 * ObjectUtil.toString(null, null)           = null
	 * ObjectUtil.toString(null, &quot;null&quot;)         = &quot;null&quot;
	 * ObjectUtil.toString(&quot;&quot;, &quot;null&quot;)           = &quot;&quot;
	 * ObjectUtil.toString(&quot;bat&quot;, &quot;null&quot;)        = &quot;bat&quot;
	 * ObjectUtil.toString(Boolean.TRUE, &quot;null&quot;) = &quot;true&quot;
	 * ObjectUtil.toString([1, 2, 3], &quot;null&quot;)    = &quot;[1, 2, 3]&quot;
	 * </pre>
	 * 
	 * @param object
	 *            对象
	 * @param nullStr
	 *            如果对象为<code>null</code>，则返回该字符串
	 * 
	 * @return 对象的<code>toString()</code>的返回值，或指定字符串
	 */
	public static String toString(Object object, String nullStr) {
		return (object == null) ? nullStr : (object.getClass().isArray() ? ArrayUtils.toString(object) : object.toString());
	}

	/**
	 * 对象的深克隆
	 * 
	 * @param obj
	 *            被克隆的对象
	 * @return 创建的对象副本
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static <T> T deepClone(T obj) {
		// 将对象写到流里

		ObjectInputStream oi = null;
		ByteArrayInputStream bi = null;
		try (ByteArrayOutputStream bo = new ByteArrayOutputStream(); ObjectOutputStream oo = new ObjectOutputStream(bo);) {
			oo.writeObject(obj); // 从流里读出来
			bi = new ByteArrayInputStream(bo.toByteArray());
			oi = new ObjectInputStream(bi);
			return (T) oi.readObject();
		} catch (Exception e) {
			throw new BenchRuntimeException("深拷贝对象异常，返回null,object=" + obj, e);
		} finally {
			if (oi != null) {
				try {
					oi.close();
				} catch (Exception e) {

				}
			}
			if (bi != null) {
				try {
					bi.close();
				} catch (Exception e) {

				}
			}
		}
	}

	/**
	 * 是否为null
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isString(Object object) {
		return object != null && object instanceof String;
	}

	/**
	 * 是否为null
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isObject(Object object) {
		return object == null;
	}

	/**
	 * 将valueList中的每个对象,按propertyName对应的值分组
	 * 
	 * @param valueList
	 * @param propertyName
	 * @return
	 */
	public static <V> Map<Object, List<V>> sortByPropertyValue(List<V> valueList, String propertyName) {
		Map<Object, List<V>> returnMap = new HashMap<Object, List<V>>();
		if (valueList == null) {
			return returnMap;
		}
		for (V v : valueList) {
			Object propertyValue = null;
			try {
				propertyValue = PropertyUtils.getProperty(v, propertyName);
			} catch (Exception e) {
				throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "获取属性值异常,value=" + v + ",propertyName=" + propertyName, e);
			}
			List<V> subList = returnMap.get(propertyValue);
			if (subList == null) {
				subList = new ArrayList<V>();
				returnMap.put(propertyValue, subList);
			}
			subList.add(v);

		}
		return returnMap;
	}

	/**
	 * 拷贝值
	 * 
	 * @param sourceObject
	 * @param targetObject
	 */
	public static void copyValue(Object sourceObject, Object targetObject) {
		if (sourceObject == null || targetObject == null) {
			return;
		}
		for (Field sourceField : FieldUtils.getAllField(sourceObject.getClass())) {
			Field targetField = FieldUtils.getFieldSafe(targetObject.getClass(), sourceField.getName());
			if (targetField == null) {
				continue;
			}
			if (!targetField.getType().isAssignableFrom(sourceField.getType())) {
				continue;
			}
			FieldUtils.setFieldValueSafe(targetObject, targetField, FieldUtils.getFieldValue(sourceField, sourceObject));
		}
	}
}
