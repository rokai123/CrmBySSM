package com.lukai.crm.workbench.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.Clue;

@Mapper
public interface ClueMapper {
	
	List<Clue> selectClueByConditionForPage(Map<String, Object> map);
	int selectClueByConditionForPageCount(Map<String, Object> map);
}
