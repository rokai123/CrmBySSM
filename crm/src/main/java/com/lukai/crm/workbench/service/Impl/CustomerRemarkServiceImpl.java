package com.lukai.crm.workbench.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.commons.utils.DateUtils;
import com.lukai.crm.commons.utils.UUIdUtils;
import com.lukai.crm.settings.domain.User;
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

	@Override
	public ReturnObject saveCreateCustomerRemark(CustomerRemark customerRemark,User user) {
		customerRemark.setCreateBy(user.getId());
		customerRemark.setCreateTime(DateUtils.formateDateTime(new Date()));
		customerRemark.setEditFlag("0");
		customerRemark.setId(UUIdUtils.getUUId());
		ReturnObject retObject = new ReturnObject();
		try {
			int retCount = customerRemarkMapper.insertCustomerRemark(customerRemark);
			if (retCount>0) {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				customerRemark.setCreateBy(user.getName());
				retObject.setResultData(customerRemark);
			}else {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
			}
		} catch (Exception e) {
			e.printStackTrace();
			retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		
		
		return retObject;
	}
	
	@Override
	public ReturnObject deleteCustomerRemarkById(String id) {
		ReturnObject retObject = new ReturnObject();
		try {
			int retCount = customerRemarkMapper.deleteCustomerRemarkById(id);
			if (retCount>0) {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
			}else {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
			}
		} catch (Exception e) {
			e.printStackTrace();
			retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		return retObject;
	}

	@Override
	public ReturnObject saveEditCustomerRemark(CustomerRemark customerRemark, User user) {
		customerRemark.setEditBy(user.getId());
		customerRemark.setEditTime(DateUtils.formateDateTime(new Date()));
		customerRemark.setEditFlag("1");
		ReturnObject retObject = new ReturnObject();
		try {
			int retCount = customerRemarkMapper.updateCustomerRemark(customerRemark);
			if (retCount>0) {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				customerRemark.setEditBy(user.getName());
				retObject.setResultData(customerRemark);
			}else {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
			}
		} catch (Exception e) {
			e.printStackTrace();
			retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		return retObject;
	}

}
