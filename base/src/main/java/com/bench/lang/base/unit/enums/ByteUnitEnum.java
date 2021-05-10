/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.unit.enums;

import com.bench.lang.base.enums.EnumBase;

/**
 * 字节单位枚举
 * 
 * @author cold
 *
 * @version $Id: ByteUnitEnum.java, v 0.1 2020年5月29日 下午2:23:29 cold Exp $
 */
public enum ByteUnitEnum implements EnumBase {

	B(1l),

	KB(1024),

	MB((long) Math.pow(1024, 2)),

	GB((long) Math.pow(1024, 3)),

	TB((long) Math.pow(1024, 4)),

	PB((long) Math.pow(1024, 5));

	private long size;

	/**
	 * @param size
	 */
	private ByteUnitEnum(long size) {
		this.size = size;
	}

	public long size() {
		return size;
	}
}
