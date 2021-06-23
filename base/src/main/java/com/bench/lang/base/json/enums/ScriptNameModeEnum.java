package com.bench.lang.base.json.enums;

import com.bench.lang.base.enums.EnumBase;

/**
 * 变量名模式
 * 
 * @author chenbug
 *
 * @version $Id: ScriptNameModeEnum.java, v 0.1 2019年7月12日 上午11:08:38 chenbug Exp $
 */
public enum ScriptNameModeEnum implements EnumBase {

	LOWER_CASE_WITH_UNDERSCORES("小写，用下划线间隔单词"),

	;

	protected String message;

	private ScriptNameModeEnum(String message) {
		this.message = message;
	}

}
