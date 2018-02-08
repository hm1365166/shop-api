package file.controller;



import com.file.entity.Download;
import com.file.entity.File;
import com.file.service.FileService;
import file.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author:HM
 * @date: 17-12-15 16:29:15
 * @since v1.0.0
 */
@Controller
@RequestMapping("/Download")
public class DownloadController {
	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);

	@Autowired
	private FileService fileService;

	@RequestMapping("/imageDownload")
	public void download(HttpServletRequest request, HttpServletResponse response, @RequestParam("url")
			String fileName) {
		//String path=request.getServletContext().getRealPath("/")+"/images/rawImages/";
		String[] url = fileName.split(";");
		if (url.length > 1) {
			FileUtil.MulFileToZip(url);

		}
		java.io.File file = new java.io.File(fileName);
		int i = fileName.lastIndexOf("/");
		String outName = fileName.substring(i + 1);
		if (file.exists()) {
			//设置MIME类型
			//	response.setContentType("application/octet-stream");
			// 或者为response.setContentType("application/x-msdownload");
			try {
				String contentType = "application/octet-stream";
				String fileRealName = new String(outName.getBytes(), "ISO-8859-1");
				Download download = new Download();
				download.setContentType(contentType);
				download.setFileName(fileRealName);
				download.setFilePath(fileName);
				FileUtil.downloadFileForWeb(response, download);
			} catch (Exception e) {
				logger.error("imageDownload Exception" + e.getCause());
				e.printStackTrace();
			} catch (Throwable throwable) {
				logger.error("下载错误 throwable Exception" + throwable.getCause());
				throwable.printStackTrace();
			}
		}
	}

	public ModelAndView download(@RequestParam("url")
			String fileName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String downLoadPath = fileName;
		System.out.println(downLoadPath);
		try {
			long fileLength = new java.io.File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}

	/**
	 * 根据确定url数组下载多个文件的压缩文件
	 * @param response
	 * @param url
	 * @author:HM
	 * @date: 2017/10/12 16:03:52
	 */
	@RequestMapping(value = "/downloadMulByUrls", method = RequestMethod.POST)
	public void downLoadSpecialMulFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("url") String url) {
		String[] filePaths = url.substring(url.indexOf(";") + 1).split(";");
		final String zipPath;
		zipPath = FileUtil.MulFileToZip(filePaths);
		final java.io.File zipFile = new java.io.File(zipPath);
		Download download = new Download();
		download.setContentType("application/octet-stream");
		download.setFileName(zipFile.getName());
		download.setFilePath(zipFile.getPath());
		try {
			FileUtil.downloadFileForWeb(response, download);
			new Thread(new Runnable() {
				@Override public void run() {
					FileUtil.DeleteFile(zipPath);
				}
			}).start();
		} catch (Throwable throwable) {
			logger.error("导出错误" + throwable.getCause());
			throwable.printStackTrace();
		}

	}

	/**
	 * 该方法支持多个文件下载
	 * 会将文件打包成压缩文件
	 * 下载
	 *
	 * @param response
	 * @param userId
	 * @author:HM
	 * @date: 2017/10/10 15:09:02
	 */
	@RequestMapping(value = "/downloadMul", method = RequestMethod.GET)
	public void downloadMulFile(HttpServletResponse response, @RequestParam("userId") String userId) {
		FileInputStream fin = null;
		try {
			List<File> resumes = fileService.getFileByUserId(userId);
			String[] filePaths = new String[resumes.size()];
			for (int i = 0; i < resumes.size(); i++) {
				filePaths[i] = resumes.get(i).getFilePath();
			}
			final String zipPath = FileUtil.MulFileToZip(filePaths);
			final java.io.File zipFile = new java.io.File(zipPath);
			Download download = new Download();
			download.setContentType("application/octet-stream");
			download.setFileName(zipFile.getName());
			download.setFilePath(zipFile.getPath());
			FileUtil.downloadFileForWeb(response, download);
			//异步删除临时文件
			new Thread(new Runnable() {
				@Override public void run() {
					FileUtil.DeleteFile(zipPath);
				}
			}).start();
		} catch (IOException e) {
			logger.error("下载上传的附件异常：{}", e.getCause());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("根据id {} 查询候选人简历记录异常：{}", userId, e.getCause());
			e.printStackTrace();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 单个文件下载
	 *
	 * @param response
	 * @param userId
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response, @RequestParam("userId") String userId) {
		FileInputStream fin = null;
		try {

			List<File> resume = fileService.getFileByUserId(String.valueOf(userId));
			Download download = new Download();
			download.setContentType(resume.get(0).getFileType());
			download.setFileName(resume.get(0).getFileName());
			download.setFilePath(resume.get(0).getFilePath());
			FileUtil.downloadFileForWeb(response, download);

		} catch (IOException e) {
			logger.error("下载上传的附件异常：{}", e.getCause());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("根据id {} 查询候选人简历记录异常：{}", userId, e.getCause());
			e.printStackTrace();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
