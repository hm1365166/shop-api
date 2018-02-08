package file.service.impl;

import com.file.entity.CheckFileChanges;
import com.file.service.FileChangeService;
import file.dao.MyFileDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * FileChangeServiceImpl
 *
 * @author:HM
 * @date: 17-12-25 10:28:19
 * @since v1.0.0
 */
public class FileChangeServiceImpl implements FileChangeService {
	private static Logger logger = LoggerFactory.getLogger(FileChangeServiceImpl.class);

	@Autowired
	MyFileDao myFileDao;

	@Override
	public CheckFileChanges getFileChangesStatus(String userId) throws Throwable {
		return myFileDao.checkFileChanges(userId);
	}
}
