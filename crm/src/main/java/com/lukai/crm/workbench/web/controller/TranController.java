package com.lukai.crm.workbench.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.settings.domain.DicValue;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.DicValueService;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.workbench.domain.Activity;
import com.lukai.crm.workbench.domain.Contacts;
import com.lukai.crm.workbench.domain.Tran;
import com.lukai.crm.workbench.service.ActivityService;
import com.lukai.crm.workbench.service.ContactsService;
import com.lukai.crm.workbench.service.TranService;

@Controller
public class TranController {
	@Autowired
	private DicValueService dicValueService;
	@Autowired
	private TranService tranService;
	@Autowired
	private UserService userService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ContactsService contactsService;
	
	@RequestMapping("/workbench/transaction/toIndex.do")
	public String toIndex(HttpServletRequest request,HttpSession session) {
		List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
		List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
		List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		request.setAttribute("transactionTypeList", transactionTypeList);
		request.setAttribute("stageList", stageList);
		request.setAttribute("sourceList", sourceList);
		request.setAttribute("user", user);
		
		return "workbench/transaction/index";
	}
	
	//分页查询交易信息
	@RequestMapping("/workbench/transaction/selectTransByConditionForPage.do")
	@ResponseBody
	public Map<String, Object> selectTransByConditionForPage(
			String owner,String name,String customerName,String stage,String type,String source,
			String contactsName,Integer pageNo,Integer pageSize
	) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("owner", owner);
		map.put("name", name);
		map.put("customerName", customerName);
		map.put("stage", stage);
		map.put("type", type);
		map.put("source", source);
		map.put("contactsName", contactsName);
		map.put("beginNo", (pageNo-1)*pageSize);
		map.put("pageSize", pageSize);
		List<Tran> tranList = tranService.queryTransByConditionForPage(map);
		int totalRows = tranService.queryTransByConditionForPageCount(map);
		HashMap<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("tranList", tranList);
		retMap.put("totalRows", totalRows);
		
		return retMap;
		
	}
	
	@RequestMapping("/workbench/transaction/toCreateTranPage.do")
	public String toCreateTranPage(HttpSession session,HttpServletRequest request) {
		User user=(User)session.getAttribute(Contants.SESSION_USER);
		List<User> userList = userService.queryAllUsers();
		List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
		List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
		List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
		request.setAttribute("user", user);
		request.setAttribute("userList", userList);
		request.setAttribute("transactionTypeList", transactionTypeList);
		request.setAttribute("stageList", stageList);
		request.setAttribute("sourceList", sourceList);
		
		return "workbench/transaction/save";
	}
	
	@RequestMapping("/workbench/transaction/queryActivitiesByNameLike.do")
	@ResponseBody
	public List<Activity> queryActivitiesByNameLike(String Actname) {
		return activityService.queryActivitiesByNameLike(Actname);
	}
	
	
	@RequestMapping("/workbench/transaction/queryContactsByNameLike.do")
	@ResponseBody
	public List<Contacts> queryContactsByNameLike(String contName) {
		return contactsService.queryContactsByNameLike(contName);
	}
	
	@RequestMapping("/workbench/transaction/queryPossibilityByStage.do")
	@ResponseBody
	public String queryPossibilityByStage(String stage) {
		ResourceBundle rb = ResourceBundle.getBundle("possibility");
		String  possibility = rb.getString(stage);
		
		
		return possibility;
	}
}
