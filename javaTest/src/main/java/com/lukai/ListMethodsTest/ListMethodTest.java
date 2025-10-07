package com.lukai.ListMethodsTest;

/*以下是 Java List 集合中所有常用方法的分类总结，适用于 ArrayList、LinkedList、Vector 等实现类：

1. 基础操作
方法 描述 示例
add(E e) 在列表末尾添加元素 list.add("A")
add(int index, E e) 在指定索引插入元素 list.add(0, "B")
addAll(Collection c) 添加另一个集合的所有元素 list.addAll(otherList)
remove(int index) 删除指定索引的元素 list.remove(0)
remove(Object o) 删除第一个匹配的元素 list.remove("A")
removeAll(Collection c) 删除所有存在于指定集合的元素 list.removeAll(toRemove)
retainAll(Collection c) 仅保留指定集合中的元素（取交集） list.retainAll(toKeep)
clear() 清空列表 list.clear()
set(int index, E e) 替换指定索引的元素 list.set(0, "C")
get(int index) 获取指定索引的元素 String s = list.get(0)
contains(Object o) 判断是否包含某个元素 boolean hasA = list.contains("A")
containsAll(Collection c) 判断是否包含指定集合的所有元素 list.containsAll(subList)
isEmpty() 判断列表是否为空 if (list.isEmpty())
size() 返回列表元素个数 int len = list.size()
2. 查找与遍历
方法 描述 示例
indexOf(Object o) 返回元素首次出现的索引（未找到返回 -1） int idx = list.indexOf("A")
lastIndexOf(Object o) 返回元素最后一次出现的索引 int lastIdx = list.lastIndexOf("A")
iterator() 返回普通迭代器（单向遍历） Iterator it = list.iterator()
listIterator() 返回 ListIterator（支持双向遍历和修改） ListIterator lit = list.listIterator()
listIterator(int index) 从指定索引返回 ListIterator ListIterator lit = list.listIterator(1)
3. 子列表与转换
方法 描述 示例
subList(int from, int to) 截取子列表（左闭右开） List sub = list.subList(1, 3)
toArray() 转换为数组 Object[] arr = list.toArray()
toArray(T[] a) 转换为指定类型的数组 String[] arr = list.toArray(new String[0])
4. 排序与比较
方法 描述 示例
sort(Comparator c) 根据比较器排序 list.sort(Comparator.naturalOrder())
equals(Object o) 比较两个列表的元素是否相同（顺序敏感） list1.equals(list2)
5. Java 8+ Stream 操作
方法 描述 示例
stream() 返回顺序流 list.stream().filter(x -> x > 0)
parallelStream() 返回并行流 list.parallelStream().forEach(...)
6. 其他实用方法
方法 描述 示例
replaceAll(UnaryOperator o) 对所有元素执行操作并替换 list.replaceAll(s -> s.toUpperCase())
forEach(Consumer action) 遍历所有元素（Java 8+） list.forEach(System.out::println)

不同 List 实现的特性
方法 ArrayList LinkedList Vector
随机访问 快（O(1)） 慢（O(n)） 快（O(1)）
插入/删除（首尾） 慢（O(n)） 快（O(1)） 慢（O(n)）
线程安全 不安全 不安全 安全（同步）
总结
• 增删改查：add/remove/set/get  
• 遍历：iterator/forEach/ListIterator  
• 工具类操作：sort/subList/toArray  
• Java 8+：stream/parallelStream/replaceAll  
根据需求选择合适的 List 实现类（如高频随机访问用 ArrayList，频繁插入删除用 LinkedList）。*/

public class ListMethodTest {
	
	public static void main(String[] args) {
		
	}
}
