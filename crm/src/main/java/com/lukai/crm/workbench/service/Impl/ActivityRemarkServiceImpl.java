package com.lukai.crm.workbench.service.Impl;

import java.util.Date;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.commons.utils.DateUtils;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.workbench.domain.ActivityRemark;
import com.lukai.crm.workbench.mapper.ActivityRemarkMapper;
import com.lukai.crm.workbench.service.ActivityRemarkService;

@Service("activityRemarkServiceImpl")
public class ActivityRemarkServiceImpl implements ActivityRemarkService{
	@Autowired
	private ActivityRemarkMapper activityRemarkMapper;
	
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

	/**
	 * 
	 * @param activityRemark
	 * @return
	 * 	マーケティングキャンペーンの備考を保存
	 */
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

	/**
	 * 
	 * @param id
	 * @return
	 * 	マーケティングキャンペーンの備考IDを条件にマーケティングキャンペーンの備考を削除
	 */
	@Override
	public ReturnObject deleteActivityRemarkById(String id) {
		ReturnObject returnObject = new ReturnObject();
		try {
			int retInt = activityRemarkMapper.deleteByPrimaryKey(id);
			if (retInt>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
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
	
	/**
	 * 
	 * @param activityRemark
	 * @return
	 * 	マーケティングキャンペーンの備考を編集保存
	 */
	@Override
	public ReturnObject saveEditActivityRemark(ActivityRemark activityRemark,HttpSession session) {
		//封装前台未传入的参数(修改时间，修改标记，修改人)
		activityRemark.setEditTime(DateUtils.formateDateTime(new Date()));
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		activityRemark.setEditBy(user.getId());
		activityRemark.setEditFlag(Contants.REMARK_EDIT_FLAG_YES_EDITED);
		//创建封装结果集对象
		ReturnObject returnObject = new ReturnObject();
		
		try {
			int retInt = activityRemarkMapper.updateActivityRemark(activityRemark);
			activityRemark.setEditBy(user.getName());
			if (retInt>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				returnObject.setResultData(activityRemark);
				returnObject.setMessage("成功に編集した");
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
