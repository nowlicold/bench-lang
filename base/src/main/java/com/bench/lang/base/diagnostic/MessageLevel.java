package com.bench.lang.base.diagnostic;

import com.bench.common.enums.EnumBase;

/**
 * 信息级别
 * 
 * @author cold
 * @version $Id: MessageLevel.java,v 0.1 2009-5-21 上午12:45:28 cold Exp $
 */
public enum MessageLevel implements EnumBase {

	//
	NO_MESSAGE("无信息"),
	//
	BRIEF_MESSAGE("简洁信息"),
	//
	DETAILED_MESSAGE("详细信息");

	private String message;

	private MessageLevel(String message) {
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dq.common.enums.EnumBase#message()
	 */
	public String message() {
		// TODO Auto-generated method stub
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dq.common.enums.EnumBase#value()
	 */
	public Number value() {
		// TODO Auto-generated method stub
		return 0;
	}

}
