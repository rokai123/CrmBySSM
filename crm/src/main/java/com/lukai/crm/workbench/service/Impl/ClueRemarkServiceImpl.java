package com.lukai.crm.workbench.service.Impl;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.workbench.domain.ClueRemark;
import com.lukai.crm.workbench.mapper.ClueRemarkMapper;
import com.lukai.crm.workbench.service.ClueRemarkService;
@Service("clueRemarkService")
public class ClueRemarkServiceImpl implements ClueRemarkService{
	@Autowired
	ClueRemarkMapper clueRemarkMapper;
	
	
	@Override
	public List<ClueRemark> queryClueRemarkByClueId(String ClueId) {
		List<ClueRemark> clueRemarks = clueRemarkMapper.selectClueRemarkForDetailByClueId(ClueId);
		return clueRemarks;
	}


	@Override
	public ReturnObject saveCreateClueRemark(ClueRemark clueRemark,HttpSession session) {
		ReturnObject returnObject = new ReturnObject();
		try {
			int ret = clueRemarkMapper.insertClueRemark(clueRemark);
			if(ret>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				User user = (User)session.getAttribute(Contants.SESSION_USER);
				clueRemark.setCreateBy(user.getName());
				returnObject.setResultData(clueRemark);
			}else {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("システム混雑中しばらくしてお試してみてください");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("システム混雑中しばらくしてお試してみてください");
		}
		return returnObject;
	}


	@Override
	public ReturnObject deleteClueRemarkById(String id) {
		ReturnObject returnObject = new ReturnObject();
		try {
			int ret = clueRemarkMapper.deleteClueRemarkByClueId(id);
			if(ret>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
			}else {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("システム混雑中しばらくしてお試してみてください");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("システム混雑中しばらくしてお試してみてください");
		}
		return returnObject;
	}


	@Override
	public List<ClueRemark> queryClueRemarkForClueConvertByClueId(String clueId) {
		
		return clueRemarkMapper.selectClueRemarkForClueConvertByClueId(clueId);
	}

}
