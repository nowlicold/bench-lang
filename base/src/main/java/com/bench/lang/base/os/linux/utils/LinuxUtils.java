package com.bench.lang.base.os.linux.utils;

import com.bench.lang.base.string.utils.StringUtils;

/**
 * linux工具类
 * 
 * @author cold
 *
 * @version $Id: LinuxUtils.java, v 0.1 2015年3月5日 下午2:56:35 cold Exp $
 */
public class LinuxUtils {

	public static final LinuxUtils INSTANCE = new LinuxUtils();

	/**
	 * 过滤sh脚本内容
	 * 
	 * @param content
	 * @return
	 */
	public static String filterShellScriptContent(String content) {
		String filterContent = StringUtils.replace(content, "\r\n", "\n");
		return filterContent;
	}
}
