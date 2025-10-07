package com.lukai.ListTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ListMethodTest {
	/*1. 定义一个 `List<Integer>`，并初始化为：`[12, 5, 8, 20, 3, 15, 7, 8, 10]`。
	2. 编写函数 `printList(List<Integer> list)`，使用 **三种方式** 遍历并打印集合：
	   * for 循环（通过下标 `get`）
	   * Iterator 迭代器
	* forEach（Lambda 表达式）
	3. 编写函数 `findAllIndex(List<Integer> list, int target)`，
	查找元素 `target` 在集合中所有出现的位置（下标），并返回一个新的 `List<Integer>` 存储所有下标。
	4. 编写函数 `removeOdd(List<Integer> list)`，
	删除集合中所有奇数，返回只包含偶数的列表。
	5. 编写函数 `replaceValue(List<Integer> list, int oldValue, int newValue)`，
	将集合中所有等于 `oldValue` 的元素替换为 `newValue`。
	6. 编写函数 `getTopN(List<Integer> list, int n)`，
	返回一个新的列表，存储集合中前 `n` 个最大的元素（要求排序后取子列表）。
	7. 在 `main` 方法中调用以上函数，演示功能。*/

	
	
	@Test
	public void listPractic() {
		/*1. 定义一个 `List<Integer>`，并初始化为：`[12, 5, 8, 20, 3, 15, 7, 8, 10]`。*/
		List<Integer> arrayList = new ArrayList<>();
		arrayList.add(12);
		arrayList.add(5);
		arrayList.add(8);
		arrayList.add(20);
		arrayList.add(3);
		arrayList.add(15);
		arrayList.add(7);
		arrayList.add(8);
		arrayList.add(10);
		
		//printList(arrayList);
		/*List<Integer> allIndex = findAllIndex(arrayList, 8);
		allIndex.forEach(System.out::println);*/
		/*List<Integer> removeOdd = removeOdd(arrayList);
		removeOdd.forEach(System.out::println);*/
		/*List<Integer> replaceValue = replaceValue(arrayList, 8, 0);
		replaceValue.forEach(System.out::println);*/
		
		List<Integer> topN = getTopN(arrayList, 8);
		topN.forEach(System.out::println);
	}
	
	public static void printList(List<Integer> list) {
		/*2. 编写函数 `printList(List<Integer> list)`，使用 **三种方式** 遍历并打印集合：
		   * for 循环（通过下标 `get`）
		   * Iterator 迭代器
		* forEach（Lambda 表达式）*/
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		System.out.println("-----------------");
		Iterator<Integer> iterator = list.iterator();
		while (iterator.hasNext()) {
			Integer integer = (Integer) iterator.next();
			System.out.println(integer);
		}
		
		for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
			Integer integer = (Integer) iterator2.next();
			System.out.println(integer);
		}
		
		System.out.println("----------------------------");
		list.forEach(System.out::println);
	}
	
	/*3. 编写函数 `findAllIndex(List<Integer> list, int target)`，
	查找元素 `target` 在集合中所有出现的位置（下标），并返回一个新的 `List<Integer>` 存储所有下标。*/
	public static List<Integer> findAllIndex(List<Integer> list, int target) {
		List<Integer> newList = new ArrayList<Integer>();
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i)==8) {
				newList.add(i);
			}
		}
		
		return newList;
	}
	
	/*4. 编写函数 `removeOdd(List<Integer> list)`，
	删除集合中所有奇数，返回只包含偶数的列表。*/
	public static List<Integer> removeOdd(List<Integer> list) {
		ArrayList<Integer> newList = new ArrayList<Integer>();
		list.stream().filter(num->num%2==0).forEach(num->{
			newList.add(num);
		});
		
		return newList;
	}
	
	/*5. 编写函数 `replaceValue(List<Integer> list, int oldValue, int newValue)`，
	将集合中所有等于 `oldValue` 的元素替换为 `newValue`。*/
	public static List<Integer> replaceValue(List<Integer> list, int oldValue, int newValue) {
		//使用List集合特有迭代器listIterator的set方法
		/*ListIterator<Integer> listIterator = list.listIterator();
		while (listIterator.hasNext()) {
			Integer integer = (Integer) listIterator.next();
			if (integer==oldValue) {
				listIterator.set(newValue);
			}
		}*/
		
		//return list;
		//使用普通for循环及集合的get方法
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i)==oldValue) {
				list.set(i, newValue);
			}
		}
		
		return list;
	}
	
	/*6. 编写函数 `getTopN(List<Integer> list, int n)`，
	返回一个新的列表，存储集合中前 `n` 个最大的元素（要求排序后取子列表）。*/
	public static List<Integer> getTopN(List<Integer> list, int n) {
		list.sort(Comparator.reverseOrder());
		
		return new ArrayList<Integer>(list.subList(0, Math.min(n, list.size())));
	}
}
