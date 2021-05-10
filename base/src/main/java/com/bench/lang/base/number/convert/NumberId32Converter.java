/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.number.convert;

import java.math.BigDecimal;

/**
 * 将10进制数字ID，按给定的替换char数组进行34进制转换 <br>
 * 34个字符=26英文字母（去掉O，去掉I），2-9数字组成 共8位长度<br>
 * 去掉了0和O是因为人眼识别0和O比较难
 * 
 * @author cold
 * 
 * @version $Id: NumberId32Converter.java, v 0.1 2012-8-10 下午6:25:49 cold Exp
 *          $
 */
public class NumberId32Converter {

	private static final BigDecimal B32 = new BigDecimal(32);

	private static final char[] ARRAY = { 'q', 'm', 'l', '2', '3', '7', '8', '9', '4', '5', '6', 'w', 'e', 'r', 't', 'y', 'u', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j',
			'k', 'z', 'x', 'c', 'v', 'b', 'n' };

	static NumberIdXConverter converter = new NumberIdXConverter(B32, ARRAY);

	/**
	 * 将10进制数字转换为36进制
	 * 
	 * @param id
	 * @return
	 */
	public static String convert(long id) {
		return converter.convert(Long.toString(id));
	}

	/**
	 * 将10进制数字转换为36进制
	 * 
	 * @param id
	 * @return
	 */
	public static String convert(int id) {
		return converter.convert(Integer.toString(id));
	}

	/**
	 * 将10进制数字转换为36进制，使用字符串作为数字是因为数字可能偏大，超过Long
	 * 
	 * @param id
	 * @return
	 */
	public static String convert(String id) {
		return converter.convert(new BigDecimal(id));

	}

	/**
	 * 将10进制数字转换为36进制，使用BigDecimal作为数字是因为数字可能偏大，超过Long
	 * 
	 * @param id
	 * @return
	 */
	public static String convert(BigDecimal id) {
		// 克隆1个新的进行处理
		return converter.convert(id);

	}

	/**
	 * 还原到数字串
	 * 
	 * @param convertedString
	 * @return
	 */
	public static BigDecimal restoreToBigDecimal(String convertedString) {
		return converter.restoreToBigDecimal(convertedString);
	}

	/**
	 * 还原到数字串
	 * 
	 * @param convertedString
	 * @return
	 */
	public static String restoreToString(String convertedString) {
		return converter.restoreToBigDecimal(convertedString).toString();
	}

	/**
	 * 还原到数字串
	 * 
	 * @param convertedString
	 * @return
	 */
	public static long restoreToLong(String convertedString) {
		return converter.restoreToBigDecimal(convertedString).longValue();
	}

	/**
	 * 还原到数字串
	 * 
	 * @param convertedString
	 * @return
	 */
	public static int restoreToInt(String convertedString) {
		return converter.restoreToBigDecimal(convertedString).intValue();
	}

}
