package com.magic.thai.web.admin;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.domain.Channel;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.service.ChannelService;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.service.MerchantService;
import com.magic.thai.db.vo.GoodsVo;
import com.magic.thai.db.vo.MerchantVo;

@Controller
@RequestMapping(value = "/a/channel")
public class ChannelController {

	@Autowired
	ChannelService channelService;

	@Autowired
	MerchantService merchantService;

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
		return listPost(1);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView listPost(@RequestParam Integer page) {
		System.out.println(1);
		ModelAndView modelandView = new ModelAndView("/admin/channel/list");
		modelandView.addObject("ps", channelService.getChannelesPage(page == null ? 1 : page));
		return modelandView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView modelAndView = new ModelAndView("/admin/channel/add");
		Channel channel = new Channel();

		modelAndView.addObject("channel", channel);
		modelAndView.addObject("goodses", goodsService.list(new GoodsVo(new Integer[] { Goods.Status.DEPLOYED })));
		modelAndView.addObject("merchants", merchantService.list(new MerchantVo()));
		return modelAndView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addProcess(@ModelAttribute Channel channel) {
		ModelAndView modelAndView = new ModelAndView("/admin/channel/list");

		modelAndView.addObject("channel", channel);
		return modelAndView;
	}
}
