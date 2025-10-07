package com.lukai.StreamTerminalTst.pojo;

import java.util.ArrayList;
import java.util.List;

public class StudentData {
	 /**
     * 获得一个存储Student对象的List集合
     */
    public static List<Student> getStudentList() {
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("张三", 21, "男", "武汉"));
        list.add(new Student("李四", 18, "女", "重庆"));
        list.add(new Student("王五", 25, "女", "成都"));
        list.add(new Student("赵六", 22, "男", "武汉"));
        list.add(new Student("王麻子", 16, "女", "成都"));
        list.add(new Student("秀娟", 29, "女", "江苏"));
        return list;
    }
}
