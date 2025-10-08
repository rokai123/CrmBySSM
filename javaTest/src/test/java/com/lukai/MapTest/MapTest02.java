package com.lukai.MapTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapTest02 {
	 public static void main(String[] args) {
	        // 1. 创建学生对象
	        Student2 stu1 = new Student2("1", "1", "张三", 50);
	        Student2 stu2 = new Student2("1", "1", "李四", 60);
	        Student2 stu3 = new Student2("1", "2", "王五", 70);
	        Student2 stu4 = new Student2("2", "1", "赵六", 80);

	        // 2. 放入List集合
	        List<Student2> dataSource = new ArrayList<>();
	        dataSource.add(stu1);
	        dataSource.add(stu2);
	        dataSource.add(stu3);
	        dataSource.add(stu4);

	        // 3. 定义嵌套Map：年级→班级→学生列表
	        Map<String, Map<String, List<Student2>>> map = new HashMap<String, Map<String, List<Student2>>>();

	        // 4. 遍历数据源进行分组
	        for (Student2 stu : dataSource) {
	            if (map.containsKey(stu.grade)) {
	                if (map.get(stu.grade).containsKey(stu.classNo)) {
	                    map.get(stu.grade).get(stu.classNo).add(stu);
	                } else {
	                    List<Student2> newList = new ArrayList<Student2>();
	                    newList.add(stu);
	                    map.get(stu.grade).put(stu.classNo, newList);
	                }
	            } else {
	                Map<String, List<Student2>> newMap = new HashMap<String, List<Student2>>();
	                map.put(stu.grade, newMap);
	                List<Student2> newList = new ArrayList<Student2>();
	                newList.add(stu);
	                newMap.put(stu.classNo, newList);
	            }
	        }

	        // 5. 输出结果
	        for (String grade : map.keySet()) {
	            System.out.println("年级：" + grade);
	            Map<String, List<Student2>> classMap = map.get(grade);
	            for (String classNo : classMap.keySet()) {
	                System.out.println("  班级：" + classNo);
	                for (Student2 s : classMap.get(classNo)) {
	                    System.out.println("    姓名：" + s.name + " 分数：" + s.score);
	                }
	            }
	        }
	    }
}

class Student2 {
    public String grade;
    public String classNo;
    public String name;
    public int score;

    public Student2(String grade, String classNo, String name, int score) {
        this.grade = grade;
        this.classNo = classNo;
        this.name = name;
        this.score = score;
    }
}
