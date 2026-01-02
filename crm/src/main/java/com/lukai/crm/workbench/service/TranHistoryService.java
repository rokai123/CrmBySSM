package com.lukai.crm.workbench.service;

import java.util.List;

import com.lukai.crm.workbench.domain.TranHistory;

public interface TranHistoryService {
	List<TranHistory> queryTranHistoryByTranId(String tranId);
}
