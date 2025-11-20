package com.lukai.crm.workbench.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.workbench.domain.ActivityRemark;
import com.lukai.crm.workbench.mapper.ActivityRemarkMapper;
import com.lukai.crm.workbench.service.ActivityRemarkService;

@Service("activityRemarkServiceImpl")
public class ActivityRemarkServiceImpl implements ActivityRemarkService{
	@Autowired
	ActivityRemarkMapper activityRemarkMapper;
	
	/**
	 * 
	 * @param activityId
	 * @return
	 *  マーケティングキャンペーンIDを条件にマーケティングキャンペーンの備考情報を検索
	 */
	@Override
	public List<ActivityRemark> queryActivityRemarksByActivityIdForDetail(String activityId) {
		List<ActivityRemark> activityRemarks = activityRemarkMapper.selectActivityRemarksByActivityIdForDetail(activityId);
		
		return activityRemarks;
	}
	
	
	
}
