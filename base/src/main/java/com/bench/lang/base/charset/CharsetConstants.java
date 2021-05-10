package com.bench.lang.base.charset;

import java.nio.charset.Charset;

/**
 * 字符集常量
 * 
 * @author cold
 *
 * @version $Id: CharsetConstants.java, v 0.1 2020年3月18日 下午12:36:03 cold Exp $
 */
public interface CharsetConstants {

	public static final Charset GBK = Charset.forName("GBK");

	public static final Charset GB2312 = Charset.forName("GB2312");

	public static final Charset UTF_8 = Charset.forName("UTF-8");
}
