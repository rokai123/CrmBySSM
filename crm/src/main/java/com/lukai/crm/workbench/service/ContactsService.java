package com.lukai.crm.workbench.service;

import java.util.List;
import java.util.Map;

import com.lukai.crm.settings.domain.User;
import com.lukai.crm.workbench.domain.Contacts;

public interface ContactsService {
	List<Contacts> queryContactsByNameLike(String fullname);
	List<Contacts> queryContactsByConditionForPage(Map<String, Object> map);
	int queryContactsByConditionForPageCount(Map<String, Object> map);
	void saveCreateContacts(Contacts contacts,User user);
}
