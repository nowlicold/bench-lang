package com.bench.lang.base.json.enums;


import com.bench.common.enums.EnumBase;

/**
 * json数据类型
 * 
 * @author chenbug
 *
 * @version $Id: JsonDataTypeEnum.java, v 0.1 2019年7月12日 上午11:08:38 chenbug Exp $
 */
public enum JsonDataTypeEnum implements EnumBase {

	STRING("字符串"),

	NUMBER("数字"),

	BOOLEAN("布尔"),

	OBJECT("对象"),

	ARRAY("数组"),

	NULL("空");

	protected String message;

	private JsonDataTypeEnum(String message) {
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
