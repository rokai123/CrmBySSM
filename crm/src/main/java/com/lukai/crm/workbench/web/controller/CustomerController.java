package com.lukai.crm.workbench.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.commons.utils.DateUtils;
import com.lukai.crm.commons.utils.UUIdUtils;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.settings.service.UserService;
import com.lukai.crm.workbench.domain.Customer;
import com.lukai.crm.workbench.domain.CustomerRemark;
import com.lukai.crm.workbench.service.CustomerRemarkService;
import com.lukai.crm.workbench.service.CustomerService;

@Controller
public class CustomerController {
	@Autowired
	CustomerService customerService;
	@Autowired
	UserService userService;
	@Autowired
	CustomerRemarkService customerRemarkService;
	@RequestMapping("/workbench/customer/toIndex.do")
	public String toIndex(HttpSession session,HttpServletRequest request) {
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		List<User> userList = userService.queryAllUsers();
		request.setAttribute("user", user);
		request.setAttribute("userList", userList);
		return "workbench/customer/index";
	}
	
	@RequestMapping("/workbench/customer/queryCustomerByConditionForPage.do")
	@ResponseBody
	public Map<String, Object> queryCustomerByConditionForPage(String name,String owner,String phone,String website,Integer pageNo,Integer pageSize) {
		//パラメータをMapオブジェクトに格納する。
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("owner", owner);
		map.put("phone", phone);
		map.put("website", website);
		map.put("beginNo", (pageNo-1)*pageSize);
		map.put("pageSize", pageSize);
		List<Customer> customerList = customerService.queryCustomerByConditionForPage(map);
		int totalRows = customerService.queryCustomerByConditionForPageCount(map);
		HashMap<String, Object> retMap = new HashMap<>();
		retMap.put("customerList", customerList);
		retMap.put("totalRows", totalRows);
		return retMap;
	}
	
	@RequestMapping("/workbench/customer/saveCreateCustomer.do")
	@ResponseBody
	public ReturnObject saveCreateCustomer(Customer customer,HttpSession session){
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		customer.setId(UUIdUtils.getUUId());
		customer.setCreateBy(user.getId());
		customer.setCreateTime(DateUtils.formateDateTime(new Date()));
		ReturnObject returnObject = new ReturnObject();
		try {
			int retInt = customerService.saveCreateCustomer(customer);
			if (retInt>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
			}else {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		return returnObject;
	}
	/**
	 * 取引先詳細情報を表示するためのコントローラ
	 * @param id
	 * @return
	 */
	@RequestMapping("/workbench/customer/queryCustomerById.do")
	@ResponseBody
	public Customer queryCustomerById(String id) {
		Customer customer = customerService.queryCustomerById(id);
		
		return customer;
	}
	
	@RequestMapping("/workbench/customer/saveEditCustomer.do")
	@ResponseBody
	public ReturnObject saveEditCustomer(Customer customer,HttpSession session) {
		ReturnObject returnObject = new ReturnObject();
		try {
			User user = (User)session.getAttribute(Contants.SESSION_USER);
			customer.setEditBy(user.getId());
			customer.setEditTime(DateUtils.formateDateTime(new Date()));
			int retInt = customerService.saveEditCustomer(customer);
			if (retInt>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
			}else {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		return returnObject;
	}
	
	@RequestMapping("/workbench/customer/deleteCustomerByIds.do")
	@ResponseBody
	public ReturnObject deleteCustomerByIds(String[] id) {
		ReturnObject returnObject = new ReturnObject();
		System.out.println(id);
		try {
			int retInt = customerService.deleteCustomerByIds(id);
			if (retInt>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
			}else {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		return returnObject;
	}
	/**
	 * 取引先詳細情報画面に遷移するためのコントローラ
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/workbench/customer/toCustomerDetail.do")
	public String toCustomerDetail(String id,HttpServletRequest request) {
		Customer customer = customerService.queryCustomerForDetailById(id);
		List<CustomerRemark> customerRemarkList = customerRemarkService.queryCustomerRemarkByCusId(id);
		request.setAttribute("customer", customer);
		request.setAttribute("customerRemarkList", customerRemarkList);
		return "workbench/customer/detail";
	}
	
	@RequestMapping("/workbench/customer/saveCreateCustomerRemark.do")
	@ResponseBody
	public ReturnObject saveCreateCustomerRemark(CustomerRemark customerRemark,HttpSession session) {
		User	user = (User)session.getAttribute(Contants.SESSION_USER);
		ReturnObject ret = customerRemarkService.saveCreateCustomerRemark(customerRemark,user.getId());
		
		return ret;
	}
}
