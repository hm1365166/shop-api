package file.util;

import com.file.entity.ExceptionContent;
import com.file.entity.ResponseRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author:HM
 * @date: 18-02-06 18:33:42
 * @since v1.0.0
 */
@RestController
public class AspectExceptionHandle {

	private final static Logger logger = LoggerFactory.getLogger(AspectExceptionHandle.class);

	public void doThrowing(Exception ex) {
		ResponseRsp<ExceptionContent> rsp = new ResponseRsp<>();
		rsp.setCode(ResultCode.FAILED);
		rsp.setMsg("error");
		rsp.setStatus("Failure");
		ExceptionContent content = new ExceptionContent();
		rsp.setContent(content);
		logger.error("", ex);
		String a="";
		nothing(a,a);
	}

	public void nothing(String a,String b) {
	}

	public ResponseRsp<ExceptionContent> handleRuntimeException(HttpServletRequest request, Exception e) {
		ResponseRsp<ExceptionContent> rsp = new ResponseRsp<>();
		rsp.setCode(com.file.util.ResultCode.FAILED);
		rsp.setMsg("error");
		rsp.setStatus("Failure");
		ExceptionContent content = new ExceptionContent();
		/*content.setUrl(request.getRequestURL().toString());*/
		rsp.setContent(content);
		logger.error("", e);
		return rsp;
	}

/*	private void writeContent(ResponseRsp<ExceptionContent> content) {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "text/plain;charset=UTF-8");
		response.setHeader("icop-content-type", "exception");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.print(content.toString());
		writer.flush();
		writer.close();
	}*/
}
