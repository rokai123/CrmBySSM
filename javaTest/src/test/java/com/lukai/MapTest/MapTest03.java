package com.lukai.MapTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/*【进阶上机题】Map + List + 函数式编程综合练习*/
public class MapTest03 {
	public static void main(String[] args) {
		Map<String, List<Student3>> stuMap = initData();
		addStudent(stuMap,"class3",new Student3("301","钢蛋",60));
		//stuMap.forEach((key,value)->System.out.println(key+":"+value));
		//findStudent(stuMap,"王五");
		//deleteStudent(stuMap,"101");
		//sortByScore(stuMap,"class2");
		countAvg(stuMap);
		//stuMap.forEach((key,value)->System.out.println(key+":"+value));
	}

	/*初始化数据
	在 initData() 方法中，向 stuMap 中放入两组学生数据，例如：
	"Class1" 包含 3 个学生
	"Class2" 包含 3 个学生*/
	public static Map<String, List<Student3>> initData() {
		Map<String, List<Student3>> stuMap = new HashMap<String, List<Student3>>();
		ArrayList<Student3> stuList1 = new ArrayList<Student3>();
		stuList1.add(new Student3("101", "张三", 88));
		stuList1.add(new Student3("102", "李四", 92));
		stuList1.add(new Student3("103", "王五", 75));
		ArrayList<Student3> stuList2 = new ArrayList<Student3>();
		stuList2.add(new Student3("201", "王五", 90));
		stuList2.add(new Student3("202", "小野", 66));
		stuList2.add(new Student3("203", "小高", 82));

		stuMap.put("class1", stuList1);
		stuMap.put("class2", stuList2);
		
		return stuMap;
	}
	
	/*添加学生
	编写方法：
	public static void addStudent(Map<String, List<Student>> map, String className, Student3 s)
	👉 功能：在指定班级中添加一个学生。如果班级不存在则新建班级。*/
	public static void addStudent(Map<String, List<Student3>> map,String calssName,Student3 s) {
		if (calssName.substring(0, 5).equals("class")) {
			if (map.containsKey(calssName)) {
				map.get(calssName).add(s);
			}else {
				ArrayList<Student3> stuList3 = new ArrayList<>();
				stuList3.add(s);
				map.put(calssName,stuList3);
			}
			
		}else {
			System.out.println("输入班级格式不合法!参考格式：class3");
		}
		
	}
	
	/*查找学生
	编写方法：
	public static void findStudent(Map<String, List<Student>> map, String name)
	👉 功能：查找所有班级中姓名为 name 的学生，并输出所在班级与信息。
	若没找到，输出“未找到该学生”。
	（要求用 Stream 实现）*/
	public static void findStudent(Map<String, List<Student3>> stuMap, String name) {
		//stream流解法
		List<String> stuList = stuMap.entrySet().stream()
			.flatMap(e->e.getValue().stream()
				.filter(stu->stu.getName().equals(name))
				.map(stu->e.getKey()+"班:"+stu))
			.collect(Collectors.toList());
		
		if (stuList.isEmpty()) {
			System.out.println("未找到该学生");
		}else {
			stuList.forEach(System.out::println);
		}
		
		//不使用Stream流，使用for和if语句的解法
		/*Set<Entry<String, List<Student3>>> stuSet = stuMap.entrySet();
		ArrayList<String> stuList = new ArrayList<>();
		stuSet.forEach(entry->{
			List<Student3> value = entry.getValue();
			for (Student3 student3 : value) {
				if (student3.getName().equals(name)) {
					stuList.add(entry.getKey()+"班："+student3);
				}
			}
		});
		
		if (stuList.isEmpty()) {
			System.out.println("该学生不存在");
		}else {
			stuList.forEach(System.out::println);
		}*/
	}
	
	/*4️.删除学生
	编写方法：
	public static void deleteStudent(Map<String, List<Student>> map, String id)
	👉 功能：在所有班级中删除指定学号的学生（注意不能在 forEach 里直接 remove）。*/
	public static void deleteStudent(Map<String, List<Student3>> stuMap, String id) {
		Set<Entry<String, List<Student3>>> entrySet = stuMap.entrySet();
		for (Entry<String, List<Student3>> entry : entrySet) {
			List<Student3> value = entry.getValue();
			value.removeIf(stu->stu.getId().equals(id));
		}
		
	}
	
	/*5️. 按成绩排序输出
	编写方法：
	public static void sortByScore(Map<String, List<Student>> map, String className)
	👉 功能：对指定班级的学生按成绩从高到低排序，并输出。
	（要求用 Lambda 或 Comparator 实现）*/
	public static void sortByScore(Map<String, List<Student3>> stuMap, String className) {
		Set<Entry<String, List<Student3>>> entrySet = stuMap.entrySet();
		for (Entry<String, List<Student3>> entry : entrySet) {
			if (entry.getKey().equals(className)) {
				List<Student3> stusValue = entry.getValue();
				stusValue.sort((s1,s2)->s2.getScore()-s1.getScore());
			}
			
		}
	}
	
	/*6️.统计平均分
	编写方法：
	public static void countAvg(Map<String, List<Student>> map)
	👉 功能：输出每个班级的平均成绩，要求使用 Stream。*/
	public static void countAvg(Map<String, List<Student3>> stuMap) {
		stuMap.entrySet().stream()
			.map(e->{
				String className = e.getKey();
				List<Student3> stu = e.getValue();
				Double avgScore = stu.stream().collect(Collectors.averagingDouble(Student3::getScore));
				return className+"班平均成绩："+avgScore;
			})
			.forEach(System.out::println);
	}
	
}


class Student3 {
    private String id;       // 学号
    private String name;     // 姓名
    private int score;       // 成绩
    
    
 // 构造方法 + getter + setter + toString()
	public Student3() {
		super();
	}



	public Student3(String id, String name, int score) {
		super();
		this.id = id;
		this.name = name;
		this.score = score;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getScore() {
		return score;
	}



	public void setScore(int score) {
		this.score = score;
	}



	@Override
	public String toString() {
		return "Student3 [id=" + id + ", name=" + name + ", score=" + score + "]";
	}

	
	
    
}
