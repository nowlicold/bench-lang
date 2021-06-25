package com.bench.lang.base.system.environment.enums;

import com.bench.lang.base.system.environment.SystemEnvName;

/**
 * 通用的统环境变量名枚举
 * 
 * @author cold
 *
 * @version $Id: CommonSystemEnvNameEnum.java, v 0.1 2018年4月7日 下午5:32:11 cold Exp $
 */
public enum CommonSystemEnvNameEnum implements SystemEnvName {

	BENCH_OS_HOSTED_CONTAINER_TYPE("操作系统所在的宿主容器类型，如果不设置，则自动识别"),

	BENCH_MOBILE_PHONE_NUMBER_STARTS_WITH("移动手机号起始值"),

	BENCH_TEMP_PATH("bench临时目录"),;

	;

	protected String message;

	private CommonSystemEnvNameEnum(String message) {
		this.message = message;
	}

	@Override
	public String message() {
		return this.message;
	}

	@Override
	public Number value() {
		return null;
	}
}
