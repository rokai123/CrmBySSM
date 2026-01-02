package com.lukai.crm.workbench.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.workbench.domain.TranHistory;
import com.lukai.crm.workbench.mapper.TranHistoryMapper;
import com.lukai.crm.workbench.service.TranHistoryService;

@Service("tranHistoryService")
public class TranHistoryServiceImpl implements TranHistoryService {
	
	@Autowired
	private TranHistoryMapper tranHistoryMapper;
	
	@Override
	public List<TranHistory> queryTranHistoryByTranId(String tranId) {
		return tranHistoryMapper.selectTranHistoryByTranId(tranId);
	}

}
