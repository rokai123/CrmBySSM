package com.lukai.ListTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class ListMethodTest02 {
	@Test
	public void listTest() {
		ArrayList<Student> arrayList = new ArrayList<>();
		add(arrayList);
		//outPutStu(arrayList);
		//findStudentByName(arrayList,"iii");
		//set(arrayList,"005");
		//removeIf(arrayList);
		//sortByScoreOrName(arrayList);
		//arrayList.forEach(System.out::println);
		calculate(arrayList);
		
	}
	
	/*1.添加功能（add）
	创建一个 ArrayList<Student> 集合。
	添加以下 5 名学生:
	001 张三 88
	002 李四 95
	003 王五 76
	004 赵六 95
	005 钱七 60*/
	public static void add(List<Student> arrayList) {
		
		arrayList.add(new Student("001","张三",88));
		arrayList.add(new Student("002","李四",95));
		arrayList.add(new Student("003","王五",76));
		arrayList.add(new Student("004","赵六",95));
		arrayList.add(new Student("005","钱七",60));
	}
	
	/*2.遍历输出（forEach）
	使用 forEach() 或增强 for 循环输出所有学生信息。*/
	public static void outPutStu(List<Student> stuList) {
		stuList.forEach(System.out::println);
			
	}
	
	/*3.查找功能（find）
	编写方法 findStudentByName(List<Student> list, String name)
	查找姓名为 "李四" 的学生并输出信息；
	如果没找到输出 "未找到该学生"。*/
	public static void findStudentByName(List<Student> list, String name) {
		//归集（toList/toSet/toMap）
		List<Student> list2 = list.stream().filter(stu->stu.getName().equals(name)).collect(Collectors.toList());
		
		if (list2.isEmpty()) {
			System.out.println("未找到该学生");
		}else {
			list2.forEach(System.out::println);
		}
		
	}
	
	/*4.修改功能（set）
	将学号 "005" 的学生成绩改为 80 分。*/
	public static void set(List<Student> stuList,String sId) {
		List<Student> list = stuList.stream().filter(s->s.getId().equals(sId)).toList();
		if (list.isEmpty()) {
			System.out.println("未找到该学生");
			return;
		}else {
			stuList.forEach(stu->{
				if (stu.getId().equals(sId)) {
					stu.setScore(80);
				}
			});
		}
		
		
		stuList.forEach(System.out::println);
	}
	
	/*5.删除功能（removeIf）
	删除所有成绩低于 70 分的学生。*/
	public static void removeIf(List<Student> stuList) {
		stuList.removeIf(s->s.getScore()<70);
		stuList.forEach(System.out::println);
	}
	
	// 5. 按成绩降序，姓名升序排序
	public static void sortByScoreOrName(List<Student> stuList) {
		stuList.sort((s1,s2)->{
			if (s1.getScore()!=s2.getScore()) {
				return s2.getScore()-s1.getScore();
			}else {
				return s1.getName().compareTo(s2.getName());
			}
		});
	}
	
	// 6️. 统计平均分、最高分、最低分
	public static void calculate(List<Student> stuList) {
		//求平均值
		Double scoreAvg = stuList.stream().collect(Collectors.averagingDouble(Student::getScore));
		System.out.println(scoreAvg);
		//最高分
		Student student = stuList.stream().collect(Collectors.maxBy((s1,s2)->s1.getScore()-s2.getScore())).get();
		System.out.println(student.getScore());
		//最低分
		Student student2 = stuList.stream().collect(Collectors.minBy((s1,s2)->s1.getScore()-s2.getScore())).get();
		System.out.println(student2.getScore());
	}
}

class Student{
	private String id;       // 学号
    private String name;     // 姓名
    private int score;       // 成绩
    
    
    
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
