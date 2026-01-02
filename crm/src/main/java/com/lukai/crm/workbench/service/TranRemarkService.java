package com.lukai.crm.workbench.service;

import java.util.List;

import com.lukai.crm.workbench.domain.TranRemark;

public interface TranRemarkService {
	List<TranRemark> queryTranRemarkByTranId(String tranId);
}
