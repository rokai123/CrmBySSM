package com.lukai.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.lukai.crm.workbench.domain.Customer;

public interface CustomerService {
	List<Customer> queryCustomerByConditionForPage(Map<String, Object> map);
	int queryCustomerByConditionForPageCount(Map<String, Object> map);
	int saveCreateCustomer(Customer customer);
	Customer queryCustomerById(String id);
	int saveEditCustomer(Customer customer);
	int deleteCustomerByIds(String[] ids);
	Customer queryCustomerForDetailById(String id);
	List<String> queryCustomerNameByNameLike(String name);
}
