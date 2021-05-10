package com.bench.lang.base.host.utils;

import com.bench.lang.base.string.build.ToStringObject;

/**
 * 
 * 
 * @author cold
 *
 * @version $Id: HostPort.java, v 0.1 2018年10月11日 下午12:14:24 cold Exp $
 */
public class HostPort extends ToStringObject {

	/**
	 * 主机名
	 */
	private String host;

	/**
	 * 端口
	 */
	private int port;

	public HostPort() {
		super();
	}

	public HostPort(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
