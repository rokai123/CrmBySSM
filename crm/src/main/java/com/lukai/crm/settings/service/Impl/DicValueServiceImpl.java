package com.lukai.crm.settings.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.settings.domain.DicValue;
import com.lukai.crm.settings.mapper.DicValueMapper;
import com.lukai.crm.settings.service.DicValueService;


@Service("dicValueService")
public class DicValueServiceImpl implements DicValueService {
	@Autowired
	DicValueMapper dicValueMapper;
	
	@Override
	public List<DicValue> queryDicValueByTypeCode(String typeCode) {
		
		return dicValueMapper.selectDicValueByTypeCode(typeCode);
	}

}
