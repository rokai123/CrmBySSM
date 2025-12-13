package com.lukai.crm.workbench.service.Impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.commons.utils.DateUtils;
import com.lukai.crm.commons.utils.UUIdUtils;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.workbench.domain.Clue;
import com.lukai.crm.workbench.domain.Contacts;
import com.lukai.crm.workbench.domain.Customer;
import com.lukai.crm.workbench.mapper.ClueMapper;
import com.lukai.crm.workbench.mapper.ContactsMapper;
import com.lukai.crm.workbench.mapper.CustomerMapper;
import com.lukai.crm.workbench.service.ClueService;
@Service("clueService")
public class ClueServiceImpl implements ClueService {
	
	@Autowired
	ClueMapper clueMapper;
	@Autowired
	CustomerMapper customerMapper;
	@Autowired
	ContactsMapper contactsMapper;
	
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
	public void saveConvertClue(Map<String, Object> map) {
		//idに基づいてリードを検索し、取り出す
		Clue clue = clueMapper.selectClueForConvertById((String)map.get("clueId"));
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
		
	}
	
}
