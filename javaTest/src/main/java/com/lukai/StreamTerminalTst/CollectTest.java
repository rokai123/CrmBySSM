package com.lukai.StreamTerminalTst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lukai.StreamTerminalTst.pojo.Student;
import com.lukai.StreamTerminalTst.pojo.StudentData;

/*收集（collect），可以说是内容最繁多、功能最丰富的部分了。
从字面上去理解，就是把一个流收集起来，最终可以是收集成一个值也可以收集成一个新的集合。
调用Stream接口提供的“<R, A> R collect(Collector<? super T, A, R> collector);”方法来实现收集操作，
并且参数中的Collector对象大都是直接通过Collectors工具类获得，实际上传入的Collector决定了collect()的行为。*/

public class CollectTest {
	public static void main(String[] args) {
		//将Stream流中的全部数据全部收集到一个集合中
		//收集为List集合,具体是哪种集合不知道
		List<String> collect = Stream.of("abc","asd","dfg").collect(Collectors.toList());
		System.out.println(collect);
		
		//收集为set集合，具体是哪种集合不知道
		Set<String> collect2 = Stream.of("abc","asd","dfg").collect(Collectors.toSet());
		System.out.println(collect2);
		
		//收集为map集合
		Map<String, String> collect3 = Stream.of("1:zhangsan","2:lisi","3:wangwu")
			.collect(Collectors.toMap(s1->s1.substring(0,s1.indexOf(":")),s2->s2.substring(s2.indexOf(":")+1)));
		System.out.println(collect3);
		
		//以指定的集合类型进行收集
		//以ArrayList进行收集
		ArrayList<String> collect4 = Stream.of("abc","asd","dfg").collect(Collectors.toCollection(ArrayList::new));
		System.out.println(collect4);
		
		System.out.println("------------------获得年龄大于20岁的女同学，然后返回按照年龄进行升序排序后的List集合-------------------");
		//获得年龄大于20岁的女同学，然后返回按照年龄进行升序排序后的List集合
		ArrayList<Student> collect5 = StudentData.getStudentList().stream()
				.filter(s->s.getAge()>20&&s.getSex().equals("女"))
				.sorted((s1,s2)->s1.getAge()-s2.getAge())
				.collect(Collectors.toCollection(ArrayList::new));
		
		System.out.println(collect5);
		
		//转换成数组，当没有指定数组类型时，默认转换成Object数组
		System.out.println("------------------转换成数组------------------");
		Object[] array = Stream.of(1,2,3,4,5).toArray();
		System.out.println(Arrays.toString(array));
		//转换为指定类型的数组
		Integer[] array2 = Stream.of(1,2,3,4,5).toArray(Integer[]::new);
		System.out.println(Arrays.toString(array2));
		
		//获取学生年龄的所有信息
		DoubleSummaryStatistics collect6 = StudentData.getStudentList().stream().collect(Collectors.summarizingDouble(stu->stu.getAge()));
		System.out.println("最大值："+collect6.getMax());
		System.out.println("最小值："+collect6.getMin());
		System.out.println("平均值"+collect6.getAverage());
		System.out.println("总和"+collect6.getSum());
		
	}
}
