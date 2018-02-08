package com.file.entity;

import com.common.vo.AbstractContentVo;

/**
 * @author:HM
 * @date: 18-02-06 16:36:58
 * @since v1.0.0
 */
public class ExceptionContent extends AbstractContentVo {

	private static final long serialVersionUID = -5503770091546381168L;

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
