package com.lukai.crm.workbench.service;

import java.util.List;

import com.lukai.crm.workbench.domain.Contacts;

public interface ContactsService {
	List<Contacts> queryContactsByNameLike(String fullname);
}
