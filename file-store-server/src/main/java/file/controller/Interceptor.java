package file.controller;


import file.util.Result;
import file.util.ResultCode;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Result rt = new Result(ResultCode.DEFAULT_CODE);
		boolean needLoginCheck = true; // 是否需要登陆校验
		boolean needAuthCheck = true; // 是否需要权限校验
		// 不需要权限校验也不需要登陆校验的接口URI路径
		final String URIS = "/login;/logout;/feedback;/modify/pwd;/export/personInfo";
		for (String uri : URIS.split(";")) {
			if (request.getRequestURI().endsWith(uri) || request.getRequestURI().contains("/feedback")) {
				needLoginCheck = false;
				needAuthCheck = false;
			}
		}
		// 查询权限操作，需要登录验证不需要权限验证
		if (request.getRequestURI().endsWith("/auth/judge")) {
			needLoginCheck = true;
			needAuthCheck = false;
		}
		// 如果需要登录验证，进行登录验证
		/*if (needLoginCheck) {
			// 如果没有登录就返回
			if (request.getSession().getAttribute("u_id") == null || request.getSession().getAttribute("u_name") == null || request.getSession().getAttribute("user_id") == null) {
				rt.setStatus(ResultCode.UN_LOGIN);
				rt.setErrorMessage("您还没有登录");
				ShopUtils.responseJson(response, rt);
				return false;
			}
		}*/

		/*// 如果需要权限校验，对用户进行权限的校验
		if (needAuthCheck) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			// 得到访问的controller应该具有的权限
			Right right = handlerMethod.getMethod().getAnnotation(Right.class);
			if (right == null) {
				return true;
			}
			*//**
		 * 判断当前用户是否是候选人
		 * 如果是候选人userId为0
		 * 如果不是，userId为当前用户id
		 *//*
			Integer userId = (Integer) request.getSession().getAttribute("user_id");
			if (!privilegeService.hasPrivilege(userId, right.code())) {
				rt.setStatus(ResultCode.FAILED);
				rt.setErrorMessage("您没有【" + right.name() + "】操作的权限");
				ShopUtils.responseJson(response, rt);
				return false;
			}
		}*/
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

	}
}

