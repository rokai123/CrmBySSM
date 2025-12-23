package com.lukai.crm.workbench.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.workbench.domain.Tran;
import com.lukai.crm.workbench.mapper.TranMapper;
import com.lukai.crm.workbench.service.TranService;

@Service("tranService")
public class TranServiceImpl implements TranService{
	@Autowired
	TranMapper tranMapper;
	
	@Override
	public List<Tran> queryTransByConditionForPage(Map<String, Object> map) {
		return tranMapper.selecTransByConditionForPage(map);
	}

	@Override
	public int queryTransByConditionForPageCount(Map<String, Object> map) {
		return tranMapper.selectTransByConditionForPageCount(map);
	}

}
