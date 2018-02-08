package com.file.entity;

import com.alibaba.fastjson.JSON;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * 多媒体请求体
 *
 * @author:HM
 * @date: 17-12-19 08:38:32
 * @since v1.0.0
 */
public class MultipartRsq<T> implements Serializable {
	private static final long serialVersionUID = 7828683340362103706L;

	List<MultipartFileEntity> multipartFileEntityList;

	private T parameter;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public T getParameter() {
		return parameter;
	}

	public void setParameter(T parameter) {
		this.parameter = parameter;
	}

	public List<MultipartFileEntity> getMultipartFileEntityList() {
		return multipartFileEntityList;
	}

	public void setMultipartFileEntityList(List<MultipartFileEntity> multipartFileEntityList) {
		this.multipartFileEntityList = multipartFileEntityList;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
