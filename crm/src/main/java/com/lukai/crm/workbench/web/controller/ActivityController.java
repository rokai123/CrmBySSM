package com.lukai.crm.workbench.web.controller;

import java.util.Date;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.commons.utils.DateUtils;
import com.lukai.crm.commons.utils.UUIdUtils;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.workbench.domain.Activity;
import com.lukai.crm.workbench.service.ActivityService;

@Controller
public class ActivityController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ActivityService activityService;
	
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
	
	
	/**
	 * 
	 * @param activity
	 * @param session
	 * @return Json Object
	 * マーケティングキャンペーン新規機能を実装する方法
	 */
	@RequestMapping("/workbench/activity/saveCreateActivity.do")
	@ResponseBody
	public Object saveCreateActivity(Activity activity,HttpSession session) {
		//セッションスコープから現在ユーザを取得。
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		//データの一貫性を保つため、アカウント名よりユーザーIDを優先して使用すること。
		activity.setCreateBy(user.getId());
		activity.setId(UUIdUtils.getUUId());
		activity.setCreateTime(DateUtils.formateDateTime(new Date()));
		ReturnObject returnObject = new ReturnObject();
		try {
			int resultNum = activityService.saveCreateActivity(activity);
			if (resultNum>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				
			}else {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		
		
		return returnObject;
		
		
	}
}
