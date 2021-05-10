/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.instance;

import java.lang.annotation.Annotation;
import java.util.List;

import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.classreading.MetadataReader;

import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.clasz.utils.ClassUtils;
import com.bench.lang.base.exception.BenchRuntimeException;
import com.bench.lang.base.instance.acceptor.MetadataReaderAcceptor;
import com.bench.lang.base.instance.utils.MetadataReaderUtils;
import com.bench.lang.base.name.annotations.Name;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * Bench实例工厂<br>
 * 针对接口实现的类，除非需要特定的new，都需要通过这个类来获取实例
 * 
 * @author cold
 *
 * @version $Id: BenchInstanceFactory.java, v 0.1 2020年4月1日 下午6:23:07 cold Exp $
 */
public class BenchInstanceFactory {

	/**
	 * 返回默认的接口实现类实例<br>
	 * 按注解Order排序
	 * 
	 * @return
	 */
	public static <T> T getDefault(Class<T> interfaceClass) {
		return getDefaultWithAnnotation(interfaceClass, null, null, null, null);
	}

	/**
	 * 返回默认的接口实现类实例<br>
	 * 按注解Order排序
	 * 
	 * @return
	 */
	public static <T> T getDefault(Class<T> interfaceClass, Class<?>[] parameterClasses, Object[] parameterValues) {
		return getDefaultWithAnnotation(interfaceClass, parameterClasses, parameterValues, null, null);
	}

	/**
	 * 返回默认的接口实现类实例<br>
	 * 按注解Order排序
	 * 
	 * @return
	 */
	public static <T> T getDefaultWithAnnotation(Class<T> interfaceClass, Class<? extends Annotation> annotationClass, Object annotationValue) {
		return getDefaultWithAnnotation(interfaceClass, null, null, annotationClass, annotationValue);
	}

	/**
	 * 返回默认的接口实现类实例<br>
	 * 按注解Order排序
	 * 
	 * @return
	 */
	public static <T> T getDefaultWithAnnotation(Class<T> interfaceClass, Class<?>[] parameterClasses, Object[] parameterValues,
			Class<? extends Annotation> annotationClass, Object annotationValue) {
		return getDefaultWithAnnotation(interfaceClass, parameterClasses, parameterValues, annotationClass, annotationValue, false);
	}

	/**
	 * 返回默认的接口实现类实例<br>
	 * 按注解Order排序
	 * 
	 * @return
	 */
	public static <T> T getDefaultNullable(Class<T> interfaceClass) {
		return getDefaultWithAnnotation(interfaceClass, null, null, null, null, true);
	}

	/**
	 * 返回默认的接口实现类实例<br>
	 * 按注解Order排序
	 * 
	 * @return
	 */
	public static <T> T getDefaultNullable(Class<T> interfaceClass, Class<?>[] parameterClasses, Object[] parameterValues) {
		return getDefaultWithAnnotation(interfaceClass, parameterClasses, parameterValues, null, null, true);
	}

	/**
	 * 返回默认的接口实现类实例<br>
	 * 按注解Order排序
	 * 
	 * @return
	 */
	public static <T> T getDefaultNullableWithAnnotation(Class<T> interfaceClass, Class<? extends Annotation> annotationClass, Object annotationValue) {
		return getDefaultWithAnnotation(interfaceClass, null, null, annotationClass, annotationValue, true);
	}

	/**
	 * 返回默认的接口实现类实例<br>
	 * 按注解Order排序
	 * 
	 * @return
	 */
	public static <T> T getDefaultNullableWithAnnotation(Class<T> interfaceClass, Class<?>[] parameterClasses, Object[] parameterValues,
			Class<? extends Annotation> annotationClass, Object annotationValue) {
		return getDefaultWithAnnotation(interfaceClass, parameterClasses, parameterValues, annotationClass, annotationValue, true);
	}

	/**
	 * 返回默认的接口实现类实例<br>
	 * 按注解Order排序
	 * 
	 * @return
	 */
	public static <T> T getDefaultWithAnnotation(Class<T> interfaceClass, Class<?>[] parameterClasses, Object[] parameterValues,
			Class<? extends Annotation> annotationClass, Object annotationValue, boolean nullable) {
		T instance = BenchClassInstanceFactory.getFirstImplementsClassInstance(interfaceClass, parameterClasses, parameterValues, annotationClass, annotationValue);
		if (!nullable && instance == null) {
			throw new BenchRuntimeException("获取默认的" + interfaceClass + "异常，不存在" + interfaceClass + "的实现类,parameterClasses=" + ArrayUtils.toString(parameterClasses)
					+ ",annotationClass=" + annotationClass + ",annotationValue=" + annotationValue);
		}
		return instance;
	}

	public static <T> T getDefaultWithAcceptor(Class<?>[] parameterClasses, Object[] parameterValues, MetadataReaderAcceptor acceptor) {
		return getDefaultNullableWithAcceptor(parameterClasses, parameterValues, acceptor, false);
	}

	public static <T> T getDefaultNullableWithAcceptor(Class<?>[] parameterClasses, Object[] parameterValues, MetadataReaderAcceptor acceptor, boolean nullable) {
		T instance = BenchClassInstanceFactory.getFirstImplementsClassInstance(parameterClasses, parameterValues, acceptor);
		if (!nullable && instance == null) {
			throw new BenchRuntimeException("获取默认的实现类异常，acceptor=" + acceptor + ",parameterClasses=" + ArrayUtils.toString(parameterClasses));
		}
		return instance;
	}

	public static <T> T getDefaultWithAcceptor(MetadataReaderAcceptor acceptor) {
		return getDefaultNullableWithAcceptor(null, null, acceptor, false);
	}

	public static <T> T getDefaultNullableWithAcceptor(MetadataReaderAcceptor acceptor, boolean nullable) {
		T instance = BenchClassInstanceFactory.getFirstImplementsClassInstance((Class<?>[]) null, (Object[]) null, acceptor);
		if (!nullable && instance == null) {
			throw new BenchRuntimeException("获取默认的实现类异常，acceptor=" + acceptor);
		}
		return instance;
	}

	/**
	 * 返回了所有实现interfaceClass接口的类实例
	 * 
	 * @param parserType
	 * @return
	 */
	public static <T> List<T> getAll(Class<T> interfaceClass) {
		return BenchClassInstanceFactory.getImplementsClassInstances(interfaceClass);
	}

	/**
	 * 返回了所有实现interfaceClass接口的类实例,包含注解annotationClass，并且注解值为annotationValue
	 * 
	 * @param <T>
	 * @param interfaceClass
	 * @param annotationClass
	 * @param annotationValue
	 * @return
	 */
	public static <T> List<T> getAll(Class<T> interfaceClass, Class<? extends Annotation> annotationClass, Object annotationValue) {
		return BenchClassInstanceFactory.getImplementsClassInstances(interfaceClass, annotationClass, annotationValue);
	}

	/**
	 * 根据名称返回interfaceClass的实现类实例<br>
	 * 注意，这些类实例必须包含Named注解
	 * 
	 * @param parserType
	 * @return
	 */
	public static <T> T getByName(Class<T> interfaceClass, String instanceName) {
		return getByNameWithAnnotation(interfaceClass, null, null, instanceName, null, null);
	}

	/**
	 * 根据名称返回interfaceClass的实现类实例<br>
	 * 注意，这些类实例必须包含Named注解
	 * 
	 * @param parserType
	 * @return
	 */
	public static <T> T getByName(Class<T> interfaceClass, Class<?>[] parameterClasses, Object[] parameterValues, String instanceName) {
		return getByNameWithAnnotation(interfaceClass, parameterClasses, parameterValues, instanceName, null, null);
	}

	/**
	 * 根据名称返回interfaceClass的实现类实例<br>
	 * 注意，这些类实例必须包含Named注解
	 * 
	 * @param parserType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getByNameWithAnnotation(Class<T> interfaceClass, Class<?>[] parameterClasses, Object[] parameterValues, String instanceName,
			Class<? extends Annotation> withAnnotationClass, Object withAnnotationValue) {
		List<MetadataReader> metadataReaders = BenchClassMetadataFactory.getImplementsClassMetadata(interfaceClass);
		MetadataReader matchedMetadataReader = metadataReaders.stream().filter(p -> {
			MergedAnnotation<Name> nameMergedAnnotation = MetadataReaderUtils.getAnnotation(p, Name.class);
			String name = nameMergedAnnotation.isPresent() ? nameMergedAnnotation.getString("value") : ClassUtils.getSimpleName(p.getClassMetadata().getClassName());
			if (!StringUtils.equals(name, instanceName)) {
				return false;
			}
			if (withAnnotationClass != null) {
				MergedAnnotation<?> withAnnotationMergedAnnotation = p.getAnnotationMetadata().getAnnotations().get(withAnnotationClass);
				if (!withAnnotationMergedAnnotation.isPresent()) {
					return false;
				}
				if (withAnnotationValue != null) {
					Object currentAnnotationValue = withAnnotationMergedAnnotation.getString("value");
					if (!withAnnotationValue.equals(currentAnnotationValue)) {
						return false;
					}
				}
			}
			return true;
		}).findFirst().orElse(null);
		if (matchedMetadataReader == null) {
			throw new BenchRuntimeException("无法查找接口的实现类，interfaceClass=" + interfaceClass + "，instanceName=" + instanceName + ",withAnnotationClass="
					+ withAnnotationClass + ",withAnnotationValue=" + withAnnotationValue);
		}
		Class<?> implementsClass = (Class<?>) BenchClassFactory.getClass(matchedMetadataReader.getClassMetadata().getClassName());
		return (T) BenchClassInstanceFactory.getInstance(implementsClass, parameterClasses, parameterValues);
	}

	/**
	 * 返回 clasz 实例,注意，在clasz的getInstance方法里，不能调用这个方法，否则会造成死循环<br>
	 * 如果clasz需提供单例方法，建议方法名使用getSingleton，而不是getInstance<Br>
	 * , 因为这个方法，最终要么是获取instance实例，要么是调用clasz的getInstance方法
	 * 
	 * @param <T>
	 * @param clasz
	 * @return
	 */
	public static <T> T getInstance(Class<T> clasz) {
		return BenchClassInstanceFactory.getInstance(clasz);
	}

	/**
	 * 返回 clasz 实例,注意，在clasz的getInstance方法里，不能调用这个方法，否则会造成死循环<br>
	 * 如果clasz需提供单例方法，建议方法名使用getSingleton，而不是getInstance<Br>
	 * , 因为这个方法，最终要么是获取instance实例，要么是调用clasz的getInstance方法
	 * 
	 * @param <T>
	 * @param clasz
	 * @return
	 */
	public static <T> T getSingleton(Class<T> clasz) {
		return BenchClassInstanceFactory.getSingleton(clasz);
	}

	/**
	 * 返回 clasz 实例,是否调用getInstance方法来获取实例
	 * 
	 * @param <T>
	 * @param clasz
	 * @param callGetInstanceMethod
	 * @return
	 */
	public static <T> T getSingleton(Class<T> clasz, boolean callGetInstanceMethod) {
		return BenchClassInstanceFactory.getSingleton(clasz, callGetInstanceMethod);
	}
}
