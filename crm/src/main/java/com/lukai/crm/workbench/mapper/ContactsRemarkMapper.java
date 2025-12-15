package com.lukai.crm.workbench.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lukai.crm.workbench.domain.ContactsRemark;

@Mapper
public interface ContactsRemarkMapper {
	int insertContactsRemarkBatch(List<ContactsRemark> contactsList);
}
