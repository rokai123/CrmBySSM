package com.lukai.crm.settings.service;

import java.util.List;

import com.lukai.crm.settings.domain.User;

public interface UserService {

	User queryUserByLoginAct(String loginAct);
	
	List<User> queryAllUsers();
	
	int saveChangePwd(User user);
}
