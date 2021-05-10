/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.clasz.scanner;

import java.io.IOException;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 * 接口类型匹配
 * 
 * @author cold
 *
 * @version $Id: InterfaceTypeFilter.java, v 0.1 2020年3月31日 上午11:54:44 cold Exp $
 */
public class InterfaceTypeFilter extends AssignableTypeFilter {

	public InterfaceTypeFilter(Class<?> targetType) {
		super(targetType);
	}

	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
		// TODO Auto-generated method stub
		return super.match(metadataReader, metadataReaderFactory);
	}

}
