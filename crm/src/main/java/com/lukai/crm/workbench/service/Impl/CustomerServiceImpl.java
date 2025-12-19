package com.lukai.crm.workbench.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.workbench.domain.Customer;
import com.lukai.crm.workbench.mapper.CustomerMapper;
import com.lukai.crm.workbench.service.CustomerService;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	CustomerMapper customerMapper;

	@Override
	public List<Customer> queryCustomerByConditionForPage(Map<String, Object> map) {
		return customerMapper.selectCustomerByConditionForPage(map);
	}

	@Override
	public int queryCustomerByConditionForPageCount(Map<String, Object> map) {
		return customerMapper.selectCustomerByConditionForPageCount(map);
	}

	@Override
	public int saveCreateCustomer(Customer customer) {
		return customerMapper.insertCustomer(customer);
	}

	@Override
	public Customer queryCustomerById(String id) {
		return customerMapper.selectCustomerById(id);
	}

	@Override
	public int saveEditCustomer(Customer customer) {
		return customerMapper.updateCustomer(customer);
	}

	@Override
	public int deleteCustomerByIds(String[] ids) {
		int retInt = customerMapper.deleteCustomerByIds(ids);
		return retInt;
	}

	@Override
	public Customer queryCustomerForDetailById(String id) {
		Customer customer = customerMapper.selectCustomerForDetailById(id);
		return customer;
	}

	
}
