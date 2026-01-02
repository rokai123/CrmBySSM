package com.lukai.crm.settings.service;

import java.util.List;

import com.lukai.crm.settings.domain.DicValue;

public interface DicValueService {
		/**
	 * データ辞書タイプコードに基づき、データ辞書値情報を検索
	 * @param typeCode
	 * @return
	 */
	List<DicValue> queryDicValueByTypeCode(String typeCode);
	
	DicValue queryDicValueById(String id);
}
