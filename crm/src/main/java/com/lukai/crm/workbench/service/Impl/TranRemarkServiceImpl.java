package com.lukai.crm.workbench.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.workbench.domain.TranRemark;
import com.lukai.crm.workbench.mapper.TranRemarkMapper;
import com.lukai.crm.workbench.service.TranRemarkService;

@Service("tranRemarkService")
public class TranRemarkServiceImpl implements TranRemarkService{

	@Autowired
	private TranRemarkMapper tranRemarkMapper;
	
	@Override
	public List<TranRemark> queryTranRemarkByTranId(String tranId) {
		return tranRemarkMapper.selectTranRemarkById(tranId);
	}

}
