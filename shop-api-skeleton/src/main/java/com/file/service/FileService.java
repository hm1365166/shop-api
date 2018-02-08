package com.file.service;



import com.file.entity.File;

import java.util.List;

public interface FileService {
	List<File> getFileByUserId(String userId) throws Throwable;

	List<File> getFileToShow() throws Throwable;

	void insertFile(File file) throws Throwable;

	void updateByUserId(File file) throws Throwable;
}
