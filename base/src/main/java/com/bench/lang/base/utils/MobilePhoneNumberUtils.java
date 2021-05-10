package com.bench.lang.base.utils;

import org.apache.commons.lang3.math.NumberUtils;

import com.bench.lang.base.instance.annotations.Singleton;
import com.bench.lang.base.string.utils.StringUtils;
import com.bench.lang.base.system.environment.enums.CommonSystemEnvNameEnum;
import com.bench.lang.base.system.utils.SystemUtils;

/**
 * 手机号工具类
 * 
 * @author cold
 * 
 * @version $Id: MobilePhoneNumberUtils.java, v 0.1 2011-8-17 上午11:34:01 cold Exp $
 */
@Singleton
public class MobilePhoneNumberUtils {

	/**
	 * 手机号长度
	 */
	private static final int LENGTH = 11;

	public static final String[] DEFULT_STARTS_WITH = { "133", "149", "153", "173", "177", "180", "181", "189", "199", "130", "131", "132", "145", "155", "156", "166",
			"171", "175", "176", "185", "186", "166", "134", "135", "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", "172", "178", "182",
			"183", "184", "187", "188", "198" };

	/** 虚拟手机号码的起始值 */
	public static final String VIRTUAL_STAERTS_WITH = "9";

	/**
	 * 得到手机号的起始号段
	 * 
	 * @return
	 */
	public static final String[] getStartsWith() {
		String mobilePhoneStartsWith = SystemUtils.getPropOrEnv(CommonSystemEnvNameEnum.BENCH_MOBILE_PHONE_NUMBER_STARTS_WITH.name());
		if (StringUtils.isEmpty(mobilePhoneStartsWith)) {
			return DEFULT_STARTS_WITH;
		}
		return StringUtils.trim(StringUtils.split(mobilePhoneStartsWith, StringUtils.COMMA_SIGN));
	}

	/**
	 * 是否正确的手机号，不包括虚拟号
	 * 
	 * @param mobilePhoneNumber
	 * @return
	 */
	public static boolean isValid(String mobilePhoneNumber) {
		return isValid(mobilePhoneNumber, false);
	}

	/**
	 * 是否正确的手机号
	 * 
	 * @param mobilePhoneNumber
	 * @param includeVirtual
	 *            是否包含虚拟号
	 * @return
	 */
	public static boolean isValid(String mobilePhoneNumber, boolean includeVirtual) {
		// 空
		if (mobilePhoneNumber == null) {
			return false;
		}
		// 非数字
		if (!NumberUtils.isDigits(mobilePhoneNumber)) {
			return false;
		}
		// 长度是否11
		if (mobilePhoneNumber.length() != LENGTH) {
			return false;
		}
		// 如果是虚拟号
		if (includeVirtual && StringUtils.startsWith(mobilePhoneNumber, VIRTUAL_STAERTS_WITH)) {
			return true;
		}
		// 是否以规定的数字开始
		for (String startsWith : getStartsWith()) {
			if (StringUtils.startsWith(mobilePhoneNumber, startsWith)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否虚拟号
	 * 
	 * @param mobilePhoneNumber
	 * @return
	 */
	public static boolean isVirtual(String mobilePhoneNumber) {
		return StringUtils.length(mobilePhoneNumber) == LENGTH && NumberUtils.isDigits(mobilePhoneNumber)
				&& StringUtils.startsWith(mobilePhoneNumber, VIRTUAL_STAERTS_WITH);
	}
}
