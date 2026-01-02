package com.lukai.crm.workbench.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.utils.DateUtils;
import com.lukai.crm.commons.utils.UUIdUtils;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.workbench.domain.Customer;
import com.lukai.crm.workbench.domain.Tran;
import com.lukai.crm.workbench.domain.TranHistory;
import com.lukai.crm.workbench.domain.TranVO;
import com.lukai.crm.workbench.mapper.CustomerMapper;
import com.lukai.crm.workbench.mapper.TranHistoryMapper;
import com.lukai.crm.workbench.mapper.TranMapper;
import com.lukai.crm.workbench.service.TranService;

@Service("tranService")
public class TranServiceImpl implements TranService{
	@Autowired
	TranMapper tranMapper;
	@Autowired
	CustomerMapper customerMapper;
	@Autowired
	TranHistoryMapper tranHistoryMapper;
	@Override
	public List<Tran> queryTransByConditionForPage(Map<String, Object> map) {
		return tranMapper.selecTransByConditionForPage(map);
	}

	@Override
	public int queryTransByConditionForPageCount(Map<String, Object> map) {
		return tranMapper.selectTransByConditionForPageCount(map);
	}

	@Override
	public void saveCreateTran(Map<String, Object> map) {
		String customerName = (String)map.get("customerName");
		Customer customer = customerMapper.selectCustomerByName(customerName);
		User user = (User)map.get(Contants.SESSION_USER);
		//如果 customer 不存在，则创建新客户
		if(customer == null) {
			customer = new Customer();
			customer.setName(customerName);
			customer.setOwner(user.getId());
			customer.setId(UUIdUtils.getUUId());
			customer.setCreateBy(user.getId());
			customer.setCreateTime(DateUtils.formateDateTime(new Date()));
			customerMapper.insertCustomer(customer);
		}
		
		//创建交易
		Tran tran = new Tran();
		tran.setId(UUIdUtils.getUUId());
		tran.setOwner((String)map.get("owner"));
		tran.setMoney((String)map.get("money"));
		tran.setName((String)map.get("name"));
		tran.setExpectedDate((String)map.get("expectedDate"));
		tran.setCustomerId(customer.getId());
		tran.setStage((String)map.get("stage"));
		tran.setType((String)map.get("type"));
		tran.setSource((String)map.get("source"));
		tran.setActivityId((String)map.get("activityId"));
		tran.setContactsId((String)map.get("contactsId"));
		tran.setCreateBy((String)map.get("createBy"));
		tran.setCreateTime(DateUtils.formateDateTime(new Date()));
		tran.setDescription((String)map.get("description"));
		tran.setContactSummary((String)map.get("contactSummary"));
		tran.setNextContactTime((String)map.get("nextContactTime"));
		tranMapper.insertTran(tran);
		//添加交易历史
		TranHistory tranHistory = new TranHistory();
		tranHistory.setId(UUIdUtils.getUUId());
		tranHistory.setCreateBy(user.getId());
		tranHistory.setCreateTime(DateUtils.formateDateTime(new Date()));
		tranHistory.setExpectedDate(tran.getExpectedDate());
		tranHistory.setMoney(tran.getMoney());
		tranHistory.setStage(tran.getStage());
		tranHistory.setTranId(tran.getId());
		tranHistoryMapper.insetTranHistory(tranHistory);
	}

	@Override
	public TranVO queryTranForDetailById(String id) {
		
		
		return tranMapper.selectTranForDetailById(id);
	}

}
