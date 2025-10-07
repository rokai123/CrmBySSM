package com.lukai.StreamTerminalTst;

import java.util.stream.Stream;

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
		
	}
}
