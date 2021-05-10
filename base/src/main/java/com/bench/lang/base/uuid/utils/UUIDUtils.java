package com.bench.lang.base.uuid.utils;

import java.util.UUID;

import com.bench.lang.base.string.utils.StringUtils;

/**
 * UUID工具类
 * 
 * @author cold
 * 
 * @version $Id: UUIDUtils.java, v 0.1 2014-5-29 下午6:14:02 cold Exp $
 */
public class UUIDUtils {

	public static final UUIDUtils INSTANCE = new UUIDUtils();

	/**
	 * 返回简单的randomUUID，去掉-
	 * 
	 * @return
	 */
	public static final String getSimpleRandomUUID() {
		return StringUtils.replace(UUID.randomUUID().toString(), StringUtils.SUB_SIGN, StringUtils.EMPTY_STRING);
	}

	/**
	 * 返回随机randomUUID
	 * 
	 * @return
	 */
	public static final String getRandomUUID() {
		return UUID.randomUUID().toString();
	}
}
