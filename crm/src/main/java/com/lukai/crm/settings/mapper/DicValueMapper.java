package com.lukai.crm.settings.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.settings.domain.DicValue;

@Mapper
public interface DicValueMapper {
	/**
	 *データ辞書タイプコードに基づき、データ辞書値情報を検索
	 * @param typeCode
	 * @return
	 */
	List<DicValue> selectDicValueByTypeCode(String typeCode);
	DicValue selectDicValueById(String id);
}
