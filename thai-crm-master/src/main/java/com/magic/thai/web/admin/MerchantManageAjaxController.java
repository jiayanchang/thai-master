package com.magic.thai.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.magic.thai.db.service.MerchantService;

@Controller
@RequestMapping(value = "/mchts")
public class MerchantManageAjaxController {

	@Autowired
	MerchantService merchantService;

	private Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

//	@RequestMapping(value = "/list", method = RequestMethod.POST)
//	@ResponseBody
//	public String addUser(@RequestBody String ucd) {
//		userService.create(DomainControllertoDomain.UserControllertoUser(gson
//				.fromJson(ucd, UserControllerDomain.class)));
//		return "Oke";
//	}
//
//	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
//	@ResponseBody
//	public void deleteUser(@PathVariable int id) {
//		userService.delete(userService.findUserbyId(id));
//	}
//
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public @ResponseBody String listUserPage() {
//		List<UserControllerDomain> listUcd = DomainToControllerDomain
//				.domaintoControllerList(userService.getAll());
//		return gson.toJson(listUcd);
//
//	}
	@RequestMapping(value = "/listData")
	public @ResponseBody String listData() {
		System.out.println("listData");
		return "[{\"id\":1,\"name\":\"thai\",\"createdDate\":\"10/09/2014\",\"type\":1},{\"id\":2,\"name\":\"test\",\"createdDate\":\"10/09/2014\",\"type\":0}]";
//		return gson.toJson(merchantService.list());
	}

}
