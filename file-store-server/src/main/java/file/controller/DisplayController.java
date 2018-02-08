package file.controller;

import com.file.entity.File;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.file.service.FileService;
import file.util.JacksonUtil;
import file.util.RedisTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author:HM
 * @date: 17-12-15 16:29:15
 * @since v1.0.0
 */
@Controller
@RequestMapping("/display")
public class DisplayController {
	private static final Logger logger = LoggerFactory.getLogger(DisplayController.class);

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, File> fileTemplate;

	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/toFile", method = RequestMethod.GET)
	public String toFileOperate(HttpServletRequest request, @RequestParam(value = "pageNum", required = true) int pageNum) {

		//获取第1页，10条内容，默认查询总数count
		List<File> files = null;
			/*
			if (RedisTemplateUtil.hasKey("files")) {
				//files = RedisTemplateUtil.getList("files",pageNum-1,pageNum);
				files = RedisTemplateUtil.get("files");
				logger.info("size"+files.size()+"pageNum"+pageNum+"file"+files);
			} else {
				files = fileService.getFileToShow();
				logger.info("size"+files.size());
				RedisTemplateUtil.setNX("files",files);
			}*/
		//使用缓存存储分页
		PageInfo pageUser = null;
		if (fileTemplate.hasKey("files")) {
			List<File> filesList = fileTemplate.opsForList().range("files", 0, fileTemplate.opsForList().size("files") - 1);
			pageUser = getCachePagesInfo(filesList, pageNum, 5);// 0 - 4 ,5 - 10
			System.out.println(JacksonUtil.toJson(filesList));
			//RedisUtil.lpush("testfiles", filesList);
			//List<File> filesList1 = RedisUtil.lrangeList("testfiles", 0, 1, File.class);
			//System.out.println("test" + filesList1);
			if (pageNum == 1) {
				files = fileTemplate.opsForList().range("files", 0, 4);
			} else {
				files = fileTemplate.opsForList().range("files", (pageNum - 1) * 5, pageNum * 5);
			}
			logger.info("size" + files.size() + "pageNum" + pageNum + "file" + files);
			logger.info("pageUser" + pageUser);
		} else {
			try {
				files = fileService.getFileToShow();
				fileTemplate.opsForList().leftPushAll("files", files);
				RedisTemplateUtil.setList("list", files);
				PageHelper.startPage(pageNum, 5);
				files = fileService.getFileToShow();
				logger.info("size" + files.size());
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}

		if (files != null && files.size() != 0) {
			request.getSession().setAttribute("pageUser", pageUser);
			request.getSession().setAttribute("fileDate", files);
			request.getSession().setAttribute("file", files.get(0));
		}

		return "/file/uploadFile";

	}

	private PageInfo getCachePagesInfo(List pageInfo, int pageNum, int pageSize) {
		PageInfo listInfo = null;
		PageInfo pageUser = null;
		listInfo = new PageInfo(pageInfo);
		logger.info("listInfo" + listInfo);
		listInfo.setPageSize(pageSize);
		pageUser = new PageInfo<>();
		pageUser.setPageNum(pageNum);
		pageUser.setPageSize(pageSize);
		int size = Integer.valueOf(listInfo.getTotal() + "");
		pageUser.setPages(size / listInfo.getPageSize() + 1);
		pageUser.setPrePage(pageNum == 0 ? pageNum : pageNum - 1);
		pageUser.setNextPage(pageNum == listInfo.getSize() / pageSize + 1 ? pageNum : pageNum + 1);
		pageUser.setSize(pageSize);
		pageUser.setTotal(listInfo.getTotal());
		return pageUser;
	}
}
