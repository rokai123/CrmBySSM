package crm;

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
import com.lukai.crm.workbench.mapper.ActivityMapper;
import com.lukai.crm.workbench.mapper.ActivityRemarkMapper;
import com.lukai.crm.workbench.mapper.ClueMapper;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext.xml",
		"classpath:mybatis-config.xml",
		"classpath:applicationContext-mvc.xml"})
public class Test {
	
	@Autowired
	ActivityMapper activityMapper;
	@Autowired
	ActivityRemarkMapper activityRemarkMapper;
	@Autowired
	ClueMapper clueMapper;
	@Autowired
	DicValueMapper dicValueMapper;
	
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
		hashMap.put("fullname", "f");
		List<Clue> clues = clueMapper.selectClueByConditionForPage(hashMap);
		clues.forEach(System.out::println);
	}
	
	@org.junit.Test
	public void selectDicValueByTypeCode() {
		String typeCode = "appellation";
		dicValueMapper.selectDicValueByTypeCode(typeCode).forEach(System.out::println);
	}
}
