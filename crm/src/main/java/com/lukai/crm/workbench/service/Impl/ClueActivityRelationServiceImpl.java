package com.lukai.crm.workbench.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.workbench.domain.Activity;
import com.lukai.crm.workbench.domain.ClueActivityRelation;
import com.lukai.crm.workbench.mapper.ActivityMapper;
import com.lukai.crm.workbench.mapper.ClueActivityRelationMapper;
import com.lukai.crm.workbench.service.ClueActivityRelationService;
@Service("clueActivityRelationService")
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService{
	
	@Autowired
	ClueActivityRelationMapper clueActivityRelationMapper;
	@Autowired
	ActivityMapper activityMapper;
	
	/**
	 * 线索与市场活动关联
	 */
	@Override
	public ReturnObject saveActivityRelations(List<ClueActivityRelation> clueActivityRelations,String[] activityIds) {
		ReturnObject returnObject = new ReturnObject();
		try {
			int retInt = clueActivityRelationMapper.insertClueActivityRelationByList(clueActivityRelations);
			
			if (retInt>0) {
				// 関連付け成功後、現在関連しているマーケティングキャンペーン一覧を取得し、フロントエンドページへ一緒に返却
				List<Activity> activities = activityMapper.selectActivityForClueDetailByActivityIds(activityIds);
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				returnObject.setResultData(activities);
			}else {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("システム混雑中、しばらくしてまたお試してみてください");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("システム混雑中、しばらくしてまたお試してみてください");
		}
		return returnObject;
	}

}
