package com.bench.lang.base.file;

import java.io.Serializable;

/**
 * 文件对象，注意，不要放置超大的文件，否则内存溢出
 * 
 * @author cold
 * 
 * @version $Id: FileItem.java, v 0.1 2013-8-26 下午4:22:21 cold Exp $
 */
public class FileItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2223557266511946968L;

	/**
	 * 文件内容
	 */
	private byte[] content;

	/**
	 * 文件名
	 */
	private String name;

	public FileItem() {
		super();
	}

	public FileItem(String name, byte[] content) {
		super();
		this.content = content;
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
