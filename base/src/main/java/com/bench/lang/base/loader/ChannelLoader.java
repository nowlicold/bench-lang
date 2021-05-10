package com.bench.lang.base.loader;

import com.bench.lang.base.charset.CharsetConstants;
import com.bench.lang.base.loader.exceptions.LoadException;

/**
 * 基于Channel的Loader<br>
 * Resource、URL、File都可以归纳为Channel
 * 
 * @author cold
 *
 * @version $Id: ChannelLoader.java, v 0.1 2020年5月19日 下午3:18:02 cold Exp $
 */
public interface ChannelLoader<CHANNEL, LOAD_DATA> extends Loader<LOAD_DATA> {

	/**
	 * 返回Channel路径<Br>
	 * 对于File：文件路径<br>
	 * 对于URL：url路径<br>
	 * 对于Classpath Resource： classpath路径
	 * 
	 * @return
	 */
	String[] getChannelPaths();

	/**
	 * 解析通道内容
	 * 
	 * @param channel
	 * @param channelContent
	 * @return
	 */
	LOAD_DATA parseChannelContent(CHANNEL channel, String channelContent) throws LoadException;

	/**
	 * 返回字符集
	 * 
	 * @return
	 */
	default String getCharset() {
		return CharsetConstants.UTF_8.name();
	}

}
