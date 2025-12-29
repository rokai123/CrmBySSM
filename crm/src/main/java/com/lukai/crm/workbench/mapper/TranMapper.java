package com.lukai.crm.workbench.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.Tran;

@Mapper
public interface TranMapper {
	int insertTran(Tran tran);
	List<Tran> selecTransByConditionForPage(Map<String, Object> map);
	int selectTransByConditionForPageCount(Map<String, Object> map);
	Tran selectTranForDetailById(String id);
}
