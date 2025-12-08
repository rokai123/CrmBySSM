package com.lukai.crm.workbench.service.Impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lukai.crm.commons.contants.Contants;
import com.lukai.crm.commons.domain.ReturnObject;
import com.lukai.crm.commons.utils.DateUtils;
import com.lukai.crm.commons.utils.HSSFUtils;
import com.lukai.crm.commons.utils.UUIdUtils;
import com.lukai.crm.settings.domain.User;
import com.lukai.crm.workbench.domain.Activity;
import com.lukai.crm.workbench.mapper.ActivityMapper;
import com.lukai.crm.workbench.service.ActivityService;
@Service("activityService")
public class ActivityServiceImpl implements ActivityService{

	@Autowired
	ActivityMapper activityMapper;
	
	@Override
	public ReturnObject saveCreateActivity(Activity activity) {
		ReturnObject returnObject = new ReturnObject();
		try {
			int resultNum = activityMapper.insertActivity(activity);
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
	
	//条件とページネーションによるマーケティングキャンペーン一覧検索
	@Override
	public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {
		
		return activityMapper.selectActivityByConditionForPage(map);
	}

	//条件に基づくマーケティングキャンペーン一覧の総件数を取得
	@Override
	public int queryCountOfActivityByCondition(Map<String, Object> map) {
		return activityMapper.selectCountOfActivityByCondition(map);
	}
	
	
	//IDに基づいて該当するマーケティングキャンペーンを削除
	@Override
	public int deleteActivityByIds(String[] ids) {
		
		return activityMapper.deleteActivityByIds(ids);
	}
	
	//IDに基づいて該当するマーケティングキャンペーンをリサーチ
	@Override
	public Activity queryActivityById(String id) {
		
		return activityMapper.selectActivityById(id);
	}

	@Override
	public int saveActivity(Activity activity) {
		
		return activityMapper.updateActivity(activity);
	}

	@Override
	public List<Activity> queryAllActivitys() {
		return activityMapper.selectAllActivities();
	}

	@Override
	public List<Activity> queryActivitysByIds(String[] ids) {
		
		return activityMapper.selectActivityByIds(ids);
	}
	
	/**
	 * 複数のマーケティングキャンペーンを一括で作成
	 * created by 102106
	 * */
	@SuppressWarnings("resource")
	@Override
	public ReturnObject saveCreateActivityByList(MultipartFile activityFile,HttpSession session) {
		ReturnObject returnObject = new ReturnObject();
		User user = (User)session.getAttribute(Contants.SESSION_USER);
		List<Activity> activityList = new ArrayList<Activity>();
		try {
			// 受信したExcelファイルをディスクに書き込む
			// String originalFilename = activityFile.getOriginalFilename();
			// File file = new File( "D:\\dev\\projects\\CRMBySSM\\excel\\"+ originalFilename);
			// activityFile.transferTo(file);
			// Excelファイルを解析し、シートデータを読み取ってActivityオブジェクトのリストに変換
			// InputStream is = new FileInputStream("D:\\dev\\projects\\CRMBySSM\\excel\\"+ originalFilename);
			InputStream is = activityFile.getInputStream();
			HSSFWorkbook wb = new HSSFWorkbook(is);
			HSSFSheet sheet = wb.getSheetAt(0);
			// 行単位でループ処理
			HSSFRow row = null;
			HSSFCell cell = null;
			Activity activity = null;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				activity = new Activity();
				activity.setId(UUIdUtils.getUUId());
				activity.setOwner(user.getId());// 顧客要件に基づき、所有者はデフォルトで現在のユーザーとする
				activity.setCreateBy(user.getId());
				activity.setCreateTime(DateUtils.formateDateTime(new Date()));
				// 列単位でループ処理
				for (int j = 0; j < row.getLastCellNum(); j++) {
					cell = row.getCell(j);
					//列のデータを取得
					String cellValue = HSSFUtils.getCellValueForString(cell);
					switch (j) {
					case 0:
						activity.setName(cellValue);
						break;
					case 1:
						activity.setStartDate(cellValue);
						break;
					case 2:
						activity.setEndDate(cellValue);
						break;
					case 3:
						activity.setCost(cellValue);
						break;
					case 4:
						activity.setDescription(cellValue);
						break;
					}
				}
				activityList.add(activity);
			}
			int resultNum = activityMapper.insertActivityByList(activityList);
			if (resultNum>0) {
				returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
				returnObject.setResultData(resultNum);
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
	
	//詳細情報のためにIDに基づいてマーケティングキャンペーンをリサーチ
	@Override
	public Activity queryActivityForDetailById(String id) {
		
		return activityMapper.selectActivityByIdForDetail(id);
	}

	@Override
	public List<Activity> queryActivityForClueDetailByClueId(String clueId) {
		List<Activity> activities = activityMapper.selectActivityForClueDetailByClueId(clueId);
		return activities;
	}
	
	/**
	 * 名称によるあいまい検索を行い、指定されたリードに未関連のマーケティングキャンペーン情報を取得
	 * @param clueId
	 */
	@Override
	public List<Activity> queryActivityByNameAndClueId(Map<String, Object> map) {
		List<Activity> activities = activityMapper.selectActivityByNameAndClueId(map);
		return activities;
	}
	
	

}
