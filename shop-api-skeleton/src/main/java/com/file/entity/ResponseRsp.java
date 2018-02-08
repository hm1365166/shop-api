package com.file.entity;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @author:HM
 * @date: 17-12-23 15:09:37
 * @since v1.0.0
 * @param <T>
 */
public class ResponseRsp<T> implements Serializable{
	/**
	 * 响应状态
	 */
	private String status;
	/**
	 * 响应信息
	 */
	private String msg;
	/**
	 * 响应code
	 */
	private int code;
	/**
	 * 响应内容
	 */
	private T content;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
