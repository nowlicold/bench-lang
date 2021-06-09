/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base;

import com.bench.lang.base.number.utils.NumberUtils;
import com.bench.lang.base.string.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证验证
 * 
 * @author chenbug
 * 
 * @version $Id: IDCardNoValidator.java, v 0.1 2013-5-28 下午5:22:24 chenbug Exp $
 */
public class IDCardNoValidator {

	static Map<String, String> AREA_MAP = new HashMap<String, String>();
	static {
		AREA_MAP.put("11", "北京");
		AREA_MAP.put("12", "天津");
		AREA_MAP.put("13", "河北");
		AREA_MAP.put("14", "山西");
		AREA_MAP.put("15", "内蒙古");
		AREA_MAP.put("21", "辽宁");
		AREA_MAP.put("22", "吉林");
		AREA_MAP.put("23", "黑龙江");
		AREA_MAP.put("31", "上海");
		AREA_MAP.put("32", "江苏");
		AREA_MAP.put("33", "浙江");
		AREA_MAP.put("34", "安徽");
		AREA_MAP.put("35", "福建");
		AREA_MAP.put("36", "江西");
		AREA_MAP.put("37", "山东");
		AREA_MAP.put("41", "河南");
		AREA_MAP.put("42", "湖北");
		AREA_MAP.put("43", "湖南");
		AREA_MAP.put("44", "广东");
		AREA_MAP.put("45", "广西");
		AREA_MAP.put("46", "海南");
		AREA_MAP.put("50", "重庆");
		AREA_MAP.put("51", "四川");
		AREA_MAP.put("52", "贵州");
		AREA_MAP.put("53", "云南");
		AREA_MAP.put("54", "西藏");
		AREA_MAP.put("61", "陕西");
		AREA_MAP.put("62", "甘肃");
		AREA_MAP.put("63", "青海");
		AREA_MAP.put("64", "宁夏");
		AREA_MAP.put("65", "新疆");
		AREA_MAP.put("71", "台湾");
		AREA_MAP.put("81", "香港");
		AREA_MAP.put("82", "澳门");
		AREA_MAP.put("91", "国外");
	}

	/**
	 * 身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
	 * 八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
	 * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位）
	 * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。 4、顺序码（第十五位至十七位）
	 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数）
	 * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, , 16 ，先对前17位数字的权求和
	 * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
	 * 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0
	 * X 9 8 7 6 5 4 3 2
	 */

	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 * @throws ParseException
	 */
	public static boolean validate(String IDStr) {
		IDStr = StringUtils.toLowerCase(IDStr);
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			// 身份证号码长度应该为15位或18位
			return false;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (!NumberUtils.isDigits(Ai)) {
			// "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return false;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			// "身份证生日无效。";
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				// "身份证生日不在有效范围。";
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			// "身份证月份无效";
			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			// "身份证日期无效";
			return false;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		if (AREA_MAP.get(Ai.substring(0, 2)) == null) {
			// "身份证地区编码错误。";
			return false;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				// "身份证无效，不是合法的身份证号码";
				return false;
			}
		} else {
			return true;
		}
		// =====================(end)=====================
		return true;
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

}
