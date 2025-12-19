package crm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lukai.crm.commons.utils.DateUtils;
import com.lukai.crm.settings.mapper.DicValueMapper;
import com.lukai.crm.workbench.domain.Activity;
import com.lukai.crm.workbench.domain.ActivityRemark;
import com.lukai.crm.workbench.domain.Clue;
import com.lukai.crm.workbench.domain.ClueActivityRelation;
import com.lukai.crm.workbench.domain.ClueRemark;
import com.lukai.crm.workbench.domain.Customer;
import com.lukai.crm.workbench.mapper.ActivityMapper;
import com.lukai.crm.workbench.mapper.ActivityRemarkMapper;
import com.lukai.crm.workbench.mapper.ClueActivityRelationMapper;
import com.lukai.crm.workbench.mapper.ClueMapper;
import com.lukai.crm.workbench.mapper.ClueRemarkMapper;
import com.lukai.crm.workbench.mapper.CustomerMapper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext.xml",
		"classpath:mybatis-config.xml",
		"classpath:applicationContext-mvc.xml"})
public class Test {
	
	@Autowired
	private ActivityMapper activityMapper;
	@Autowired
	private ActivityRemarkMapper activityRemarkMapper;
	@Autowired
	private ClueMapper clueMapper;
	@Autowired
	private DicValueMapper dicValueMapper;
	@Autowired
	private ClueRemarkMapper clueRemarkMapper;
	@Autowired
	private ClueActivityRelationMapper clueActivityRelationMapper;
	@Autowired
	private CustomerMapper customerMapper;
	@org.junit.Test
	public void dateUtilsTest() {
		String formateDateTime = DateUtils.formateDateTime(new Date());
		System.out.println(formateDateTime);
	}
	@org.junit.Test
	public void selectActivityByIdForDetailTest() {
		
		Activity activity = activityMapper.selectActivityByIdForDetail("3f0fa1d7efb24087a7d54e3cce2907c6");
		
		System.out.println(activity.getOwner());
	}
	
	@org.junit.Test
	public void selectByPrimaryKeyTest() {
		List<ActivityRemark> selectByPrimaryKey =activityRemarkMapper.selectActivityRemarksByActivityIdForDetail("3f0fa1d7efb24087a7d54e3cce2907c6");
		System.out.println(selectByPrimaryKey);
	}
	
	@org.junit.Test
	public void selecClueByConditionForPage() {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("source", "6b86f215e69f4dbd8a2daa22efccf0cf");
		List<Clue> clues = clueMapper.selectClueByConditionForPage(hashMap);
		clues.forEach(System.out::println);
	}
	
	@org.junit.Test
	public void selectDicValueByTypeCode() {
		String typeCode = "appellation";
		dicValueMapper.selectDicValueByTypeCode(typeCode).forEach(System.out::println);
	}
	
	@org.junit.Test
	public void selectClueRemarkByClueId() {
		String clueId = "66ca2b3686574e7e9a38973ca6f0637e";
		List<ClueRemark> clues = clueRemarkMapper.selectClueRemarkForDetailByClueId(clueId);
		clues.forEach(System.out::println);
	}
	@org.junit.Test
	public void selectActivityByNameAndClueIdTest() {
		String name = "03";
		String clueId ="66ca2b3686574e7e9a38973ca6f0637e";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("clueId", clueId);
		List<Activity> activities = activityMapper.selectActivityByNameAndClueId(map);
		activities.forEach(System.out::println);
	}
	
	@org.junit.Test
	public void insertClueActivityRelationByList() {
		ArrayList<ClueActivityRelation> clueActivityRelations = new ArrayList<ClueActivityRelation>();
		ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
		clueActivityRelation.setId("1234567");
		clueActivityRelation.setClueId("asfsadf");
		clueActivityRelation.setActivityId("fsdfsd");
		clueActivityRelations.add(clueActivityRelation);
		int ret = clueActivityRelationMapper.insertClueActivityRelationByList(clueActivityRelations);
		System.out.println(ret);
	}
	
	@org.junit.Test
	public void selectClueRemarkForClueConvertByClueIdTest() {
		String clueId = "asfsad";
		List<ClueRemark> clueRemarks = clueRemarkMapper.selectClueRemarkForClueConvertByClueId(clueId);
		clueRemarks.forEach(System.out::println);
	}
	@org.junit.Test
	public void selectClueActivityRelationByClueIdTest() {
		String clueId ="asfsad";
		List<ClueActivityRelation> carList = clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
		carList.forEach(System.out::println);
	}
	
	@org.junit.Test
	public void selectCustomerByConditionForPage() {
		Integer beginNo =0;
		Integer pageSize =2;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("beginNo", beginNo);
		map.put("pageSize", pageSize);
		List<Customer> customers = customerMapper.selectCustomerByConditionForPage(map);
		customers.forEach(System.out::println);
		
		
	}
}
