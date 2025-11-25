package com.lukai.crm.workbench.web.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.workbench.domain.Clue;

@Controller
public class ClueController {
	@Autowired
	UserService userService;
	
	/**
	 * 进入线索首页
	 * @param request
	 * @return
	 */
	@RequestMapping("/workbench/clue/index.do")
	public String index(HttpServletRequest request) {
		List<User> users = userService.queryAllUsers();
		request.setAttribute("userList", users);
		
		return "workbench/clue/index";
	}
	
	@RequestMapping("queryClueByConditionForPage")
	@ResponseBody
	public List<Clue> queryClueByConditionForPage(String fullName){
		
		return null;
	}
}
