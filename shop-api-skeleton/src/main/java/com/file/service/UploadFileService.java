package com.file.service;

import com.file.entity.MultipartRsq;
import com.file.util.Result;

/**
 * UploadFileService
 *
 * @author:HM
 * @date: 17-12-23 10:52:03
 * @since v1.0.0
 */
public interface UploadFileService {

	/**
	 * uploadFile
	 *
	 * @param multipartRsq
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 17-12-23 10:52:27
	 * @since v1.0.0
	 */
	Result uploadFile(MultipartRsq multipartRsq) throws Throwable;
}
