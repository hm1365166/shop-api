package controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	final private Logger logger = LoggerFactory.getLogger(this.getClass());


	@RequestMapping("/login")
	public String toLogin() {
		return "index";
	}



}
