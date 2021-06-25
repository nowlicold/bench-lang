/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.instance;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.classreading.MetadataReader;

import com.bench.lang.base.clasz.utils.ClassUtils;
import com.bench.lang.base.instance.acceptor.MetadataReaderAcceptor;
import com.bench.lang.base.instance.acceptor.SimpleMetadataReaderAcceptor;
import com.bench.lang.base.instance.annotations.Default;
import com.bench.lang.base.instance.utils.MetadataReaderUtils;
import com.bench.lang.base.list.utils.ListUtils;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * Bench类工厂<br>
 * 目的是将运行资源降低到最低，只载入需要的类<br>
 * 
 * @author cold
 *
 * @version $Id: BenchClassFactory.java, v 0.1 2020年3月30日 下午6:00:03 cold Exp $
 */
public class BenchClassFactory {
	/**
	 * 接口和实现了接口的所有类集合映射
	 */
	private static Map<Class<?>, List<Class<?>>> interfaceImplementClassesMap = new ConcurrentHashMap<Class<?>, List<Class<?>>>();

	/**
	 * 注解和实现了接口的所有类集合映射
	 */
	private static Map<Class<? extends Annotation>, List<Class<?>>> annotationImplementClassesMap = new ConcurrentHashMap<Class<? extends Annotation>, List<Class<?>>>();

	/**
	 * 接口和实现了接口的第一个类集合映射
	 */
	private static Map<Class<?>, Class<?>> interfaceFirstImplementClasseMap = new ConcurrentHashMap<Class<?>, Class<?>>();

	/**
	 * 已加载的类名和类映射
	 */
	private static Map<String, Class<?>> loadedClassMap = new ConcurrentHashMap<String, Class<?>>();

	/**
	 * 根据传入的接口，返回实现了这个接口的所有类<br>
	 * 基于缓存
	 * 
	 * @param <T>
	 * @param interfaceClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<Class<? extends T>> getImplementsClasses(Class<T> interfaceClass) {
		List<Class<?>> classList = (List<Class<?>>) interfaceImplementClassesMap.get(interfaceClass);
		if (classList != null) {
			List<Class<? extends T>> returnList = new ArrayList<Class<? extends T>>();
			classList.forEach(p -> returnList.add((Class<T>) p));
			return returnList;
		}
		List<MetadataReader> matchedMetadataList = BenchClassMetadataFactory.getImplementsClassMetadata(interfaceClass);
		classList = getClassesFromMetas(matchedMetadataList);
		List<Class<?>> existing = interfaceImplementClassesMap.putIfAbsent(interfaceClass, classList);
		if (existing != null) {
			classList = existing;
		}
		List<Class<? extends T>> returnList = new ArrayList<Class<? extends T>>();
		classList.forEach(p -> returnList.add((Class<T>) p));
		return returnList;
	}

	/**
	 * 返回包含注解annotationClass的所有实现类<br>
	 * 
	 * @param annotationClass
	 * @return
	 */
	public static List<Class<?>> getImplementsClassWithAnnotation(Class<? extends Annotation> annotationClass) {
		List<Class<?>> classList = (List<Class<?>>) annotationImplementClassesMap.get(annotationClass);
		if (classList != null) {
			return classList;
		}
		List<MetadataReader> matchedMetadataList = BenchClassMetadataFactory.getImplementsClassMetadataWithAnnotation(annotationClass);
		classList = getClassesFromMetas(matchedMetadataList);
		List<Class<?>> existing = annotationImplementClassesMap.putIfAbsent(annotationClass, classList);
		if (existing != null) {
			return existing;
		}
		return classList;
	}

	/**
	 * 返回实现了interfaceClass接口的所有类，类包含注解annotationClass<br>
	 * interfaceClass和annotationClass不能同时为null<br>
	 * 注意，这个方法不使用缓存，因为接口+注解，可能有很多组合
	 * 
	 * @param interfaceClass
	 *            不为null，则判断
	 * @param annotationClass
	 *            不为null，则判断
	 * @return
	 */
	public static List<Class<?>> getImplementsClass(Class<?> interfaceClass, Class<? extends Annotation> annotationClass) {
		List<MetadataReader> matchedMetadataList = BenchClassMetadataFactory.getImplementsClassMetadata(interfaceClass, annotationClass);
		return getClassesFromMetas(matchedMetadataList);
	}

	/**
	 * @param interfaceClass
	 * @param annotationClass
	 * @param annotationValue
	 * @return
	 */
	public static <T> List<Class<? extends T>> getImplementsClasses(Class<T> interfaceClass, Class<? extends Annotation> annotationClass, Object annotationValue) {
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
		return getImplementsClasse(acceptor);
	}

	/**
	 * 根据传入的接口，返回实现了这个接口的第一个实现类<br>
	 * 基于缓存
	 * 
	 * @param <T>
	 * @param interfaceClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getFirstImplementsClass(Class<T> interfaceClass) {
		// 缓存一波，提升性能
		Class<T> implementsClass = (Class<T>) interfaceFirstImplementClasseMap.get(interfaceClass);
		if (implementsClass != null) {
			return (Class<T>) implementsClass;
		}
		implementsClass = (Class<T>) getFirstImplementsClass(interfaceClass, null);
		Class<T> existing = (Class<T>) interfaceFirstImplementClasseMap.putIfAbsent(interfaceClass, implementsClass);
		if (existing != null) {
			return existing;
		}
		return implementsClass;
	}

	/**
	 * 根据传入的接口，返回实现了这个接口的第一个实现类<br>
	 * 基于缓存
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T> List<Class<? extends T>> getImplementsClasse(MetadataReaderAcceptor acceptor) {
		List<MetadataReader> acceptedMetadataList = BenchClassMetadataFactory.getByAcceptor(acceptor);
		return getClassesFromMetas(acceptedMetadataList);
	}

	/**
	 * 获取元数据集合中的类集合
	 * 
	 * @param acceptedMetadataList
	 * @return
	 */
	protected static <T> List<Class<? extends T>> getClassesFromMetas(List<MetadataReader> acceptedMetadataList) {
		List<Class<? extends T>> returnClassList = new ArrayList<Class<? extends T>>();
		for (MetadataReader reader : acceptedMetadataList) {
			returnClassList.add((Class<? extends T>) getClass(reader.getClassMetadata().getClassName()));
		}
		return returnClassList;
	}

	/**
	 * 返回包含注解annotationClass的第一个类<br>
	 * 基于Order排序<br>
	 * 注意，这个方法不使用缓存
	 * 
	 * @return
	 */
	public static Class<?> getFirstImplementsClasseWithAnnotation(Class<? extends Annotation> annotationClass) {
		return getFirstImplementsClass(null, annotationClass);
	}

	/**
	 * 返回实现了interfaceClass接口的第一个类，类包含注解annotationClass<br>
	 * interfaceClass不能为空， annotationClass可以为null <br>
	 * 按如下规则：<br>
	 * 1、如果有Default，则返回Default<br>
	 * 2、无Default，按照Order排序，取第一个<br>
	 * 无缓存
	 * 
	 * @param interfaceClass
	 *            不能为null
	 * @param annotationClass
	 *            不为null，则判断
	 * @return
	 */
	public static Class<?> getFirstImplementsClass(Class<?> interfaceClass, Class<? extends Annotation> annotationClass) {
		List<MetadataReader> matchedMetadataList = BenchClassMetadataFactory.getImplementsClassMetadata(interfaceClass, annotationClass);
		if (matchedMetadataList.size() == 0) {
			return null;
		}
		MetadataReader firstMetadataReader = null;
		// 查找默认
		List<MetadataReader> defaultedMetadataList = new ArrayList<MetadataReader>();
		matchedMetadataList.forEach(p -> {
			// 如果包含Default注解，则加入List
			if (p.getAnnotationMetadata().getAnnotationTypes().contains(Default.class.getName())) {
				defaultedMetadataList.add(p);
			}
		});
		// 如果默认有多个，则报错了
		if (defaultedMetadataList.size() > 1) {
			List<String> defaultedClassNames = new ArrayList<String>();
			defaultedMetadataList.forEach(p -> {
				defaultedClassNames.add(p.getClassMetadata().getClassName());
			});
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"查找到多个包含Default注解的实现类，interfaceClass=" + interfaceClass + ",implementsClasses=" + defaultedClassNames);
		}
		// 刚好一个默认值，则返回它
		if (defaultedMetadataList.size() == 1) {
			firstMetadataReader = defaultedMetadataList.get(0);
		} else if (matchedMetadataList.size() > 0) {
			// 获取第一个
			firstMetadataReader = matchedMetadataList.get(0);
		}
		if (firstMetadataReader != null) {
			return getClass(firstMetadataReader.getClassMetadata().getClassName());
		} else {
			return null;
		}
	}

	public static Class<?> getFirstImplementsClass(Class<?> interfaceClass, Class<? extends Annotation> annotationClass, Object withAnnotationValue) {
		return getFirstImplementsClass(null, interfaceClass, annotationClass, withAnnotationValue);
	}

	/**
	 * 返回实现了interfaceClass接口的第一个类，类包含注解annotationClass<br>
	 * interfaceClass不能为空， annotationClass可以为null <br>
	 * 无缓存
	 * 
	 * @param interfaceClass
	 *            不能为null
	 * @param annotationClass
	 *            不为null，则判断
	 * @return
	 */
	public static Class<?> getFirstImplementsClass(List<String> packagesNames, Class<?> interfaceClass, Class<? extends Annotation> annotationClass,
			Object withAnnotationValue) {
		List<MetadataReader> matchedMetadataList = BenchClassMetadataFactory.getImplementsClassMetadata(packagesNames, interfaceClass, annotationClass);
		if (matchedMetadataList.size() == 0) {
			return null;
		}
		// 根据注解值过滤一次
		if (annotationClass != null && withAnnotationValue != null) {
			List<MetadataReader> byAnnotationClassMatchedList = new ArrayList<MetadataReader>();
			for (MetadataReader metadata : matchedMetadataList) {
				MergedAnnotation<?> nameMergedAnnotation = metadata.getAnnotationMetadata().getAnnotations().get(annotationClass);
				if (!nameMergedAnnotation.isPresent()) {
					continue;
				}
				// 根据注解值过滤一遍
				Object currentAnnotationValue = nameMergedAnnotation.getValue("value").orElseGet(null);
				if (currentAnnotationValue.getClass().isArray()) {
					for (int i = 0; i < Array.getLength(currentAnnotationValue); i++) {
						Object currentSingleAnnotationValue = Array.get(currentAnnotationValue, i);
						if (StringUtils.equals(withAnnotationValue.toString(), currentSingleAnnotationValue.toString())) {
							byAnnotationClassMatchedList.add(metadata);
							break;
						}
					}
				} else if (StringUtils.equals(withAnnotationValue.toString(), currentAnnotationValue.toString())) {
					byAnnotationClassMatchedList.add(metadata);
				}
			}
			matchedMetadataList = byAnnotationClassMatchedList;
		}
		MetadataReader firstMetadataReader = null;
		// 查找默认
		List<MetadataReader> defaultedMetadataList = new ArrayList<MetadataReader>();
		matchedMetadataList.forEach(p -> {
			// 如果包含Default注解，则加入List
			if (p.getAnnotationMetadata().getAnnotationTypes().contains(Default.class.getName())) {
				defaultedMetadataList.add(p);
			}
		});
		// 如果默认有多个，则报错了
		if (defaultedMetadataList.size() > 1) {
			List<String> defaultedClassNames = new ArrayList<String>();
			defaultedMetadataList.forEach(p -> {
				defaultedClassNames.add(p.getClassMetadata().getClassName());
			});
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"查找到多个包含Default注解的实现类，interfaceClass=" + interfaceClass + ",implementsClasses=" + defaultedClassNames);
		}
		// 刚好一个默认值，则返回它
		if (defaultedMetadataList.size() == 1) {
			firstMetadataReader = defaultedMetadataList.get(0);
		} else if (matchedMetadataList.size() > 0) {
			// 获取第一个
			firstMetadataReader = matchedMetadataList.get(0);
		}
		if (firstMetadataReader != null) {
			return getClass(firstMetadataReader.getClassMetadata().getClassName());
		}
		return null;
	}

	/**
	 * 返回实现了interfaceClass接口的第一个类，类包含注解annotationClass<br>
	 * interfaceClass不能为空， annotationClass可以为null <br>
	 * 无缓存
	 *
	 */
	public static Class<?> getFirstImplementsClass(MetadataReaderAcceptor acceptor) {
		List<MetadataReader> matchedMetadataList = BenchClassMetadataFactory.getByAcceptor(acceptor);
		if (matchedMetadataList.size() == 0) {
			return null;
		}
		MetadataReader firstMetadataReader = null;
		// 查找默认
		List<MetadataReader> defaultedMetadataList = new ArrayList<MetadataReader>();
		matchedMetadataList.forEach(p -> {
			// 如果包含Default注解，则加入List
			if (p.getAnnotationMetadata().getAnnotationTypes().contains(Default.class.getName())) {
				defaultedMetadataList.add(p);
			}
		});
		// 如果默认有多个，则报错了
		if (defaultedMetadataList.size() > 1) {
			List<String> defaultedClassNames = new ArrayList<String>();
			defaultedMetadataList.forEach(p -> {
				defaultedClassNames.add(p.getClassMetadata().getClassName());
			});
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"查找到多个包含Default注解的实现类，acceptor=" + acceptor + ",implementsClasses=" + defaultedClassNames);
		}
		// 刚好一个默认值，则返回它
		if (defaultedMetadataList.size() == 1) {
			firstMetadataReader = defaultedMetadataList.get(0);
		} else if (matchedMetadataList.size() > 0) {
			// 获取第一个
			firstMetadataReader = matchedMetadataList.get(0);
		}
		if (firstMetadataReader != null) {
			return getClass(firstMetadataReader.getClassMetadata().getClassName());
		}
		return null;
	}

	/**
	 * 获取类 <br>
	 * 基于缓存
	 * 
	 * @param firstMetadataReader
	 * @return
	 */
	public static Class<?> getClass(String className) {
		// 加载类
		Class<?> clasz = loadedClassMap.get(className);
		if (clasz == null) {
			try {
				clasz = ClassUtils.forName(className);
			} catch (ClassNotFoundException e) {
				throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"无法找到类,class=" + className);
			}
			loadedClassMap.putIfAbsent(className, clasz);
		}
		return clasz;
	}

}
