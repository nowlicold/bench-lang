/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.zip;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 
 * @author cold
 *
 * @version $Id: ZipEntryVisitor.java, v 0.1 2019年6月5日 下午2:45:49 cold Exp $
 */
public interface ZipEntryVisitor {

	/**
	 * 访问一个压缩块
	 * 
	 * @param zipEntry
	 * @param is
	 */
	public void visit(ZipEntry zipEntry, ZipInputStream is) throws IOException;

	/**
	 * 是否继续访问下一个
	 * 
	 * @return
	 */
	public default boolean visitNext() {
		return true;
	}

}
