package com.lukai.crm.workbench.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.commons.utils.DateUtils;
import com.lukai.crm.commons.utils.UUIdUtils;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.workbench.domain.Clue;
import com.lukai.crm.workbench.domain.ClueActivityRelation;
import com.lukai.crm.workbench.domain.ClueRemark;
import com.lukai.crm.workbench.domain.Contacts;
import com.lukai.crm.workbench.domain.ContactsActivityRelation;
import com.lukai.crm.workbench.domain.ContactsRemark;
import com.lukai.crm.workbench.domain.Customer;
import com.lukai.crm.workbench.domain.CustomerRemark;
import com.lukai.crm.workbench.domain.Tran;
import com.lukai.crm.workbench.domain.TranRemark;
import com.lukai.crm.workbench.mapper.ClueActivityRelationMapper;
import com.lukai.crm.workbench.mapper.ClueMapper;
import com.lukai.crm.workbench.mapper.ClueRemarkMapper;
import com.lukai.crm.workbench.mapper.ContactsActivityRelationMapper;
import com.lukai.crm.workbench.mapper.ContactsMapper;
import com.lukai.crm.workbench.mapper.ContactsRemarkMapper;
import com.lukai.crm.workbench.mapper.CustomerMapper;
import com.lukai.crm.workbench.mapper.CustomerRemarkMapper;
import com.lukai.crm.workbench.mapper.TranMapper;
import com.lukai.crm.workbench.mapper.TranRemarkMapper;
import com.lukai.crm.workbench.service.ClueRemarkService;
import com.lukai.crm.workbench.service.ClueService;
@Service("clueService")
public class ClueServiceImpl implements ClueService {
	
	@Autowired
	private ClueMapper clueMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@Autowired
	private ContactsMapper contactsMapper;
	@Autowired
	private ClueRemarkService clueRemarkService;
	@Autowired
	private CustomerRemarkMapper customerRemarkMapper;
	@Autowired
	private ContactsRemarkMapper contactsRemarkMapper;
	@Autowired
	private ClueActivityRelationMapper clueActivityRelationMapper;
	@Autowired
	private ContactsActivityRelationMapper contActRelMapper;
	@Autowired
	private TranMapper tranMapper;
	@Autowired
	private TranRemarkMapper tranRemarkMapper;
	@Autowired
	private ClueRemarkMapper clueRemarkMapper;
	
	public Map<String,Object> queryClueByConditionForPage(Map<String,Object> map) {
		List<Clue> clues = clueMapper.selectClueByConditionForPage(map);
		int totalRows = clueMapper.selectClueByConditionForPageCount(map);
		HashMap<String, Object> retMap = new HashMap<String,Object>();
		retMap.put("clueList", clues);
		retMap.put("totalRows", totalRows);
		return retMap;
	}
	
	/**
	 * リードを挿入する
	 * 
	 */
	@Override
	public ReturnObject saveCreateClue(Clue clue) {
		ReturnObject retObject = new ReturnObject();
		try {
			int retInt = clueMapper.insertClue(clue);
			if (retInt>0) {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				retObject.setMessage("リードを登録しました");
			}else {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
			}
		} catch (Exception e) {
			e.printStackTrace();
			retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		
		return retObject;
	}

	@Override
	public ReturnObject deleteClueByIds(String[] ids) {
		ReturnObject retObject = new ReturnObject();
		try {
			int retInt = clueMapper.deleteClueByIds(ids);
			if (retInt>0) {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				retObject.setMessage("リードを削除しました");
		}else {
				retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		} catch (Exception e) {
			e.printStackTrace();
			retObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			retObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		
		return retObject;
	}

	@Override
	public Clue queryClueByClueId(String id) {
		Clue clue = clueMapper.selectClueByClueId(id);
		return clue;
	}

	@Override
	public Clue queryClueForEditByClueId(String id) {
		Clue clue = clueMapper.selectClueForEditByClueId(id);
		return clue;
	}

	@Override
	public ReturnObject saveEditClue(Clue clue) {
		ReturnObject returnObject = new ReturnObject();
		try {
			int retInt = clueMapper.updateClue(clue);
			if (retInt>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
			}else {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		return returnObject;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveConvertClue(Map<String, Object> map) {
		//idに基づいてリードを検索し、取り出す
		String clueId = (String)map.get("clueId");
		Clue clue = clueMapper.selectClueForConvertById(clueId);
		//顧客に関するデータをカプセル化して挿入する
		Customer customer = new Customer();
		customer.setId(UUIdUtils.getUUId());
		customer.setOwner(((User)map.get(Contants.SESSION_USER)).getId());
		customer.setName(clue.getCompany());
		customer.setWebsite(clue.getWebsite());
		customer.setPhone(clue.getPhone());
		customer.setCreateBy(((User)map.get(Contants.SESSION_USER)).getId());
		customer.setCreateTime(DateUtils.formateDateTime(new Date()));
		customer.setContactSummary(clue.getContactSummary());
		customer.setDescription(clue.getDescription());
		customer.setNextContactTime(clue.getNextContactTime());
		customer.setAddress(clue.getAddress());
		
		customerMapper.insertCustomer(customer);
		
		//顧客に関するデータをカプセル化して挿入する
		Contacts contacts = new Contacts();
		contacts.setId(UUIdUtils.getUUId());
		contacts.setOwner(((User)map.get(Contants.SESSION_USER)).getId());
		contacts.setCustomerId(customer.getId());
		contacts.setFullname(clue.getFullname());
		contacts.setAppellation(clue.getAppellation());
		contacts.setEmail(clue.getEmail());
		contacts.setMphone(clue.getMphone());
		contacts.setJob(clue.getJob());
		contacts.setCreateBy(((User)map.get(Contants.SESSION_USER)).getId());
		contacts.setCreateTime(DateUtils.formateDateTime(new Date()));
		contacts.setDescription(clue.getDescription());
		contacts.setContactSummary(clue.getContactSummary());
		contacts.setNextContactTime(clue.getNextContactTime());
		contacts.setSource(clue.getSource());
		contacts.setAddress(clue.getAddress());
		contactsMapper.insertContacts(contacts);
		
		//根据clueId查询出该线索的所有备注信息
		List<ClueRemark> clueRemarks = clueRemarkService.queryClueRemarkForClueConvertByClueId(clueId);
		//判断该线索是否有备注，没有的话则不必转换给客户和联系人
		if (clueRemarks!=null&&clueRemarks.size()>0) {
			CustomerRemark customerRemark = null;
			ContactsRemark contactsRemark =null;
			List<CustomerRemark> customerRemarkList = new ArrayList<>();
			List<ContactsRemark> contactsRemarkList = new ArrayList<>();
			for (ClueRemark clueRemark : clueRemarks) {
				customerRemark = new CustomerRemark();
				customerRemark.setId(UUIdUtils.getUUId());
				customerRemark.setNoteContent(clueRemark.getNoteContent());
				customerRemark.setCreateBy(clueRemark.getCreateBy());
				customerRemark.setCreateTime(clueRemark.getCreateTime());
				customerRemark.setEditBy(clueRemark.getEditBy());
				customerRemark.setEditTime(clueRemark.getEditTime());
				customerRemark.setEditFlag(clueRemark.getEditFlag());
				customerRemark.setCustomerId(customer.getId());
				customerRemarkList.add(customerRemark);
				
				contactsRemark=new ContactsRemark();
				contactsRemark.setId(UUIdUtils.getUUId());
				contactsRemark.setNoteContent(clueRemark.getNoteContent());
				contactsRemark.setCreateBy(clueRemark.getCreateBy());
				contactsRemark.setCreateTime(clueRemark.getCreateTime());
				contactsRemark.setEditBy(clueRemark.getEditBy());
				contactsRemark.setEditTime(clueRemark.getEditTime());
				contactsRemark.setEditFlag(clueRemark.getEditFlag());
				contactsRemark.setContactsId(contacts.getId());
				contactsRemarkList.add(contactsRemark);
			}
			customerRemarkMapper.insertCustomerRemarkBatch(customerRemarkList);
			contactsRemarkMapper.insertContactsRemarkBatch(contactsRemarkList);	
		}
		
		//根据clueId查询当前线索与市场活动的关联关系数据
		List<ClueActivityRelation> carList = clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
		if (carList!=null&&carList.size()>0) {
			
			ContactsActivityRelation conActRel = null;
			List<ContactsActivityRelation> conActRelList = new ArrayList<>();
			for (ClueActivityRelation car : carList) {
				conActRel = new ContactsActivityRelation();
				conActRel.setId(UUIdUtils.getUUId());
				conActRel.setActivityId(car.getActivityId());
				conActRel.setContactsId(contacts.getId());
				conActRelList.add(conActRel);
			}
			contActRelMapper.insertContactsActivityRelationByList(conActRelList);
			
		}
		
		String  isCreateTran =(String)map.get("isCreateTran");
		boolean createTran = Boolean.parseBoolean(isCreateTran);
		if (createTran) {
			Tran tran = new Tran();
			tran.setId(UUIdUtils.getUUId());
			tran.setOwner(((User)map.get(Contants.SESSION_USER)).getId());
			tran.setMoney((String)map.get("money"));
			tran.setName((String)map.get("name"));
			tran.setExpectedDate((String)map.get("expectedDate"));
			tran.setCustomerId(customer.getId());
			tran.setStage((String)map.get("stage"));
			tran.setActivityId((String)map.get("activityId"));
			tran.setContactsId(contacts.getId());
			tran.setCreateBy(((User)map.get(Contants.SESSION_USER)).getId());
			tran.setCreateTime(DateUtils.formateDateTime(new Date()));
			tranMapper.insertTran(tran);
			
			TranRemark tranRemark = null;
			List<TranRemark> tranRemarkList = new ArrayList<>();
			if (carList!=null&&carList.size()>0) {
				for (ClueRemark clueRemark : clueRemarks) {
					tranRemark=new TranRemark();
					tranRemark.setId(UUIdUtils.getUUId());
					tranRemark.setNoteContent(clueRemark.getNoteContent());
					tranRemark.setCreateBy(clueRemark.getCreateBy());
					tranRemark.setCreateTime(clueRemark.getCreateTime());
					tranRemark.setEditBy(clueRemark.getEditBy());
					tranRemark.setEditTime(clueRemark.getEditTime());
					tranRemark.setEditFlag(clueRemark.getEditFlag());
					tranRemark.setTranId(tran.getId());
					tranRemarkList.add(tranRemark);
				}
				tranRemarkMapper.insertTranRemarkByList(tranRemarkList);
			}
		}
		//删除线索的备注信息
		clueRemarkMapper.deleteClueRemarkByClueId(clueId);
		//删除线索与市场活动的关联关系
		clueActivityRelationMapper.deleteClueActivityRelationByClueId(clueId);
		//删除线索
		clueMapper.deleteClueById(clueId);
	}
	
}
