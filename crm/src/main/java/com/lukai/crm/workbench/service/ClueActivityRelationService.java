package com.lukai.crm.workbench.service;

import java.util.List;

import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.workbench.domain.ClueActivityRelation;

public interface ClueActivityRelationService {
	
	ReturnObject saveActivityRelations(List<ClueActivityRelation> clueActivityRelations,String[] activityIds);
}
