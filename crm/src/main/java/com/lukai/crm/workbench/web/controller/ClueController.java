package com.lukai.crm.workbench.web.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukai.crm.settings.domain.DicValue;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.DicValueService;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.workbench.domain.Clue;

@Controller
public class ClueController {
	@Autowired
	UserService userService;
	@Autowired
	DicValueService dicValueService;
	/**
	 * 进入线索首页
	 * @param request
	 * @return
	 */
	@RequestMapping("/workbench/clue/index.do")
	public String index(HttpServletRequest request) {
		List<User> users = userService.queryAllUsers();
		List<DicValue> appellations = dicValueService.queryDicValueByTypeCode("appellation");
		List<DicValue> clueStates = dicValueService.queryDicValueByTypeCode("clueState");
		List<DicValue> sources = dicValueService.queryDicValueByTypeCode("source");
		
		request.setAttribute("appellationList", appellations);
		request.setAttribute("clueStateList", clueStates);
		request.setAttribute("sourceList", sources);
		request.setAttribute("userList", users);
		return "workbench/clue/index";
	}
	
	@RequestMapping("queryClueByConditionForPage")
	@ResponseBody
	public List<Clue> queryClueByConditionForPage(String fullName){
		
		return null;
	}
}
