package file.controller;

import com.file.entity.ExceptionContent;
import com.file.entity.ResponseRsp;
import com.file.util.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:HM
 * @date: 18-02-06 14:28:30
 * @since v1.0.0
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
	private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseRsp<ExceptionContent> handleRuntimeException(HttpServletRequest request,Exception e) {
		ResponseRsp<ExceptionContent> rsp = new ResponseRsp<>();
		rsp.setCode(ResultCode.FAILED);
		rsp.setMsg("error");
		rsp.setStatus("Failure");
		ExceptionContent content = new ExceptionContent();
		/*content.setUrl(request.getRequestURL().toString());*/
		rsp.setContent(content);
		logger.error("", e);
		return rsp;
	}

}
