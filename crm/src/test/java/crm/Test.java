package crm;

import java.util.Date;

import com.lukai.crm.commons.utils.DateUtils;

public class Test {
	@org.junit.Test
	public void dateUtilsTest() {
		String formateDateTime = DateUtils.formateDateTime(new Date());
		System.out.println(formateDateTime);
	}
}
