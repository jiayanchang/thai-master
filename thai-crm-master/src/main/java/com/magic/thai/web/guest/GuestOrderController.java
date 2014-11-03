package com.magic.thai.web.guest;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
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

import com.magic.thai.db.dao.MerchantOrderGoodsPickupDao;
import com.magic.thai.db.dao.MerchantOrderNotesDao;
import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.domain.MerchantOrderGoodsPickup;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.ChannelOrderService;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.service.InterfaceOrderService;
import com.magic.thai.db.service.MerchantService;
import com.magic.thai.db.service.OrderService;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.exception.webservice.ParameterException;
import com.magic.thai.security.GuestProfile;
import com.magic.thai.util.Asserts;
import com.magic.thai.util.Md5CryptoUtils;

@Controller
@RequestMapping(value = "/g/order")
public class GuestOrderController {

	@Autowired
	MerchantService merchantService;
	@Autowired
	OrderService orderService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	MerchantOrderNotesDao merchantOrderNotesDao;
	@Autowired
	ChannelOrderService channelOrderService;
	@Autowired
	InterfaceOrderService interfaceOrderService;
	@Autowired
	MerchantOrderGoodsPickupDao merchantOrderGoodsPickupDao;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable int id, @RequestParam("t") String token, ModelMap model, HttpSession session) throws ThaiException {
		MerchantOrder order = orderService.fetch(id);
		Asserts.isTrue(Md5CryptoUtils.create(order.getContractorEmail() + order.getOrderNo()).substring(0, 8).equalsIgnoreCase(token),
				new ParameterException("验证身份异常"));
		User user = new User();
		user.setCodeName(order.getContractor());
		user.setName(order.getContractor());
		GuestProfile guestProfile = new GuestProfile(user, null);
		session.setAttribute("userprofile", guestProfile);
		model.addAttribute("order", order);
		return "/guest/edit_order";
	}

	@RequestMapping(value = "/edit/process", method = RequestMethod.POST)
	public String editprocess(@ModelAttribute("order") MerchantOrder orderbean, ModelMap model, @RequestParam String needsPickup,
			@RequestParam String flightNo, @RequestParam String arrivedDate, @RequestParam String arrivedTime, @RequestParam Date deptDate,
			HttpSession session) throws ThaiException {
		GuestProfile guestProfile = (GuestProfile) session.getAttribute("userprofile");
		Asserts.isTrue(guestProfile != null, new ThaiException("验证身份异常"));
		try {
			MerchantOrderGoodsPickup pickup = new MerchantOrderGoodsPickup();
			pickup.setArrivedDate(arrivedDate);
			pickup.setArrivedTime(arrivedTime);
			pickup.setFlightNo(flightNo);

			MerchantOrder order = orderService.update(orderbean, BooleanUtils.toBoolean(needsPickup) ? pickup : null, deptDate,
					guestProfile);

			model.addAttribute("message", "修改成功");
			model.addAttribute("order", order);
			model.addAttribute("isNeedsPickup", order.getGoodses().get(0).isNeedsPickup());
			model.addAttribute("pickup", merchantOrderGoodsPickupDao.loadByMogId(order.getGoodses().get(0).getId()));

			return "/guest/edit_success";
		} catch (ThaiException e) {
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
			return "/guest/edit_order";
		}
	}
}
