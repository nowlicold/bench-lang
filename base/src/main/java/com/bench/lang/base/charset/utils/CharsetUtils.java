package com.bench.lang.base.charset.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.mozilla.universalchardet.UniversalDetector;

import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.charset.CharsetConstants;
import com.bench.lang.base.string.utils.StringUtils;
import com.bench.lang.base.utils.Assert;

/**
 * charset工具类
 * 
 * @author cold
 *
 * @version $Id: CharsetUtils.java, v 0.1 2018年8月1日 下午3:54:29 cold Exp $
 */
public class CharsetUtils {

	/**
	 * 是不是UTF-8编码
	 * 
	 * @param data
	 * @return
	 */
	public static final boolean isUtf8Charset(byte[] data) {
		return StringUtils.equalsIgnoreCase(getCharset(data), CharsetConstants.UTF_8.name());
	}

	/**
	 * 是不是UTF-8编码
	 * 
	 * @param data
	 * @return
	 */
	public static final boolean isGBKCharset(byte[] data) {
		return StringUtils.equalsIgnoreCase(getCharset(data), CharsetConstants.GBK.name());
	}

	/**
	 * 检测编码<br>
	 * 注意，data长度过低时，检测不正确
	 * 
	 * @param data
	 * @param defaultCharset
	 * @return
	 */
	public static String getCharset(byte[] data, String defaultCharset) {
		if (ArrayUtils.getLength(data) == 0) {
			return defaultCharset;
		}
		UniversalDetector encDetector = new UniversalDetector(null);
		encDetector.handleData(data, 0, data.length);
		encDetector.dataEnd();
		try {
			return StringUtils.defaultString(encDetector.getDetectedCharset(), defaultCharset);
		} finally {
			encDetector.reset();
		}
	}

	/**
	 * 检测inputStream的编码
	 * 
	 * @param inputStream
	 * @param defaultCharset
	 * @return
	 * @throws IOException
	 */
	public static String getCharset(InputStream inputStream, String defaultCharset) throws IOException {
		Assert.notNull(inputStream, "inputstream不能为空");
		return StringUtils.defaultString(UniversalDetector.detectCharset(inputStream), defaultCharset);
	}

	/**
	 * 检测文件编码
	 * 
	 * @param file
	 * @param defaultCharset
	 * @return
	 * @throws IOException
	 */
	public static String getCharset(File file, String defaultCharset) throws IOException {
		Assert.notNull(file, "file不能为空");
		return StringUtils.defaultString(UniversalDetector.detectCharset(file), defaultCharset);
	}

	/**
	 * 检测文件编码
	 * 
	 * @param filePath
	 * @param defaultCharset
	 * @return
	 * @throws IOException
	 */
	public static String getCharset(String filePath, String defaultCharset) throws IOException {
		Assert.notNull(filePath, "filePath不能为空");
		return getCharset(new File(filePath), defaultCharset);
	}

	/**
	 * 检测编码<br>
	 * 注意，data长度过低时，检测不正确
	 * 
	 * @param data
	 * @return
	 */
	public static String getCharset(byte[] data) {
		return getCharset(data, null);
	}
}
