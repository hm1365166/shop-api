package file.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ShopUtils {

	/**
	 * 响应json数据
	 *
	 * @param response
	 * @param rt
	 * @throws IOException
	 */
	public static void responseJson(HttpServletResponse response, Result rt) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf8");
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(rt));
	}
}
