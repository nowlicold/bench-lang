/**
 * Project: roma.web
 *
 * File Created at 2009-5-18
 * $Id: RequestUtils.java 10830 2009-05-18 11:07:47Z JadeWang $
 *
 * Copyright 2008 bench.com Croporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Bench Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with bench.com.
 */
package com.bench.lang.base;

import com.bench.lang.base.array.utils.ArrayUtils;
import com.bench.lang.base.map.multi.MultiValuedMap;
import com.bench.lang.base.map.utils.MapUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.string.utils.StringEscapeUtils;
import com.bench.lang.base.string.utils.StringUtils;
import com.bench.lang.base.url.utils.URLCodecUtils;
import com.bench.lang.base.web.WebHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * RequestUtils
 * 
 * @author cold
 */
public final class RequestUtils {

	private static final String JSESSION_URI_FLAG = ";jsession";

	/** goto参数名称 */
	public static final String GOTO_URL = "gotoUrl";

	private static Logger logger = LoggerFactory.getLogger(RequestUtils.class);

	/**
	 * 默认URLEncoding的值
	 */
	public static final String DEFAULT_CHARSET = "GBK";

	public static final String DELIMITER_EQUAL = "=";

	public static final String DELIMITER_AMP = "&";

	/**
	 * 返回url中去掉参数后的地址
	 * 
	 * @param url
	 * @return
	 */
	public static final String getUrlWithoutParameters(String url) {
		return StringUtils.substringBefore(url, StringUtils.QUESTION_MARK);
	}

	/**
	 * 得到referer引用
	 * 
	 * @param request
	 * @return
	 */
	public static final String getUserAgent(HttpServletRequest request) {
		return request.getHeader("user-agent");
	}

	/**
	 * 得到referer引用
	 * 
	 * @param request
	 * @return
	 */
	public static final String getReferer(HttpServletRequest request) {
		String refer = request.getHeader("referer");
		if (StringUtils.isEmpty(refer)) {
			refer = request.getHeader("Referer");
		}
		return refer;
	}

	/**
	 * 得到referer引用
	 * 
	 * @param request
	 * @return
	 */
	public static final String getReferer(WebRequest request) {
		String refer = request.getHeader("referer");
		if (StringUtils.isEmpty(refer)) {
			refer = request.getHeader("Referer");
		}
		return refer;
	}

	/**
	 * 返回header的kv map，如果name相同的有多个，只返回第一个
	 * 
	 * @param request
	 * @return
	 */
	public static final Map<String, String> getHeaderMap(HttpServletRequest request) {
		Map<String, String> dataMap = new HashMap<String, String>();
		Enumeration<?> enums = request.getHeaderNames();
		while (enums.hasMoreElements()) {
			String name = ObjectUtils.toString(enums.nextElement());
			dataMap.put(name, request.getHeader(name));
		}
		return dataMap;
	}

	public static final Map<String, String> getHeaderMapSafe(HttpServletRequest request) {
		try {
			return getHeaderMap(request);
		} catch (Exception e) {
			logger.error("获取Header Map异常", e);
			return null;
		}
	}

	public static final Map<String, String[]> getHeaderValuesMap(HttpServletRequest request) {
		Map<String, String[]> dataMap = new HashMap<String, String[]>();
		Enumeration<?> enums = request.getHeaderNames();
		while (enums.hasMoreElements()) {
			String name = ObjectUtils.toString(enums.nextElement());
			List<String> headerValues = new ArrayList<String>();
			Enumeration<String> headerValueEnumeration = request.getHeaders(name);
			while (headerValueEnumeration.hasMoreElements()) {
				headerValues.add(headerValueEnumeration.nextElement());
			}
			dataMap.put(name, headerValues.toArray(new String[headerValues.size()]));
		}
		return dataMap;
	}

	/**
	 * 返回header的kv map，如果name相同的有多个，只返回第一个
	 * 
	 * @param request
	 * @return
	 */
	public static final Map<String, String> getHeaderMap(WebRequest request) {
		Map<String, String> dataMap = new HashMap<String, String>();
		Iterator<String> enums = request.getHeaderNames();
		while (enums.hasNext()) {
			String name = ObjectUtils.toString(enums.next());
			dataMap.put(name, request.getHeader(name));
		}
		return dataMap;
	}

	/**
	 * 返回header的kv map
	 * 
	 * @param request
	 * @return
	 */
	public static final Map<String, String[]> getHeaderValuesMap(WebRequest request) {
		Map<String, String[]> dataMap = new HashMap<String, String[]>();
		Iterator<String> enums = request.getHeaderNames();
		while (enums.hasNext()) {
			String name = ObjectUtils.toString(enums.next());
			dataMap.put(name, request.getHeaderValues(name));
		}
		return dataMap;
	}

	/**
	 * @param request
	 * @param nameToSimpleMatch
	 * @return
	 */
	public static Map<String, String[]> getParameterByNameSimpleMatch(WebRequest request, String nameToSimpleMatch) {
		Map<String, String[]> dataMap = new HashMap<String, String[]>();
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			if (StringUtils.isSimpleMatch(nameToSimpleMatch, entry.getKey())) {
				dataMap.put(entry.getKey(), entry.getValue());
			}
		}
		return dataMap;
	}

	public static Map<String, Object> getParameterMap(WebRequest request) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 获得POST 过来参数设置到新的params中
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			// 过滤
			String key = ObjectUtils.toString(entry.getKey());
			if (entry.getValue() == null) {
				dataMap.put(key, null);
			}

			if (entry.getValue().getClass().isArray()) {
				String[] values = (String[]) entry.getValue();
				StringBuffer valueBuf = new StringBuffer();
				for (String value : values) {
					valueBuf.append(value).append(",");
				}
				if (valueBuf.length() >= 1) {
					valueBuf = valueBuf.deleteCharAt(valueBuf.length() - 1);
				}
				dataMap.put(key, valueBuf.toString());
			} else {
				dataMap.put(key, entry.getValue());
			}

		}
		return dataMap;
	}

	/**
	 * 获取请求中的kvMap
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getParameterStringMap(WebRequest request) {
		Map<String, String> dataMap = new HashMap<String, String>();
		// 获得POST 过来参数设置到新的params中
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			// 过滤
			String key = ObjectUtils.toString(entry.getKey());
			if (entry.getValue() == null || ArrayUtils.getLength(entry.getValue()) == 0) {
				dataMap.put(key, null);
			}

			if (entry.getValue().getClass().isArray()) {
				String[] values = (String[]) entry.getValue();
				StringBuffer valueBuf = new StringBuffer();
				for (String value : values) {
					valueBuf.append(value).append(",");
				}
				if (valueBuf.length() >= 1) {
					valueBuf = valueBuf.deleteCharAt(valueBuf.length() - 1);
				}
				dataMap.put(key, valueBuf.toString());
			} else {
				dataMap.put(key, entry.getValue()[0]);
			}

		}
		return dataMap;
	}

	public static Map<String, String> getParameterStringMap(ServletRequest request) {
		return MapUtils.convert(getParameterMap(request));
	}

	public static Map<String, Object> getParameterMapSafe(ServletRequest request) {
		try {
			return getParameterMap(request);
		} catch (Exception e) {
			logger.error("获取参数Map异常", e);
			return null;
		}
	}

	/**
	 * 转换为签名用的Map
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getParameterMap(ServletRequest request) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 获得POST 过来参数设置到新的params中
		Map<?, ?> parameterMap = request.getParameterMap();
		for (Map.Entry<?, ?> entry : parameterMap.entrySet()) {
			// 过滤
			String key = ObjectUtils.toString(entry.getKey());
			if (entry.getValue() == null) {
				dataMap.put(key, null);
			}

			if (entry.getValue().getClass().isArray()) {

				String[] values = (String[]) entry.getValue();
				StringBuffer valueBuf = new StringBuffer();
				for (String value : values) {
					valueBuf.append(value).append(",");
				}
				if (valueBuf.length() >= 1) {
					valueBuf = valueBuf.deleteCharAt(valueBuf.length() - 1);
				}
				dataMap.put(key, valueBuf.toString());
			} else {
				dataMap.put(key, entry.getValue());
			}

		}
		return dataMap;
	}

	/**
	 * 将parameters重新组装成query string。
	 * 
	 * @param params
	 * @return query string，如果没有参数，则返回<code>null</code>
	 */
	public static String toQueryString(final Map<String, String[]> params) {
		final StringBuilder buffer = new StringBuilder();

		for (final Map.Entry<String, String[]> entry : params.entrySet()) {
			String key = entry.getKey();
			final Object[] values = entry.getValue();

			if (ArrayUtils.isEmpty(values)) {
				continue;
			}

			key = StringEscapeUtils.escapeURL(key);

			for (Object valueObject : values) {
				if (valueObject == null) {
					valueObject = "";
				}

				if (valueObject instanceof String) {
					if (buffer.length() > 0) {
						buffer.append("&");
					}

					final String value = StringEscapeUtils.escapeURL((String) valueObject);

					buffer.append(key).append("=").append(value);
				}
			}
		}

		if (buffer.length() == 0) {
			return null;
		}

		return buffer.toString();
	}

	/**
	 * 将params中的key和value组装成url参数形式
	 * 
	 * @param params
	 *            Map<String, String> 先要用Collections.sort(keys);
	 * @param charset
	 *            默认Constants.DEFAULT_CHARSET
	 * @param needEncode
	 *            是否对value使用URLEncoder.encode()
	 * @param keepBlankValue
	 *            是否保留空白value到queryString中
	 * @return String "key1=value1&key2=value2&key3=value3....", 默认""
	 */
	@SuppressWarnings("deprecation")
	public final static String changeMapToQueryString(Map<String, String> params, String charset, boolean needEncode, boolean keepBlankValue) {
		if (null == params || params.isEmpty()) {
			return "";
		}
		if (StringUtils.isBlank(charset)) {
			charset = DEFAULT_CHARSET;
		}

		StringBuilder sb = new StringBuilder("");
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		int count = keys.size();

		boolean append = false;
		for (int i = 0; i < count; i++) {
			String key = keys.get(i);
			if (StringUtils.isEmpty(key)) {
				continue;
			}
			String value = params.get(key);
			if (needEncode) {
				try {
					if (StringUtils.isNotBlank(value)) {
						value = URLEncoder.encode(value, charset);
					}
				} catch (UnsupportedEncodingException ex) {
					logger.warn(ex.getMessage());
					if (StringUtils.isNotBlank(value)) {
						value = URLEncoder.encode(value);
					}
				}
			}
			if (StringUtils.isNotBlank(key) && (StringUtils.isNotBlank(value) || keepBlankValue)) {
				// 防止null对象变成"null"
				if (StringUtils.isBlank(value)) {
					value = "";
				}
				sb.append(key).append(DELIMITER_EQUAL).append(value).append(DELIMITER_AMP);
				append = true;
			}
		}
		if (append) {
			sb.deleteCharAt(sb.length() - 1);
		}

		String query = sb.toString();
		return StringUtils.chomp(query, DELIMITER_AMP);
	}

	/**
	 * 将params中的key和value组装成url参数形式(去掉空白的value)
	 * 
	 * @param params
	 *            Map<String, String>
	 * @param charset
	 *            URLEncoder.encode()使用, 默认Constants.DEFAULT_CHARSET
	 * @return String "key1=value1&key2=value2&key3=value3....", 默认""
	 */

	public final static String changeMapToQueryString(Map<String, String> params, String charset) {
		return changeMapToQueryString(params, charset, true, false);
	}

	public final static Map<String, String> changeQueryStringToMap(String query, String charset, boolean needDecode, boolean keepBlankValue) {
		return changeQueryStringToMap(query, charset, needDecode, keepBlankValue, false);
	}

	/**
	 * 将url参数字符串转换成map
	 * 
	 * @param query
	 *            String "key1=value1&key2=(空白value)&key3=value3...."; key的名字不能相同, 否则覆盖
	 * @param charset
	 *            默认Constants.DEFAULT_CHARSET
	 * @param needDecode
	 *            是否对value使用URLDecoder.decode()
	 * @param keepBlankValue
	 *            是否保留空白value到map中
	 * @param withoutUrl
	 *            query中已经是kv参数了，不包含url
	 * @return Map<String, String>, 默认null
	 */
	@SuppressWarnings("deprecation")
	public final static Map<String, String> changeQueryStringToMap(String query, String charset, boolean needDecode, boolean keepBlankValue, boolean withoutUrl) {
		if (StringUtils.isBlank(query)) {
			return null;
		}
		// 如果=号，那肯定不是一个带参数的字符串
		if (StringUtils.indexOf(query, StringUtils.EQUAL_SIGN) < 0) {
			return new HashMap<String, String>(0);
		}
		if (StringUtils.isBlank(charset)) {
			charset = DEFAULT_CHARSET;
		}
		if (!withoutUrl) {
			int idnex = query.indexOf("?");
			if (idnex > 0) {
				query = query.substring(idnex + 1);
			}
		}

		String[] entrys = StringUtils.split(query, DELIMITER_AMP);
		if (null == entrys || 0 == entrys.length) {
			return null;
		}

		Map<String, String> params = new HashMap<String, String>();
		for (String entry : entrys) {
			String[] keyValue = StringUtils.split(entry, DELIMITER_EQUAL, 2);
			if (null == keyValue || 0 == keyValue.length) {
				continue;
			}

			String key = keyValue[0];
			if (1 == keyValue.length) {
				if (keepBlankValue) {
					params.put(key, null);
				}
				continue;
			}

			String value = keyValue[1];
			if (2 == keyValue.length) {
				if (needDecode) {
					try {
						if (StringUtils.isNotBlank(value)) {
							value = URLDecoder.decode(value, charset);
						}
					} catch (UnsupportedEncodingException ex) {
						logger.warn(ex.getMessage());
						if (StringUtils.isNotBlank(value)) {
							value = URLDecoder.decode(value);
						}
					}
				}
				params.put(key, value);
			}
		}

		return params;
	}

	/**
	 * 将url参数字符串转换成map(保留空白的value)
	 * 
	 * @param query
	 *            String "key1=value1&key2=value2&key3=value3...."; key的名字不能相同, 否则覆盖
	 * @param charset
	 *            默认Constants.DEFAULT_CHARSET
	 * @param needDecode
	 *            是否对value使用URLDecoder.decode()
	 * @return Map<String, String>, 默认null
	 */

	public final static Map<String, String> changeQueryStringToMap(String query, String charset, boolean needDecode) {
		return changeQueryStringToMap(query, charset, needDecode, true);
	}

	/**
	 * 将url参数字符串转换成map
	 * 
	 * @param query
	 *            String "key1=value1&key2=value2&key3=value3...."; key的名字不能相同, 否则覆盖
	 * @param charset
	 *            URLDecoder.decode()使用, 默认Constants.DEFAULT_CHARSET
	 * @return Map<String, String>, 默认null
	 */

	public final static Map<String, String> changeQueryStringToMap(String query, String charset) {
		return changeQueryStringToMap(query, charset, true);
	}

	/**
	 * 转换成数据Map
	 * 
	 * @param parameterMap
	 * @return
	 */
	public static Map<String, String> convertToDataMap(Map<?, ?> parameterMap) {
		Map<String, String> dataMap = new HashMap<String, String>();
		// 获得POST 过来参数设置到新的params中
		for (Map.Entry<?, ?> entry : parameterMap.entrySet()) {
			// 过滤
			String key = ObjectUtils.toString(entry.getKey());
			if (entry.getValue() == null) {
				dataMap.put(key, null);
			}

			if (entry.getValue().getClass().isArray()) {

				String[] values = (String[]) entry.getValue();
				StringBuffer valueBuf = new StringBuffer();
				for (String value : values) {
					valueBuf.append(value).append(",");
				}
				if (valueBuf.length() > 1) {
					valueBuf = valueBuf.deleteCharAt(valueBuf.length() - 1);
				}
				dataMap.put(key, valueBuf.toString());
			} else {
				dataMap.put(key, ObjectUtils.toString(entry.getValue()));
			}

		}
		return dataMap;
	}

	/**
	 * 在URL后面添加参数
	 * 
	 * @param url
	 * @param paramterName
	 * @param paramterValue
	 * @param encoding
	 * @return
	 */
	public static String appendParameter(String url, String paramterName, String paramterValue, String encoding) {
		StringBuffer urlBuf = new StringBuffer(url);
		if (url.indexOf("?") > 0) {
			if (!url.endsWith("&")) {
				urlBuf.append("&");
			}
		} else {
			urlBuf.append("?");
		}
		urlBuf.append(paramterName);
		urlBuf.append("=");
		if (!StringUtils.isEmpty(paramterValue)) {
			urlBuf.append(URLCodecUtils.encode(paramterValue, encoding));
		}
		return urlBuf.toString();
	}

	/**
	 * 在URL后面添加参数
	 * 
	 * @param url
	 * @param parameters
	 * @param encoding
	 * @return
	 */
	public static String appendParameters(String url, Map<String, String> parameters, String encoding) {
		return appendParameters(url, parameters, false, encoding);
	}

	/**
	 * 在URL后面添加参数
	 * 
	 * @param url
	 * @param parameters
	 * @param encoding
	 * @return
	 */
	public static String appendParameters(String url, Map<String, ? extends Object> parameters, boolean appendEmpty, String encoding) {
		StringBuffer urlBuf = new StringBuffer(url);
		if (url.indexOf("?") > 0) {
			if (!url.endsWith("&")) {
				urlBuf.append("&");
			}
		} else {
			urlBuf.append("?");
		}
		for (Map.Entry<String, ? extends Object> entry : parameters.entrySet()) {
			// null
			if (entry.getValue() == null) {
				if (!appendEmpty) {
					continue;
				}
				urlBuf.append(entry.getKey());
				urlBuf.append("=");
				urlBuf.append("&");
				continue;
			}
			// 数组
			if (entry.getValue().getClass().isArray()) {
				// 数组为空
				if (ArrayUtils.getLength(entry.getValue()) == 0) {
					if (!appendEmpty) {
						continue;
					}
					urlBuf.append(entry.getKey());
					urlBuf.append("=");
					urlBuf.append("&");
					continue;
				}
				for (int i = 0; i < Array.getLength(entry.getValue()); i++) {
					String value = ObjectUtils.toString(Array.get(entry.getValue(), i));
					if (StringUtils.isEmpty(value) && !appendEmpty) {
						continue;
					}
					urlBuf.append(entry.getKey());
					urlBuf.append("=");
					urlBuf.append(URLCodecUtils.encode(value, encoding));
					urlBuf.append("&");
				}
				continue;
			} else if (Collection.class.isAssignableFrom(entry.getValue().getClass())) {
				Collection<?> valueCollection = (Collection<?>) entry.getValue();
				// 数组为空
				if (valueCollection.size() == 0) {
					if (!appendEmpty) {
						continue;
					}
					urlBuf.append(entry.getKey());
					urlBuf.append("=");
					urlBuf.append("&");
					continue;
				}
				for (Object valueObj : valueCollection) {
					String value = ObjectUtils.toString(valueObj);
					if (StringUtils.isEmpty(value) && !appendEmpty) {
						continue;
					}
					urlBuf.append(entry.getKey());
					urlBuf.append("=");
					urlBuf.append(URLCodecUtils.encode(value, encoding));
					urlBuf.append("&");
				}
				continue;
			}

			// 其他情况，作为字符串处理
			String value = ObjectUtils.toString(entry.getValue());
			if (StringUtils.isEmpty(value) && !appendEmpty) {
				continue;
			}
			urlBuf.append(entry.getKey());
			urlBuf.append("=");
			urlBuf.append(URLCodecUtils.encode(value, encoding));
			urlBuf.append("&");

		}
		char lastChar = urlBuf.charAt(urlBuf.length() - 1);
		if (lastChar == '?' || lastChar == '&') {
			return urlBuf.substring(0, urlBuf.length() - 1);
		} else {
			return urlBuf.toString();
		}

	}

	public static String appendParameters(String url, MultiValuedMap<String, ? extends Object> parameters, boolean appendEmpty, String encoding) {
		StringBuffer urlBuf = new StringBuffer(url);
		if (url.indexOf("?") > 0) {
			if (!url.endsWith("&")) {
				urlBuf.append("&");
			}
		} else {
			urlBuf.append("?");
		}
		for (Map.Entry<String, ? extends Collection<? extends Object>> entry : parameters.asMap().entrySet()) {
			// 集合为空
			if (entry.getValue().size() == 0) {
				if (!appendEmpty) {
					continue;
				}
				urlBuf.append(entry.getKey());
				urlBuf.append("=");
				urlBuf.append("&");
				continue;
			}
			// 遍历集合
			for (Object value : entry.getValue()) {
				String stringValue = ObjectUtils.toString(value);
				if (StringUtils.isEmpty(stringValue) && !appendEmpty) {
					continue;
				}
				urlBuf.append(entry.getKey());
				urlBuf.append("=");
				urlBuf.append(URLCodecUtils.encode(stringValue, encoding));
				urlBuf.append("&");
			}
		}
		char lastChar = urlBuf.charAt(urlBuf.length() - 1);
		if (lastChar == '?' || lastChar == '&') {
			return urlBuf.substring(0, urlBuf.length() - 1);
		} else {
			return urlBuf.toString();
		}

	}

	/**
	 * 获取请求的URI，去掉了jsessionid等信息
	 * 
	 * @param uri
	 * @return
	 */
	public static String getRequestURI(String uri) {
		if (StringUtils.isEmpty(uri)) {
			return StringUtils.EMPTY_STRING;
		}
		final int jsessionPosition = uri.indexOf(JSESSION_URI_FLAG);
		if (jsessionPosition == -1) {
			return uri;
		}
		final int questionMarkPosition = uri.indexOf(StringUtils.COMMA_SIGN);
		if (questionMarkPosition < jsessionPosition) {
			return uri.substring(0, uri.indexOf(JSESSION_URI_FLAG));
		}
		return uri.substring(0, jsessionPosition) + uri.substring(questionMarkPosition);
	}

	/**
	 * 获取请求的URI，去掉了jsessionid等信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestURI(HttpServletRequest request) {
		return getRequestURI(request.getRequestURI());
	}

	/**
	 * 从请求参数和referer的header中获取gotoUrl
	 * 
	 * @param webRequest
	 * @return
	 */
	public static final String getGotoUrlWithReferer(HttpServletRequest webRequest) {
		return WebHelper.getGotoUrlWithReferer(webRequest);
	}

	/**
	 * 从请求参数和referer的header中获取gotoUrl
	 * 
	 * @param webRequest
	 * @return
	 */
	public static final String getGotoUrlWithReferer(WebRequest webRequest) {
		return WebHelper.getGotoUrlWithReferer(webRequest);
	}

	/**
	 * 从请求参数中得到gotoUrl
	 * 
	 * @param webRequest
	 * @return
	 */
	public static final String getGotoUrlFromParam(HttpServletRequest webRequest) {
		return WebHelper.getGotoUrlFromParam(webRequest);
	}

	/**
	 * 从请求参数中得到gotoUrl
	 * 
	 * @param webRequest
	 * @return
	 */
	public static final String getGotoUrlFromParam(WebRequest webRequest) {
		return WebHelper.getGotoUrlFromParam(webRequest);
	}

}
