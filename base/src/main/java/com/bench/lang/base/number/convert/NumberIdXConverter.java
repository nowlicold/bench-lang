/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.number.convert;

import java.math.BigDecimal;
import java.util.Stack;

import com.bench.lang.base.string.utils.StringUtils;

/**
 * 将10进制数字ID，按给定的替换char数组进行x进制转换
 * 
 * @author cold
 * 
 * @version $Id: NumberIdXConverter.java, v 0.1 2012-8-10 下午6:25:49 cold Exp $
 */
public class NumberIdXConverter {

	private BigDecimal x;

	private char[] charArray;

	public NumberIdXConverter(BigDecimal x, char[] charArray) {
		this.x = x;
		this.charArray = charArray;
	}

	/**
	 * 将10进制数字转换为26进制
	 * 
	 * @param id
	 * @return
	 */
	public String convert(long id) {
		return convert(Long.toString(id));
	}

	/**
	 * 将10进制数字转换为26进制
	 * 
	 * @param id
	 * @return
	 */
	public String convert(int id) {
		return convert(Integer.toString(id));
	}

	/**
	 * 将10进制数字转换为26进制，使用字符串作为数字是因为数字可能偏大，超过Long
	 * 
	 * @param id
	 * @return
	 */
	public String convert(String id) {
		return convert(new BigDecimal(id));

	}

	/**
	 * 将10进制数字转换为26进制，使用BigDecimal作为数字是因为数字可能偏大，超过Long
	 * 
	 * @param id
	 * @return
	 */
	public String convert(BigDecimal id) {
		// 克隆1个新的进行处理
		BigDecimal rest = id.add(BigDecimal.ZERO);
		Stack<Character> stack = new Stack<Character>();
		StringBuilder result = new StringBuilder(0);
		while (rest.compareTo(BigDecimal.ZERO) != 0) {
			BigDecimal[] diviedeResult = rest.divideAndRemainder(x);
			stack.add(charArray[diviedeResult[1].intValue()]);
			rest = diviedeResult[0];
		}

		for (; !stack.isEmpty();) {
			result.append(stack.pop());
		}
		return result.toString();

	}

	/**
	 * 还原到数字串
	 * 
	 * @param convertedString
	 * @return
	 */
	public BigDecimal restoreToBigDecimal(String convertedString) {
		if (StringUtils.isEmpty(convertedString)) {
			return new BigDecimal(0);
		}
		BigDecimal multiple = new BigDecimal(1);
		BigDecimal result = new BigDecimal(0);
		Character c;
		for (int i = 0; i < convertedString.length(); i++) {
			c = convertedString.charAt(convertedString.length() - i - 1);
			result = result.add(new BigDecimal(_x_value(c)).multiply(multiple));
			multiple = multiple.multiply(x);
		}
		return result;
	}

	/**
	 * 还原到数字串
	 * 
	 * @param convertedString
	 * @return
	 */
	public String restoreToString(String convertedString) {
		return restoreToBigDecimal(convertedString).toString();
	}

	/**
	 * 还原到数字串
	 * 
	 * @param convertedString
	 * @return
	 */
	public long restoreToLong(String convertedString) {
		return restoreToBigDecimal(convertedString).longValue();
	}

	/**
	 * 还原到数字串
	 * 
	 * @param convertedString
	 * @return
	 */
	public int restoreToInt(String convertedString) {
		return restoreToBigDecimal(convertedString).intValue();
	}

	private int _x_value(Character c) {
		for (int i = 0; i < charArray.length; i++) {
			if (c == charArray[i]) {
				return i;
			}
		}
		return -1;
	}

}
