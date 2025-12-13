package com.lukai.crm.workbench.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.Customer;

@Mapper
public interface CustomerMapper {
	int insertCustomer(Customer customer);
}
