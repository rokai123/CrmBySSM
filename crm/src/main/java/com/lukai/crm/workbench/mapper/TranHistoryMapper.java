package com.lukai.crm.workbench.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.TranHistory;

@Mapper
public interface TranHistoryMapper {
	int insetTranHistory(TranHistory tranHistory);

}
