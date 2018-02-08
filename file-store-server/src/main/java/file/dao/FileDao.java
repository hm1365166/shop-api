package file.dao;


import com.file.entity.File;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDao {

	/**
	 *
	 * @param userId
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 2017/10/10 08:38:08
	 */
	List<File> getFileByUserId(String userId) throws Throwable;

	/**
	 *
	 * @return
	 * @throws Throwable
	 * @author:HM
	 * @date: 2017/10/11 11:09:21
	 */
	List<File> getFileToShow() throws Throwable;

	/**
	 *
	 * @param file
	 * @throws Throwable
	 * @author:HM
	 * @date: 2017/10/10 10:15:20
	 *
	 */
	void insertFile(File file) throws Throwable;

	/**
	 *
	 * @param file
	 * @throws Throwable
	 * @author:HM
	 * @date: 2017/10/10 15:59:25
	 */
	void updateByUserId(File file) throws Throwable;

}
