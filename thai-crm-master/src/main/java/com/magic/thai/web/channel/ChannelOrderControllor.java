package com.magic.thai.web.channel;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.domain.Channel;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.service.ChannelOrderService;
import com.magic.thai.db.service.ChannelService;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.service.InterfaceOrderService;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.security.UserProfile;
import com.magic.thai.web.ws.vo.CreateOrderVo;
import com.magic.thai.web.ws.vo.QueryGoodsesVo;


@Controller
@RequestMapping(value = "/c/order")
public class ChannelOrderControllor {

	@Autowired
	ChannelService channelService;
	
	@Autowired
	ChannelOrderService channelOrderService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	InterfaceOrderService interfaceOrderService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpSession session) {
		return listPost(new OrderVo(), session);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView listPost(@ModelAttribute OrderVo orderVo, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		ModelAndView modelandView = new ModelAndView("/channel/order/list");
		OrderVo vo = new OrderVo();
		vo.setChannelId(channelService.loadByMerchantId(userprofile.getMerchant().getId()).getId());
		modelandView.addObject("ps", channelOrderService.getOrderesPage(vo));
		modelandView.addObject("vo", orderVo);
		return modelandView;
	}
	
	@RequestMapping(value = "/{id}")
	public ModelAndView view(@PathVariable int id) {
		ModelAndView modelandView = new ModelAndView("/channel/order/view");
		modelandView.addObject("channelOrder", channelOrderService.fetch(id));
		return modelandView;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model, HttpSession session) throws ThaiException {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		
		Channel channel = channelService.loadByMerchantId(userprofile.getMerchant().getId());
		QueryGoodsesVo vo = new QueryGoodsesVo();
		vo.setToken(channel.getToken());
		List<Goods> goodses = interfaceOrderService.queryGoodses(vo);
		model.addAttribute("goodses", goodses);
		
		
		CreateOrderVo createOrderVo = new CreateOrderVo();
		model.addAttribute("createOrderVo", createOrderVo);
		model.addAttribute("deptDate", DateUtils.addDays(new Date(), 10));
		return "/channel/order/add";
	}

	@RequestMapping(value = "/add/process", method = RequestMethod.POST)
	public String addprocess(@ModelAttribute CreateOrderVo createOrderVo, ModelMap model, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		Channel channel = channelService.loadByMerchantId(userprofile.getMerchant().getId());
		try {
			channel = channelService.fetch(channel.getId());
			interfaceOrderService.channelCreateOrder(createOrderVo, channel, userprofile);
		} catch (ThaiException e) {
			model.addAttribute("message", e.getMessage());
			model.addAttribute("deptDate", DateUtils.addDays(new Date(), 10));

			QueryGoodsesVo vo = new QueryGoodsesVo();
			vo.setToken(channel.getToken());
			List<Goods> goodses;
			try {
				goodses = interfaceOrderService.queryGoodses(vo);
				model.addAttribute("goodses", goodses);			
			} catch (ThaiException excepted) {
				excepted.printStackTrace();
			}
			e.printStackTrace();
			return "/channel/order/add";
		}
		return "redirect:/c/order/list";
	}
	
}
