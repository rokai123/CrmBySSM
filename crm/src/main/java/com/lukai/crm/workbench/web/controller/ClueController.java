package com.lukai.crm.workbench.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukai.crm.settings.domain.DicValue;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.DicValueService;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.workbench.service.ClueService;

@Controller
public class ClueController {
	@Autowired
	UserService userService;
	@Autowired
	DicValueService dicValueService;
	@Autowired
	ClueService clueService;
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
	
	@RequestMapping("/workbench/clue/queryClueByConditionForPage")
	@ResponseBody
	public Object queryClueByConditionForPage(String fullName, String company, String phone, String source, String owner,
																				String mphone, String state, int pageNo, int pageSize)
	{
		HashMap<String, Object> clueMap = new HashMap<String, Object>();
		int beginNo = (pageNo - 1) * pageSize;
		clueMap.put("fullName", fullName);
		clueMap.put("company", company);
		clueMap.put("phone", phone);
		clueMap.put("source", source);
		clueMap.put("owner", owner);
		clueMap.put("mphone", mphone);
		clueMap.put("state", state);
		clueMap.put("beginNo", beginNo);
		clueMap.put("pageSize", pageSize);
		Map<String, Object> retMap= clueService.queryClueByConditionForPage(clueMap);
		return retMap;
	}
}
