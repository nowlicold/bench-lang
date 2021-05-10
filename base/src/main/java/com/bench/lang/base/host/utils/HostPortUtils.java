package com.bench.lang.base.host.utils;

import java.util.ArrayList;
import java.util.List;

import com.bench.lang.base.string.utils.StringUtils;

/**
 * 
 * 
 * @author cold
 *
 * @version $Id: HostPortUtils.java, v 0.1 2018年10月11日 下午12:15:04 cold Exp $
 */
public class HostPortUtils {

	/**
	 * 主机端口解析
	 * 
	 * @param hostPorts
	 * @return
	 */
	public static List<HostPort> parse(String hostPorts) {
		List<HostPort> returnList = new ArrayList<HostPort>();
		for (String hostPort : StringUtils.split(hostPorts, StringUtils.COMMA_SIGN)) {
			String[] hostProtAry = StringUtils.split(hostPort, StringUtils.COLON_SIGN);
			returnList.add(new HostPort(StringUtils.trim(hostProtAry[0]), Integer.parseInt(StringUtils.trim(hostProtAry[1]))));
		}
		return returnList;
	}

	/**
	 * 转换为String
	 * 
	 * @param hostPorts
	 * @return
	 */
	public static String toString(List<HostPort> hostPorts) {
		StringBuffer buf = new StringBuffer();
		for (HostPort hostPort : hostPorts) {
			buf.append(hostPort.getHost()).append(StringUtils.COLON_SIGN).append(hostPort.getPort()).append(StringUtils.COMMA_SIGN);
		}
		if (buf.length() > 0) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

}
