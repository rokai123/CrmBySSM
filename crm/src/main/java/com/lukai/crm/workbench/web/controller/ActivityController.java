package com.lukai.crm.workbench.web.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.lukai.crm.workbench.domain.Activity;
import com.lukai.crm.workbench.service.ActivityService;

@Controller
public class ActivityController {
	@Autowired
	UserService userService;
	
	@Autowired
	ActivityService activityService;

	/*
	 * by rokai123 
	 * 
	 * */
	@RequestMapping("/workbench/activity/index.do")
	public String index(HttpServletRequest request) {
		List<User> users = userService.queryAllUsers();
		request.setAttribute("userList", users);
		
		return "workbench/activity/index";
	}
	
	
	/**
	 * 
	 * @param activity
	 * @param session
	 * @return Json Object
	 * マーケティングキャンペーン新規機能を実装する方法
	 */
	@RequestMapping("/workbench/activity/saveCreateActivity.do")
	@ResponseBody
	public Object saveCreateActivity(Activity activity,HttpSession session) {
		//セッションスコープから現在ユーザを取得。
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		//データの一貫性を保つため、アカウント名よりユーザーIDを優先して使用すること。
		activity.setCreateBy(user.getId());
		activity.setId(UUIdUtils.getUUId());
		activity.setCreateTime(DateUtils.formateDateTime(new Date()));
		ReturnObject returnObject = new ReturnObject();
		try {
			int resultNum = activityService.saveCreateActivity(activity);
			if (resultNum>0) {
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
	 * 検索条件を元にマーケティングキャンペーン及び総件数を検索する
	 * @param name
	 * @param owner
	 * @param startDate
	 * @param endDate
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * 検索結果をJson Objectで返す
	 */
	@RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
	@ResponseBody
	public Object queryActivityByConditionForPage(String name,String owner,String startDate,String endDate,
																			   Integer pageNo,Integer pageSize) {
		//パラメータをMapオブジェクトに格納する。
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("owner", owner);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("beginNo", (pageNo-1)*pageSize);
		map.put("pageSize", pageSize);
		List<Activity> activities = activityService.queryActivityByConditionForPage(map);
		int totalRows = activityService.queryCountOfActivityByCondition(map);
		Map<String,Object> retMap = new HashMap<String,Object>();
		retMap.put("activities", activities);
		retMap.put("totalRows", totalRows);
		return retMap;
	}
	
	/**
	 * マーケティングキャンペーンを削除する
	 * @param id
	 * @return
	 * created by rokai123
	 * 削除結果をJson Objectで返す
	 */
	@RequestMapping("/workbench/activity/deleteActivityByIds.do")
	@ResponseBody
	public Object deleteActivityByIds(String[] id) {
		ReturnObject returnObject= new ReturnObject();
		try {
			int resultInt = activityService.deleteActivityByIds(id);
			if (resultInt>0) {
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
	 * IDに基づいて該当するマーケティングキャンペーンをリサーチ
	 * created by 102106
	 * 
	 */
	@RequestMapping("/workbench/activity/queryActivityById.do")
	@ResponseBody
	public Object queryActivityById(String id) {
		
		Activity activity = activityService.queryActivityById(id);
		return activity;
	}
	
	/**
	 * IDに基づいて該当するマーケティングキャンペーンを更新する
	 * 
	 * @param activity
	 * @return Json Object
	 * created by 102106
	 * 検索結果をJson Objectで返す
	 */
	@RequestMapping("/workbench/activity/saveEditActivity.do")
	@ResponseBody
	public Object saveEditActivity(Activity activity,HttpSession session) {
		ReturnObject returnObject = new ReturnObject();
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		activity.setEditTime(DateUtils.formateDateTime(new Date()));
		activity.setEditBy(user.getId());
		try {
			int resultInt = activityService.saveActivity(activity);
			if (resultInt>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
			}else {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
				returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
			}
		}catch (Exception e) {
			e.printStackTrace();
			returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
			returnObject.setMessage("システムが混雑中です、しばらくしてから再度お試しください");
		}
		return returnObject;
	}
	
	@SuppressWarnings("resource")
	@RequestMapping("/workbench/activity/exportAllActivitys.do")
	public void exportAllActivitys(HttpServletResponse response) throws Exception {
		//获取所有市场活动
		List<Activity> activities = activityService.queryAllActivitys();
		
		//在服务器生成Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("マーケティングキャンペーン");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("ID");
		cell = row.createCell(1);
		cell.setCellValue("所有者");
		cell = row.createCell(2);
		cell.setCellValue("キャンペーン名");
		cell=row.createCell(3);
		cell.setCellValue("開始日");
		cell=row.createCell(4);
		cell.setCellValue("終了日");
		cell=row.createCell(5);
		cell.setCellValue("コスト");
		cell=row.createCell(6);
		cell.setCellValue("コメント");
		cell=row.createCell(7);
		cell.setCellValue("作成日時");
		cell=row.createCell(8);
		cell.setCellValue("作成者");
		cell=row.createCell(9);
		cell.setCellValue("更新日時");
		cell=row.createCell(10);
		cell.setCellValue("更新者");
		
		//遍历activities，创建HSSFRow对象，生成所有的数据行
		Activity activity;
		if (activities!=null && activities.size()>0) {
			for(int i=0;i<activities.size();i++){
				row = sheet.createRow(i+1);
				activity = activities.get(i);
				cell = row.createCell(0);
				cell.setCellValue(activity.getId());
				cell = row.createCell(1);
				cell.setCellValue(activity.getOwner());
				cell = row.createCell(2);
				cell.setCellValue(activity.getName());
				cell = row.createCell(3);
				cell.setCellValue(activity.getStartDate());
				cell = row.createCell(4);
				cell.setCellValue(activity.getEndDate());
				cell = row.createCell(5);
				cell.setCellValue(activity.getCost());
				cell = row.createCell(6);
				cell.setCellValue(activity.getDescription());
				cell = row.createCell(7);
				cell.setCellValue(activity.getCreateTime());
				cell = row.createCell(8);
				cell.setCellValue(activity.getCreateBy());
				cell = row.createCell(9);
				cell.setCellValue(activity.getEditTime());
				cell = row.createCell(10);
				cell.setCellValue(activity.getEditBy());
			}
		}
		//生成excel文件
		OutputStream os = new FileOutputStream("D:\\dev\\projects\\CRMBySSM\\excel\\activity.xls");
		wb.write(os);
		//关闭资源
		os.close();
		wb.close();

		//把生成的Excel文件下载到客户端
		response.setContentType("application/octet-stream;charset=utf-8");
		response.addHeader("Content-Disposition", "attachment;filename=activity.xls");
		OutputStream out = response.getOutputStream();
		FileInputStream fis = new FileInputStream("D:\\dev\\projects\\CRMBySSM\\excel\\activity.xls");
		byte[] buff = new byte[256];
		int len = 0;
		while ((len=fis.read(buff))!=-1) {
			out.write(buff, 0, len);
		}
		fis.close();
		out.flush();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 跳转到某条市场活动的详情页面
	 * 
	 */
	@RequestMapping("/workbench/activity/detailActivity.do")
	public String detailActivity() { 
		// Activity activity = activityService.queryActivityForDetailById(id);
		// List<ActivityRemark> remarkList = activityService.queryActivityRemarkForDetailByActivityId(id);
		// request.setAttribute("activity", activity);
		// request.setAttribute("remarkList", remarkList);
		return "workbench/activity/detail";
	}

}
