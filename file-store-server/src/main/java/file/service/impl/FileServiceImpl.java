package file.service.impl;

import com.file.entity.File;
import file.dao.FileDao;

import com.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
