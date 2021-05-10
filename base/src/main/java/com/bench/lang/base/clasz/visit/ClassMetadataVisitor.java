package com.bench.lang.base.clasz.visit;

import org.springframework.core.type.classreading.MetadataReader;

/**
 * 类元数据访问
 * 
 * @author cold
 *
 * @version $Id: ClassMetadataVisitor.java, v 0.1 2017年4月19日 下午10:10:29 cold Exp $
 */
public interface ClassMetadataVisitor {

	/**
	 * 访问一个类的元数据
	 * @param metadataReader
	 */
	public void visit(MetadataReader metadataReader);

}
