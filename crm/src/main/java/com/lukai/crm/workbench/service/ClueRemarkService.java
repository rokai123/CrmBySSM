package com.lukai.crm.workbench.service;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.workbench.domain.ClueRemark;

public interface ClueRemarkService {
	List<ClueRemark> queryClueRemarkByClueId(String ClueId);
	ReturnObject saveCreateClueRemark(ClueRemark clueRemark,HttpSession session);
	ReturnObject deleteClueRemarkById(String id);
	List<ClueRemark> queryClueRemarkForClueConvertByClueId(String clueId);
}
