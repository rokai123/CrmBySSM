package com.lukai.crm.workbench.mapper;

import java.util.List;

import com.lukai.crm.workbench.domain.CustomerRemark;

public interface CustomerRemarkMapper {
	int insertCustomerRemarkBatch(List<CustomerRemark> customerRemarkList);
	List<CustomerRemark> selectCustomerRemarkByCusId(String cusId);
	int insertCustomerRemark(CustomerRemark customerRemark);
}
