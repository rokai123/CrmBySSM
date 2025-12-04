package com.lukai.crm.workbench.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.ClueRemark;

/**
 * リードIDに基づきリード備考情報を検索
 */
@Mapper
public interface ClueRemarkMapper {
	List<ClueRemark> selectClueRemarkForDetailByClueId(String clueId);
}
