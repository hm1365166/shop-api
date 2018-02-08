package com.common.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author:HM
 * @date: 18-02-06 16:34:35
 * @since v1.0.0
 */
public abstract class AbstractContentVo implements Serializable {
	private static final long serialVersionUID = -695130234497863428L;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
