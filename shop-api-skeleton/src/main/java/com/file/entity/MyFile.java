package com.file.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class MyFile implements Serializable {

	private Integer id;

	private String userId;

	private String fileUseType;

	private String fileName;

	private String fileType;

	private String fileStatue;

	private String realPath;

	private String relativePath;

	private Timestamp uploadTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getFileUseType() {
		return fileUseType;
	}

	public void setFileUseType(String fileUseType) {
		this.fileUseType = fileUseType == null ? null : fileUseType.trim();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType == null ? null : fileType.trim();
	}

	public String getFileStatue() {
		return fileStatue;
	}

	public void setFileStatue(String fileStatue) {
		this.fileStatue = fileStatue == null ? null : fileStatue.trim();
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath == null ? null : realPath.trim();
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath == null ? null : relativePath.trim();
	}

	public Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}
}