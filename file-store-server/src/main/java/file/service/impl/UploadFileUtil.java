package file.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.file.entity.MultipartFileEntity;
import com.file.entity.MultipartRsq;
import com.file.entity.MyFile;
import com.file.num.FileStatueEnum;
import com.file.service.MyFileService;
import com.file.service.UploadFileService;
import com.file.util.DateHelper;
import com.file.util.Result;
import com.file.util.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service("uploadFileService")
public class UploadFileUtil implements UploadFileService {

	private final static Logger logger = LoggerFactory.getLogger(UploadFileUtil.class);

	@Autowired
	private DataSourceTransactionManager transactionManager;

	@Value("${person_file_path}")
	private String filePath;

	@Autowired
	private MyFileService myFileService;

	@Override
	public Result uploadFile(MultipartRsq multipartRsq) throws Throwable {

		if (StringUtils.isEmpty(multipartRsq.getParameter())) {
			return new Result(ResultCode.PARAMETER_REQUET);
		}
		String userId = (String) multipartRsq.getParameter();
		Result rt = new Result(ResultCode.DEFAULT_CODE);
		List<MultipartFileEntity> multipartFiles = multipartRsq.getMultipartFileEntityList();
		if (multipartFiles == null) {
			rt.setStatus(ResultCode.FAILED);
			rt.setErrorMessage("请添加附件");
			throw new FileNotFoundException();
		}

		int countSameFile = 0;
		for (MultipartFileEntity multipartFile : multipartFiles) {

			//String fileName = DateUtils.now().getTime() + "_" + multipartFile.getOriginalFilename();
			String fileName = multipartFile.getOriginalFilename();
			Calendar calendar = Calendar.getInstance();
			StringBuilder path = new StringBuilder();
			//calendar.setTimeInMillis();
			path.append(filePath);
			path.append(calendar.get(Calendar.YEAR));
			path.append(calendar.get(Calendar.MONTH) + 1);
			path.append(calendar.get(Calendar.DATE));
			path.append("/");
			path.append(userId);
			path.append("/");
			String insertPath = path.substring(path.indexOf("images/") + 7);
			java.io.File dir = new java.io.File(path.toString());
			if (!dir.exists()) {
				dir.mkdirs();
			}

			java.io.File file = new java.io.File(path.toString() + fileName);
			try {

				if (!file.exists()) {
					file.createNewFile();
				} else {
					++countSameFile;
					logger.error(fileName + "存在同名文件");
					continue;
				}

			} catch (IOException e) {
				logger.info("上传文件：创建文件异常:\r\n {}", e.toString());
				rt.setStatus(ResultCode.FAILED);
				rt.setErrorMessage("创建文件异常");
				return rt;
			}

			//事务开始
			DefaultTransactionDefinition txDef = new DefaultTransactionDefinition();

			TransactionStatus status = transactionManager.getTransaction(txDef);
			try {
				Timestamp currentDate = DateHelper.getCurrenTimestamp();
				// 插入候选人简历信息
				MyFile fileRecord = new MyFile();
				fileRecord.setUserId(userId);
				fileRecord.setRealPath(path.toString() + fileName);
				fileRecord.setRelativePath(insertPath + fileName);
				fileRecord.setFileType(multipartFile.getContentType());
				fileRecord.setFileName(multipartFile.getOriginalFilename());
				fileRecord.setFileStatue(FileStatueEnum.SUCCESS.getName());
				fileRecord.setUploadTime(currentDate);
				myFileService.insertMyFile(fileRecord);
				transactionManager.commit(status);

				// 将文件保存到服务器上
				FileCopyUtils.copy(multipartFile.getFileBytes(), file);

				JSONObject json = new JSONObject();
				json.put("userId", userId);
				json.put("name", multipartFile.getOriginalFilename());
				json.put("status", "done");
				//json.put("url", NODE_DOMAIN + "/api/file/Download?userId=" + resumeId);
				rt.setStatus(ResultCode.SUCCESS);
				rt.setContent(json.toString());
			} catch (IOException e) {
				logger.error("保存文件到服务器异常:{}", e.toString());
				rt.setStatus(ResultCode.FAILED);
				rt.setErrorMessage("保存文件到服务器异常");
				e.printStackTrace();
			} catch (Throwable throwable) {
				logger.error("插入file异常" + throwable.getCause());
				throwable.printStackTrace();
			}
		}
		logger.info("存在同名文件共 " + countSameFile);
		return rt;
	}

}
