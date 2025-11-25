package com.lukai.crm.workbench.service.Impl;

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
	
	public List<Clue> queryClueByConditionForPage(Map<String,Object> map) {
		List<Clue> clues = clueMapper.selectClueByConditionForPage(map);
		return clues;
	}
	
}
