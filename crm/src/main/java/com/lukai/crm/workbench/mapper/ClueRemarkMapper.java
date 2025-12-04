package com.lukai.crm.workbench.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.ClueRemark;


@Mapper
public interface ClueRemarkMapper {
	/**
	 * リードIDに基づきリード備考情報を検索
	 */
	List<ClueRemark> selectClueRemarkForDetailByClueId(String clueId);
	/**
	 * リード備考情報を挿入
	 */
	int insertClueRemark(ClueRemark clueRemark);
	/**
	 * リード備考情報を削除
	 */
	int deleteClueRemarkByClueId(String id);
}
