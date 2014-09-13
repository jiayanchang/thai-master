package com.magic.thai.util;

import java.util.ArrayList;
import java.util.List;

import com.magic.thai.db.domain.User;
import com.magic.thai.web.admin.UserControllerDomain;

public class DomainToControllerDomain {

	public static UserControllerDomain domaintoController(User user){
		UserControllerDomain ucd = new UserControllerDomain();
		ucd.setUsername(user.getName());
		ucd.setName(user.getName());
		return ucd;
	}
	
	public static List<UserControllerDomain> domaintoControllerList(List<User> list){
		List <UserControllerDomain> listUcd = new ArrayList<UserControllerDomain>();
		for(User user : list){
			listUcd.add(domaintoController(user));
		}
		return listUcd;
		
	}
	
}
