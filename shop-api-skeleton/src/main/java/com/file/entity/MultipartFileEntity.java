package com.file.entity;

import java.io.Serializable;

/**
 * MultipartFileEntity
 * @author:HM
 * @date: 17-12-21 17:35:28
 * @since v1.0.0
 */
public class MultipartFileEntity implements Serializable{

	private String name;

	private String originalFilename;

	private String contentType;

	private boolean isEmpty;

	private byte[] fileBytes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean empty) {
		isEmpty = empty;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	private long size;

	public byte[] getFileBytes() {
		return fileBytes;
	}

	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}

}
