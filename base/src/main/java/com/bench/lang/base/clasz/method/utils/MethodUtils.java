/**
 * 
 */
package com.bench.lang.base.clasz.method.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.bench.lang.base.clasz.field.utils.FieldUtils;
import com.bench.lang.base.exception.BenchRuntimeException;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * @author cold
 * 
 */
public class MethodUtils extends org.apache.commons.lang3.reflect.MethodUtils {

	/**
	 * 得到get方法名
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String getGetMethod(String fieldName) {
		return "get" + StringUtils.toFirstCharUpperCase(fieldName);
	}

	/**
	 * 调用方法
	 * 
	 * @param object
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invoke(Object object, Method method, Object args) {
		try {
			return method.invoke(object, args);
		} catch (Exception e) {
			throw new BenchRuntimeException("调用方法异常,object=" + object + ",method=" + method + ",args=" + args, e);
		}
	}

	/**
	 * 调用方法
	 * 
	 * @param object
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invoke(Object object, Method method) {
		try {
			return method.invoke(object);
		} catch (Exception e) {
			throw new BenchRuntimeException("调用方法异常,object=" + object + ",method=" + method, e);
		}
	}

	/**
	 * 获取声明的方法
	 * 
	 * @param clasz
	 * @param methodName
	 * @return
	 */
	public static Method getDeclaredMethod(Class<?> clasz, String methodName) {
		try {
			return clasz.getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * 获取声明的方法
	 * 
	 * @param clasz
	 * @param methodName
	 * @return
	 */
	public static Method getMethod(Class<?> clasz, String methodName) {
		try {
			return clasz.getMethod(methodName);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * 获取最近的方法
	 * 
	 * @param clasz
	 * @param methodName
	 * @return
	 */
	public static Method getNearstMethod(Class<?> clasz, Method method) {
		Class<?> targetClasz = clasz;
		while (targetClasz != null) {
			Method targetMethod = getMethod(clasz, method.getName(), method.getParameterTypes());
			if (targetMethod != null) {
				return targetMethod;
			}
			targetClasz = targetClasz.getSuperclass();
		}
		return null;
	}

	/**
	 * 获取声明的方法
	 * 
	 * @param clasz
	 * @param methodName
	 * @return
	 */
	public static Method getMethod(Class<?> clasz, String methodName, Class<?>... parameterTypes) {
		try {
			return clasz.getMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	public static Method getDeclaredMethod(Class<?> clasz, String methodName, boolean findParent) {
		try {
			return clasz.getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
			if (findParent && clasz.getSuperclass() != null) {
				return getDeclaredMethod(clasz.getSuperclass(), methodName, findParent);
			}
			return null;
		}
	}

	public static Method getDeclaredMethod(Class<?> clasz, String methodName, Class<?>[] parameterTypes, boolean findParent) {
		try {
			return clasz.getDeclaredMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			if (findParent && clasz.getSuperclass() != null) {
				return getDeclaredMethod(clasz.getSuperclass(), methodName, parameterTypes, findParent);
			}
			return null;
		}
	}

	/**
	 * 得到get方法
	 * 
	 * @param clasz
	 * @param fieldName
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static Method getGetMethod(Class<?> clasz, String fieldName) throws NoSuchMethodException {
		return clasz.getMethod(getGetMethod(fieldName), null);
	}

	/**
	 * 获取当前类继承的接口上的含有annotationClass注解的方法
	 * 
	 * @param field
	 * @return
	 */
	public static List<Method> getInterfaceMethodContainsAnnotation(Class<?> clasz, Class<? extends Annotation> annotationClass) {
		List<Method> methodList = new ArrayList<Method>();
		for (Class<?> interfaceClass : clasz.getInterfaces()) {
			for (Method method : interfaceClass.getMethods()) {
				if (method.getAnnotation(annotationClass) != null) {
					methodList.add(method);
				}
			}
		}
		return methodList;
	}

	/**
	 * 是否Getter类method
	 * 
	 * @param method
	 */
	public static boolean isGetterMethod(Method method) {
		return FieldUtils.getGetterField(method) != null;
	}

}
