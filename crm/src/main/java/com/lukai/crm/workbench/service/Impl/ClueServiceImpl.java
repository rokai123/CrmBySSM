package com.lukai.crm.workbench.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.workbench.domain.Clue;
import com.lukai.crm.workbench.mapper.ClueMapper;
import com.lukai.crm.workbench.service.ClueService;
@Service("clueService")
public class ClueServiceImpl implements ClueService {
	
	@Autowired
	ClueMapper clueMapper;
	
	public Map<String,Object> queryClueByConditionForPage(Map<String,Object> map) {
		List<Clue> clues = clueMapper.selectClueByConditionForPage(map);
		int totalRows = clueMapper.selectClueByConditionForPageCount(map);
		HashMap<String, Object> retMap = new HashMap<String,Object>();
		retMap.put("clueList", clues);
		retMap.put("totalRows", totalRows);
		return retMap;
	}
	
	/**
	 * リードを挿入する
	 * 
	 */
	@Override
	public ReturnObject saveCreateClue(Clue clue) {
		ReturnObject retObject = new ReturnObject();
		try {
			int retInt = clueMapper.insertClue(clue);
			if (retInt>0) {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				retObject.setMessage("リードを登録しました");
			}else {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
			}
		} catch (Exception e) {
			e.printStackTrace();
			retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		
		return retObject;
	}

	@Override
	public ReturnObject deleteClueByIds(String[] ids) {
		ReturnObject retObject = new ReturnObject();
		try {
			int retInt = clueMapper.deleteClueByIds(ids);
			if (retInt>0) {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				retObject.setMessage("リードを削除しました");
		}else {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		} catch (Exception e) {
			e.printStackTrace();
			retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		
		return retObject;
	}

	@Override
	public Clue queryClueByClueId(String id) {
		Clue clue = clueMapper.selectClueByClueId(id);
		return clue;
	}

	@Override
	public Clue queryClueForEditByClueId(String id) {
		Clue clue = clueMapper.selectClueForEditByClueId(id);
		return clue;
	}
	
}
