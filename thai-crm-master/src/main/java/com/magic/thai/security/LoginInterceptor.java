package com.magic.thai.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.magic.thai.db.service.UserService;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Autowired
	UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object arg2) throws Exception {
		logger.info("preHandle uri : {}, method : {}", req.getRequestURI(), req.getMethod());

		if ("true".equals(req.getParameter("testmode"))) {
			req.getSession().setAttribute("userprofile", userService.login(req.getParameter("username"), req.getParameter("password")));
			// response.sendRedirect("/" + req.getParameter("url"));
			// return true;
		}

		UserProfile userprofile = (UserProfile) req.getSession().getAttribute("userprofile");
		// Assert.notNull(userprofile, "请登陆");
		if (userprofile == null) {
			response.sendRedirect("/" + req.getContextPath());
			return false;
		}
		return true;
	}

}
