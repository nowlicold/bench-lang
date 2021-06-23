/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.instance.acceptor;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.yuan.common.enums.error.CommonErrorCodeEnum;
import com.yuan.common.exception.BenchRuntimeException;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.bench.lang.base.clasz.scanner.ClassScanner;
import com.bench.lang.base.clasz.scanner.InterfaceTypeFilter;
import com.bench.lang.base.list.utils.ListUtils;
import com.bench.lang.base.pattern.utils.PatternMatchUtils;
import com.bench.lang.base.string.build.ToStringObject;
import com.bench.lang.base.string.utils.StringUtils;

/**
 * 抽象的类元数据接受器
 * 
 * @author cold
 *
 * @version $Id: AbstractMetadataReaderAcceptor.java, v 0.1 2020年4月7日 上午9:52:39 cold Exp $
 */
public abstract class AbstractMetadataReaderAcceptor extends ToStringObject implements MetadataReaderAcceptor {

	/**
	 * 接口匹配
	 */
	private List<InterfaceTypeFilter> interfaceTypeFilters;

	/**
	 * 注解匹配
	 */
	private List<AnnotationTypeFilter> annotationTypeFilters;

	/**
	 * 包名列表，表示实现类必须在这些包名中
	 */
	private List<String> packageNames = ListUtils.toList("com.bench");

	/**
	 * 接口类集合，如果不为空，则判断元数据是否全部实现了这些接口
	 */
	private List<Class<?>> interfaceClasses;

	/**
	 * 注解集合，如果不为空，判断是否全部包含了这些注解
	 */
	private List<Class<? extends Annotation>> annotationClasses;

	/**
	 * 是否包含抽象类
	 */
	private boolean includeAbstractClass = false;

	/**
	 * 是否包含接口
	 */
	private boolean includeInterface = false;

	public boolean isIncludeAbstractClass() {
		return includeAbstractClass;
	}

	public void setIncludeAbstractClass(boolean includeAbstractClass) {
		this.includeAbstractClass = includeAbstractClass;
	}

	public boolean isIncludeInterface() {
		return includeInterface;
	}

	public void setIncludeInterface(boolean includeInterface) {
		this.includeInterface = includeInterface;
	}

	/**
	 * 内部接受方法
	 * 
	 * @param reader
	 * @return
	 */
	protected abstract boolean acceptInternal(MetadataReader reader);

	@Override
	public boolean accept(MetadataReader t) {
		// TODO Auto-generated method stub
		if (!(includeInterface && t.getClassMetadata().isInterface() || !t.getClassMetadata().isInterface())) {
			return false;
		}
		// 是否抽象类
		if (!(includeAbstractClass && t.getClassMetadata().isAbstract() || !t.getClassMetadata().isAbstract())) {
			return false;
		}
		// 如果是内部类，则
		if (StringUtils.indexOf(t.getClassMetadata().getClassName(), StringUtils.DOLLAR_SIGN) > 0) {
			return false;
		}
		/**
		 * 是否符合接口
		 */
		if (ListUtils.size(interfaceClasses) > 0) {
			initInterfaceTypeFilters();
			for (InterfaceTypeFilter filter : interfaceTypeFilters) {
				try {
					if (!filter.match(t, ClassScanner.getMetadataReaderFactory())) {
						return false;
					}
				} catch (IOException e) {
					throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"判断当前类是否实现接口时异常,MetadataReader=" + t + ",filter" + filter + ",interfaceClass=" + filter.getTargetType(), e);
				}
			}
		}

		/**
		 * 检测包名是否匹配
		 */
		if (ListUtils.size(this.getPackageNames()) > 0) {
			boolean packageMatched = false;
			for (String packageName : this.getPackageNames()) {
				if (!StringUtils.endsWith(packageName, StringUtils.DOT_SIGN)) {
					packageName += StringUtils.DOT_SIGN;
				}
				packageName += StringUtils.ASTERRISK_SIGN;
				packageMatched = PatternMatchUtils.match(packageName, t.getClassMetadata().getClassName());
				if (packageMatched) {
					break;
				}
			}
			if (!packageMatched) {
				return false;
			}
		}

		// 如果要检查注解类
		if (ListUtils.size(annotationClasses) > 0) {
			initAnnotationTypeFilters();
			for (AnnotationTypeFilter filter : annotationTypeFilters) {
				try {
					if (!filter.match(t, ClassScanner.getMetadataReaderFactory())) {
						return false;
					}
				} catch (IOException e) {
					throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"判断当前类是否包含注解时异常,MetadataReader=" + t + ",filter" + filter + ",annotationClass=" + filter.getAnnotationType(), e);
				}
			}
		}
		return acceptInternal(t);
	}

	/**
	 * 初始化类过滤器
	 */
	private void initInterfaceTypeFilters() {
		if (interfaceTypeFilters != null) {
			return;
		}
		synchronized (interfaceClasses) {
			if (interfaceTypeFilters != null) {
				return;
			}
			interfaceTypeFilters = new ArrayList<InterfaceTypeFilter>();
			interfaceClasses.forEach(p -> {
				interfaceTypeFilters.add(new InterfaceTypeFilter(p));
			});
		}
	}

	private void initAnnotationTypeFilters() {
		if (annotationTypeFilters != null) {
			return;
		}
		synchronized (this.annotationClasses) {
			if (annotationTypeFilters != null) {
				return;
			}
			annotationTypeFilters = new ArrayList<AnnotationTypeFilter>();
			annotationClasses.forEach(p -> {
				annotationTypeFilters.add(new AnnotationTypeFilter(p, true, true));
			});
		}
	}

	public List<Class<?>> getInterfaceClasses() {
		return interfaceClasses;
	}

	public void setInterfaceClasses(List<Class<?>> interfaceClasses) {
		this.interfaceClasses = interfaceClasses;
	}

	public List<Class<? extends Annotation>> getAnnotationClasses() {
		return annotationClasses;
	}

	public void setAnnotationClasses(List<Class<? extends Annotation>> annotationClasses) {
		this.annotationClasses = annotationClasses;
	}

	public List<String> getPackageNames() {
		return packageNames;
	}

	public void setPackageNames(List<String> packageNames) {
		this.packageNames = packageNames;
	}
}
