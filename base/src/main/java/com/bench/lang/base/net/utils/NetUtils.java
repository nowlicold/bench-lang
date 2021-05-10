package com.bench.lang.base.net.utils;

import java.net.InetAddress;

public class NetUtils {

	/**
	 * 根据主机名获取IP
	 * 
	 * @param hostName
	 * @return
	 */
	public static String GetIpByHostName(String hostName) {
		try {
			InetAddress address = InetAddress.getByName(hostName);
			return address.getHostAddress();
		} catch (Exception e) {
			return null;
		}
	}

}
