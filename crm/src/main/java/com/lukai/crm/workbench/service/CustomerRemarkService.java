package com.lukai.crm.workbench.service;

import java.util.List;

import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.workbench.domain.CustomerRemark;

public interface CustomerRemarkService {
	List<CustomerRemark> queryCustomerRemarkByCusId(String cusId);
	ReturnObject saveCreateCustomerRemark(CustomerRemark customerRemark,User user);
	ReturnObject deleteCustomerRemarkById(String id);
	ReturnObject saveEditCustomerRemark(CustomerRemark customerRemark,User user);
}
