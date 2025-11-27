package com.lukai.crm.workbench.service;

import java.util.Map;

public interface ClueService {

	Map<String,Object> queryClueByConditionForPage(Map<String, Object> clueMap);
	
}
