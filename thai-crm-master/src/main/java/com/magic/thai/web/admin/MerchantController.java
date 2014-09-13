package com.magic.thai.web.admin;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.service.MerchantService;
import com.magic.thai.security.UserProfile;
import com.magic.thai.web.validator.UserValidator;

@Controller
@RequestMapping(value = "/a/merchant")
public class MerchantController {

	private static Logger logger = LoggerFactory.getLogger(MerchantController.class);

	@Autowired
	UserValidator userValidator;

	@Autowired
	MerchantService merchantService;

	private String message = "";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		System.out.println("add");
		ModelAndView modelAndView = new ModelAndView("/admin/merchant/add");
		Merchant merchant = new Merchant();
		modelAndView.addObject("merchant", merchant);
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addUserProsses(@ModelAttribute("merchant") Merchant merchant, @RequestParam CommonsMultipartFile file,
			SessionStatus status, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");

		merchantService.create(merchant, userprofile);
		if (file != null) {
			File imageFile = new File(session.getServletContext().getRealPath("/") + merchant.getDetails().getLogoPath());
			try {
				file.transferTo(imageFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("redirect:/a/merchant/list");

	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editUserPage(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView("/admin/merchant/edit");
		Merchant merchant = merchantService.fetch(id);
		modelAndView.addObject("merchant", merchant);
		return modelAndView;
	}

	/*
	 * @ModelAttribute untuk model form dan ("ucd") untuk validator
	 * BindingResult result
	 */
	@RequestMapping(value = "/edit/proccess", method = RequestMethod.POST)
	public ModelAndView editUserprosses(@ModelAttribute("merchant") Merchant merchant, @RequestParam CommonsMultipartFile file,
			SessionStatus status, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("redirect:/user/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");

		merchantService.update(merchant, userprofile);

		if (file != null) {
			File imageFile = new File(session.getServletContext().getRealPath("/") + merchant.getDetails().getLogoPath());
			try {
				if (imageFile.exists()) {
					imageFile.delete();
				}
				file.transferTo(imageFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return modelAndView;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteUserPage(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("redirect:/user/list");
		// userService.delete(userService.findUserbyId(id));
		message = "User successfull delete";
		return modelAndView;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelandView = new ModelAndView("/admin/merchant/list");
		modelandView.addObject("ps", merchantService.getMerchants(null, -1, 1));
		return modelandView;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView searchMerchants(@RequestParam String name, @RequestParam int status, @RequestParam int currPage) {
		logger.info("status : {}, currPage : {}", status, currPage);
		ModelAndView modelandView = new ModelAndView("/admin/merchant/list");
		modelandView.addObject("ps", merchantService.getMerchants(name, status, currPage));
		modelandView.addObject("name", name);
		modelandView.addObject("currPage", currPage);
		modelandView.addObject("status", status);
		return modelandView;
	}

	// @RequestMapping(value = "/json", method = RequestMethod.GET)
	// public String json4Combo() {
	// List<UserControllerDomain> listUcd = DomainToControllerDomain
	// .domaintoControllerList(userService.getAll());
	//
	// message = "";
	// return null;
	// }

	public static void main(String[] args) {
		System.out.println(MerchantController.class.getClassLoader().getResource("decorators.xml"));
	}
}
