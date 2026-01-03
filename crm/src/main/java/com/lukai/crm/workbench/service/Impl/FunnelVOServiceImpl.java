package com.lukai.crm.workbench.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.workbench.domain.FunnelVO;
import com.lukai.crm.workbench.mapper.FunnelVOMapper;
import com.lukai.crm.workbench.service.FunnelVOService;

@Service("funnelVOServiceImpl")
public class FunnelVOServiceImpl implements FunnelVOService{

	@Autowired
	private FunnelVOMapper funnelVOMapper;
	
	@Override
	public List<FunnelVO> queryFunnelVOsForChart() {
		return funnelVOMapper.selectFunnelVOsForChart();
	}

}
