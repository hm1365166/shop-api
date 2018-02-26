package file.service.impl;

import com.file.entity.File;
import com.file.service.FileService;
import file.dao.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:HM
 * @date: 18-02-26 16:52:37
 * @since v1.0.0
 */
@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileDao fileDao;

	@Override
	public List<File> getFileByUserId(String userId) throws Throwable {
		return fileDao.getFileByUserId(userId);
	}

	@Override
	public List<File> getFileToShow() throws Throwable {
		return fileDao.getFileToShow();
	}

	@Override
	public void insertFile(File file) throws Throwable {
		fileDao.insertFile(file);
	}

	@Override
	public void updateByUserId(File file) throws Throwable {

	}
}
