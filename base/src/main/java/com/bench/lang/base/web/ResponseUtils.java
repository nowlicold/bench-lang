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
package com.bench.lang.base.web;

import com.bench.lang.base.bool.utils.BooleanUtils;
import com.bench.lang.base.clasz.field.utils.FieldUtils;
import com.bench.lang.base.number.utils.NumberUtils;
import com.bench.lang.base.object.utils.ObjectUtils;
import com.bench.lang.base.web.cookie.CookieParseUtils;
import com.bench.lang.base.web.cookie.HeaderNameEnum;

import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * RequestUtils
 * 
 * @author cold
 */
public final class ResponseUtils {

	public static final Map<String, Collection<String>> getHeaderMap(HttpServletResponse response) {
		Map<String, Collection<String>> dataMap = new HashMap<String, Collection<String>>();
		for (String headerName : response.getHeaderNames()) {
			dataMap.put(headerName, response.getHeaders(headerName));
		}
		return dataMap;
	}

	public static final List<Cookie> getCookieMap(HttpServletResponse response) {
		List<Cookie> returnList = new ArrayList<Cookie>();
		if (response instanceof ServletResponseWrapper) {
			Object realResponse = response;
			int count = 0;
			while (count < 20 && realResponse instanceof ServletResponseWrapper) {
				realResponse = ((ServletResponseWrapper) response).getResponse();
			}

			// 如果是undertow
			if (realResponse.getClass().getName().equals("io.undertow.servlet.spec.HttpServletResponseImpl")) {
				Object exchange = FieldUtils.getFieldValue(realResponse, "exchange");
				Map<?, ?> cookieMap = (Map<?, ?>) FieldUtils.getFieldValue(exchange, "responseCookies");
				if (cookieMap != null) {
					for (Map.Entry<?, ?> entry : cookieMap.entrySet()) {
						// io.undertow.server.handlers.CookieImpl
						Object undertowCookie = entry.getValue();
						if (undertowCookie.getClass().getName().equals("io.undertow.server.handlers.CookieImpl")) {
							Cookie cookie = new Cookie(ObjectUtils.toString(FieldUtils.getFieldValue(undertowCookie, "name")),
									ObjectUtils.toString(FieldUtils.getFieldValue(undertowCookie, "value")));
							cookie.setComment(ObjectUtils.toString(FieldUtils.getFieldValue(undertowCookie, "comment")));
							cookie.setDomain(ObjectUtils.toString(FieldUtils.getFieldValue(undertowCookie, "domain")));
							cookie.setHttpOnly(BooleanUtils.toBoolean(FieldUtils.getFieldValue(undertowCookie, "httpOnly")));
							Object maxAge = FieldUtils.getFieldValue(undertowCookie, "maxAge");
							if (maxAge != null) {
								cookie.setMaxAge(NumberUtils.toInt(maxAge));
							}
							cookie.setPath(ObjectUtils.toString(FieldUtils.getFieldValue(undertowCookie, "path")));
							cookie.setSecure(BooleanUtils.toBoolean(FieldUtils.getFieldValue(undertowCookie, "secure")));
							cookie.setVersion(NumberUtils.toInt(FieldUtils.getFieldValue(undertowCookie, "version")));
							returnList.add(cookie);
						}
						// io.undertow.servlet.spec.ServletCookieAdaptor
						else if (undertowCookie.getClass().getName().equals("io.undertow.servlet.spec.ServletCookieAdaptor")) {
							Cookie cookie = (Cookie) FieldUtils.getFieldValue(undertowCookie, "cookie");
							returnList.add(cookie);
						}
					}
				}
			}
		}

		// 从Header中获取Cookie
		Collection<String> headerCookies = response.getHeaders(HeaderNameEnum.SET_COOKIE.headerName());
		if (headerCookies != null) {
			for (String headerCookie : headerCookies) {
				List<com.bench.lang.base.web.cookie.Cookie> parsedHeaderCookie = CookieParseUtils.parseFromSetCookieHeader(headerCookie);
				parsedHeaderCookie.forEach(p -> {
					returnList.add(p);
				});
			}
		}
		return returnList;
	}

}
