package com.lukai.shangjiTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
	
	public static void main(String[] args) {
		ArrayList<Student> stuList = new ArrayList<>();
		stuList.add(new Student("zhangsan",21));
		stuList.add(new Student("lisi",18));
		stuList.add(new Student("wangwu",25));
		stuList.add(new Student("zhaoliu",16));
		stuList.add(new Student("qiansan",30));
		ArrayList<Integer> intList = new ArrayList<Integer>();
		intList.add(21);
		intList.add(18);
		intList.add(25);
		intList.add(16);
		intList.add(30);
		List<Student> checkList = checkList(stuList, intList);
		System.out.println(checkList);
	}
	
	public static List<Student> checkList(List<Student> stuList,List<Integer> numList) {
		//计算出第二个参数中数字的平均值
		int ageNum = numList.stream().collect(Collectors.averagingInt(o1->o1)).intValue();
		//filter过滤出大于该平均值的所有对象
		List<Student> list = stuList.stream().filter(s->s.getAge()>ageNum).toList();
		//迭代循环遍历过程中给集合中每个对象年龄+1
		for (Iterator<Student> iterator = list.iterator(); iterator.hasNext();) {
			Student student = (Student) iterator.next();
			student.setAge(student.getAge()+1);
		}
		//最终返回对象集合
		return list;
	}
	
}

class Student{
	private String name;
	private int age;
	
	public Student() {
		super();
	}



	public Student(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + "]";
	}
	
	
}
