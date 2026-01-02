package com.lukai.crm.workbench.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.TranHistory;

@Mapper
public interface TranHistoryMapper {
	int insetTranHistory(TranHistory tranHistory);
	List<TranHistory> selectTranHistoryByTranId(String tranId);
}
