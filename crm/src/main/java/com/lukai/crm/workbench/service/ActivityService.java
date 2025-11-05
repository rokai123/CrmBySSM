package com.lukai.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.lukai.crm.workbench.domain.Activity;

public interface ActivityService {
	int saveCreateActivity(Activity activity);
	List<Activity> queryActivityByConditionForPage(Map<String, Object> map);
	int queryCountOfActivityByCondition(Map<String, Object> map);
}
