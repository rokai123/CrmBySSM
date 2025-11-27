package com.lukai.crm.workbench.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
}
