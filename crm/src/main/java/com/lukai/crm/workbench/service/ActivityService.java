package com.lukai.crm.workbench.service;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.workbench.domain.Activity;

public interface ActivityService {
	ReturnObject saveCreateActivity(Activity activity);
	List<Activity> queryActivityByConditionForPage(Map<String, Object> map);
	int queryCountOfActivityByCondition(Map<String, Object> map);
	int deleteActivityByIds(String[] ids);
	Activity queryActivityById(String id);
	int saveActivity(Activity activity);
	List<Activity> queryAllActivitys();
	List<Activity> queryActivitysByIds(String[] ids);
	ReturnObject saveCreateActivityByList(MultipartFile activityFile,HttpSession session);
	Activity queryActivityForDetailById(String id);
	List<Activity> queryActivityForClueDetailByClueId(String clueId);
}
