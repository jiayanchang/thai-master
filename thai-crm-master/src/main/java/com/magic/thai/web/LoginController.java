package com.magic.thai.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.service.UserService;
import com.magic.thai.security.UserProfile;

@Controller
public class LoginController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView tologin() {
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam String loginName, @RequestParam String password, @RequestParam String captcha,
			HttpSession session, HttpServletRequest request) throws Exception {

		// result.rejectValue("email", "error.user",
		// "An account already exists for this email.");
		// ObjectError error = new
		// ObjectError("email","An account already exists for this email.");
		// result.addError(error);
		logger.info("{} login! captcha is {}", loginName, session.getAttribute("captcha"));
		Assert.isTrue(captcha.equalsIgnoreCase(session.getAttribute("captcha").toString()), "验证码错误");
		UserProfile userprofile = userService.login(loginName, password);

		session.setAttribute("userprofile", userprofile);
		if (userprofile.isPlatformUser()) {
			return new ModelAndView("redirect:/a/order/list");
		} else {
			return new ModelAndView("redirect:/f/goods/list");
		}
	}

	@RequestMapping(value = "/a/index")
	public ModelAndView adminMainPage() {
		return new ModelAndView("/admin/index");
	}

	@RequestMapping(value = "/f/index")
	public ModelAndView frontMainPage() {
		return new ModelAndView("/front/index");
	}
}
