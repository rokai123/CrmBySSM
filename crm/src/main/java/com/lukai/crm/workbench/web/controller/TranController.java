package com.lukai.crm.workbench.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.settings.domain.DicValue;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.DicValueService;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.workbench.domain.Activity;
import com.lukai.crm.workbench.domain.Contacts;
import com.lukai.crm.workbench.domain.Tran;
import com.lukai.crm.workbench.domain.TranHistory;
import com.lukai.crm.workbench.domain.TranRemark;
import com.lukai.crm.workbench.domain.TranVO;
import com.lukai.crm.workbench.service.ActivityService;
import com.lukai.crm.workbench.service.ContactsService;
import com.lukai.crm.workbench.service.CustomerService;
import com.lukai.crm.workbench.service.TranHistoryService;
import com.lukai.crm.workbench.service.TranRemarkService;
import com.lukai.crm.workbench.service.TranService;

@Controller
public class TranController {
	@Autowired
	private DicValueService dicValueService;
	@Autowired
	private TranService tranService;
	@Autowired
	private UserService userService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private TranHistoryService tranHistoryService;
	@Autowired
	private TranRemarkService tranRemarkService;
	
	@RequestMapping("/workbench/transaction/toIndex.do")
	public String toIndex(HttpServletRequest request,HttpSession session) {
		List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
		List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
		List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		request.setAttribute("transactionTypeList", transactionTypeList);
		request.setAttribute("stageList", stageList);
		request.setAttribute("sourceList", sourceList);
		request.setAttribute("user", user);
		
		return "workbench/transaction/index";
	}
	
	//分页查询交易信息
	@RequestMapping("/workbench/transaction/selectTransByConditionForPage.do")
	@ResponseBody
	public Map<String, Object> selectTransByConditionForPage(
			String owner,String name,String customerName,String stage,String type,String source,
			String contactsName,Integer pageNo,Integer pageSize
	) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("owner", owner);
		map.put("name", name);
		map.put("customerName", customerName);
		map.put("stage", stage);
		map.put("type", type);
		map.put("source", source);
		map.put("contactsName", contactsName);
		map.put("beginNo", (pageNo-1)*pageSize);
		map.put("pageSize", pageSize);
		List<Tran> tranList = tranService.queryTransByConditionForPage(map);
		int totalRows = tranService.queryTransByConditionForPageCount(map);
		HashMap<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("tranList", tranList);
		retMap.put("totalRows", totalRows);
		
		return retMap;
		
	}
	
	@RequestMapping("/workbench/transaction/toCreateTranPage.do")
	public String toCreateTranPage(HttpSession session,HttpServletRequest request) {
		User user=(User)session.getAttribute(Contants.SESSION_USER);
		List<User> userList = userService.queryAllUsers();
		List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
		List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
		List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
		request.setAttribute("user", user);
		request.setAttribute("userList", userList);
		request.setAttribute("transactionTypeList", transactionTypeList);
		request.setAttribute("stageList", stageList);
		request.setAttribute("sourceList", sourceList);
		
		return "workbench/transaction/save";
	}
	
	@RequestMapping("/workbench/transaction/queryActivitiesByNameLike.do")
	@ResponseBody
	public List<Activity> queryActivitiesByNameLike(String Actname) {
		return activityService.queryActivitiesByNameLike(Actname);
	}
	
	
	@RequestMapping("/workbench/transaction/queryContactsByNameLike.do")
	@ResponseBody
	public List<Contacts> queryContactsByNameLike(String contName) {
		return contactsService.queryContactsByNameLike(contName);
	}
	
	@RequestMapping("/workbench/transaction/queryPossibilityByStage.do")
	@ResponseBody
	public String queryPossibilityByStage(String stage) {
		ResourceBundle rb = ResourceBundle.getBundle("possibility");
		String  possibility = rb.getString(stage);
		
		
		return possibility;
	}
	
	@RequestMapping("/workbench/transaction/queryCustomerNameByNameLike.do")
	@ResponseBody
	public List<String> queryCustomerNameByNameLike(String name) {
		return customerService.queryCustomerNameByNameLike(name);
	}
	
	/**
	 * 保存创建的交易
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping("/workbench/transaction/saveCreateTran.do")
	@ResponseBody
	public ReturnObject saveCreateTran(HttpSession session,@RequestParam Map<String, Object> map) {
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		map.put(Contants.SESSION_USER, user);
		
		ReturnObject returnObject = new ReturnObject();
		try {
			tranService.saveCreateTran(map);
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
			
		}
		
		return returnObject;
	}
	
	@RequestMapping("/workbench/transaction/TranDetailPage.do")
	public String TranDetailPage(String id,HttpServletRequest request) {
		TranVO tran = tranService.queryTranForDetailById(id);//交易信息
		List<TranHistory> TranHistoryList = tranHistoryService.queryTranHistoryByTranId(id);//交易历史列表
		ResourceBundle rb = ResourceBundle.getBundle("possibility");//将可能性设置到交易对象中
		String  possibility = rb.getString(tran.getStage());
		tran.setPossibility(possibility);
		List<TranRemark> tranRemarkList = tranRemarkService.queryTranRemarkByTranId(id);//交易备注信息列表
		List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");//阶段值列表
		request.setAttribute("stageList", stageList);
		request.setAttribute("tranRemarkList", tranRemarkList);
		request.setAttribute("tran", tran);
		request.setAttribute("tranHistoryList", TranHistoryList);
		return "workbench/transaction/detail";
	}
	
	@RequestMapping("/workbench/transaction/deleteTranByIds.do")
	@ResponseBody
	public ReturnObject deleteTranByIds(@RequestParam("id") String[] ids) {
		ReturnObject returnObject = new ReturnObject();
		try {
			int retInt = tranService.deleteTranByIds(ids);
			if (retInt>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				returnObject.setMessage("削除しました。");
			}else {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("削除に失敗しました。しばらくしてから再度お試しください。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("削除に失敗しました。しばらくしてから再度お試しください。");
		}
		
		
		return returnObject;
	}
}
