package com.bench.lang.base.web.cookie;

import com.bench.common.enums.EnumBase;

/**
 * 头部名枚举
 * 
 * @author chenbug
 * 
 * @version $Id: HeaderNameEnum.java, v 0.1 2013-3-6 下午3:06:56 chenbug Exp $
 */
public enum HeaderNameEnum implements EnumBase {

	ACCEPT_CHARSET("Accept-Charset", "浏览器可以接受的字符编码"),

	USER_AGENT("User-Agent", "头部标志"),

	AUTHORIZATION("Authorization", "头部认证"),

	HOST("Host", "主机"),

	COOKIE("Cookie", "客户端发送Cookie"),

	SET_COOKIE("Set-Cookie", "服务端返回Cookie"),

	ACCEPT("Accept", "Accept"),

	ACCEPT_ENCODING("Accept-Encoding", "Accept-Encoding"),

	ACCEPT_LANGUAGE("Accept-Language", "Accept-Language"),

	CACHE_CONTROL("Cache-Control", "Cache-Control"),

	CONTENT_TYPE("Content-Type", "Content-Type"),

	CONTENT_DISPOSITION("Content-Disposition", "文件下载"),

	CONTENT_LENGTH("Content-Length", "内容长度"),

	CONNECTION("Connection", "Connection"),

	PRAGMA("Pragma", "Pragma"),

	REFERER("Referer", "Referer"),

	BENCH_CAPTCHA_VERIFY_CODE("BenchCaptchaVerifyCode", "bench验证码"),

	BENCH_HTTP_CLIENT_REQUEST("bench-http-client-request", "Bench的HttpClient请求"),

	X_FORWARDED_FOR("X-Forwarded-For", "通过HTTP代理或负载均衡方式连接到Web服务器的客户端最原始的IP地址"),

	/**
	 * 以下为跨域访问的头部
	 */
	ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin", "响应 header 指示是否该响应可以与具有给定资源共享原点"),

	ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods", "预检请求：允许跨域的方法"),

	ACCESS_CONTROL_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials",
			"响应头表示是否可以将对请求的响应暴露给页面。返回true则可以，其他值均不可以。Credentials可以是 cookies, authorization headers 或 TLS client certificates"),

	ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers",
			"响应首部 Access-Control-Allow-Headers 用于 preflight request （预检请求）中，列出了将会在正式请求的 Access-Control-Request-Headers 字段中出现的首部信息。"),

	ACCESS_CONTROL_EXPOSE_HEADERS("Access-Control-Expose-Headers",
			"响应报头指示哪些报头可以公开为通过列出他们的名字的响应的一部分。默认情况下，只显示6个简单的响应标头：Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma，如果您希望客户端能够访问其他标题，则必须使用Access-Control-Expose-Headers标题列出它们"),

	ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age",
			"The Access-Control-Max-Age 这个响应首部表示 preflight request  （预检请求）的返回结果（即 Access-Control-Allow-Methods 和Access-Control-Allow-Headers 提供的信息） 可以被缓存多久。"),

	ACCESS_CONTROL_REQUEST_HEADERS("Access-Control-Request-Headers", "客户端发出的header，发出请求时报头用于预检请求让服务器知道哪些 HTTP 头的实际请求时将被使用。"),

	ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method", "客户端发送出的header，发出请求时报头用于预检请求让服务器知道哪些 HTTP 方法的实际请求时将被使用。这个头是必要的，因为预检请求始终是一个OPTIONS，并且不使用与实际请求相同的方法。"),

	;

	private String headerName;

	private String message;

	private HeaderNameEnum(String headerName, String message) {
		this.headerName = headerName;
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.enums.EnumBase#message()
	 */
	@Override
	public String message() {
		// TODO Auto-generated method stub
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.enums.EnumBase#value()
	 */
	@Override
	public Number value() {
		// TODO Auto-generated method stub
		return null;
	}

	public String headerName() {
		return this.headerName;
	}

}
