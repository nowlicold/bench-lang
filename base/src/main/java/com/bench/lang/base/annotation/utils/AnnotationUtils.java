package com.bench.lang.base.annotation.utils;

import java.lang.annotation.Annotation;

import com.bench.lang.base.clasz.target.locate.TargetClassLocateManager;
import com.bench.lang.base.clasz.target.locate.TargetClassLocateResult;
import com.bench.lang.base.clasz.utils.ClassUtils;

/**
 * 注解工具类
 * 
 * @author cold
 *
 * @version $Id: AnnotationUtils.java, v 0.1 2018年6月28日 上午10:27:51 cold Exp $
 */
public class AnnotationUtils extends org.springframework.core.annotation.AnnotationUtils {

	/**
	 * 获取注解
	 * 
	 * @param clazz
	 *            注解所在的类
	 * @param annotationClass
	 *            注解类
	 * @param findParent
	 *            是否查找父（父类和接口）
	 * @return
	 */
	public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotationClass) {
		return org.springframework.core.annotation.AnnotationUtils.findAnnotation(clazz, annotationClass);
	}

	/**
	 * 从注解数组中，根据注解类型获取注解
	 * 
	 * @param <T>
	 * @param annotations
	 * @param annotationClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T getAnnotation(Annotation[] annotations, Class<T> annotationClass) {
		if (annotations == null) {
			return null;
		}
		for (Annotation anno : annotations) {
			if (anno.annotationType() == annotationClass) {
				return (T) anno;
			}
		}
		return null;
	}

	/**
	 * 获取被注解的类,因为可能是当前类或者父类被注解了
	 * 
	 * @param clazz
	 *            注解所在的类
	 * @param annotationClass
	 *            注解类
	 * @param findParent
	 *            是否查找父（父类和接口）
	 * @return
	 */
	public static <T extends Annotation> Class<?> getAnnotatedClass(Class<?> clazz, Class<T> annotationClass) {
		T ano = clazz.getAnnotation(annotationClass);
		if (ano != null) {
			return clazz;
		}
		Class<?> parentClasz = clazz.getSuperclass();
		if (parentClasz != null && parentClasz != Object.class) {
			Class<?> annotationClasz = getAnnotatedClass(parentClasz, annotationClass);
			if (annotationClasz != null) {
				return annotationClasz;
			}
		}
		if (!clazz.isInterface()) {
			for (Class<?> interfaceClass : clazz.getInterfaces()) {
				ano = interfaceClass.getAnnotation(annotationClass);
				if (ano != null) {
					return interfaceClass;
				}
			}
		}
		return null;
	}

	/**
	 * 获取注解
	 * 
	 * @param clazz
	 *            注解所在的类
	 * @param annotationClass
	 *            注解类
	 * @param findParent
	 *            是否查找父（父类和接口）
	 * @return
	 */
	public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotationClass, boolean findParent) {
		if (!findParent) {
			return clazz.getAnnotation(annotationClass);
		}
		for (Class<?> c : ClassUtils.getClassHierarchy(clazz)) {
			if (c.getAnnotation(annotationClass) != null) {
				return c.getAnnotation(annotationClass);
			}
		}
		return null;
	}

	/**
	 * 获取被注解的类
	 * 
	 * @param clazz
	 *            注解所在的类
	 * @param annotationClass
	 *            注解类
	 * @param findParent
	 *            是否查找父（父类和接口）
	 * @return
	 */
	public static <T extends Annotation> Class<?> getAnnotatedClass(Class<?> clazz, Class<T> annotationClass, boolean findParent) {
		if (!findParent) {
			T ano = clazz.getAnnotation(annotationClass);
			if (ano != null) {
				return clazz;
			}
		}
		for (Class<?> c : ClassUtils.getClassHierarchy(clazz)) {
			if (c.getAnnotation(annotationClass) != null) {
				return c;
			}
		}
		return null;
	}

	/**
	 * 获取注解
	 * 
	 * @param obj
	 *            注解所在对象，因为可能是AOP代理的，如果被代理，只能通过这钟方式获取到实际类
	 * @param annotationClass
	 *            注解类
	 * @param findParent
	 *            是否插座父（父类和接口）
	 * @return
	 */
	public static <T extends Annotation> T getAnnotation(Object obj, Class<T> annotationClass, boolean findParent) {
		TargetClassLocateResult locateResult = TargetClassLocateManager.getInstance().locate(obj);
		Class<?> clazz = locateResult.getTargetClass();
		return getAnnotation(clazz, annotationClass, findParent);
	}

	/**
	 * 返回被注解的类
	 * 
	 * @param obj
	 * @param annotationClass
	 * @param findParent
	 * @return
	 */
	public static <T extends Annotation> Class<?> getAnnotatedClass(Object obj, Class<T> annotationClass, boolean findParent) {
		TargetClassLocateResult locateResult = TargetClassLocateManager.getInstance().locate(obj);
		Class<?> clazz = locateResult.getTargetClass();
		return getAnnotatedClass(clazz, annotationClass, findParent);
	}

}
