/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.enums;

import com.bench.common.enums.EnumBase;

/**
 * 
 * @author cold
 *
 * @version $Id: BenchStartupTypeEnum.java, v 0.1 2019年12月24日 下午6:22:43 cold Exp $
 */
public enum BenchStartupTypeEnum implements EnumBase {

	BENCH_APP,

	JUNIT,

	PROJECT_GEN,

	DAL_GEN,
	;

	@Override
	public String message() {
		return null;
	}

	@Override
	public Number value() {
		return null;
	}
}
