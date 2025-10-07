package com.lukai.StreamTerminalTst;

import java.util.List;
import java.util.stream.Stream;

import com.lukai.StreamTerminalTst.pojo.Student;
import com.lukai.StreamTerminalTst.pojo.StudentData;

/*归约（reduce），将所有元素按照指定的规则合并成一个结果。在Stream接口中，常用的归约方法如下：
1. Optional<T> reduce(BinaryOperator<T> accumulator);
2. T reduce(T identity, BinaryOperator<T> accumulator);*/

public class ReduceTest01 {
	public static void main(String[] args) {
		//将集合中所有的数字求和
		Stream.of(1,2,3,4,5).reduce((x,y)->x+y).get();
		
		//使用数学工具类Math求和
		Integer integer2 = Stream.of(1,2,3,4,5).reduce(Math::addExact).get();
		System.out.println(integer2);
		
		//获得集合中所有元素相乘的结果
		Integer integer3 = Stream.of(1,2,3,4,5).reduce((x,y)->x*y).get();
		System.out.println(integer3);
		Integer integer = Stream.of(1,2,3,4,5).reduce(Math::multiplyExact).get();
		System.out.println(integer);
		
		//获取最大长度的字符串
		String string = Stream.of("I", "love", "you", "too").reduce((s1,s2)->s1.length()>s2.length()?s1:s2).get();
		System.out.println(string);
		
		System.out.println("---------------------------------需求：获得所有学生的总年龄---------------------------------");
		// 需求：获得所有学生的总年龄
		List<Student> studentList = StudentData.getStudentList();
		Integer integer4 = studentList.stream().map(Student::getAge).reduce(Math::addExact).get();
		System.out.println(integer4);
		
		
		System.out.println("---------------------------需求：获得10和集合中所有元素“相加”的结果---------------------------");
		Integer reduce = Stream.of(1,2,3,4,5,6).reduce(10,Math::addExact);
		System.out.println(reduce);
		
		/*reduce操作可以实现从一组元素中生成一个值，而max()、min()、count()等方法都属于reduce操作，
		将它们单独设为方法只是因为常用，在Stream接口中这些方法如下：
		long count(); 获得元素的个数
		Optional<T> max(Comparator<? super T> comparator); 获得最大的元素
		Optional<T> min(Comparator<? super T> comparator); 获得最小的元素*/
		
		System.out.println("---------------------------需求：获得元素的个数---------------------------");
		long count = StudentData.getStudentList().stream().count();
		System.out.println(count);
		System.out.println(StudentData.getStudentList().size());
		
		System.out.println("---------------------------需求：获得年龄“最大”的学生---------------------------");
		Student student = StudentData.getStudentList().stream().max((stu1,stu2)->stu1.getAge()-stu2.getAge()).get();
		System.out.println(student);
				
		System.out.println("---------------------------需求：获得学生的“最大”年龄---------------------------");
		Integer integer5 = StudentData.getStudentList().stream().map(Student::getAge).max((n1,n2)->n1-n2).get();
		System.out.println(integer5);
		
		
		// 需求：获得年龄“最小”的学生
		System.out.println("---------------------------需求：获得年龄“最小”的学生---------------------------");
		Student student2 = StudentData.getStudentList().stream().min((s1,s2)->s1.getAge()-s2.getAge()).get();
		System.out.println(student2);
		
		
	}
}
