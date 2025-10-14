package com.lukai.MapTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MapTest04 {
	public static void main(String[] args) {
		Map<String, List<Student4>> stuData = initData();
		addStudent(stuData,"Class3",new Student4("05","du",85));
		/*stuData.forEach((key,value)->{
			System.out.println(key+value);
		});*/
		
		findStudent(stuData,"张三0");
	}
	
	/*① 初始化数据*/
	public static Map<String, List<Student4>> initData(){
		HashMap<String, List<Student4>> stuMap = new HashMap<String, List<Student4>>();
		ArrayList<Student4> stuList1 = new ArrayList<>();
		stuList1.add(Student4.builder().id("01").name("张三").score(88).build());
		stuList1.add(Student4.builder().id("02").name("李四").score(92).build());
		stuList1.add(Student4.builder().id("03").name("王五").score(75).build());
		ArrayList<Student4> stuList2 = new ArrayList<>();
		stuList2.add(Student4.builder().id("04").name("赵六").score(85).build());
		stuList2.add(Student4.builder().id("05").name("前七").score(95).build());
		stuMap.put("Class1",stuList1);
		stuMap.put("Class2",stuList2);
		
		return stuMap;
		
	}
	
	/*② 添加学生*/
	/*	功能：向指定班级添加一个学生（不存在该班则创建新的班级）。
	*/	
	public static void addStudent(Map<String, List<Student4>> stuMap, String className, Student4 stu) {
		Set<Entry<String, List<Student4>>> entrySet = stuMap.entrySet();
		for (Entry<String, List<Student4>> entry : entrySet) {
			String key = entry.getKey();
			if (key.equals(className)) {
				entry.getValue().add(stu);
			}else {
				ArrayList<Student4> arrayList = new ArrayList<Student4>();
				arrayList.add(stu);
				stuMap.put(className, arrayList);
				break;
			}
			
		}
	}

	/*③ 查找学生（根据姓名）*/
	/*功能：查找所有姓名匹配的学生，输出其所在班级及信息。
	要求使用 Stream + flatMap。*/
	public static void findStudent(Map<String, List<Student4>> stuMap, String name) {
		List<String> msgList = stuMap.entrySet().stream()
			.flatMap(e->e.getValue().stream()
				.filter(stu->stu.getName().equals(name))
				.map(stu->e.getKey()+"班:"+stu.toString()))
			.toList();
		
		boolean empty = msgList.isEmpty();
		if (empty) {
			System.out.println("未找到该学生");
		}else {
			msgList.forEach(System.out::println);
		}
	}

}




@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Student4{
	private String id;
    private String name;
    private double score;
	
	
}
