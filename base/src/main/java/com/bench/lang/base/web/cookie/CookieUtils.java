package com.bench.lang.base.web.cookie;

import com.bench.lang.base.QuotedStringTokenizer;
import com.bench.lang.base.web.ResponseUtils;
import com.bench.lang.base.string.utils.StringUtils;
import com.bench.lang.base.uri.utils.URIUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Cookie工具类<br>
 * Set-Cookie例子:<br>
 * Set-Cookie: BAIDUID=975080C0BCCFB576B8AA609EC01342BE:FG=1; expires=Thu, 31-Dec-37 23:55:55 GMT; max-age=2147483647; path=/; domain=.baidu.com<br>
 * Set-Cookie: BIDUPSID=975080C0BCCFB576B8AA609EC01342BE; expires=Thu, 31-Dec-37 23:55:55 GMT; max-age=2147483647; path=/; domain=.baidu.com<br>
 * Set-Cookie: PSTM=1566521375; expires=Thu, 31-Dec-37 23:55:55 GMT; max-age=2147483647; path=/; domain=.baidu.com<br>
 * Set-Cookie: delPer=0; path=/; domain=.baidu.com<br>
 * Set-Cookie: BDSVRTM=0; path=/<br>
 * Set-Cookie: BD_HOME=0; path=/<br>
 * Set-Cookie: H_PS_PSSID=1439_21078_18560_29523_29518_29098_29567_29220_26350_29071; path=/; domain=.baidu.com<br>
 * 
 * @author cold
 * 
 * @version $Id: CookieUtils.java, v 0.1 2013-10-24 下午12:47:46 cold Exp $
 */
public class CookieUtils {

	public static final String SET_COOKIE_HEADER = "Set-Cookie";

	public static final char SEPARATORS[] = { '\t', ' ', '\"', '(', ')', ',', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '{', '}' };

	private static TimeZone __GMT = TimeZone.getTimeZone("GMT");

	private static String[] DAYS = { "Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	private static String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Jan" };

	protected static final DateFormat _dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
	static {
		_dateFormat.setTimeZone(__GMT);
	}
	public final static String __01Jan1970 = formatDate(0, true).trim();

	/**
	 * 解析Fri, 23 Aug 2019 00:52:27 GMT
	 * 
	 * @param expiration
	 * @return
	 */
	public static Long parseExpires(final String expiration) {
		try {
			return _dateFormat.parse(expiration).getTime();
		} catch (final ParseException exception) {
			return null;
		}
	}

	public static String formatExpires(final Long expirationTime) {
		return _dateFormat.format(new Date(expirationTime));
	}

	/**
	 * Format HTTP date "EEE, dd MMM yyyy HH:mm:ss 'GMT'" or "EEE, dd-MMM-yy HH:mm:ss 'GMT'"for cookies
	 */
	public static String formatDate(long date, boolean cookie) {
		StringBuffer buf = new StringBuffer(32);
		GregorianCalendar gc = new GregorianCalendar(__GMT);
		gc.setTimeInMillis(date);
		formatDate(buf, gc, cookie);
		return buf.toString();
	}

	/**
	 * 得到cookie的domain
	 * 
	 * @param request
	 * @return
	 */
	public static String getCookieRootDomain(HttpServletRequest request) {
		String serverName = request.getServerName();
		if (StringUtils.indexOf(serverName, StringUtils.DOT_SIGN) <= 0) {
			return serverName;
		} else {
			return "." + URIUtils.getRootDomain(serverName);
		}
	}

	/**
	 * Format HTTP date "EEE, dd MMM yyyy HH:mm:ss 'GMT'" or "EEE, dd-MMM-yy HH:mm:ss 'GMT'"for cookies
	 */
	public static String formatDate(Calendar calendar, boolean cookie) {
		StringBuffer buf = new StringBuffer(32);
		formatDate(buf, calendar, cookie);
		return buf.toString();
	}

	/* ------------------------------------------------------------ */
	/**
	 * Format HTTP date "EEE, dd MMM yyyy HH:mm:ss 'GMT'" or "EEE, dd-MMM-yy HH:mm:ss 'GMT'"for cookies
	 */
	public static String formatDate(StringBuffer buf, long date, boolean cookie) {
		GregorianCalendar gc = new GregorianCalendar(__GMT);
		gc.setTimeInMillis(date);
		formatDate(buf, gc, cookie);
		return buf.toString();
	}

	/* ------------------------------------------------------------ */
	/**
	 * Format HTTP date "EEE, dd MMM yyyy HH:mm:ss 'GMT'" or "EEE, dd-MMM-yy HH:mm:ss 'GMT'"for cookies
	 */
	public static void formatDate(StringBuffer buf, Calendar calendar, boolean cookie) {
		// "EEE, dd MMM yyyy HH:mm:ss 'GMT'"
		// "EEE, dd-MMM-yy HH:mm:ss 'GMT'", cookie

		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
		int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		int century = year / 100;
		year = year % 100;

		int epoch = (int) ((calendar.getTimeInMillis() / 1000) % (60 * 60 * 24));
		int seconds = epoch % 60;
		epoch = epoch / 60;
		int minutes = epoch % 60;
		int hours = epoch / 60;

		buf.append(DAYS[day_of_week]);
		buf.append(',');
		buf.append(' ');
		StringUtils.append2digits(buf, day_of_month);

		if (cookie) {
			buf.append('-');
			buf.append(MONTHS[month]);
			buf.append('-');
			StringUtils.append2digits(buf, century);
			StringUtils.append2digits(buf, year);
		} else {
			buf.append(' ');
			buf.append(MONTHS[month]);
			buf.append(' ');
			StringUtils.append2digits(buf, century);
			StringUtils.append2digits(buf, year);
		}
		buf.append(' ');
		StringUtils.append2digits(buf, hours);
		buf.append(':');
		StringUtils.append2digits(buf, minutes);
		buf.append(':');
		StringUtils.append2digits(buf, seconds);
		buf.append(" GMT");
	}

	/**
	 * 获取Cookie
	 * 
	 * @param name
	 *            cookie名，区分大小写
	 * @param request
	 * @return
	 */
	public static Cookie getCookie(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		// 迭代查找
		for (Cookie cookie : request.getCookies()) {
			if (StringUtils.equals(cookie.getName(), name)) {
				return cookie;
			}
		}
		return null;
	}

	/**
	 * 将cookie转换为String
	 * 
	 * @param cookie
	 * @return
	 */
	public static String toString(Cookie cookie) {
		String name = cookie.getName();
		String value = cookie.getValue();
		int version = cookie.getVersion();

		// Check arguments
		if (name == null || name.length() == 0)
			throw new IllegalArgumentException("Bad cookie name");

		// Format value and params
		StringBuffer buf = new StringBuffer(128);
		String name_value_params = null;
		synchronized (buf) {
			QuotedStringTokenizer.quoteIfNeeded(buf, name);
			buf.append('=');
			buf.append(getCookieHeaderValue(cookie));
			// TODO - straight to Buffer?
			name_value_params = buf.toString();
		}
		return name_value_params;

	}

	/**
	 * 转换成cookie的头部指
	 * 
	 * @param cookie
	 */
	public static String getCookieHeaderValue(Cookie cookie) {
		StringBuffer buf = new StringBuffer();
		String value = cookie.getValue();
		int version = cookie.getVersion();
		if (value != null && value.length() > 0)
			QuotedStringTokenizer.quoteIfNeeded(buf, value);

		if (version > 0) {
			buf.append(";Version=");
			buf.append(version);
			String comment = cookie.getComment();
			if (comment != null && comment.length() > 0) {
				buf.append(";Comment=");
				QuotedStringTokenizer.quoteIfNeeded(buf, comment);
			}
		}
		String path = cookie.getPath();
		if (path != null && path.length() > 0) {
			buf.append(";Path=");
			if (path.startsWith("\""))
				buf.append(path);
			else
				QuotedStringTokenizer.quoteIfNeeded(buf, path);
		}
		String domain = cookie.getDomain();
		if (domain != null && domain.length() > 0) {
			buf.append(";Domain=");
			QuotedStringTokenizer.quoteIfNeeded(buf, domain.toLowerCase());
		}

		long maxAge = cookie.getMaxAge();
		if (maxAge >= 0) {
			if (version == 0) {
				buf.append(";Expires=");
				if (maxAge == 0)
					buf.append(__01Jan1970);
				else
					formatDate(buf, System.currentTimeMillis() + 1000L * maxAge, true);
			} else {
				buf.append(";Max-Age=");
				buf.append(maxAge);
			}
		} else if (version > 0) {
			buf.append(";Discard");
		}

		if (cookie.getSecure()) {
			buf.append(";Secure");
		}
		if (cookie.isHttpOnly())
			buf.append(";HttpOnly");

		return buf.toString();
	}

	/**
	 * 设置cookie
	 * 
	 * @param name
	 *            cookie名，区分大小写
	 * @param value
	 *            cookie值
	 * @param expiry
	 *            超时
	 * @param secure
	 *            是否安全协议
	 * @param domain
	 *            域
	 * @param path
	 *            路径
	 * @param response
	 * @return
	 */
	public static Cookie setCookie(String name, String value, int expiry, boolean secure, String domain, String path, HttpServletResponse response) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(expiry);
		cookie.setPath(path);
		cookie.setDomain(domain);
		cookie.setSecure(secure);
		response.addCookie(cookie);
		return cookie;
	}

	/**
	 * 转换为Cookie值
	 * 
	 * @param cookieMap
	 * @return
	 */
	public static String toCookieValue(Map<String, String> cookieMap) {
		StringBuffer cookies = new StringBuffer();
		for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
			if (StringUtils.hasText(entry.getKey()) && StringUtils.hasText(entry.getValue())) {
				cookies.append(entry.getKey()).append("=").append(entry.getValue()).append("; ");
			}
		}
		if (StringUtils.hasText(cookies.toString())) {
			return cookies.toString();
		}
		return null;
	}

	/**
	 * @param response
	 * @return
	 */
	public static final List<Cookie> getCookieMap(HttpServletResponse response) {
		return ResponseUtils.getCookieMap(response);
	}
}
