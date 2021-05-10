package com.bench.lang.base.result;

import java.util.HashMap;
import java.util.Map;

import com.bench.lang.base.error.ErrorCode;
import com.bench.lang.base.string.build.ToStringObject;

/**
 * 结果基类
 * 
 * @author cold
 * 
 * @version $Id: BaseResult.java, v 0.1 2014-7-2 下午1:39:06 cold Exp $
 */
public class BaseResult extends ToStringObject {

	/**
	 * 是否成功
	 */
	protected boolean success;
	/**
	 * 错误信息
	 */
	protected ErrorCode error;

	/**
	 * 详细信息
	 */
	private String detailMessage;

	/**
	 * 返回结果集
	 */
	protected Map<String, String> resultMap = new HashMap<String, String>();

	public BaseResult(boolean success) {
		super();
		this.success = success;
	}

	public BaseResult() {
		super();
	}

	public BaseResult(boolean success, ErrorCode error) {
		super();
		this.success = success;
		this.error = error;
	}

	public BaseResult(boolean success, ErrorCode error, String detailMessage, Map<String, String> resultMap) {
		super();
		this.success = success;
		this.error = error;
		this.detailMessage = detailMessage;
		this.resultMap = resultMap;
	}

	public BaseResult(boolean success, ErrorCode error, String detailMessage) {
		super();
		this.success = success;
		this.error = error;
		this.detailMessage = detailMessage;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ErrorCode getError() {
		return error;
	}

	public void setError(ErrorCode error) {
		this.error = error;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	public Map<String, String> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}

	public void addResultMap(Map<String, String> resultMap) {
		this.resultMap.putAll(resultMap);
	}

	/**
	 * 放置结果集
	 * 
	 * @param key
	 * @param value
	 */
	public void putResult(String key, String value) {
		this.resultMap.put(key, value);
	}

	/**
	 * 从result copy出来
	 * 
	 * @param result
	 */
	public <R extends BaseResult> void copyFrom(R result) {
		this.setSuccess(result.isSuccess());
		this.setDetailMessage(result.getDetailMessage());
		this.setError(result.getError());
		if (this.getResultMap() == null) {
			this.setResultMap(result.getResultMap());
		} else {
			this.getResultMap().putAll(result.getResultMap());
		}

	}

	/**
	 * copy到result
	 * 
	 * @param result
	 */
	public void copyTo(BaseResult result) {
		result.setSuccess(this.isSuccess());
		result.setDetailMessage(this.getDetailMessage());
		result.setError(this.getError());
		if (result.getResultMap() == null) {
			result.setResultMap(this.getResultMap());
		} else {
			result.getResultMap().putAll(this.getResultMap());
		}
	}

}
