package com.bench.lang.base.json.enums;

import com.bench.common.enums.EnumBase;

/**
 * 变量名模式
 * 
 * @author cold
 *
 * @version $Id: ScriptNameModeEnum.java, v 0.1 2019年7月12日 上午11:08:38 cold Exp $
 */
public enum ScriptNameModeEnum implements EnumBase {

	LOWER_CASE_WITH_UNDERSCORES("小写，用下划线间隔单词"),

	;

	protected String message;

	private ScriptNameModeEnum(String message) {
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
