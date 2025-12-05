package com.lukai.crm.workbench.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.commons.utils.DateUtils;
import com.lukai.crm.commons.utils.UUIdUtils;
import com.lukai.crm.settings.domain.DicValue;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.DicValueService;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.workbench.domain.Activity;
import com.lukai.crm.workbench.domain.Clue;
import com.lukai.crm.workbench.domain.ClueRemark;
import com.lukai.crm.workbench.service.ActivityService;
import com.lukai.crm.workbench.service.ClueRemarkService;
import com.lukai.crm.workbench.service.ClueService;

@Controller
public class ClueController {
	@Autowired
	UserService userService;
	@Autowired
	DicValueService dicValueService;
	@Autowired
	ClueService clueService;
	@Autowired
	ClueRemarkService clueRemarkService;
	@Autowired
	ActivityService activityService;
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
	public Object queryClueByConditionForPage(String fullname, String company, String phone, String source, String owner,
																				String mphone, String state, int pageNo, int pageSize)
	{
		HashMap<String, Object> clueMap = new HashMap<String, Object>();
		int beginNo = (pageNo - 1) * pageSize;
		clueMap.put("fullname", fullname);
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
	/**
	 * 保存创建的线索
	 * @param clue
	 * @param request
	 * @return
	 */
	@RequestMapping("/workbench/clue/saveCreateClue.do")
	@ResponseBody
	public ReturnObject saveCreateClue(Clue clue,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Contants.SESSION_USER);
		clue.setCreateBy(user.getId());
		clue.setCreateTime(DateUtils.formateDateTime(new Date()));
		clue.setId(UUIdUtils.getUUId());
		return clueService.saveCreateClue(clue);
	}
	/**
	 * 根据id删除线索
	 * @param ids
	 * @return
	 */
	@RequestMapping("/workbench/clue/deleteClueByIds.do")
	@ResponseBody
	public ReturnObject deleteClueByIds(String[] id) {
		return clueService.deleteClueByIds(id);
	}
	
	/**
	 * リードの詳細画面を初期化する
	 */
	@RequestMapping("/workbench/clue/detailClue.do")
	public String detailClue(String id,HttpServletRequest request) {
		Clue clue = clueService.queryClueByClueId(id);
		List<ClueRemark> clueRemarks = clueRemarkService.queryClueRemarkByClueId(id);
		List<Activity> activities = activityService.queryActivityForClueDetailByClueId(id);
		request.setAttribute("clue", clue);
		request.setAttribute("clueRemarks", clueRemarks);
		request.setAttribute("activities", activities);
		
		return "workbench/clue/detail";
	}
	
	
	@RequestMapping("/workbench/clue/queryClueForEditById.do")
	@ResponseBody
	public Clue queryClueById(String id) {
		Clue clue = clueService.queryClueForEditByClueId(id);
		return clue;
	}

}
