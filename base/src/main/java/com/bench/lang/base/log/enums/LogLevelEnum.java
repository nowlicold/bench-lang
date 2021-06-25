package com.bench.lang.base.log.enums;

import com.bench.common.enums.EnumBase;

/**
 * 日志等级
 * 
 * @author cold
 *
 * @version $Id: LogLevelEnum.java, v 0.1 2020年3月23日 下午5:56:03 cold Exp $
 */
public enum LogLevelEnum implements EnumBase {

	OFF(0),

	ERROR(1),

	WARN(2),

	INFO(3),

	DEBUG(4),

	TRACE(5),

	;

	protected int value;

	/**
	 * @param value
	 */
	private LogLevelEnum(int value) {
		this.value = value;
	}

	public static LogLevelEnum valueOf(int value) {
		for (LogLevelEnum level : LogLevelEnum.values()) {
			if (level.value == value) {
				return level;
			}
		}
		return null;
	}

	@Override
	public String message() {
		return null;
	}

	public Number value() {
		return value;
	}

	/**
	 * 获取更低一级的日志级别
	 * 
	 * @return
	 */
	public LogLevelEnum getLowerLevel() {
		int lowerValue = this.value + 1;
		return valueOf(lowerValue);
	}

	/**
	 * 获取更高一级的日志级别
	 * 
	 * @return
	 */
	public LogLevelEnum getHeigherLevel() {
		int lowerValue = this.value - 1;
		return valueOf(lowerValue);
	}

}
