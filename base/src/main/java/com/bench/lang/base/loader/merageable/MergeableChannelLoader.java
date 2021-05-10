package com.bench.lang.base.loader.merageable;

import java.util.Map;

import com.bench.lang.base.loader.ChannelLoader;
import com.bench.lang.base.loader.exceptions.LoadException;

/**
 * 可合并的基于Channel的Loader<br>
 * Resource、URL、File都可以归纳为Channel
 * 
 * @author cold
 *
 * @version $Id: MergeableChannelLoader.java, v 0.1 2020年5月19日 下午3:18:02 cold Exp $
 */
public interface MergeableChannelLoader<CHANNEL, LOAD_DATA> extends ChannelLoader<CHANNEL, LOAD_DATA>, MergeableLoader<LOAD_DATA> {

	/**
	 * 读取通道内容
	 * 
	 * @param channelPath
	 * @return
	 */
	Map<CHANNEL, String> readChannelContent(String channelPath) throws LoadException;

	/**
	 * 合并加载
	 * 
	 * @return
	 * @throws LoadException
	 */
	default LOAD_DATA mergedLoad() throws LoadException {
		// TODO Auto-generated method stub
		LOAD_DATA loadData = newLoadData();
		// 遍历每个文件
		for (String channelPath : this.getChannelPaths()) {
			// 读取通道内容，考虑通道路径可能是模糊的*，会匹配到多个真正的通道对象，这里返回多个读取内容
			Map<CHANNEL, String> channelContentMap = null;
			try {
				channelContentMap = readChannelContent(channelPath);
			} catch (Exception e) {
				throw new LoadException("从Channel读取内容异常,channelPath=" + channelPath + ",loader=" + this, e);
			}

			/**
			 * 解析每个通道内容
			 */
			if (channelContentMap != null) {
				for (Map.Entry<CHANNEL, String> channelContentEntry : channelContentMap.entrySet()) {
					LOAD_DATA currentLoadData = null;
					try {
						currentLoadData = parseChannelContent(channelContentEntry.getKey(), channelContentEntry.getValue());
					} catch (Exception e) {
						throw new LoadException("解析Channel内容异常,channelContentEntry=" + channelContentEntry + ",loader=" + this, e);
					}
					// 合并内容
					mergeLoadData(currentLoadData, loadData);
				}
			}
		}
		return loadData;
	}

}
