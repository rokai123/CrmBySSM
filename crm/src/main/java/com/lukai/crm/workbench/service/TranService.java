package com.lukai.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.lukai.crm.workbench.domain.Tran;

public interface TranService {
	List<Tran> queryTransByConditionForPage(Map<String, Object> map);
	int queryTransByConditionForPageCount(Map<String, Object> map);

}
