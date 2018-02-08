package file.service.impl;

import com.file.entity.CheckFileChanges;
import com.file.entity.MyFile;
import com.file.service.MyFileService;
import com.github.pagehelper.PageHelper;
import file.dao.MyFileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyFileServiceImpl implements MyFileService {

	@Autowired
	private MyFileDao myFileDao;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	@Override
	public void insertMyFile(MyFile myFile) throws Throwable {
		myFileDao.insertMyFile(myFile);
	}

	/*@Override
	public void insertMyFileBanth(List<MyFile> myFileList) throws Throwable {
		for (MyFile myFile : myFileList) {
			myFileDao.insertMyFile(myFile);
		}
	}*/

	@Override
	public List<MyFile> getMyFileByUserId(String userId) throws Throwable {
		return myFileDao.getMyFileByUserId(userId);
	}

	@Override
	public List<MyFile> getMyFileByUserIdWithPage(int pageNum,String userId) throws Throwable {
		PageHelper.startPage(pageNum, 5);
		return myFileDao.getMyFileByUserId(userId);
	}

	@Override
	public List<String> getFileRealPathById(List<Integer> ids) throws Throwable {
		return myFileDao.getFileRealPathById(ids);
	}

	@Override
	public CheckFileChanges getFileChangesStatus(String userId) throws Throwable {
		return myFileDao.checkFileChanges(userId);
	}

}
