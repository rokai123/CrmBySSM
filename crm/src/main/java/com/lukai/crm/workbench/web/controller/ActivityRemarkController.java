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
		// フロントエンドから渡されていないパラメータをカプセル化
		activityRemark.setId(UUIdUtils.getUUId());
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		activityRemark.setCreateBy(user.getId());
		activityRemark.setCreateTime(DateUtils.formateDateTime(new Date()));
		activityRemark.setEditFlag(Contants.REMARK_EDIT_FLAG_NO_EDITED);
		
		// カプセル化済みのactivityRemarkをサービス層で処理
		ReturnObject retData = activityRemarkService.saveActivityRemark(activityRemark);
		
		
		return retData;
		
	}
	
	/**
	 * マーケティングキャンペーンの備考IDを条件にマーケティングキャンペーンの備考を削除
	 * @param id
	 * @return
	 */
	@RequestMapping("/workbench/activity/deleteActivityRemarkById.do")
	@ResponseBody
	public ReturnObject deleteActivityRemarkById(String id) {
		
		ReturnObject ret = activityRemarkService.deleteActivityRemarkById(id);
		
		return ret;
	}
	
	/**
	 * マーケティングキャンペーンの備考を編集保存
	 * @param activityRemark
	 * @param session
	 * @return
	 */
	@RequestMapping("/workbench/activity/saveEditActivityRemark.do")
	@ResponseBody
	public ReturnObject saveEditActivityRemark(ActivityRemark activityRemark,HttpSession session) {
		ReturnObject returnObject = activityRemarkService.saveEditActivityRemark(activityRemark, session);
		
		return returnObject;
	}
}
