package com.bench.lang.base;


import com.bench.lang.base.regexp.RegexValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Ip验证工具类
 * 
 * @author cold
 * 
 * @version $Id: IpValidateUtils.java, v 0.1 2013-10-30 下午3:21:07 cold Exp $
 */
public class IpValidateUtils {

	public static final IpValidateUtils INSTANCE = new IpValidateUtils();

	private static final int IPV4_MAX_OCTET_VALUE = 255;

	private static final int MAX_UNSIGNED_SHORT = 0xffff;

	private static final int BASE_16 = 16;

	private static final String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";

	// Max number of hex groups (separated by :) in an IPV6 address
	private static final int IPV6_MAX_HEX_GROUPS = 8;

	// Max hex digits in each IPv6 group
	private static final int IPV6_MAX_HEX_DIGITS_PER_GROUP = 4;

	private final static RegexValidator ipv4Validator = new RegexValidator(IPV4_REGEX);

	/**
	 * 是否IPV4地址
	 * 
	 * @param inet4Address
	 * @return
	 */
	public static boolean isIpV4(String inet4Address) {
		// verify that address conforms to generic IPv4 format
		String[] groups = ipv4Validator.match(inet4Address);

		if (groups == null) {
			return false;
		}

		// verify that address subgroups are legal
		for (String ipSegment : groups) {
			if (ipSegment == null || ipSegment.length() == 0) {
				return false;
			}

			int iIpSegment = 0;

			try {
				iIpSegment = Integer.parseInt(ipSegment);
			} catch (NumberFormatException e) {
				return false;
			}

			if (iIpSegment > IPV4_MAX_OCTET_VALUE) {
				return false;
			}

			if (ipSegment.length() > 1 && ipSegment.startsWith("0")) {
				return false;
			}

		}

		return true;
	}

	public boolean isIpV6(String inet6Address) {
		boolean containsCompressedZeroes = inet6Address.contains("::");
		if (containsCompressedZeroes && (inet6Address.indexOf("::") != inet6Address.lastIndexOf("::"))) {
			return false;
		}
		if ((inet6Address.startsWith(":") && !inet6Address.startsWith("::")) || (inet6Address.endsWith(":") && !inet6Address.endsWith("::"))) {
			return false;
		}
		String[] octets = inet6Address.split(":");
		if (containsCompressedZeroes) {
			List<String> octetList = new ArrayList<String>(Arrays.asList(octets));
			if (inet6Address.endsWith("::")) {
				// String.split() drops ending empty segments
				octetList.add("");
			} else if (inet6Address.startsWith("::") && !octetList.isEmpty()) {
				octetList.remove(0);
			}
			octets = octetList.toArray(new String[octetList.size()]);
		}
		if (octets.length > IPV6_MAX_HEX_GROUPS) {
			return false;
		}
		int validOctets = 0;
		int emptyOctets = 0; // consecutive empty chunks
		for (int index = 0; index < octets.length; index++) {
			String octet = octets[index];
			if (octet.length() == 0) {
				emptyOctets++;
				if (emptyOctets > 1) {
					return false;
				}
			} else {
				emptyOctets = 0;
				// Is last chunk an IPv4 address?
				if (index == octets.length - 1 && octet.contains(".")) {
					if (!isIpV4(octet)) {
						return false;
					}
					validOctets += 2;
					continue;
				}
				if (octet.length() > IPV6_MAX_HEX_DIGITS_PER_GROUP) {
					return false;
				}
				int octetInt = 0;
				try {
					octetInt = Integer.parseInt(octet, BASE_16);
				} catch (NumberFormatException e) {
					return false;
				}
				if (octetInt < 0 || octetInt > MAX_UNSIGNED_SHORT) {
					return false;
				}
			}
			validOctets++;
		}
		if (validOctets > IPV6_MAX_HEX_GROUPS || (validOctets < IPV6_MAX_HEX_GROUPS && !containsCompressedZeroes)) {
			return false;
		}
		return true;
	}
}
