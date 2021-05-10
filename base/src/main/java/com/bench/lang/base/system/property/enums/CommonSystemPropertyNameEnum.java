package com.bench.lang.base.system.property.enums;

import com.bench.lang.base.system.property.SystemPropertyName;

/**
 * 通用的系统环境变量名枚举
 * 
 * @author cold
 *
 * @version $Id: CommonSystemPropertyNameEnum.java, v 0.1 2018年4月7日 下午5:32:11 cold Exp $
 */
public enum CommonSystemPropertyNameEnum implements SystemPropertyName {

	;

	protected String message;

	private CommonSystemPropertyNameEnum(String message) {
		this.message = message;
	}

}
