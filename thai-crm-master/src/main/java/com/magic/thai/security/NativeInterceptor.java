package com.magic.thai.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class NativeInterceptor extends HandlerInterceptorAdapter {

	Logger logger = LoggerFactory.getLogger(NativeInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object arg2) throws Exception {
		logger.info("preHandle uri : {}, method : {}", req.getRequestURI(), req.getMethod());
		UserProfile userprofile = (UserProfile) req.getSession().getAttribute("userprofile");
		// Assert.notNull(userprofile, "请登陆");
		if (userprofile == null || !userprofile.isPlatformUser()) {
			response.sendRedirect("/" + req.getContextPath());
			return false;
		}
		return true;
	}

}
