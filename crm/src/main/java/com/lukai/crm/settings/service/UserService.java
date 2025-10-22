package com.lukai.crm.settings.service;

import java.util.Map;

import com.lukai.crm.settings.domain.User;

public interface UserService {

	User queryUserByLoginActAndPwd(Map<String, Object> map);
}
