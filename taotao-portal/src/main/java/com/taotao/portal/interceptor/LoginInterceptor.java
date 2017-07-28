package com.taotao.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private UserService userService;

	@Value("${SSO_LOGIN_URL}")
	private String SSO_LOGIN_URL;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		TbUser user = userService.getUserByToken(request, response);
		System.out.println(SSO_LOGIN_URL);
		if (user == null) {
			response.sendRedirect("http://localhost:8084/page/login" + "?redirectURL=" + request.getRequestURL());
			return false;
		}
		
		request.setAttribute("user", user);
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

//	private String getUrl(HttpServletRequest request) {
//		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
//				+ request.getContextPath() + request.getRequestURI();
//		String url2 = request.getRequestURL().toString();
//		System.out.println(url2);
//		return url;
//	}

}
