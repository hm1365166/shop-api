package file.util;



import com.file.entity.Download;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.util.Calendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 导出
	 * @param response
	 * @param download
	 * @throws Throwable
	 * @author:HM
	 * @date: 2017/10/10 16:17:50
	 */
	public static void downloadFileForWeb(HttpServletResponse response, Download download) {
		FileInputStream fin = null;
		ServletOutputStream outputStream = null;
		response.setContentType(download.getContentType());
		try {
			String fileName = new String(download.getFileName().getBytes("UTF-8"), "ISO-8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + "");
			fin = new FileInputStream(download.getFilePath());
			outputStream = response.getOutputStream();
			FileCopyUtils.copy(fin, outputStream);
		} catch (Exception e) {
			logger.error("下载错误" + e.getCause());
			e.printStackTrace();
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将多个文件打包成zip文件
	 *
	 * @param filePath
	 * 文件所在路径
	 * @return
	 * 返回一个压缩文件夹得地址
	 * @author:HM
	 * @date: 2017/10/10 15:09:56
	 */
	public static String MulFileToZip(String[] filePath) {
		if (filePath == null || filePath.length == 0) {
			logger.info("文件路径为空");
			return null;
		}
		//存放多文件下载，临时压缩文件夹
		String zipFileName = "D:" + File.separator + "zipTest";
		File zipFile = new File(zipFileName);
		if (!zipFile.exists()) {
			zipFile.mkdirs();
		}
		Long now = DateHelper.getNow().getTime();
		File zip = new File(zipFileName + File.separator + now + "zipTemp" + ".zip");
		InputStream input = null;
		ZipOutputStream zipOut = null;
		try {
			zipOut = new ZipOutputStream(new FileOutputStream(zip));
			File[] files = new File[filePath.length];
			for (int i = 0; i < filePath.length; i++) {
				files[i] = new File(filePath[i]);
			}
			// zip的名称为
			zipOut.setComment(now + "zipTemp");
			//循环将需要压缩的文件压缩
			for (int i = 0; i < files.length; ++i) {
				input = new FileInputStream(files[i]);
				zipOut.putNextEntry(new ZipEntry(files[i].getName()));

				int temp = 0;
				while ((temp = input.read()) != -1) {
					zipOut.write(temp);
				}
			}
		} catch (Exception e) {
			logger.error("压缩文件异常" + e.getCause());
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (zipOut != null) {
					zipOut.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return zip.getPath();
	}

	/**
	 * 将图片转换为byte数组
	 * @param path 图片路径
	 * @return
	 */
	public static byte[] image2byte(String path) {
		//定义byte数组
		byte[] data = null;
		//输入流
		FileImageInputStream input = null;
		try {
			input = new FileImageInputStream(new File(path));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead = 0;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			data = output.toByteArray();
			output.close();
			input.close();
		} catch (FileNotFoundException ex1) {
			ex1.printStackTrace();
		} catch (IOException ex1) {
			ex1.printStackTrace();
		}
		logger.info("" + data + "size" + data.length);
		return data;
	}

	//byte数组到图片
	public static void byte2image(byte[] data, String path) throws IOException {
		int i = path.lastIndexOf("/") + 1;
		String pathToCreate = path.substring(0, i);

		//得到倒数第二个符号的  index
		/*int i1 = path.lastIndexOf("/", path.lastIndexOf("/") - 1)+1;
		System.out.println(path.substring(i1,i-1));*/

		File paths = new File(pathToCreate);//文件夹全路径
		File pathName = new File(path);//文件全路径

		if (!paths.exists()) {
			paths.mkdirs();//创建文件夹
		}

		if (!pathName.exists()) {
			pathName.createNewFile();//创建文件
		} else {
			logger.info("创建文件失败,存在同名文件");
			throw new FileAlreadyExistsException("创建文件失败,存在同名文件");
		}

		if (data.length < 3 || path.equals("")) {
			return;
		}

		try {
			FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));

			imageOutput.write(data, 0, data.length);//写入文件
			imageOutput.close();
			System.out.println("Make Picture success,Please find image in " + path);
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getCause());
			ex.printStackTrace();
		}
	}

	/**
	 * 批量删除
	 * @param filePath
	 * @author:HM
	 * @date: 2017/10/10 16:22:36
	 */
	public static void DeleteFile(String... filePath) {
		for (int i = 0; i < filePath.length; i++) {
			File file = new File(filePath[i]);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	public static void main(String[] args) {
		String[] s = new String[1];
		s[0] = "D:/hr/comssmm/src/main/webapp/images/20171011/Lighthouse.jpg";
		//MulFileToZip(s);
		Calendar calendar = Calendar.getInstance();
		long time = DateHelper.getNow().getTime();
		calendar.setTimeInMillis(time);
	}

}
