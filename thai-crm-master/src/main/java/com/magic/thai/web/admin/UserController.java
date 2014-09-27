package com.magic.thai.web.admin;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.UserService;
import com.magic.thai.exception.OrderStatusException;
import com.magic.thai.security.UserProfile;

@Controller
@RequestMapping(value = "/a/user")
public class UserController {

	@Autowired
	UserService userService;

	private String message = "";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView tolist() {
		ModelAndView modelAndView = new ModelAndView("redirect:/a/user/list");
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addUser() {
		ModelAndView modelAndView = new ModelAndView("/admin/user/add");
		modelAndView.addObject("user", new User());
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addUserProsses(@ModelAttribute("user") User userbean, BindingResult result, SessionStatus status,
			HttpSession session) {
		ModelAndView modelAndViewUser = new ModelAndView("/admin/user/add");
		ModelAndView modelAndView = new ModelAndView("redirect:/a/user/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");

		if (result.hasErrors()) {
			modelAndViewUser.addObject("action", "/a/user/add");
			modelAndViewUser.addObject("url", "/a/user/add");
			modelAndViewUser.addObject("user", userbean);
			return modelAndViewUser;
		} else {
			userService.create(userbean, userprofile);
			message = "User successfull created";
			return modelAndView;
		}
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editUserPage(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView("/admin/user/edit");
		modelAndView.addObject("user", userService.findUserbyId(id));
		return modelAndView;
	}

	/*
	 * @ModelAttribute untuk model form dan ("ucd") untuk validator
	 * BindingResult result
	 */
	@RequestMapping(value = "/edit/proccess", method = RequestMethod.POST)
	public ModelAndView editUserprosses(@ModelAttribute("user") User userbean, BindingResult result, SessionStatus status,
			HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");

		ModelAndView modelAndView = new ModelAndView("redirect:/a/user/list");
		ModelAndView modelAndViewError = new ModelAndView("/admin/user/edit");
		System.out.println(userbean.getId() + " id");
		if (result.hasErrors()) {
			modelAndViewError.addObject("action", "/user/edit/proccess");
			modelAndViewError.addObject("url", "/user");
			modelAndViewError.addObject("user", userbean);
			return modelAndViewError;
		} else {
			User user = userService.findUserbyId(userbean.getId());
			user.setCodeName(userbean.getCodeName());
			user.setName(userbean.getName());
			user.setLoginName(userbean.getLoginName());
			user.setMobile(userbean.getMobile());
			user.setPassword(userbean.getPassword());
			userService.update(user, userprofile);
			message = "User successfull updated";
			return modelAndView;
		}
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteUserPage(@PathVariable int id, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");

		ModelAndView modelAndView = new ModelAndView("redirect:/a/user/list");
		userService.delete(id, userprofile);
		message = "User successfull deleted";
		return modelAndView;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		ModelAndView modelandView = new ModelAndView("/admin/user/list");
		modelandView.addObject("ps", userService.getUsersPage(null, null, -1, 1, userprofile));
		message = "";
		return modelandView;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView listUserPage(@RequestParam String name, @RequestParam String loginName, @RequestParam int status,
			@RequestParam("vo.page") int page, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");

		ModelAndView modelandView = new ModelAndView("/admin/user/list");
		modelandView.addObject("ps", userService.getUsersPage(name, loginName, status, page, userprofile));
		modelandView.addObject("name", name);
		modelandView.addObject("status", status);
		modelandView.addObject("page", page);
		modelandView.addObject("loginName", loginName);
		message = "";
		return modelandView;
	}

	@RequestMapping(value = "/enable/{id}")
	public ModelAndView enable(@PathVariable int id, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		ModelAndView modelAndView = new ModelAndView("redirect:/a/user/list");
		try {
			userService.enable(id, userprofile);
			message = "用户启用成功";
		} catch (OrderStatusException e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		return modelAndView;
	}

	@RequestMapping(value = "/disable/{id}")
	public ModelAndView disable(@PathVariable int id, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		ModelAndView modelAndView = new ModelAndView("redirect:/a/user/list");
		try {
			userService.disable(id, userprofile);
			message = "用户停用成功";
		} catch (OrderStatusException e) {
			e.printStackTrace();
			message = e.getMessage();
		}
		return modelAndView;
	}
}
