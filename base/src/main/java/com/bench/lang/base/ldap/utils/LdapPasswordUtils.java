package com.bench.lang.base.ldap.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.bench.lang.base.base64.utils.BASE64Utils;
import com.bench.lang.base.string.utils.StringUtils;
import com.bench.lang.base.unix.UnixCrypt;
import com.bench.common.enums.error.CommonErrorCodeEnum;
import com.bench.common.exception.BenchRuntimeException;

/**
 * Ldap密码工具类
 * 
 * @author cold
 *
 * @version $Id: LdapPasswordUtils.java, v 0.1 2016年3月28日 下午7:27:32 cold Exp $
 */
public class LdapPasswordUtils {

	public static final String ALGORITHM_MD5 = "MD5";

	public static final String ALGORITHM_SHA = "SHA";

	public static final String ALGORITHM_SSHA = "SSHA";

	public static final String ALGORITHM_SMD5 = "SMD5";

	public static final String ALGORITHM_CRYPT = "CRYPT";

	/**
	 * 验证密码
	 * 
	 * @param digestWithAlgorithm
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean verify(String digestWithAlgorithm, String password) {
		String alg = null;
		int size = 0;
		if (digestWithAlgorithm.regionMatches(true, 0, "{CRYPT}", 0, 7)) {
			digestWithAlgorithm = digestWithAlgorithm.substring(7);
			return UnixCrypt.matches(digestWithAlgorithm, password);
		}
		if (digestWithAlgorithm.regionMatches(true, 0, "{SHA}", 0, 5)) {
			digestWithAlgorithm = digestWithAlgorithm.substring(5);
			alg = "SHA-1";
			size = 20;
		} else if (digestWithAlgorithm.regionMatches(true, 0, "{SSHA}", 0, 6)) {
			digestWithAlgorithm = digestWithAlgorithm.substring(6);
			alg = "SHA-1";
			size = 20;
		} else if (digestWithAlgorithm.regionMatches(true, 0, "{MD5}", 0, 5)) {
			digestWithAlgorithm = digestWithAlgorithm.substring(5);
			alg = "MD5";
			size = 16;
		} else if (digestWithAlgorithm.regionMatches(true, 0, "{SMD5}", 0, 6)) {
			digestWithAlgorithm = digestWithAlgorithm.substring(6);
			alg = "MD5";
			size = 16;
		} else {
			return digestWithAlgorithm.equals(password);
		}

		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance(alg);
		} catch (NoSuchAlgorithmException e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "无法找到算法,alg=" + alg, e);
		}
		byte hs[][] = split(BASE64Utils.decodeToBytes(digestWithAlgorithm), size);
		byte hash[] = hs[0];
		byte salt[] = hs[1];
		msgDigest.reset();
		msgDigest.update(password.getBytes());
		msgDigest.update(salt);
		byte pwhash[] = msgDigest.digest();
		return MessageDigest.isEqual(hash, pwhash);
	}

	/**
	 * 产生摘要
	 * 
	 * @param password
	 * @param algorithm
	 * @return
	 */
	public static String generateDigest(String password, String algorithm) {
		return generateDigest(password, StringUtils.EMPTY_STRING, algorithm);
	}

	/**
	 * 
	 * @param password
	 * @param saltHex
	 * @param algorithm
	 * @return
	 */
	public static String generateDigest(String password, String saltHex, String algorithm) {
		if (algorithm.equalsIgnoreCase("crypt"))
			return "{CRYPT}" + UnixCrypt.crypt(password);
		if (algorithm.equalsIgnoreCase("sha"))
			algorithm = "SHA-1";
		else if (algorithm.equalsIgnoreCase("md5"))
			algorithm = "MD5";
		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "无法找到算法,algorithm=" + algorithm, e);
		}
		byte salt[] = new byte[0];
		if (saltHex != null)
			salt = fromHex(saltHex);
		String label = null;
		if (algorithm.startsWith("SHA"))
			label = salt.length <= 0 ? "{SHA}" : "{SSHA}";
		else if (algorithm.startsWith("MD5"))
			label = salt.length <= 0 ? "{MD5}" : "{SMD5}";
		msgDigest.reset();
		msgDigest.update(password.getBytes());
		msgDigest.update(salt);
		byte pwhash[] = msgDigest.digest();
		StringBuilder digest = new StringBuilder(null == label ? "" : label);
		digest.append(BASE64Utils.encode(concatenate(pwhash, salt)));
		return digest.toString();
	}

	private static byte[] concatenate(byte l[], byte r[]) {
		byte b[] = new byte[l.length + r.length];
		System.arraycopy(l, 0, b, 0, l.length);
		System.arraycopy(r, 0, b, l.length, r.length);
		return b;
	}

	private static byte[][] split(byte src[], int n) {
		byte l[];
		byte r[];
		if (src.length <= n) {
			l = src;
			r = new byte[0];
		} else {
			l = new byte[n];
			r = new byte[src.length - n];
			System.arraycopy(src, 0, l, 0, n);
			System.arraycopy(src, n, r, 0, r.length);
		}
		byte lr[][] = { l, r };
		return lr;
	}

	private static byte[] fromHex(String s) {
		s = s.toLowerCase();
		byte b[] = new byte[(s.length() + 1) / 2];
		int j = 0;
		int nybble = -1;
		for (int i = 0; i < s.length(); i++) {
			int h = hexits.indexOf(s.charAt(i));
			if (h >= 0)
				if (nybble < 0) {
					nybble = h;
				} else {
					b[j++] = (byte) ((nybble << 4) + h);
					nybble = -1;
				}
		}

		if (nybble >= 0)
			b[j++] = (byte) (nybble << 4);
		if (j < b.length) {
			byte b2[] = new byte[j];
			System.arraycopy(b, 0, b2, 0, j);
			b = b2;
		}
		return b;
	}

	public static String hexits = "0123456789abcdef";
}
