package com.file.service;

import com.file.entity.CheckFileChanges;
import com.file.entity.MyFile;

import java.util.List;

public interface MyFileService {

	/**
	 *
	 * @author:HM
	 * @date: 17-12-25 10:30:49
	 * @since v1.0.0
	 * @param myFile
	 * @throws Throwable
	 */
	void insertMyFile(MyFile myFile) throws Throwable;

	/**
	 * getFileChangesStatus
	 *
	 * @param userId
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 17-12-25 10:27:31
	 * @since v1.0.0
	 */
	List<MyFile> getMyFileByUserId(String userId) throws Throwable;

	/**
	 * getFileChangesStatus
	 *
	 * @param userId
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 17-12-25 10:27:31
	 * @since v1.0.0
	 */
	List<MyFile> getMyFileByUserIdWithPage(int pageNum,String userId) throws Throwable;


	/**
	 *
	 * @author:HM
	 * @date: 17-12-25 10:30:24
	 * @since v1.0.0
	 * @param ids
	 * @return
	 * @throws Throwable
	 */
	List<String> getFileRealPathById(List<Integer> ids) throws Throwable;

	/**
	 * getFileChangesStatus
	 *
	 * @param userId
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 17-12-25 10:27:31
	 * @since v1.0.0
	 */
	CheckFileChanges getFileChangesStatus(String userId) throws Throwable;
}
