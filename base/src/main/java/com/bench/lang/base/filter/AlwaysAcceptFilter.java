/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.filter;

/**
 * 永远接受
 * 
 * @author cold
 *
 * @version $Id: AlwaysAcceptFilter.java, v 0.1 2018年10月25日 下午5:31:54 cold Exp $
 */
public class AlwaysAcceptFilter implements Filter<Object> {

	public static final AlwaysAcceptFilter INSTANCE = new AlwaysAcceptFilter();

	@Override
	public boolean accept(Object object) {
		// TODO Auto-generated method stub
		return true;
	}

}
