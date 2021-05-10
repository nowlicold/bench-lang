/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.instance.visitor;

import org.springframework.core.type.classreading.MetadataReader;

/**
 * 
 * @author cold
 *
 * @version $Id: MetadataReaderVisitor.java, v 0.1 2020年4月9日 下午12:06:10 cold Exp $
 */
public interface MetadataReaderVisitor {

	/**
	 * 访问一个metadataReader
	 * 
	 * @param metadataReader
	 * @param interfaceMetadata
	 * @return
	 */
	boolean visit(MetadataReader metadataReader);
}
