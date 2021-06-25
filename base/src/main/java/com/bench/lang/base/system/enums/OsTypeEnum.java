package com.bench.lang.base.system.enums;

import com.bench.common.enums.EnumBase;

/**
 * 
 * 操作系统类型
 * 
 * @author cold
 *
 * @version $Id: OsTypeEnum.java, v 0.1 2019年12月18日 下午1:59:22 cold Exp $
 */
public enum OsTypeEnum implements EnumBase {

	AIX,

	HP_UX,

	IRIX,

	LINUX,

	SOLARIS,

	MAC,

	OS2,

	SUN_OS,

	WINDOWS,

	UNKNOWN,;

	@Override
	public String message() {
		return null;
	}

	@Override
	public Number value() {
		return null;
	}
}
