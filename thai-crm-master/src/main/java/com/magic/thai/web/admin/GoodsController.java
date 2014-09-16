package com.magic.thai.web.admin;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.exception.GoodsStatusException;
import com.magic.thai.exception.NoPermissionsException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.web.DataVo;

@Controller
@RequestMapping(value = "/a/goods")
public class GoodsController {

	@Autowired
	GoodsService goodsService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable int id) {
		ModelAndView modelandView = new ModelAndView("/admin/goods/view");
		modelandView.addObject("goods", goodsService.fetch(id));
		return modelandView;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ModelAndView viewOnly(@PathVariable int id) {
		ModelAndView modelandView = new ModelAndView("/admin/goods/view");
		modelandView.addObject("goods", goodsService.load(id));
		return modelandView;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		return listPost(null, null, null, null, 1);
	}

	@RequestMapping(value = "/audits", method = RequestMethod.GET)
	public ModelAndView audits() {
		return listPost(null, null, null, Goods.Status.AUDITING, 1);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView listPost(@RequestParam String title, @RequestParam String dept, @RequestParam String arr,
			@RequestParam Integer status, @RequestParam Integer page) {
		ModelAndView modelandView = new ModelAndView("/admin/goods/list");
		modelandView.addObject("ps",
				goodsService.getGoodsesPage(title, dept, arr, status == null ? null : new Integer[] { status }, page == null ? 1 : page));
		modelandView.addObject("title", title);
		modelandView.addObject("dept", dept);
		modelandView.addObject("arr", arr);
		modelandView.addObject("status", status);
		modelandView.addObject("page", page);
		return modelandView;
	}

	@RequestMapping(value = "/audit/{id}", method = RequestMethod.GET)
	public ModelAndView audit(@PathVariable int id) {
		ModelAndView modelandView = new ModelAndView("/admin/goods/audit");
		modelandView.addObject("goods", goodsService.fetch(id));
		return modelandView;
	}

	@RequestMapping(value = "/pass", method = RequestMethod.POST)
	public ModelAndView pass(@RequestParam("id") int id, HttpSession session) {
		ModelAndView modelandView = new ModelAndView("redirect:/a/goods/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		try {
			goodsService.pass(id, userprofile);
		} catch (NoPermissionsException e) {
			e.printStackTrace();
			modelandView.addObject("message", "您不能审核该商品");
		} catch (GoodsStatusException e) {
			e.printStackTrace();
			modelandView.addObject("message", e.getMessage());
		}
		return modelandView;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST)
	public ModelAndView reject(@RequestParam int id, @RequestParam String reason, HttpSession session) {
		ModelAndView modelandView = new ModelAndView("redirect:/a/goods/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		try {
			goodsService.reject(id, reason, userprofile);
		} catch (GoodsStatusException e) {
			e.printStackTrace();
			modelandView.addObject("message", e.getMessage());
		}
		return modelandView;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public ModelAndView cancel(@RequestParam int id, @RequestParam String reason, HttpSession session) {
		ModelAndView modelandView = new ModelAndView("redirect:/a/goods/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		try {
			goodsService.cancel(id, reason, userprofile);
			modelandView.addObject("data", DataVo.success(id));
		} catch (GoodsStatusException e) {
			e.printStackTrace();
			modelandView.addObject("data", DataVo.fail(e.getMessage()));
			modelandView.addObject("message", e.getMessage());
		}
		return modelandView;
	}
}
