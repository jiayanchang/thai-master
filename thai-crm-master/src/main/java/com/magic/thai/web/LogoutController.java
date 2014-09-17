package com.magic.thai.web;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.service.UserService;
import com.magic.thai.security.UserProfile;

@Controller
public class LogoutController {

	private static Logger logger = LoggerFactory.getLogger(LogoutController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/logout")
	public ModelAndView login(HttpSession session) throws Exception {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		if (userprofile == null) {
			return new ModelAndView("redirect:/");
		} else {
			logger.info("{} is logout!", userprofile.getUser().getCodeName());
			session.setAttribute("userprofile", null);
		}
		return new ModelAndView("redirect:/");
	}
}
