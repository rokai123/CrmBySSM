package com.lukai.crm.workbench.service;

import java.util.List;

import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.workbench.domain.ActivityRemark;

public interface ActivityRemarkService {
	
	List<ActivityRemark> queryActivityRemarksByActivityIdForDetail(String activityId);
	ReturnObject saveActivityRemark(ActivityRemark activityRemark);
}
