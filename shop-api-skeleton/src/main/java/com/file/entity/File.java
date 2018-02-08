package com.file.entity;

import java.io.Serializable;

public class File implements Serializable {
	private Integer id;
	private String fileName;
	private String filePath;
	private String fileType;
	private String fileIdentity;
	private String fileStatue;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileIdentity() {
		return fileIdentity;
	}

	public void setFileIdentity(String fileIdentity) {
		this.fileIdentity = fileIdentity;
	}

	public String getFileStatue() {
		return fileStatue;
	}

	public void setFileStatue(String fileStatue) {
		this.fileStatue = fileStatue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
