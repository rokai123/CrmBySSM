package com.lukai.crm.workbench.service;

import java.util.Map;

import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.workbench.domain.Clue;

public interface ClueService {

	Map<String,Object> queryClueByConditionForPage(Map<String, Object> clueMap);
	ReturnObject saveCreateClue(Clue clue);
	ReturnObject deleteClueByIds(String[] ids);
	Clue queryClueByClueId(String id);
	Clue queryClueForEditByClueId(String id);
	ReturnObject saveEditClue(Clue clue);
}
