package com.lukai.crm.workbench.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.ClueActivityRelation;

@Mapper
public interface ClueActivityRelationMapper {
	int insertClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelations);
	int deleteClueActivityRelationByClueIdAndActId(Map<String, String> map);
}
