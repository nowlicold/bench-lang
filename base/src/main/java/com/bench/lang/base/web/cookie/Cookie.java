/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.web.cookie;

/**
 * 
 * @author cold
 *
 * @version $Id: Cookie.java, v 0.1 2019年8月23日 上午8:20:35 cold Exp $
 */
public class Cookie extends javax.servlet.http.Cookie {

	/**
	 * 
	 */
	private static final long serialVersionUID = -820392066115789958L;

	protected String expires;

	protected boolean sameSiteStrict = false;

	public Cookie(String name, String value) {
		super(name, value);
		// TODO Auto-generated constructor stub
	}

	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

	public boolean isSameSiteStrict() {
		return sameSiteStrict;
	}

	public void setSameSiteStrict(boolean sameSiteStrict) {
		this.sameSiteStrict = sameSiteStrict;
	}

}
