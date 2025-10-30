package com.lukai.crm.workbench.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.workbench.domain.Activity;
import com.lukai.crm.workbench.mapper.ActivityMapper;
import com.lukai.crm.workbench.service.ActivityService;
@Service("activityService")
public class ActivityServiceImpl implements ActivityService{

	@Autowired
	ActivityMapper activityMapper;
	
	@Override
	public int saveCreateActivity(Activity activity) {
		// TODO 自動生成されたメソッド・スタブ
		return activityMapper.insertActivity(activity);
	}

}
