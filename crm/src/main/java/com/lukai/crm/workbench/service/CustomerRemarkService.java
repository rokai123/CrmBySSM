package com.lukai.crm.workbench.service;

import java.util.List;

import com.lukai.crm.workbench.domain.CustomerRemark;

public interface CustomerRemarkService {
	List<CustomerRemark> queryCustomerRemarkByCusId(String cusId);
}
