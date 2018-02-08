package com.file.service;

import com.file.entity.CheckFileChanges;

/**
 * FileChangeService
 *
 * @author:HM
 * @date: 17-12-25 10:27:37
 * @since v1.0.0
 */
public interface FileChangeService {

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
