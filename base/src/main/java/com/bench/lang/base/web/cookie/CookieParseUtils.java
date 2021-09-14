package com.bench.lang.base.web.cookie;

import com.bench.lang.base.number.utils.NumberUtils;
import com.bench.lang.base.string.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * Cookie解析器
 * 
 * @author chenbug
 *
 * @version $Id: CookieParser.java, v 0.1 2019年8月23日 上午8:17:51 chenbug Exp $
 */
public class CookieParseUtils {

	protected static com.bench.lang.base.web.cookie.Cookie _parseSingleCookieFromHeader(final String headerValue) {
		final String trimmedHeaderValue = headerValue.trim();
		final Integer trimmedHeaderLength = trimmedHeaderValue.length();

		final Integer endOfKeyIndex = trimmedHeaderValue.indexOf("=");
		if (endOfKeyIndex < 0) {
			return null;
		}

		final String key = trimmedHeaderValue.substring(0, endOfKeyIndex);

		final String value;
		final Integer endOfValueIndex;
		{
			Integer startOfValueIndex = endOfKeyIndex + 1;
			if (startOfValueIndex >= trimmedHeaderLength) {
				endOfValueIndex = startOfValueIndex;
				value = trimmedHeaderValue.substring(startOfValueIndex, endOfValueIndex);
			} else {
				final Integer indexOfNextSemicolon = trimmedHeaderValue.indexOf(';', startOfValueIndex);
				final Integer indexOfLastQuotation = trimmedHeaderValue.lastIndexOf('"');
				final Boolean hasSemicolon = (indexOfNextSemicolon >= 0);
				final Boolean isEmpty = (indexOfNextSemicolon.equals(startOfValueIndex));

				final Boolean isQuoted;
				{
					final Boolean nextCharIsQuotation = (trimmedHeaderValue.charAt(startOfValueIndex) == '"');
					final Boolean hasTwoQuotationMarks = (!indexOfLastQuotation.equals(startOfValueIndex));
					isQuoted = (nextCharIsQuotation && hasTwoQuotationMarks);
				}

				if (isEmpty) {
					endOfValueIndex = startOfValueIndex;
				} else if (isQuoted) {
					endOfValueIndex = indexOfLastQuotation;
					startOfValueIndex += 1;
				} else {
					if (hasSemicolon) {
						endOfValueIndex = indexOfNextSemicolon;
					} else {
						endOfValueIndex = trimmedHeaderLength;
					}
				}

				final String unTrimmedValue = trimmedHeaderValue.substring(startOfValueIndex, endOfValueIndex);
				if (isQuoted) {
					value = unTrimmedValue;
				} else {
					value = unTrimmedValue.trim();
				}
			}
		}

		final com.bench.lang.base.web.cookie.Cookie cookie = new com.bench.lang.base.web.cookie.Cookie(key.trim(), value);
		final Integer indexOfNextSemicolon = trimmedHeaderValue.indexOf(';', endOfValueIndex);
		final Boolean hasSemicolon = (indexOfNextSemicolon >= 0);
		if (hasSemicolon) {
			final String[] segments = trimmedHeaderValue.substring(indexOfNextSemicolon).split(";");
			for (final String segment : segments) {
				final String trimmedSegment = segment.trim();
				final Integer indexOfTrimmedSegmentEqualSign = trimmedSegment.indexOf('=');
				final Boolean segmentHasEqualSign = (indexOfTrimmedSegmentEqualSign >= 0);
				if (!segmentHasEqualSign) {
					if (trimmedSegment.equalsIgnoreCase("secure")) {
						cookie.setSecure(true);
					} else if (trimmedSegment.equalsIgnoreCase("httponly")) {
						cookie.setHttpOnly(true);
					}
				} else {
					final String trimmedSegmentKey = trimmedSegment.substring(0, indexOfTrimmedSegmentEqualSign).trim();
					final String trimmedSegmentValue = trimmedSegment.substring(indexOfTrimmedSegmentEqualSign + 1);

					switch (trimmedSegmentKey.toLowerCase()) {
					case "expires":
						cookie.setExpires(trimmedSegmentValue);
						break;
					case "max-age":
						cookie.setMaxAge(NumberUtils.toInt(trimmedSegmentValue));
						break;
					case "domain":
						cookie.setDomain(trimmedSegmentValue);
						break;
					case "path":
						cookie.setPath(trimmedSegmentValue);
						break;
					case "samesite":
						cookie.setSameSiteStrict(StringUtils.equalsIgnoreCase(trimmedSegmentValue, "strict"));
						break;
					}
				}
			}
		}

		return cookie;
	}

	/**
	 * Parses the value of a Set-Cookie header. Accounts for Set-Cookie headers squashed into a single header delimited by commas.
	 */
	public static List<com.bench.lang.base.web.cookie.Cookie> parseFromSetCookieHeader(final String setCookieHeaderValue) {
		final List<com.bench.lang.base.web.cookie.Cookie> cookies = new ArrayList<com.bench.lang.base.web.cookie.Cookie>();

		String previousCookiePart = "";
		for (final String headerValue : setCookieHeaderValue.split(",")) {
			final Integer headerValueLength = headerValue.length();
			if (headerValueLength > 3) {
				final String expiresMatchString = "expires=" + headerValue.substring(headerValueLength - 3, headerValueLength).toLowerCase();
				final Integer matchIndex = headerValue.toLowerCase().lastIndexOf(expiresMatchString);
				if (matchIndex.equals(headerValueLength - expiresMatchString.length())) {
					previousCookiePart = headerValue + ",";
					continue;
				}
			}

			final com.bench.lang.base.web.cookie.Cookie cookie = _parseSingleCookieFromHeader(previousCookiePart + headerValue);
			if (cookie != null) {
				cookies.add(cookie);
			}

			previousCookiePart = "";
		}

		return cookies;
	}

	/**
	 * Parses the value of a Cookie header.
	 */
	public static List<com.bench.lang.base.web.cookie.Cookie> parseFromCookieHeader(final String cookieHeaderValue) {
		final List<com.bench.lang.base.web.cookie.Cookie> cookies = new ArrayList<com.bench.lang.base.web.cookie.Cookie>();

		for (final String keyValuePair : cookieHeaderValue.split(";")) {
			final Integer indexOfEqualsCharacter = keyValuePair.indexOf("=");
			if (indexOfEqualsCharacter < 0) {
				continue;
			}

			final Integer lengthOfKeyValuePair = keyValuePair.length();

			final String key = keyValuePair.substring(0, indexOfEqualsCharacter).trim();
			final String value = keyValuePair.substring(Math.min(indexOfEqualsCharacter + 1, lengthOfKeyValuePair), lengthOfKeyValuePair).trim();

			if (key.isEmpty()) {
				continue;
			}

			cookies.add(new com.bench.lang.base.web.cookie.Cookie(key, value));
		}

		return cookies;
	}

	public static List<String> compileCookiesIntoSetCookieHeaderValues(final List<com.bench.lang.base.web.cookie.Cookie> cookies) {
		final List<String> setCookieHeaderValues = new ArrayList<String>();

		for (final com.bench.lang.base.web.cookie.Cookie cookie : cookies) {
			final StringBuilder stringBuilder = new StringBuilder();

			stringBuilder.append(cookie.getName());
			stringBuilder.append("=");
			stringBuilder.append(cookie.getValue());

			final String expiresDateString = cookie.getExpires();
			if (expiresDateString != null) {
				final String cookieExpiresValue = com.bench.lang.base.web.cookie.CookieUtils.formatExpires(com.bench.lang.base.web.cookie.CookieUtils.parseExpires(expiresDateString));
				stringBuilder.append("; Expires=");
				stringBuilder.append(cookieExpiresValue);
			}

			final Integer maxAge = cookie.getMaxAge();
			if (maxAge != null) {
				stringBuilder.append("; Max-Age=");
				stringBuilder.append(maxAge);
			}

			final String domain = cookie.getDomain();
			if (domain != null) {
				stringBuilder.append("; Domain=");
				stringBuilder.append(domain);
			}

			final String path = cookie.getPath();
			if (path != null) {
				stringBuilder.append("; Path=");
				stringBuilder.append(path);
			}

			final Boolean isSecure = cookie.getSecure();
			if (isSecure) {
				stringBuilder.append("; Secure");
			}

			final Boolean isHttpOnly = cookie.isHttpOnly();
			if (isHttpOnly) {
				stringBuilder.append("; HttpOnly");
			}

			final Boolean isSameSiteStrict = cookie.isSameSiteStrict();
			if (isSameSiteStrict != null) {
				stringBuilder.append("; SameSite=" + (isSameSiteStrict ? "Strict" : "Lax"));
			}

			setCookieHeaderValues.add(stringBuilder.toString());
		}

		return setCookieHeaderValues;
	}
}
