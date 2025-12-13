package com.lukai.crm.workbench.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.Contacts;

@Mapper
public interface ContactsMapper {
	int insertContacts(Contacts contacts);
}
