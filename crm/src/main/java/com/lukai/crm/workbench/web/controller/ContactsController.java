package com.lukai.crm.workbench.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.workbench.domain.Contacts;
import com.lukai.crm.workbench.service.ContactsService;

@Controller
public class ContactsController {

	@Autowired
	private UserService userService;
	@Autowired
	private ContactsService contactsService;

	
	@RequestMapping("/workbench/contacts/toIndex.do")
	public String toIndex(HttpSession session,Model model) {
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		List<User> userList = userService.queryAllUsers();
		
		model.addAttribute("userList", userList);
		return "workbench/contacts/index";
	}
	
	@RequestMapping("/workbench/contacts/queryContactsByConditionForPage.do")
	@ResponseBody
	public Map<String, Object> queryContactsByConditionForPage(
			@RequestParam Map<String, Object> map,
			@RequestParam("pageNo") Integer pageNo,
			@RequestParam("pageSize") Integer pageSize
			) {
			
		int beginNo = (pageNo - 1) * pageSize;
		map.put("beginNo", beginNo);
		map.put("pageSize", pageSize);
		List<Contacts> contactsList = contactsService.queryContactsByConditionForPage(map);
		int  totalCount= contactsService.queryContactsByConditionForPageCount(map);
		
		HashMap<String, Object> resultMap = new HashMap<>();
		resultMap.put("contactsList", contactsList);
		resultMap.put("totalCount", totalCount);
		
		return resultMap;
	}
}
