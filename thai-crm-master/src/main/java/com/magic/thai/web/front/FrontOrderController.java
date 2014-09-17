package com.magic.thai.web.front;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.domain.Order;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.db.vo.OrderVo;
import com.magic.thai.security.UserProfile;

@Controller
@RequestMapping(value = "/f/order")
public class FrontOrderController {

	@Autowired
	OrderService orderService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpSession session) {
		OrderVo vo = new OrderVo();
		return listPost(vo, session);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView listPost(@ModelAttribute OrderVo orderVo, HttpSession session) {
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		ModelAndView modelandView = new ModelAndView("/front/order/list");
		modelandView.addObject("ps", orderService.getOrderesPage(orderVo, userprofile.getUser().getMerchantId()));
		modelandView.addObject("vo", orderVo);
		return modelandView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView modelandView = new ModelAndView("/front/order/add");
		modelandView.addObject("order", new Order());
		return modelandView;
	}

}
