package com.lukai.crm.settings.service.Impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.mapper.UserMapper;
import com.lukai.crm.settings.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User queryUserByLoginActAndPwd(Map<String, Object> map) {
		return userMapper.queryUserByactAndPwd(map);
	}
	
}
