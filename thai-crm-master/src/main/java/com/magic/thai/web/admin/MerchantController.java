package com.magic.thai.web.admin;

import java.io.File;
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
import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.MerchantService;
import com.magic.thai.security.UserProfile;

@Controller
@RequestMapping(value = "/a/merchant")
public class MerchantController {

	private static Logger logger = LoggerFactory.getLogger(MerchantController.class);

	@Autowired
	MerchantService merchantService;

	private String message = "";

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView("/admin/merchant/view");
		Merchant merchant = merchantService.fetch(id);
		modelAndView.addObject("merchant", merchant);
		return modelAndView;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ModelAndView viewOnly(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView("/admin/merchant/view");
		Merchant merchant = merchantService.load(id);
		modelAndView.addObject("merchant", merchant);
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView("/admin/merchant/add");
		Merchant merchant = new Merchant();
		modelAndView.addObject("merchant", merchant);
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addProsses(@ModelAttribute("merchant") Merchant merchant, @RequestParam String adminLoginName,
			@RequestParam String adminPassword, @RequestParam CommonsMultipartFile file, SessionStatus status, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");

		User user = new User();
		user.setLoginName(adminLoginName);
		user.setPassword(adminPassword);
		user.setName(merchant.getName());
		merchantService.create(merchant, user, userprofile);
		uploadLogo(merchant, file, session);
		return new ModelAndView("redirect:/a/merchant/list");

	}

	private void uploadLogo(Merchant merchant, CommonsMultipartFile file, HttpSession session) {
		if (file != null) {
			String host = session.getServletContext().getRealPath("/");
			if (!host.endsWith("/")) {
				host += "/";
			}
			File imageFile = new File(host + "upload/logo/" + merchant.getId() + ".jpg");
			try {
				file.transferTo(imageFile);
				merchant.getDetails().setLogoPath(imageFile.getPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editUserPage(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView("/admin/merchant/edit");
		Merchant merchant = merchantService.fetch(id);
		modelAndView.addObject("merchant", merchant);
		return modelAndView;
	}

	@RequestMapping(value = "/edit/proccess", method = RequestMethod.POST)
	public ModelAndView editUserprosses(@ModelAttribute("merchant") Merchant merchantbean, @RequestParam CommonsMultipartFile file,
			SessionStatus status, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("redirect:/a/merchant/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");

		Merchant merchant = merchantService.fetch(merchantbean.getId());
		merchant.setCodeName(merchantbean.getCodeName());
		merchant.setName(merchantbean.getName());
		merchant.setNameEn(merchantbean.getNameEn());
		merchant.setMobile(merchantbean.getMobile());
		merchant.setTel(merchantbean.getTel());
		merchant.setFax(merchantbean.getFax());
		merchant.setAddress(merchantbean.getAddress());
		merchant.getDetails().setNotes(merchantbean.getDetails().getNotes());
		merchantService.update(merchant, userprofile);
		uploadLogo(merchant, file, session);
		return modelAndView;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("redirect:/a/merchant/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		merchantService.delete(id, userprofile);
		message = "User successfull delete";
		return modelAndView;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");

		ModelAndView modelandView = new ModelAndView("/admin/merchant/list");
		modelandView.addObject("ps", merchantService.getMerchantsPage(null, -1, 1));
		return modelandView;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView searchMerchants(@RequestParam String name, @RequestParam int status, @RequestParam("vo.page") int page,
			HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		logger.info("status : {}, currPage : {}", status, page);
		ModelAndView modelandView = new ModelAndView("/admin/merchant/list");
		modelandView.addObject("ps", merchantService.getMerchantsPage(name, status, page));
		modelandView.addObject("name", name);
		modelandView.addObject("page", page);
		modelandView.addObject("status", status);
		return modelandView;
	}

	@RequestMapping(value = "/enable/{id}")
	public ModelAndView enable(@PathVariable int id, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		ModelAndView modelAndView = new ModelAndView("redirect:/a/merchant/list");
		merchantService.enable(id, userprofile);
		message = "商家启用成功";
		return modelAndView;
	}

	@RequestMapping(value = "/disable/{id}")
	public ModelAndView disable(@PathVariable int id, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		ModelAndView modelAndView = new ModelAndView("redirect:/a/merchant/list");
		merchantService.disable(id, userprofile);
		message = "商家停用成功";
		return modelAndView;
	}

}
