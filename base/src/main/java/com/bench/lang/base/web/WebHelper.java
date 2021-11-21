/*
 * zkyoua.com Inc.
 * Copyright (c) 2004-2005 All Rights Reserved.
 */
package com.bench.lang.base.web;

import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.locale.utils.LocaleUtils;
import com.bench.lang.base.number.utils.NumberUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.string.utils.StringEscapeUtils;
import com.bench.lang.base.string.utils.StringUtils;
import com.bench.lang.base.uri.utils.URIUtils;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

/**
 * @author cold
 * 
 * @version $Id: WebHelper.java,v 1.3 2006/02/07 12:39:01 xishi Exp $
 */
public class WebHelper {

	public static final String REQUEST_ATTRIBUTE_SCHEME = "bench.request.scheme";

	public static final String REQUEST_ATTRIBUTE_SERVER_PORT = "bench.request.server.port";

	public static final String SERVER_SCHEME_HTTP = "http";
	public static final String SERVER_SCHEME_HTTPS = "https";

	public static final int SERVER_PORT_HTTP = 80;
	public static final int SERVER_PORT_HTTPS = 443;

	public static final WebHelper INSTANCE = new WebHelper();

	/**
	 * 从请求参数和referer的header中获取gotoUrl
	 * 
	 * @param webRequest
	 * @return
	 */
	public static final String getGotoUrlWithReferer(HttpServletRequest webRequest) {
		String gotoUrl = webRequest.getParameter("gotoUrl");
		if (StringUtils.isEmpty(gotoUrl)) {
			gotoUrl = webRequest.getParameter("goto");
		}
		if (StringUtils.isEmpty(gotoUrl)) {
			gotoUrl = webRequest.getHeader("Referer");
		}
		return gotoUrl;
	}

	/**
	 * 从请求参数和referer的header中获取gotoUrl
	 * 
	 * @param webRequest
	 * @return
	 */
	public static final String getGotoUrlWithReferer(WebRequest webRequest) {
		String gotoUrl = webRequest.getParameter("gotoUrl");
		if (StringUtils.isEmpty(gotoUrl)) {
			gotoUrl = webRequest.getParameter("goto");
		}
		if (StringUtils.isEmpty(gotoUrl)) {
			gotoUrl = webRequest.getHeader("Referer");
		}
		return gotoUrl;
	}

	/**
	 * 从参数中得到gotoUrl
	 * 
	 * @param webRequest
	 * @return
	 */
	public static final String getGotoUrlFromParam(HttpServletRequest webRequest) {
		String gotoUrl = webRequest.getParameter("gotoUrl");
		if (StringUtils.isEmpty(gotoUrl)) {
			return webRequest.getParameter("goto");
		} else {
			return gotoUrl;
		}
	}

	/**
	 * 从参数中得到gotoUrl
	 * 
	 * @param webRequest
	 * @return
	 */
	public static final String getGotoUrlFromParam(WebRequest webRequest) {
		String gotoUrl = webRequest.getParameter("gotoUrl");
		if (StringUtils.isEmpty(gotoUrl)) {
			return webRequest.getParameter("goto");
		} else {
			return gotoUrl;
		}
	}

	/**
	 * 是否当前根域名下的域名
	 * 
	 * @param gotoUrl
	 * @param rootDomain
	 * @return
	 */
	public static boolean isCurrentRootDomain(String gotoUrl, String rootDomain) {
		if (StringUtils.isEmpty(gotoUrl)) {
			return false;
		}
		if (gotoUrl.indexOf("?") > 0) {
			gotoUrl = gotoUrl.substring(0, gotoUrl.indexOf("?"));
		}
		// 判断是否相同域名
		return URIUtils.isSameRootDomain(gotoUrl, rootDomain);
	}

	public static String getFullRequestURI(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer();
		String scheme = ObjectUtils.toString(request.getAttribute(WebHelper.REQUEST_ATTRIBUTE_SCHEME));
		if (StringUtils.isEmpty(scheme)) {
			scheme = request.getScheme();
		}
		buffer.append(scheme).append("://").append(request.getServerName());

		boolean appendPort = false;
		int port = NumberUtils.toInt(request.getAttribute(WebHelper.REQUEST_ATTRIBUTE_SERVER_PORT));
		if (port == 0) {
			port = request.getServerPort();
		}
		if (scheme.equals(SERVER_SCHEME_HTTP)) {
			appendPort = port != SERVER_PORT_HTTP;
		} else if (scheme.equals(SERVER_SCHEME_HTTPS)) {
			appendPort = port != SERVER_PORT_HTTPS;
		} else {
			appendPort = true;
		}
		if (appendPort) {
			buffer.append(":");
			buffer.append(port);
		}
		buffer.append(request.getRequestURI());

		return buffer.toString();
	}

	/**
	 * 取得当前<code>HttpServletRequest<code>的URL以及参数（除了指定的参数）<br>
	 * 主要适用于从当前页面跳转到其他页面，等其他页面完成工作后再跳转回本页面。<br>
	 * 
	 * @param request
	 * 
	 * @return
	 */
	public static String getRequestURLWithParameters(HttpServletRequest request) {
		String url = getFullRequestURI(request);
		String query = getRequestParameters(request);

		if (StringUtils.isNotBlank(query)) {
			return url + "?" + query;
		} else {
			return url;
		}
	}

	/**
	 * 取得当前<code>HttpServletRequest<code>的URL以及参数（除了指定的参数）<br>
	 * 主要适用于从当前页面跳转到其他页面，等其他页面完成工作后再跳转回本页面。<br>
	 * 
	 * @param request
	 *            当前的访问请求
	 * @param exceptParams
	 *            去除的参数
	 * @return 当前URL+参数字符串
	 */
	public static String getRequestURLWithParameters(HttpServletRequest request, String[] exceptParams) {
		String url = getFullRequestURI(request);
		String query = getRequestParameters(request, exceptParams);

		if (StringUtils.isNotBlank(query)) {
			return url + "?" + query;
		} else {
			return url;
		}
	}

	/**
	 * 取得请求中的所有参数，拼接成一个String.
	 * 
	 * @param request
	 * 
	 * @return
	 */
	public static String getRequestParameters(HttpServletRequest request) {
		return getRequestParameters(request, new String[] {});
	}

	/**
	 * 取得请求中的所有参数(出去exceptParams中指定的参数)，拼接成一个String.
	 * 
	 * @param request
	 * @param exceptParams
	 * @return
	 */
	public static String getRequestParameters(HttpServletRequest request, String[] exceptParams) {
		String query = "";
		StringBuffer sb = new StringBuffer();

		Enumeration<?> paramEnums = request.getParameterNames();

		while (paramEnums.hasMoreElements()) {
			String parmName = (String) paramEnums.nextElement();
			String parmValue = request.getParameter(parmName);

			try {
				if (StringUtils.isNotBlank(parmName) && !ArrayUtils.contains(exceptParams, parmName)) {
					// 必须删除安全性敏感的参数
					sb.append(parmName + "=" + URLEncoder.encode(parmValue, "GBK") + "&");
				}
			} catch (Exception e) {
				// ignore
			}
		}

		if (sb.length() > 0) {
			int len = sb.length();

			query = sb.substring(0, len - 1); // remove the last &
		} else {
			query = null;
		}

		return query;
	}

	/**
	 * 进行URL编码，使用LocaleUtils中指定的charset。
	 * 
	 * @param str
	 *            要编码的字符串
	 * 
	 * @return 编码后的字符串
	 */
	public String escapeURL(String str) {
		String charset = StringUtils.trimToNull(LocaleUtils.getContext().getCharset());

		try {
			return StringEscapeUtils.escapeURL(str, charset);
		} catch (UnsupportedEncodingException e) {
			return StringEscapeUtils.escapeURL(str);
		}
	}

	/**
	 * Get an attribute from session.
	 * 
	 * <p>
	 * Simple helper method hides the underlying session mechanism.
	 * </p>
	 * 
	 * @param key
	 * @param request
	 * 
	 * @return
	 */
	public Object getSessionAttribute(String key, HttpServletRequest request) {
		return request.getSession().getAttribute(key);
	}

	/**
	 * 设置session中变量的值.
	 * 
	 * @param key
	 * @param value
	 * @param request
	 */
	public void setSessionAttribute(String key, Object value, HttpServletRequest request) {
		if ((request != null) && StringUtils.isNotBlank(key)) {
			request.getSession().setAttribute(key, value);
		}
	}

}
