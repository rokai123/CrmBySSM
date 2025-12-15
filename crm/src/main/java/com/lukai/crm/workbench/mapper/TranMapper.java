package com.lukai.crm.workbench.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.Tran;

@Mapper
public interface TranMapper {
	int insertTran(Tran tran);
}
