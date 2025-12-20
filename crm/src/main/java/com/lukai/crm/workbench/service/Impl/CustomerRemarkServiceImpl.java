package com.lukai.crm.workbench.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.workbench.domain.CustomerRemark;
import com.lukai.crm.workbench.mapper.CustomerRemarkMapper;
import com.lukai.crm.workbench.service.CustomerRemarkService;
@Service("customerRemarkService")
public class CustomerRemarkServiceImpl implements CustomerRemarkService{
	@Autowired
	CustomerRemarkMapper customerRemarkMapper;
	
	/**
	 * 通过客户id查询客户备注信息
	 */
	@Override
	public List<CustomerRemark> queryCustomerRemarkByCusId(String cusId) {
		
		return customerRemarkMapper.selectCustomerRemarkByCusId(cusId);
	}

}
