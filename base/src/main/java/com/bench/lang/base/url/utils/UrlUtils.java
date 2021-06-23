/**
 * created since 2009-7-16
 */
package com.bench.lang.base.url.utils;

import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.bench.lang.base.string.utils.StringUtils;
import com.yuan.common.enums.error.CommonErrorCodeEnum;
import com.yuan.common.exception.BenchRuntimeException;

/**
 * url工具类
 * 
 * @author duwei
 * @version $Id: UrlUtils.java,v 0.1 2009-7-16 下午08:58:32 duwei Exp $
 */
public class UrlUtils {

	public static final UrlUtils INSTANCE = new UrlUtils();

	public static String getRootUrl(String schema, String domainName, int port) {
		StringBuffer buf = new StringBuffer();
		buf.append(schema).append("://").append(domainName);
		if (port != 80) {
			buf.append(":").append(port);
		}
		return buf.toString();
	}

	/**
	 * 链接URL
	 * 
	 * @param paths
	 * @return
	 */
	public static String join(String... paths) {
		StringBuffer buf = new StringBuffer();
		for (String path : paths) {
			if (buf.length() > 0) {
				if (path.startsWith(StringUtils.SLASH_SIGN)) {
					path = path.substring(1);
				}
			}
			if (StringUtils.isEmpty(path)) {
				continue;
			}
			buf.append(path);
			if (!path.endsWith(StringUtils.SLASH_SIGN)) {
				buf.append(StringUtils.SLASH_SIGN);
			}
		}
		// 如果最后一个路径不是以/结束的，则删除最后的/
		if (buf.length() > 0 && !StringUtils.endsWith(paths[paths.length - 1], StringUtils.SLASH_SIGN)) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * 编码URL
	 * 
	 * @param url
	 * @param encoding
	 * @return
	 */
	public static String encode(String url, String encoding) {
		try {
			return URLEncoder.encode(url, encoding);
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"编码url异常，url=" + url + ",encoding=" + encoding, e);
		}
	}

	/**
	 * 解码URL
	 * 
	 * @param url
	 * @param encoding
	 * @return
	 */
	public static String decode(String url, String encoding) {
		try {
			return URLDecoder.decode(url, encoding);
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR,"解码url异常，url=" + url + ",encoding=" + encoding);
		}
	}

	/**
	 * 解析URL
	 * 
	 * @param url
	 * @return
	 */
	public static URL parse(String url) {
		if (StringUtils.isEmpty(url)) {
			return null;
		}
		try {
			return new URL(url);
		} catch (Exception e) {
			throw new BenchRuntimeException(CommonErrorCodeEnum.SYSTEM_ERROR, "解析url异常，url=" + url);
		}
	}
}
