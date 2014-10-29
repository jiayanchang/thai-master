package com.magic.thai.web.admin;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.service.SnapshotGoodsService;
import com.magic.thai.db.vo.GoodsVo;
import com.magic.thai.exception.GoodsStatusException;
import com.magic.thai.exception.NoPermissionsException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.web.DataVo;

@Controller
@RequestMapping(value = "/a/goods")
public class GoodsController {

	@Autowired
	GoodsService goodsService;

	@Autowired
	SnapshotGoodsService snapshotGoodsService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/{id}")
	public ModelAndView view(@PathVariable int id) {
		ModelAndView modelandView = new ModelAndView("/admin/goods/view");
		modelandView.addObject("goods", goodsService.fetch(id));
		return modelandView;
	}

	@RequestMapping(value = "/snapshot/{mogId}")
	public ModelAndView snapshot(@PathVariable int mogId) {
		ModelAndView modelandView = new ModelAndView("/admin/goods/snapshot");
		modelandView.addObject("goods", snapshotGoodsService.fetchByMogId(mogId));
		return modelandView;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		return listPost(new GoodsVo());
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView listPost(@ModelAttribute GoodsVo goodsVo) {
		goodsVo.excludeStatuses = new Integer[] { Goods.Status.NEW, Goods.Status.REJECTED };
		ModelAndView modelandView = new ModelAndView("/admin/goods/list");
		modelandView.addObject("ps", goodsService.getGoodsesPage(goodsVo));
		modelandView.addObject("vo", goodsVo);
		return modelandView;
	}

	@RequestMapping(value = "/audits", method = RequestMethod.GET)
	public ModelAndView audits() {
		GoodsVo vo = new GoodsVo();
		vo.status = Goods.Status.AUDITING;
		return listPost(vo);
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable int id) {
		ModelAndView modelAndView = new ModelAndView("/admin/goods/edit");
		modelAndView.addObject("goods", goodsService.fetch(id));
		return modelAndView;
	}

	@RequestMapping(value = "/edit/proccess", method = RequestMethod.POST)
	public ModelAndView editProccess(@ModelAttribute Goods goods, @RequestParam CommonsMultipartFile picPathFile,
			@RequestParam CommonsMultipartFile linePicPathAFile, @RequestParam CommonsMultipartFile linePicPathBFile,
			@RequestParam CommonsMultipartFile linePicPathCFile, @RequestParam CommonsMultipartFile linePicPathDFile, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("redirect:/a/goods/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		try {
			updateFile(goods, picPathFile, linePicPathAFile, linePicPathBFile, linePicPathCFile, linePicPathDFile, session);
			goods.setStatus(Goods.Status.DEPLOYED);
			goodsService.update(goods, userprofile);
			modelAndView.addObject("message", "商品修改成功");
		} catch (GoodsStatusException e) {
			e.printStackTrace();
			modelAndView.setViewName("/admin/goods/edit");
			modelAndView.addObject("message", e.getMessage());
		}
		return modelAndView;
	}

	private void updateFile(Goods goods, CommonsMultipartFile picPathFile, CommonsMultipartFile linePicPathAFile,
			CommonsMultipartFile linePicPathBFile, CommonsMultipartFile linePicPathCFile, CommonsMultipartFile linePicPathDFile,
			HttpSession session) {
		if (uploadFile(picPathFile, session.getServletContext(), goods, "picPath.jpg")) {
			goods.getDetails().setPicPath("/resources/goods/" + goods.getId() + "/picPath.jpg");
		}

		if (uploadFile(linePicPathAFile, session.getServletContext(), goods, "a.jpg")) {
			goods.getDetails().setLinePicPathA("/resources/goods/" + goods.getId() + "/a.jpg");
		}

		if (uploadFile(linePicPathBFile, session.getServletContext(), goods, "b.jpg")) {
			goods.getDetails().setLinePicPathB("/resources/goods/" + goods.getId() + "/b.jpg");
		}

		if (uploadFile(linePicPathCFile, session.getServletContext(), goods, "c.jpg")) {
			goods.getDetails().setLinePicPathC("/resources/goods/" + goods.getId() + "/c.jpg");
		}

		if (uploadFile(linePicPathDFile, session.getServletContext(), goods, "d.jpg")) {
			goods.getDetails().setLinePicPathD("/resources/goods/" + goods.getId() + "/d.jpg");
		}
	}

	private boolean uploadFile(CommonsMultipartFile file, ServletContext context, Goods goods, String filename) {
		if (file == null) {
			return false;
		}
		String parentPath = context.getRealPath("/") + "/upload/goods/" + goods.getId();
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
	public ModelMap reject(@RequestParam int id, @RequestParam String reason, HttpSession session, ModelMap model) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		try {
			goodsService.reject(id, reason, userprofile);
		} catch (GoodsStatusException e) {
			e.printStackTrace();
			model.put("message", e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public ModelMap cancel(@RequestParam int id, @RequestParam String reason, HttpSession session, ModelMap model) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		try {
			goodsService.cancel(id, reason, userprofile);
			model.put("data", DataVo.success(id));
		} catch (GoodsStatusException e) {
			e.printStackTrace();
			model.put("data", DataVo.fail(e.getMessage()));
			model.put("message", e.getMessage());
		}
		return model;
	}
}
