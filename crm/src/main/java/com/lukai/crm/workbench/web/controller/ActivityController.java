package com.lukai.crm.workbench.web.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.UserService;

@Controller
public class ActivityController {
	
	@Autowired
	UserService userService;
	
	/*
	 * by rokai123 
	 * 
	 * */
	@RequestMapping("/workbench/activity/index.do")
	public String index(HttpServletRequest request) {
		List<User> users = userService.queryAllUsers();
		users.stream().forEach(System.out::println);
		request.setAttribute("userList", users);
		
		return "workbench/activity/index";
	}
}
