package com.bench.lang.base.utils;

import com.bench.lang.base.string.utils.StringUtils;

/**
 * @author cold
 * @version $Id: UnicodeUtils.java,v 0.1 2009-5-21 上午12:04:01 cold Exp $
 */
public class UnicodeUtils {

	/**
	 * 字符串编码成Unicode编码
	 */
	public static String encode(String str) throws Exception {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		StringBuffer sb = new StringBuffer();
		for (char c : str.toCharArray()) {
			sb.append("\\u");
			int j = (c >>> 8); // 取出高8位
			String hex = Integer.toHexString(j);
			if (hex.length() == 1)
				sb.append("0");
			sb.append(hex);
			j = (c & 0xFF); // 取出低8位
			hex = Integer.toHexString(j);
			if (hex.length() == 1)
				sb.append("0");
			sb.append(hex);

		}
		return sb.toString();
	}

	/**
	 * 解码unicode
	 * 
	 * @param theString
	 * @return
	 */
	public static String decode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

}
