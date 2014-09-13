package com.magic.thai.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.magic.thai.db.service.UserService;
import com.magic.thai.util.DomainToControllerDomain;
import com.magic.thai.web.validator.UserValidator;

@Controller
@RequestMapping(value = "/users")
public class UserManageAjaxController {

	@Autowired
	UserValidator userValidator;

	@Autowired
	UserService userService;

	private Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public String addUser(@RequestBody String ucd) {

		// userService.create(DomainControllertoDomain.UserControllertoUser(gson
		// .fromJson(ucd, UserControllerDomain.class)));
		return "Oke";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteUser(@PathVariable int id) {

		// userService.delete(userService.findUserbyId(id));

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody String listUserPage() {

		List<UserControllerDomain> listUcd = DomainToControllerDomain.domaintoControllerList(userService.getAll());

		return gson.toJson(listUcd);

	}

}
