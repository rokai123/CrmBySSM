package com.lukai.crm.workbench.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.FunnelVO;

@Mapper
public interface FunnelVOMapper {
	
	List<FunnelVO> selectFunnelVOsForChart();
}
