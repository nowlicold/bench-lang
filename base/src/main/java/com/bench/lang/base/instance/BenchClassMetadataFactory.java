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
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.type.classreading.MetadataReader;

import com.bench.lang.base.Constants;
import com.bench.lang.base.clasz.scanner.ClassScanner;
import com.bench.lang.base.clasz.visit.ClassMetadataVisitor;
import com.bench.lang.base.instance.acceptor.MetadataReaderAcceptor;
import com.bench.lang.base.instance.acceptor.SimpleMetadataReaderAcceptor;
import com.bench.lang.base.instance.comparator.MetadataReaderOrderComparator;
import com.bench.lang.base.list.utils.ListUtils;
import com.bench.lang.base.utils.Assert;

/**
 * Bench类元数据工厂
 * 
 * @author cold
 *
 * @version $Id: BenchClassMetadataFactory.java, v 0.1 2020年3月30日 下午6:00:03 cold Exp $
 */
public class BenchClassMetadataFactory {

	private static List<MetadataReader> benchClassMetadataReaders = new ArrayList<MetadataReader>();

	/**
	 * 接口和实现了接口的类元数据映射
	 */
	private static Map<Class<?>, List<MetadataReader>> interfaceWithImplementsClassesMap = new ConcurrentHashMap<Class<?>, List<MetadataReader>>();

	/**
	 * 接口和实现了接口的类元数据映射
	 */
	private static Map<Class<? extends Annotation>, List<MetadataReader>> annotationWithImplementsClassesMap = new ConcurrentHashMap<Class<? extends Annotation>, List<MetadataReader>>();

	static {
		/**
		 * 采集所有类元数据
		 */
		ClassScanner.scan(Constants.BENCH_JAVA_PACKAGE_PREFIX, new ClassMetadataVisitor() {
			@Override
			public void visit(MetadataReader metadataReader) {
				// TODO Auto-generated method stub
				benchClassMetadataReaders.add(metadataReader);
			}
		});

	}

	/**
	 * 返回实现了interfaceClass接口的所有类元数据<br>
	 * 
	 * @param interfaceClass
	 * @param includeAbstract
	 * @return
	 */

	public static List<MetadataReader> getImplementsClassMetadata(Class<?> interfaceClass) {
		// 从缓存map获取
		List<MetadataReader> returnList = interfaceWithImplementsClassesMap.get(interfaceClass);
		if (returnList != null) {
			return returnList;
		}
		// 没有，则进行初始化
		returnList = getImplementsClassMetadata(interfaceClass, null);
		List<MetadataReader> exsited = interfaceWithImplementsClassesMap.putIfAbsent(interfaceClass, returnList);
		if (exsited != null) {
			return exsited;
		}
		return returnList;
	}

	/**
	 * 返回包含注解annotationClass的实现类的元数据<br>
	 * 
	 * @param interfaceClass
	 * @param includeAbstract
	 * @return
	 */

	public static List<MetadataReader> getImplementsClassMetadataWithAnnotation(Class<? extends Annotation> annotationClass) {
		// 从缓存map获取
		List<MetadataReader> returnList = annotationWithImplementsClassesMap.get(annotationClass);
		if (returnList != null) {
			return returnList;
		}
		// 没有，则进行初始化
		returnList = getImplementsClassMetadata(null, annotationClass);
		List<MetadataReader> exsited = annotationWithImplementsClassesMap.putIfAbsent(annotationClass, returnList);
		if (exsited != null) {
			return exsited;
		}
		return returnList;
	}

	/**
	 * 返回实现了interfaceClass接口的所有类元数据，类包含注解annotationClass<br>
	 * interfaceClass和annotationClass不能同时为null
	 * 
	 * @param interfaceClass
	 *            不为null，则判断
	 * @param annotationClass
	 *            不为null，则判断
	 * @param includeAbstract
	 *            是否包含抽象类
	 * @return
	 */
	public static List<MetadataReader> getImplementsClassMetadata(Class<?> interfaceClass, Class<? extends Annotation> annotationClass) {
		return getImplementsClassMetadata(null, interfaceClass, annotationClass);
	}

	/**
	 * 返回实现了interfaceClass接口的所有类元数据，类包含注解annotationClass<br>
	 * interfaceClass和annotationClass不能同时为null
	 * 
	 * @param packageNames
	 *            符合这些包名，不为null，则判断
	 * @param interfaceClass
	 *            不为null，则判断
	 * @param annotationClass
	 *            不为null，则判断
	 * @param includeAbstract
	 *            是否包含抽象类
	 * @return
	 */
	public static List<MetadataReader> getImplementsClassMetadata(List<String> packageNames, Class<?> interfaceClass, Class<? extends Annotation> annotationClass) {
		Assert.isTrue(interfaceClass != null || annotationClass != null, "interfaceClass和annotationClass不能都为null");
		SimpleMetadataReaderAcceptor acceptor = new SimpleMetadataReaderAcceptor();
		if (interfaceClass != null) {
			acceptor.setInterfaceClasses(ListUtils.toList(interfaceClass));
		}
		if (annotationClass != null) {
			acceptor.setAnnotationClasses(ListUtils.toList(annotationClass));
		}
		acceptor.setPackageNames(packageNames);
		return getByAcceptor(acceptor);
	}

	/**
	 * 根据acceptor获取类元数据集合
	 * 
	 * @param acceptor
	 * @return
	 */
	public static List<MetadataReader> getByAcceptor(MetadataReaderAcceptor acceptor) {
		List<MetadataReader> matchedMetadataList = new ArrayList<MetadataReader>();
		benchClassMetadataReaders.forEach(p -> {
			if (acceptor.accept(p)) {
				matchedMetadataList.add(p);
			}
		});
		// 按照Order注解排序
		Collections.sort(matchedMetadataList, MetadataReaderOrderComparator.INSTANCE);
		return matchedMetadataList;
	}

}
