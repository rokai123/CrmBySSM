package com.lukai.crm.workbench.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.ClueActivityRelation;

@Mapper
public interface ClueActivityRelationMapper {
	int insertClueActivityRelationByList(List<ClueActivityRelation> clueActivityRelations);
}
