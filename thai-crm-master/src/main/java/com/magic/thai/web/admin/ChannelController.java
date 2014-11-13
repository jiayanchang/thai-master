package com.magic.thai.web.admin;

import java.sql.Date;
import java.text.SimpleDateFormat;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.magic.thai.db.dao.ChannelDao;
import com.magic.thai.db.dao.MerchantDao;
import com.magic.thai.db.domain.Channel;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.Merchant;
import com.magic.thai.db.domain.User;
import com.magic.thai.db.service.ChannelService;
import com.magic.thai.db.service.GoodsService;
import com.magic.thai.db.service.MerchantService;
import com.magic.thai.db.service.UserService;
import com.magic.thai.db.vo.GoodsVo;
import com.magic.thai.db.vo.MerchantVo;
import com.magic.thai.db.vo.UserVo;
import com.magic.thai.security.UserProfile;

@Controller
@RequestMapping(value = "/a/channel")
public class ChannelController {

	@Autowired
	ChannelService channelService;
	@Autowired
	ChannelDao channelDao;
	@Autowired
	MerchantService merchantService;
	@Autowired
	MerchantDao merchantDao;
	@Autowired
	GoodsService goodsService;
	@Autowired
	UserService userService;

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
	public ModelAndView listPost(@RequestParam("vo.page") Integer page) {
		ModelAndView modelandView = new ModelAndView("/admin/channel/list");
		modelandView.addObject("ps", channelService.getChannelesPage(page == null ? 1 : page));
		return modelandView;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("/admin/channel/add");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		Channel channel = new Channel();
		modelAndView.addObject("channel", channel);
		modelAndView.addObject("goodses", goodsService.list(new GoodsVo(new Integer[] { Goods.Status.DEPLOYED })));
		modelAndView.addObject("merchants", merchantService.list(new MerchantVo()));

		setUserOptions(modelAndView, userprofile);
		return modelAndView;
	}

	private void setUserOptions(ModelAndView modelAndView, UserProfile userprofile) {
		UserVo vo = new UserVo();
		vo.merchantId = userprofile.getMerchant().getId();
		vo.containsPf4list = true;
		modelAndView.addObject("users", userService.list(vo));
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addProcess(@ModelAttribute Channel channel, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("redirect:/a/channel/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		channelService.create(channel, userprofile);
		return modelAndView;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable int id, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("/admin/channel/edit");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");

		modelAndView.addObject("channel", channelService.fetch(id));
		modelAndView.addObject("goodses", goodsService.list(new GoodsVo(new Integer[] { Goods.Status.DEPLOYED })));
		modelAndView.addObject("merchants", merchantService.list(new MerchantVo()));
		setUserOptions(modelAndView, userprofile);
		return modelAndView;
	}

	@RequestMapping(value = "/edit/process", method = RequestMethod.POST)
	public ModelAndView editProcess(@ModelAttribute("channel") Channel channelbean, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("redirect:/a/channel/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		channelService.update(channelbean, userprofile);
		return modelAndView;
	}

	@RequestMapping(value = "/open/{id}")
	public ModelAndView open(@PathVariable int id, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("redirect:/a/channel/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		channelService.open(id, userprofile);
		return modelAndView;
	}

	@RequestMapping(value = "/delete/{id}")
	public ModelAndView delete(@PathVariable int id, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("redirect:/a/channel/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		channelService.delete(id, userprofile);
		return modelAndView;
	}

	@RequestMapping(value = "/close/{id}")
	public ModelAndView close(@PathVariable int id, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("redirect:/a/channel/list");
		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		channelService.close(id, userprofile);
		return modelAndView;
	}

	@RequestMapping(value = "/open/{id}/merchant", method = RequestMethod.GET)
	public ModelAndView openMerchant(@PathVariable int id, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("/admin/channel/open_merchant");
		// UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");
		Channel channel = channelService.fetch(id);

		if (channel.isOpenedMerchant() && channel.getMerchantId() != null) {
			Merchant merchant = merchantService.load(channel.getMerchantId());
			if (merchant != null) {
				if (!merchant.isOpenedChannel()) {
					merchant.setType(Merchant.Type.CHANNEL);
					merchant.setOpenedChannel(true);
					merchantDao.update(merchant);
				}
				modelAndView.setViewName("redirect:/a/channel/list");
				return modelAndView;
			}
		}

		modelAndView.addObject("channel", channelService.fetch(id));
		Merchant merchant = new Merchant();
		merchant.setName(channel.getName());
		modelAndView.addObject("merchant", merchant);
		return modelAndView;
	}

	@RequestMapping(value = "/open/merchant", method = RequestMethod.POST)
	public ModelAndView openMerchantPost(@ModelAttribute("merchant") Merchant merchant, @RequestParam String adminLoginName,
			@RequestParam String adminPassword, @RequestParam int channelId, @RequestParam CommonsMultipartFile file, SessionStatus status,
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("redirect:/a/channel/list");

		UserProfile userprofile = (UserProfile) session.getAttribute("userprofile");

		User user = new User();
		user.setLoginName(adminLoginName);
		user.setPassword(adminPassword);
		user.setName(merchant.getName());
		merchant.setOpenedChannel(true);
		merchant.setType(Merchant.Type.CHANNEL);
		int merchantId = merchantService.create(merchant, user, userprofile);

		Channel channel = channelService.fetch(channelId);
		channel.setOpenedMerchant(true);
		channel.setMerchantId(merchantId);
		channelDao.update(channel);
		
		return modelAndView;
	}
}
