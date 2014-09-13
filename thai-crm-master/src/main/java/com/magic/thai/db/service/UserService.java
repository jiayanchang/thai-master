package com.magic.thai.db.service;

import java.util.List;

import com.magic.thai.db.domain.User;
import com.magic.thai.exception.LoginException;
import com.magic.thai.security.UserProfile;

public interface UserService {
	List<User> getAll();

	User findUserbyId(int id);

	int create(User user, UserProfile userprofile);

	void update(User user, UserProfile userprofile);

	UserProfile login(String username, String password) throws LoginException;
}
