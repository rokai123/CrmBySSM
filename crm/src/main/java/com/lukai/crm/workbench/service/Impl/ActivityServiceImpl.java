package com.lukai.crm.workbench.service.Impl;

import java.util.List;
import java.util.Map;

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
		
		return activityMapper.insertActivity(activity);
	}
	
	//条件とページネーションによるマーケティングキャンペーン一覧検索
	@Override
	public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {
		
		return activityMapper.selectActivityByConditionForPage(map);
	}

	//条件に基づくマーケティングキャンペーン一覧の総件数を取得
	@Override
	public int queryCountOfActivityByCondition(Map<String, Object> map) {
		return activityMapper.selectCountOfActivityByCondition(map);
	}
	
	
	//IDに基づいて該当するマーケティングキャンペーンを削除
	@Override
	public int deleteActivityByIds(String[] ids) {
		
		return activityMapper.deleteActivityByIds(ids);
	}
	
	//IDに基づいて該当するマーケティングキャンペーンをリサーチ
	@Override
	public Activity queryActivityById(String id) {
		
		return activityMapper.selectActivityById(id);
	}

	@Override
	public int saveActivity(Activity activity) {
		
		return activityMapper.updateActivity(activity);
	}

	@Override
	public List<Activity> queryAllActivitys() {
		return activityMapper.selectAllActivities();
	}

}
