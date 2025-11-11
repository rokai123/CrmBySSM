package com.lukai.crm.workbench.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	/**
	 * 検索条件を元にマーケティングキャンペーン及び総件数を検索する
	 * @param name
	 * @param owner
	 * @param startDate
	 * @param endDate
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * 検索結果をJson Objectで返す
	 */
	@RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
	@ResponseBody
	public Object queryActivityByConditionForPage(String name,String owner,String startDate,String endDate,
																			   Integer pageNo,Integer pageSize) {
		//パラメータをMapオブジェクトに格納する。
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("owner", owner);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("beginNo", (pageNo-1)*pageSize);
		map.put("pageSize", pageSize);
		List<Activity> activities = activityService.queryActivityByConditionForPage(map);
		int totalRows = activityService.queryCountOfActivityByCondition(map);
		Map<String,Object> retMap = new HashMap<String,Object>();
		retMap.put("activities", activities);
		retMap.put("totalRows", totalRows);
		return retMap;
	}
	
	/**
	 * マーケティングキャンペーンを削除する
	 * @param id
	 * @return
	 * created by rokai123
	 * 削除結果をJson Objectで返す
	 */
	@RequestMapping("/workbench/activity/deleteActivityByIds.do")
	@ResponseBody
	public Object deleteActivityByIds(String[] id) {
		ReturnObject returnObject= new ReturnObject();
		try {
			int resultInt = activityService.deleteActivityByIds(id);
			if (resultInt>0) {
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
	
	/**
	 * IDに基づいて該当するマーケティングキャンペーンをリサーチ
	 * created by 102106
	 * 
	 */
	@RequestMapping("/workbench/activity/queryActivityById.do")
	@ResponseBody
	public Object queryActivityById(String id) {
		
		Activity activity = activityService.queryActivityById(id);
		return activity;
	}
	
	/**
	 * IDに基づいて該当するマーケティングキャンペーンを更新する
	 * 
	 * @param activity
	 * @return Json Object
	 * created by 102106
	 * 検索結果をJson Objectで返す
	 */
	@RequestMapping("/workbench/activity/saveEditActivity.do")
	@ResponseBody
	public Object saveEditActivity(Activity activity,HttpSession session) {
		ReturnObject returnObject = new ReturnObject();
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		activity.setEditTime(DateUtils.formateDateTime(new Date()));
		activity.setEditBy(user.getId());
		try {
			int resultInt = activityService.saveActivity(activity);
			if (resultInt>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
			}else {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
			}
		}catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		return returnObject;
	}
}
