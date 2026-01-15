package com.lukai.crm.workbench.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.Contacts;

@Mapper
public interface ContactsMapper {
	int insertContacts(Contacts contacts);
	List<Contacts> selectContactsByNameLike(String fullname);
	List<Contacts> selectContactsByConditionForPage(Map<String, Object> map); 
	int selectContactsByConditionForPageCount(Map<String, Object> map);
	int deleteContactsByIds(String[] ids);
	Contacts selectContactsById(String id);
	int updateContacts(Contacts contacts);
}
