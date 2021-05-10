package com.bench.lang.base.jmx.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;

/**
 * jmx工具类
 * 
 * @author cold
 *
 * @version $Id: JmxUtils.java, v 0.1 2015年9月10日 下午5:14:40 cold Exp $
 */
public class JmxUtils {

	/**
	 * 返回所有的MBean服务器
	 * 
	 * @return
	 */
	public static List<MBeanServer> getMBeanServers() {
		return MBeanServerFactory.findMBeanServer(null);
	}

	/**
	 * 根据agentId返回MBeanServer
	 * 
	 * @param agentId
	 * @return
	 */
	public static MBeanServer getMBeanServer(String agentId) {
		List<MBeanServer> returnList = MBeanServerFactory.findMBeanServer(agentId);
		if (returnList.size() > 0) {
			return returnList.get(0);
		}
		return null;
	}

	/**
	 * 在所有的MBeanServer中查找
	 * 
	 * @param name
	 * @param query
	 * @return
	 */
	public static Map<MBeanServer, Set<ObjectInstance>> queryMBeans(ObjectName name, QueryExp query) {
		Map<MBeanServer, Set<ObjectInstance>> returnMap = new HashMap<MBeanServer, Set<ObjectInstance>>();
		for (MBeanServer server : getMBeanServers()) {
			Set<ObjectInstance> returnSet = server.queryMBeans(name, query);
			if (returnSet.size() > 0) {
				returnMap.put(server, returnSet);
			}
		}
		return returnMap;
	}

	/**
	 * 在所有的MBeanServer中查找
	 * 
	 * @param name
	 * @param query
	 * @return
	 */
	public static Map<MBeanServer, Set<ObjectName>> queryNames(ObjectName name, QueryExp query) {
		Map<MBeanServer, Set<ObjectName>> returnMap = new HashMap<MBeanServer, Set<ObjectName>>();
		for (MBeanServer server : getMBeanServers()) {
			Set<ObjectName> returnSet = server.queryNames(name, query);
			if (returnSet.size() > 0) {
				returnMap.put(server, returnSet);
			}
		}
		return returnMap;

	}
}
