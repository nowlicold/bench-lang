package com.bench.lang.base;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Ip工具类
 * 
 * @author cold
 * 
 * @version $Id: IpUtils.java, v 0.1 2013-10-30 下午3:21:07 cold Exp $
 */
public class IpUtils {

	public static final String LOCAL_IP_V4 = "127.0.0.1";

	private static final long[][] INNER_IPS = new long[][] { { getLongIp("10.0.0.0"), getLongIp("10.255.255.255") },
			{ getLongIp("172.16.0.0"), getLongIp("172.31.255.255") }, { getLongIp("192.168.0.0"), getLongIp("192.168.255.255") } };

	private static final int MAX_IP_WARN_VALUE = 100000;
	private static final Logger log = LoggerFactory.getLogger(IpUtils.class);

	/**
	 * 在当前ip上增加value值
	 * 
	 * @param ip
	 * @param value
	 * @return
	 */
	public static String addIp(String ip, int value) {
		return getStringIp(getLongIp(ip) + value);
	}

	/**
	 * 返回IpV4地址集合
	 * 
	 * @param ipList
	 * @return
	 */
	public static List<String> getIpV4(List<String> ipList) {
		List<String> returnList = new ArrayList<String>();
		for (String ip : ipList) {
			if (IpValidateUtils.isIpV4(ip)) {
				returnList.add(ip);
			}
		}
		return returnList;
	}

	/**
	 * 将ip转换为long
	 * 
	 * @param ipAddress
	 * @return
	 */
	public static long getLongIp(String ipAddress) {
		byte[] b = null;
		try {
			b = InetAddress.getByName(ipAddress).getAddress();
		} catch (Exception e) {
			throw new IllegalArgumentException(ipAddress + "不是一个正确的ip");
		}
		long l = b[0] << 24L & 0xff000000L | b[1] << 16L & 0xff0000L | b[2] << 8L & 0xff00L | b[3] << 0L & 0xffL;
		return l;
	}

	/**
	 * 是否内网IP
	 * 
	 * @param ipAddress
	 * @return
	 */
	public static boolean isInnerIp(String ipAddress) {
		long ip = getLongIp(ipAddress);
		for (long[] ipRange : INNER_IPS) {
			if (ip >= ipRange[0] && ip <= ipRange[1]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 将long转换为ip
	 * 
	 * @param ip
	 * @return
	 */
	public static String getStringIp(long ip) {
		byte[] b = new byte[4];
		int i = (int) ip;// 低３２位
		b[0] = (byte) ((i >> 24) & 0x000000ff);
		b[1] = (byte) ((i >> 16) & 0x000000ff);
		b[2] = (byte) ((i >> 8) & 0x000000ff);
		b[3] = (byte) ((i >> 0) & 0x000000ff);
		try {
			return InetAddress.getByAddress(b).getHostAddress();
		} catch (Exception e) {
			throw new IllegalArgumentException(ip + "不是一个正确long类型的ip");
		}
	}

	/**
	 * 计算2个ip段之间的ip数
	 * 
	 * @param ip1
	 * @param ip2
	 * @return
	 */
	public static long getIpCount(String ip1, String ip2) {
		return getLongIp(ip2) - getLongIp(ip1);
	}

	/**
	 * 返回起止段内的所有IP，注意起止段不要太大，容易造成内存溢出
	 * 
	 * @param startIp
	 * @param endIp
	 * @return
	 */
	public static List<String> getIps(String startIp, String endIp) {
		List<String> returnList = new ArrayList<String>();
		long start = getLongIp(startIp);
		long end = getLongIp(endIp);
		long count = end - start;
		if (count > MAX_IP_WARN_VALUE) {
			log.warn("计算起止ip段内的ip清单，ip数过多，startIp=" + startIp + ",endIp=" + endIp + ",count=" + count);
		}
		for (long i = 0; i < count; i++) {
			returnList.add(getStringIp(start + i));
		}
		return returnList;
	}

	/**
	 * 访问起止ip范围内的每个Ip
	 * 
	 * @param startIp
	 * @param endIp
	 * @param visitor
	 */
	public void visitRange(String startIp, String endIp, IpVisitor visitor) {
		long start = getLongIp(startIp);
		long end = getLongIp(endIp);
		long count = end - start;
		for (long i = 0; i < count; i++) {
			String ip = getStringIp(start + i);
			visitor.visit(ip);
		}
	}

	public static boolean isValidIp(String text) {
		if (text != null && !text.isEmpty()) {
			// 定义正则表达式
			String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
					+ "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
			// 判断ip地址是否与正则表达式匹配
			if (text.matches(regex)) {
				// 返回判断信息
				return true;
			} else {
				// 返回判断信息
				return false;
			}
		}
		return false;
	}

	/**
	 * 返回字符串ip
	 * 
	 * @param address
	 * @return
	 */
	public static String getStringIp(InetAddress address) {
		return address.getHostAddress();
	}

	/**
	 * 返回InetAddress
	 * 
	 * @param address
	 * @return
	 */
	public static InetAddress getAddress(String ip) {
		try {
			return InetAddress.getByName(ip);
		} catch (Exception e) {
			throw new IllegalArgumentException(ip + "不是一个正确的IP地址");
		}
	}

	/**
	 * 访问Ip
	 * 
	 * @author cold
	 *
	 * @version $Id: IpUtils.java, v 0.1 2015年3月3日 下午5:53:24 cold Exp $
	 */
	public static interface IpVisitor {
		public void visit(String ip);
	}

}
