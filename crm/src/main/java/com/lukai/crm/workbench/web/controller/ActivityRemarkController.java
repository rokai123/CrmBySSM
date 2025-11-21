package com.lukai.crm.workbench.web.controller;

import java.util.Date;

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
import com.lukai.crm.workbench.domain.ActivityRemark;
import com.lukai.crm.workbench.service.ActivityRemarkService;

@Controller
public class ActivityRemarkController {
	
	@Autowired
	ActivityRemarkService activityRemarkService;
	
	@RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
	@ResponseBody
	public ReturnObject saveCreateActivityRemark(ActivityRemark activityRemark,HttpSession session) {
		//封装前台没有传过来的参数
		activityRemark.setId(UUIdUtils.getUUId());
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		activityRemark.setCreateBy(user.getId());
		activityRemark.setCreateTime(DateUtils.formateDateTime(new Date()));
		activityRemark.setEditFlag(Contants.REMARK_EDIT_FLAG_NO_EDITED);
		
		//封装好的activityRemark交给service层处理
		ReturnObject retData = activityRemarkService.saveActivityRemark(activityRemark);
		
		
		return retData;
		
	}
}
