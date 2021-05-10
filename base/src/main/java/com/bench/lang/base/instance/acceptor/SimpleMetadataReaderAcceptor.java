/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.instance.acceptor;

import org.springframework.core.type.classreading.MetadataReader;

/**
 * 简单的元数据接受器
 * 
 * @author cold
 *
 * @version $Id: SimpleMetadataReaderAcceptor.java, v 0.1 2020年4月7日 上午10:20:31 cold Exp $
 */
public class SimpleMetadataReaderAcceptor extends AbstractMetadataReaderAcceptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.lang.base.instance.acceptor.AbstractMetadataReaderAcceptor#acceptInternal(org.springframework.core.type.classreading.MetadataReader)
	 */
	@Override
	protected boolean acceptInternal(MetadataReader reader) {
		// TODO Auto-generated method stub
		return true;
	}

}
