/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.instance;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.classreading.MetadataReader;

import com.bench.lang.base.accept.Acceptor;
import com.bench.lang.base.annotation.utils.AnnotationUtils;
import com.bench.lang.base.clasz.utils.ClassUtils;
import com.bench.lang.base.instance.acceptor.MetadataReaderAcceptor;
import com.bench.lang.base.instance.acceptor.SimpleMetadataReaderAcceptor;
import com.bench.lang.base.instance.annotations.NonSingleton;
import com.bench.lang.base.instance.annotations.Singleton;
import com.bench.lang.base.instance.utils.MetadataReaderUtils;
import com.bench.lang.base.list.utils.ListUtils;
import com.bench.lang.base.order.Ordered;
import com.bench.lang.base.order.comparator.OrderedComparator;
import com.bench.lang.base.utils.ConcurrentUtils;

/**
 * Bench类实例工厂<br>
 * 不直接对外提供服务<br>
 * 目的是将运行资源降低到最低<br>
 * 因为getImplementsClassInstances是根据接口名动态加载实现类，可以保证只载入需要的类。<br>
 * 通过单例方法getSingleton，保证只在使用时才实例化单例化
 * 
 * @author cold
 *
 * @version $Id: BenchClassInstanceFactory.java, v 0.1 2020年3月30日 下午6:00:03 cold Exp $
 */
public class BenchClassInstanceFactory {

	/**
	 * 接口和实现了接口的所有类集合映射
	 */
	private static Map<Class<?>, List<?>> interfaceImplementInstancesMap = new ConcurrentHashMap<Class<?>, List<?>>();

	/**
	 * 注解和包含了注解的所有类集合映射
	 */
	private static Map<Class<? extends Annotation>, List<?>> annotatedInstancesMap = new ConcurrentHashMap<Class<? extends Annotation>, List<?>>();

	/**
	 * 类和单例映射
	 */
	private static final Map<Class<?>, Object> singleInstanceMap = new ConcurrentHashMap<Class<?>, Object>();

	/**
	 * 非单例的类
	 */
	private static Set<Class<?>> nonSingletonClassSet = ConcurrentUtils.newConcurrentHashSet();

	/**
	 * 单例的类
	 */
	private static Set<Class<?>> singletonClassSet = ConcurrentUtils.newConcurrentHashSet();

	/**
	 * 根据传入的接口，返回实现了这个接口的所有类实例
	 * 
	 * @param <T>
	 * @param interfaceClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getImplementsClassInstances(Class<T> interfaceClass) {
		List<T> classIntanceList = (List<T>) interfaceImplementInstancesMap.get(interfaceClass);
		if (classIntanceList != null) {
			return classIntanceList;
		}
		classIntanceList = new ArrayList<T>();
		List<Class<? extends T>> implementsClassList = BenchClassFactory.getImplementsClasses(interfaceClass);
		for (Class<? extends T> implementsClass : implementsClassList) {
			classIntanceList.add(getInstance(implementsClass));
		}
		List<T> existing = (List<T>) interfaceImplementInstancesMap.putIfAbsent(interfaceClass, classIntanceList);
		if (existing != null) {
			return existing;
		}
		if (Ordered.class.isAssignableFrom(interfaceClass)) {
			Collections.sort((List<Ordered>) classIntanceList, OrderedComparator.INSTANCE);
		}
		return classIntanceList;
	}

	/**
	 * 根据传入的接口，返回实现了这个接口的第一个实例
	 * 
	 * @param <T>
	 * @param interfaceClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFirstImplementsClassInstance(Class<T> interfaceClass) {
		Class<?> clasz = BenchClassFactory.getFirstImplementsClass(interfaceClass);
		if (clasz == null) {
			return null;
		}
		return (T) getInstance(clasz);
	}

	public static <T> T getFirstImplementsClassInstance(Class<T> interfaceClass, Class<?>[] parameterClasses, Object[] parameterValues) {
		Class<?> clasz = BenchClassFactory.getFirstImplementsClass(interfaceClass);
		if (clasz == null) {
			return null;
		}
		return (T) getInstance(clasz, parameterClasses, parameterValues);
	}

	/**
	 * 根据传入的接口，返回实现了这个接口的第一个实例
	 * 
	 * @param <T>
	 * @param interfaceClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFirstImplementsClassInstance(Class<T> interfaceClass, Class<? extends Annotation> annotationClass, Object annotationValue) {
		Class<?> clasz = BenchClassFactory.getFirstImplementsClass(interfaceClass, annotationClass, annotationValue);
		if (clasz == null) {
			return null;
		}
		return (T) getInstance(clasz);
	}

	/**
	 * 返回实现了接口，并且包含注解annotationClass，且注解值为annotationValue的所有类实例
	 * 
	 * @param <T>
	 * @param interfaceClass
	 * @param annotationClass
	 * @param annotationValue
	 * @return
	 */
	public static <T> List<T> getImplementsClassInstances(Class<T> interfaceClass, Class<? extends Annotation> annotationClass, Object annotationValue) {
		SimpleMetadataReaderAcceptor acceptor = new SimpleMetadataReaderAcceptor() {
			@Override
			protected boolean acceptInternal(MetadataReader reader) {
				// TODO Auto-generated method stub
				MergedAnnotation<? extends Annotation> mergedAnnotation = MetadataReaderUtils.getAnnotation(reader, annotationClass);
				return mergedAnnotation.getValue("value").get() == annotationValue;
			}
		};
		acceptor.setInterfaceClasses(ListUtils.toList(interfaceClass));
		acceptor.setAnnotationClasses(ListUtils.toList(annotationClass));
		List<Class<?>> classes = BenchClassFactory.getImplementsClasse(acceptor);
		List<T> classIntanceList = new ArrayList<T>(classes.size());
		classes.forEach(clasz -> {
			classIntanceList.add((T) getInstance(clasz));
		});
		return classIntanceList;
	}

	/**
	 * 根据传入的接口，返回实现了这个接口的第一个实例
	 * 
	 * @param <T>
	 * @param interfaceClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFirstImplementsClassInstance(Class<T> interfaceClass, Class<?>[] parameterClasses, Object[] parameterValues,
			Class<? extends Annotation> annotationClass, Object annotationValue) {
		Class<?> clasz = BenchClassFactory.getFirstImplementsClass(interfaceClass, annotationClass, annotationValue);
		if (clasz == null) {
			return null;
		}
		return (T) getInstance(clasz, parameterClasses, parameterValues);
	}

	public static <T> T getFirstImplementsClassInstance(Class<?>[] parameterClasses, Object[] parameterValues, MetadataReaderAcceptor acceptor) {
		Class<?> clasz = BenchClassFactory.getFirstImplementsClass(acceptor);
		if (clasz == null) {
			return null;
		}
		return (T) getInstance(clasz, parameterClasses, parameterValues);
	}

	/**
	 * 根据传入的接口，返回实现了这个接口的第一个实例
	 * 
	 * @param <T>
	 * @param interfaceClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFirstImplementsClassInstance(Class<T> interfaceClass, Acceptor<MetadataReader> acceptor) {

		Class<?> clasz = BenchClassFactory.getFirstImplementsClass(interfaceClass);
		if (clasz == null) {
			return null;
		}
		return (T) getInstance(clasz);
	}

	/**
	 * 根据传入的注解，返回包含了这个注解的所有类实例
	 * 
	 * @param annotationClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> getAnnotatedClassInstances(Class<? extends Annotation> annotationClass) {
		List<Object> classIntanceList = (List<Object>) annotatedInstancesMap.get(annotationClass);
		if (classIntanceList != null) {
			return classIntanceList;
		}
		classIntanceList = new ArrayList<Object>();
		List<Class<?>> implementsClassList = BenchClassFactory.getImplementsClassWithAnnotation(annotationClass);
		for (Class<?> implementsClass : implementsClassList) {
			classIntanceList.add(getInstance(implementsClass));
		}
		List<Object> existing = (List<Object>) annotatedInstancesMap.putIfAbsent(annotationClass, classIntanceList);
		if (existing != null) {
			return existing;
		}
		return classIntanceList;
	}

	/**
	 * 获取类的实例
	 * 
	 * @param <T>
	 * @param clasz
	 * @return
	 */
	public static <T> T getInstance(Class<T> clasz) {
		return getInstance(clasz, null, null);
	}

	/**
	 * 获取clasz的实例
	 * 
	 * @param <T>
	 * @param clasz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> clasz, Class<?>[] parameterClasses, Object[] parameterValues) {
		// 是否单例
		boolean singleton = isSingletonClass(clasz);
		// 非单例，new返回
		if (!singleton) {
			return ClassUtils.newInstance(clasz, parameterClasses, parameterValues);
		}
		T instance = (T) singleInstanceMap.get(clasz);
		if (instance != null) {
			return instance;
		}
		// 锁定类，防止多次初始化
		synchronized (clasz) {
			instance = (T) singleInstanceMap.get(clasz);
			if (instance != null) {
				return instance;
			}
			T newInstance = ClassUtils.getInstance(clasz, false, false);
			instance = (T) singleInstanceMap.putIfAbsent(clasz, newInstance);
			if (instance == null) {
				instance = newInstance;
			}
		}
		return instance;
	}

	/**
	 * 获取单例
	 * 
	 * @param <T>
	 * @param clasz
	 * @return
	 */
	public static <T> T getSingleton(Class<T> clasz) {
		return getSingleton(clasz, null, null);
	}

	/**
	 * 获取单例
	 * 
	 * @param <T>
	 * @param clasz
	 * @return
	 */
	public static <T> T getSingleton(Class<T> clasz, boolean callGetInstanceMethod) {
		return getSingleton(clasz, null, null, callGetInstanceMethod);
	}

	/**
	 * 获取clasz的单例
	 * 
	 * @param <T>
	 * @param clasz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSingleton(Class<T> clasz, Class<?>[] parameterClasses, Object[] parameterValues) {
		return getSingleton(clasz, parameterClasses, parameterValues, true);
	}

	/**
	 * 获取clasz的单例
	 * 
	 * @param <T>
	 * @param clasz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSingleton(Class<T> clasz, Class<?>[] parameterClasses, Object[] parameterValues, boolean callGetInstanceMethod) {
		T instance = (T) singleInstanceMap.get(clasz);
		if (instance == null) {
			T newInstance = ClassUtils.getInstance(clasz, parameterClasses, parameterValues, true, callGetInstanceMethod);
			instance = (T) singleInstanceMap.putIfAbsent(clasz, newInstance);
			if (instance == null) {
				instance = newInstance;
			}
		}
		return instance;
	}

	/**
	 * 是否单例clasz
	 * 
	 * @param clasz
	 * @return
	 */
	public static boolean isSingletonClass(Class<?> clasz) {
		if (singletonClassSet.contains(clasz)) {
			return true;
		}
		if (nonSingletonClassSet.contains(clasz)) {
			return false;
		}

		boolean matchSingleContinue = true;
		boolean singleton = false;
		/**************************************************************
		 * 如果能找到NonSingleton注解，则肯定不是单例,安全第一
		 *************************************************************/
		if (matchSingleContinue && AnnotationUtils.getAnnotation(clasz, NonSingleton.class) != null) {
			matchSingleContinue = false;
			singleton = false;
		}

		/**************************************************************
		 * 如果能找到Singleton注解(包括Inject的Singleton)，则肯定是单例
		 *************************************************************/
		if (matchSingleContinue
				&& (AnnotationUtils.getAnnotation(clasz, Singleton.class) != null || AnnotationUtils.getAnnotation(clasz, javax.inject.Singleton.class) != null)) {
			matchSingleContinue = false;
			singleton = true;
		}
		if (singleton) {
			singletonClassSet.add(clasz);
		} else {
			nonSingletonClassSet.add(clasz);
		}
		return singleton;
	}
}
