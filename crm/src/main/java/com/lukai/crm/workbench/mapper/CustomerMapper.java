package com.lukai.crm.workbench.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.Customer;

@Mapper
public interface CustomerMapper {
	int insertCustomer(Customer customer);
	
	List<Customer> selectCustomerByConditionForPage(Map<String, Object> map);
	
	int selectCustomerByConditionForPageCount(Map<String, Object> map);
	
	Customer selectCustomerById(String customerId);
	
	int updateCustomer(Customer customer);
	
	int deleteCustomerByIds(String[] ids);
	
	Customer selectCustomerForDetailById(String customerId);
}
