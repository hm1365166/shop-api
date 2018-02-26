package com.file.service;

import com.file.entity.Download;
import com.file.entity.ResponseRsp;
import com.file.util.Result;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * DownloadFileService
 *
 * @author:HM
 * @date: 17-12-23 10:53:10
 * @since v1.0.0
 */
public interface DownloadFileService {

	/**
	 * downloadMulFile
	 *
	 * @author:HM
	 * @date: 18-02-26 16:23:04
	 * @since v1.0.0
	 * @param id
	 * @return
	 */
	ResponseRsp downloadMulFile(List<Integer> id);

}
