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
import com.lukai.crm.workbench.domain.ClueRemark;
import com.lukai.crm.workbench.service.ClueRemarkService;

@Controller
public class ClueRemarkController {
	@Autowired
	ClueRemarkService clueRemarkService;
	
	@RequestMapping("/workbench/clue/saveCreateClueRemark.do")
	@ResponseBody
	public ReturnObject saveCreateClueRemark(ClueRemark clueRemark,HttpSession session) {
		//パラメータを収集する
		String id = UUIdUtils.getUUId();
		clueRemark.setId(id);
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		clueRemark.setCreateBy(user.getId());
		clueRemark.setCreateTime( DateUtils.formateDateTime(new Date()));
		clueRemark.setEditFlag("0");
		
		ReturnObject returnObject = clueRemarkService.saveCreateClueRemark(clueRemark,session);
		return returnObject;
	}
	
	@RequestMapping("/workbench/clue/removeClueRemarkById.do")
	@ResponseBody
	public ReturnObject removeClueRemarkById(String id) {
		ReturnObject returnObject = clueRemarkService.deleteClueRemarkById(id);
		return returnObject;
	}
}
