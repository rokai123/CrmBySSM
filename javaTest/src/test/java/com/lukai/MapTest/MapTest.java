package com.lukai.MapTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.Test;

/*题目：班级学生管理系统（List + Map 综合）
定义一个 `Student` 类，包含以下字段：
* `String id`（学号）
* `String name`（姓名）
* `int score`（成绩）
定义一个 `Map<String, List<Student>>` ，键（key）为班级名（如 `"Class1"`、`"Class2"`），
值（value）为该班级的学生列表。
请完成以下操作：
1. 创建两个班级 `"Class1"` 和 `"Class2"`，并向其中分别添加若干 `Student` 对象。
2. 输出所有班级的学生信息（要求遍历 `Map` 与 `List`）。
3. 查找 `"Class1"` 中成绩大于 80 分的学生，并输出他们的姓名。
4. 在 `"Class2"` 中删除一个指定学号的学生。
5. 对 `"Class1"` 的学生按照成绩从高到低排序，并输出排序后的结果。
*/


public class MapTest {
	@Test
	public void mapTest() {
		HashMap<String, List<Student>> StuHashMap = new HashMap<>();
		StuHashMap.put("Class1", new ArrayList<Student>());
		StuHashMap.put("Class2", new ArrayList<Student>());
		
		addStu(StuHashMap);
		//outputStu(StuHashMap);
		//findStusNameByScore(StuHashMap);
		//deleteStuById(StuHashMap,"S101");
		sortByScore(StuHashMap);
	}
	
	
	//1. 创建两个班级 `"Class1"` 和 `"Class2"`，并向其中分别添加若干 `Student` 对象。
	public void addStu(Map<String,List<Student>> map) {
		
		map.get("Class1").add(new Student("S001", "Alice", 90));
		map.get("Class1").add(new Student("S002", "Bob", 75));
		map.get("Class1").add(new Student("S003", "Tom", 85));
		map.get("Class2").add(new Student("S101", "Jerry", 88));
		map.get("Class2").add(new Student("S102", "Maggie", 65));
		map.get("Class2").add(new Student("S103", "Tony", 92));
		
	}
	
	//2. 输出所有班级的学生信息（要求遍历 `Map` 与 `List`）。
	public void outputStu(Map<String, List<Student>> stuMap) {
		Set<Entry<String, List<Student>>> entrySet = stuMap.entrySet();
		entrySet.forEach(entry->{
			String key = entry.getKey();
			List<Student> value = entry.getValue();
			if ("Class1".equals(key)) {
				value.forEach(stu->{
					System.out.println(key+":"+stu.toString());
				});
				
			}else if ("Class2".equals(key)) {
				value.forEach(stu->{
					System.out.println(key+":"+stu.toString());
				});
			}
		});
		
	}
	
	/*3. 查找 `"Class1"` 中成绩大于 80 分的学生，并输出他们的姓名。*/
	public void findStusNameByScore(Map<String, List<Student>> stuMap) {
		Set<Entry<String, List<Student>>> entrySet = stuMap.entrySet();
		entrySet.forEach(entry->{
			String key = entry.getKey();
			List<Student> value = entry.getValue();
			if ("Class1".equals(key)) {
				value.stream()
					.filter(stu->stu.getScore()>80)
					.map(Student::getName)
					.forEach(System.out::println);
				
			}
		});
	}
	
	/*4. 在 `"Class2"` 中删除一个指定学号的学生。*/
	public void deleteStuById(Map<String, List<Student>> stuMap,String id) {
		//“边读边删”同一个集合，Java 会认为这个集合被非法修改。
		//触发 ConcurrentModificationException（并发修改异常）。
		/*stuMap.get("Class2").forEach(stu->{
			if (stu.getId().equals(id)) {
				stuMap.get("Class2").remove(stu);
			}
		});*/
		
		List<Student> list = stuMap.get("Class2");
		//使用下标遍历
		/*for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId().equals(id)) {
				list.remove(i);
			}
			
		}*/
		
		//或者使用removeIf方法（推荐）
		list.removeIf(stu->stu.getId().equals(id));
		
		stuMap.get("Class2").forEach(System.out::println);
		
		
	}
	
	//5. 对 `"Class1"` 的学生按照成绩从高到低排序，并输出排序后的结果。
	public static void sortByScore(Map<String, List<Student>> stuMap) {
		List<Student> list = stuMap.get("Class1");
		list.sort((stu1,stu2)->stu1.getScore()-stu2.getScore());
		list.forEach(System.out::println);
		
	}
}

class Student{
	private String id;
	private String name;
	private int score;
	
	
	public Student() {
		super();
	}


	public Student(String id, String name, int score) {
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
		return "Student [id=" + id + ", name=" + name + ", score=" + score + "]";
	}
	
	
	
}
