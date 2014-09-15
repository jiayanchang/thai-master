package com.magic.thai.web.front;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsPriceSegment;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.security.UserProfile;
import com.magic.thai.util.PaginationSupport;

@Controller
@RequestMapping(value = "/f/goods")
public class FrontGoodsController {

	@Autowired
	GoodsService goodsService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelandView = new ModelAndView("/front/goods/list");
		PaginationSupport ps = goodsService.getGoodsesPage(null, null, null, -1, 1, 11);
		System.out.println("ps : " + ps.getItems().size());
		modelandView.addObject("ps", ps);
		return modelandView;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView listPost(@RequestParam String title, @RequestParam String dept, @RequestParam String arr,
			@RequestParam Integer status, @RequestParam Integer page, HttpSession session) {
		System.out.println(1);
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");

		ModelAndView modelandView = new ModelAndView("/front/goods/list");
		modelandView.addObject("ps", goodsService.getGoodsesPage(title, dept, arr, status == null ? -1 : status, page == null ? 1 : page,
				userprofile.getUser().getMerchantId()));
		return modelandView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView("/front/goods/add");
		Goods goods = new Goods();
		goods.getSegments().add(new GoodsPriceSegment());
		modelAndView.addObject("goods", goods);
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addProcess(@ModelAttribute Goods goods, @RequestParam CommonsMultipartFile picPathFile,
			@RequestParam CommonsMultipartFile linePicPathAFile, @RequestParam CommonsMultipartFile linePicPathBFile,
			@RequestParam CommonsMultipartFile linePicPathCFile, @RequestParam CommonsMultipartFile linePicPathDFile, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("/front/goods/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		goods.setStatus(Goods.Status.AUDITING);
		int id = goodsService.create(goods, userprofile);

		if (uploadFile(picPathFile, session.getServletContext(), goods, "picPath.jpg")) {
			goods.getDetails().setPicPath(
					session.getServletContext().getRealPath("/") + "/upload/goods/" + goods.getRootId() + "/picPath.jpg");
		}
		if (uploadFile(linePicPathAFile, session.getServletContext(), goods, "a.jpg")) {
			goods.getDetails().setPicPath(session.getServletContext().getRealPath("/") + "/upload/goods/" + goods.getRootId() + "/a.jpg");
		}
		if (uploadFile(linePicPathBFile, session.getServletContext(), goods, "b.jpg")) {
			goods.getDetails().setPicPath(session.getServletContext().getRealPath("/") + "/upload/goods/" + goods.getRootId() + "/b.jpg");
		}
		if (uploadFile(linePicPathCFile, session.getServletContext(), goods, "c.jpg")) {
			goods.getDetails().setPicPath(session.getServletContext().getRealPath("/") + "/upload/goods/" + goods.getRootId() + "/c.jpg");
		}
		if (uploadFile(linePicPathDFile, session.getServletContext(), goods, "d.jpg")) {
			goods.getDetails().setPicPath(session.getServletContext().getRealPath("/") + "/upload/goods/" + goods.getRootId() + "/d.jpg");
		}
		goodsService.update(goods.getDetails(), userprofile);
		modelAndView.addObject("goods", goods);
		return modelAndView;
	}

	private boolean uploadFile(CommonsMultipartFile file, ServletContext context, Goods goods, String filename) {
		if (file == null) {
			return false;
		}
		String parentPath = context.getRealPath("/") + "/upload/goods/" + goods.getRootId();
		File parentDir = new File(parentPath);
		if (!parentDir.exists()) {
			parentDir.mkdir();
		}

		File imageFile = new File(parentPath + "/" + filename);
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
		return true;
	}
}
