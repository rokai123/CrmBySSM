package com.lukai.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.lukai.crm.workbench.domain.Tran;
import com.lukai.crm.workbench.domain.TranVO;

public interface TranService {
	List<Tran> queryTransByConditionForPage(Map<String, Object> map);
	int queryTransByConditionForPageCount(Map<String, Object> map);
	void saveCreateTran(Map<String, Object> map);
	TranVO queryTranForDetailById(String id);

}
