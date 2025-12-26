package com.lukai.crm.workbench.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.Contacts;

@Mapper
public interface ContactsMapper {
	int insertContacts(Contacts contacts);
	List<Contacts> selectContactsByNameLike(String fullname);
}
