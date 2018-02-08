package com.file.entity;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class Download implements Serializable {
	private String contentType;
	private String fileName;
	private String header;
	private String filePath;
	private byte[] transferBytes;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public byte[] getTransferBytes() {
		return transferBytes;
	}

	public void setTransferBytes(byte[] transferBytes) {
		this.transferBytes = transferBytes;
	}
}
