package com.lukai.crm.workbench.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.settings.domain.User;

@Controller
public class CustomerController {

	
	@RequestMapping("/workbench/customer/toIndex.do")
	public String toIndex(HttpSession session,HttpServletRequest request) {
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		request.setAttribute("user", user);
		return "workbench/customer/index";
	}
}
