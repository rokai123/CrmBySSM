package com.lukai.crm.workbench.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.commons.utils.DateUtils;
import com.lukai.crm.commons.utils.UUIdUtils;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.workbench.domain.Contacts;
import com.lukai.crm.workbench.domain.Customer;
import com.lukai.crm.workbench.mapper.ContactsMapper;
import com.lukai.crm.workbench.mapper.CustomerMapper;
import com.lukai.crm.workbench.service.ContactsService;
@Service("contactsService")
public class ContactsServiceImpl implements ContactsService {

	@Autowired
	private ContactsMapper contactsMapper;
	@Autowired
	private CustomerMapper customerMapper;

    

	@Override
	public List<Contacts> queryContactsByNameLike(String fullname) {
		return contactsMapper.selectContactsByNameLike(fullname);
	}

	@Override
	public List<Contacts> queryContactsByConditionForPage(Map<String, Object> map) {
		return contactsMapper.selectContactsByConditionForPage(map);
	}

	@Override
	public int queryContactsByConditionForPageCount(Map<String, Object> map) {
		return contactsMapper.selectContactsByConditionForPageCount(map);
	}

	@Override
	public void saveCreateContacts(Contacts contacts,User user){
		String customerName = contacts.getCustomerName();
		Customer customer = customerMapper.selectCustomerByName(customerName);
		//不存在客户则新建
		if (customer == null) {
			customer = new Customer();
			customer.setId(UUIdUtils.getUUId());
			customer.setName(contacts.getCustomerName());
			customer.setCreateBy(user.getId());
			customer.setCreateTime(DateUtils.formateDateTime(new Date()));
			customerMapper.insertCustomer(customer);
		}
		//创建联系人
		contacts.setCustomerId(customer.getId());
		contacts.setId(UUIdUtils.getUUId());
		contacts.setCreateBy(user.getId());
		contacts.setCreateTime(DateUtils.formateDateTime(new Date()));
		contactsMapper.insertContacts(contacts);
	}
	
	
	
}
