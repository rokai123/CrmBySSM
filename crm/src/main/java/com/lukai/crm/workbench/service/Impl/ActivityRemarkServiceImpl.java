package com.lukai.crm.workbench.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
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

	@Override
	public ReturnObject saveActivityRemark(ActivityRemark activityRemark) {
		ReturnObject returnObject = new ReturnObject();
		try {
			int retInt = activityRemarkMapper.insertActivityRemark(activityRemark);
			if (retInt>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				returnObject.setResultData(activityRemark);
			}else {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		
		return returnObject;
	}
	
	
	
}
