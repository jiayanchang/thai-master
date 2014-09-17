package com.magic.thai.web.front;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.GoodsPriceSegment;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.vo.GoodsVo;
import com.magic.thai.exception.GoodsStatusException;
import com.magic.thai.security.UserProfile;

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

	@RequestMapping(value = "/{id}")
	public ModelAndView listPost(@PathVariable int id) {
		ModelAndView modelandView = new ModelAndView("/front/goods/view");
		modelandView.addObject("goods", goodsService.fetch(id));
		return modelandView;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpSession session) {
		GoodsVo vo = new GoodsVo();
		return listPost(vo, session);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView listPost(@ModelAttribute GoodsVo vo, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		vo.merchantId = userprofile.getUser().getMerchantId() + "";

		ModelAndView modelandView = new ModelAndView("/front/goods/list");
		modelandView.addObject("ps", goodsService.getGoodsesPage(vo));
		modelandView.addObject("goodsVo", vo);
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

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView("/front/goods/edit");
		modelAndView.addObject("goods", goodsService.fetch(id));
		return modelAndView;
	}

	@RequestMapping(value = "/edit/proccess", method = RequestMethod.POST)
	public ModelAndView editProccess(@ModelAttribute Goods goods, @RequestParam CommonsMultipartFile picPathFile,
			@RequestParam CommonsMultipartFile linePicPathAFile, @RequestParam CommonsMultipartFile linePicPathBFile,
			@RequestParam CommonsMultipartFile linePicPathCFile, @RequestParam CommonsMultipartFile linePicPathDFile, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("redirect:/f/goods/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		try {
			updateFile(goods, picPathFile, linePicPathAFile, linePicPathBFile, linePicPathCFile, linePicPathDFile, session);
			goodsService.update(goods, userprofile);
			modelAndView.addObject("message", "编辑成功");
		} catch (GoodsStatusException e) {
			e.printStackTrace();
			modelAndView.setViewName("/front/goods/edit");
			modelAndView.addObject("message", e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addProcess(@ModelAttribute Goods goods, @RequestParam CommonsMultipartFile picPathFile,
			@RequestParam CommonsMultipartFile linePicPathAFile, @RequestParam CommonsMultipartFile linePicPathBFile,
			@RequestParam CommonsMultipartFile linePicPathCFile, @RequestParam CommonsMultipartFile linePicPathDFile, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("redirect:/f/goods/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		goodsService.create(goods, userprofile);
		updateFile(goods, picPathFile, linePicPathAFile, linePicPathBFile, linePicPathCFile, linePicPathDFile, session);
		goodsService.update(goods.getDetails(), userprofile);
		modelAndView.addObject("goods", goods);
		return modelAndView;
	}

	private void updateFile(Goods goods, CommonsMultipartFile picPathFile, CommonsMultipartFile linePicPathAFile,
			CommonsMultipartFile linePicPathBFile, CommonsMultipartFile linePicPathCFile, CommonsMultipartFile linePicPathDFile,
			HttpSession session) {
		if (uploadFile(picPathFile, session.getServletContext(), goods, "picPath.jpg")) {
			goods.getDetails().setPicPath("/resources/goods/" + goods.getRootId() + "/picPath.jpg");
		} else {
			goods.getDetails().setPicPath(null);
		}

		if (uploadFile(linePicPathAFile, session.getServletContext(), goods, "a.jpg")) {
			goods.getDetails().setLinePicPathA("/resources/goods/" + goods.getRootId() + "/a.jpg");
		} else {
			goods.getDetails().setLinePicPathA(null);
		}

		if (uploadFile(linePicPathBFile, session.getServletContext(), goods, "b.jpg")) {
			goods.getDetails().setLinePicPathB("/resources/goods/" + goods.getRootId() + "/b.jpg");
		} else {
			goods.getDetails().setLinePicPathB(null);
		}

		if (uploadFile(linePicPathCFile, session.getServletContext(), goods, "c.jpg")) {
			goods.getDetails().setLinePicPathC("/resources/goods/" + goods.getRootId() + "/c.jpg");
		} else {
			goods.getDetails().setLinePicPathC(null);
		}

		if (uploadFile(linePicPathDFile, session.getServletContext(), goods, "d.jpg")) {
			goods.getDetails().setLinePicPathD("/resources/goods/" + goods.getRootId() + "/d.jpg");
		} else {
			goods.getDetails().setLinePicPathD(null);
		}
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
