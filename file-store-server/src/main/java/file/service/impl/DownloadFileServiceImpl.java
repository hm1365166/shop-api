package file.service.impl;

import com.file.entity.Download;
import com.file.entity.ResponseRsp;
import com.file.service.DownloadFileService;
import com.file.service.MyFileService;
import file.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * DownloadFileServiceImpl
 *
 * @author:HM
 * @date: 17-12-23 11:18:34
 * @since v1.0.0
 */
@Service("downloadFileService")
public class DownloadFileServiceImpl implements DownloadFileService {

	private final static Logger logger = LoggerFactory.getLogger(DownloadFileServiceImpl.class);

	/*private final ExecutorService executors = new ThreadPoolExecutor(10, 50, 3, TimeUnit.HOURS,
			new ArrayBlockingQueue<Runnable>(3000),
			new ThreadPoolExecutor.CallerRunsPolicy());*/

	@Autowired
	MyFileService myFileService;

	@Override
	public ResponseRsp downloadMulFile(List<Integer> ids) {
		Download download = new Download();
		ResponseRsp<Download> responseRsp = new ResponseRsp<>();
		FileInputStream inputStream = null;
		try {
			List<String> resumes = myFileService.getFileRealPathById(ids);

			if (resumes == null) {
				return null;
			}

			String[] filePaths = resumes.toArray(new String[resumes.size()]);
			final String zipPath = FileUtil.MulFileToZip(filePaths);

			final java.io.File zipFile = new java.io.File(zipPath);

			download.setContentType("application/octet-stream");
			download.setFileName(zipFile.getName());
			download.setFilePath(zipFile.getPath());
			inputStream = new FileInputStream(zipPath);
			byte[] transferBytes = readInputStream(inputStream);
			download.setTransferBytes(transferBytes);

			//异步删除临时文件
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						//异步线程休息1秒，在方法返回后，将生成的临时压缩文件删除
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						logger.info("删除临时文件失败", e.getCause());
						e.printStackTrace();
					}
					FileUtil.DeleteFile(zipPath);
				}
			}).start();

		} catch (Exception e) {
			logger.error("根据id {} 查询用户图片真实路径错误：{}", ids, e.getCause());
			e.printStackTrace();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		responseRsp.setContent(download);
		return responseRsp;
	}

	/**
	 * 从输入流获取数据
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inputStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.close();
		inputStream.close();
		return outputStream.toByteArray();
	}
}
