package com.file.entity;

import java.io.Serializable;

public class CheckFileChanges implements Serializable {
	private static final long serialVersionUID = 8463166570261611255L;
	/**
	 * 最大id值
	 */
	private int id;
	/**
	 * 当前记录数
	 */
	private int idCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCount() {
		return idCount;
	}

	public void setIdCount(int idCount) {
		this.idCount = idCount;
	}
}
