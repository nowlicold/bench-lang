package com.bench.lang.base.file;

/**
 * 文件全数据对象，注意，不要放置超大的文件，否则内存溢出
 * 
 * @author cold
 * 
 * @version $Id: FileItem.java, v 0.1 2013-8-26 下午4:22:21 cold Exp $
 */
public class FileFullItem extends FileItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8000559939720210929L;
	/**
	 * 文件路径
	 */
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
