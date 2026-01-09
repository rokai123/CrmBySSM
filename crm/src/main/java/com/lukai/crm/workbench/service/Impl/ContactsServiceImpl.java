package com.lukai.crm.workbench.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.workbench.domain.Contacts;
import com.lukai.crm.workbench.mapper.ContactsMapper;
import com.lukai.crm.workbench.service.ContactsService;
@Service("contactsService")
public class ContactsServiceImpl implements ContactsService {
	@Autowired
	private ContactsMapper contactsMapper;

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
	
	
	
}
