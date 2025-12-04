package com.lukai.crm.workbench.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.workbench.domain.ClueRemark;
import com.lukai.crm.workbench.mapper.ClueRemarkMapper;
import com.lukai.crm.workbench.service.ClueRemarkService;
@Service("clueRemarkService")
public class ClueRemarkServiceImpl implements ClueRemarkService{
	@Autowired
	ClueRemarkMapper clueRemarkMapper;
	
	
	@Override
	public List<ClueRemark> queryClueRemarkByClueId(String ClueId) {
		List<ClueRemark> clueRemarks = clueRemarkMapper.selectClueRemarkForDetailByClueId(ClueId);
		return clueRemarks;
	}

}
