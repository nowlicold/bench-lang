/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.number.convert;

import java.math.BigDecimal;

/**
 * 将10进制数字ID，按给定的替换char数组进行62进制转换
 * 
 * @author cold
 * 
 * @version $Id: NumberId62Converter.java, v 0.1 2012-8-10 下午6:25:49 cold Exp
 *          $
 */
public class NumberId62Converter {

	private static final BigDecimal B62 = new BigDecimal(62);

	private static final char[] ARRAY = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
			'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G',
			'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M' };

	static NumberIdXConverter converter = new NumberIdXConverter(B62, ARRAY);

	/**
	 * 将10进制数字转换为26进制
	 * 
	 * @param id
	 * @return
	 */
	public static String convert(long id) {
		return converter.convert(Long.toString(id));
	}

	/**
	 * 将10进制数字转换为26进制
	 * 
	 * @param id
	 * @return
	 */
	public static String convert(int id) {
		return converter.convert(Integer.toString(id));
	}

	/**
	 * 将10进制数字转换为26进制，使用字符串作为数字是因为数字可能偏大，超过Long
	 * 
	 * @param id
	 * @return
	 */
	public static String convert(String id) {
		return converter.convert(new BigDecimal(id));

	}

	/**
	 * 将10进制数字转换为26进制，使用BigDecimal作为数字是因为数字可能偏大，超过Long
	 * 
	 * @param id
	 * @return
	 */
	public static String convert(BigDecimal id) {
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
