package com.lukai.crm.workbench.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.ClueRemark;

@Mapper
public interface ClueRemarkMapper {
	List<ClueRemark> selectClueRemarkByClueId(String clueId);
}
