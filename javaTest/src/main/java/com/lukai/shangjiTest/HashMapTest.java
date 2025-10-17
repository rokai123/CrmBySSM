package com.lukai.shangjiTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HashMapTest {
	public static void main(String[] args) {
		Map<String, Integer> hashMap = new HashMap<String, Integer>();
		hashMap.put("order_000278_34_10", 2);
		hashMap.put("order_000483_68_320",1);
		hashMap.put("order_000973_73_70", 4);
		ArrayList<String> idsList = new ArrayList<String>();
		idsList.add("0004");
		orderDetailPrint(hashMap, 2, idsList);
	}
	
	public static void orderDetailPrint(Map<String, Integer> orderMap,int discountType,List<String> idsList) {
		//获取下单次数
		int orderCount = orderMap.size();
		Set<Entry<String, Integer>> entrySet = orderMap.entrySet();
		ArrayList<String> resultList = new ArrayList<String>();
		entrySet.forEach(e->{
			String key = e.getKey();
			String[] split = key.split("_");
			String order = split[0];
			String orderNum = split[1];
			//String转换成Integer
			int goodsId = Integer.parseInt(split[2]);
			int price = Integer.parseInt(split[3]);
			
			//值为0时，对商品id为奇数的商品，一律半价优惠。
			if (discountType==0) {
				if (goodsId%2!=0) {
					Integer shuLiang = e.getValue();
					int disPri = price/2;
					int zongJia = shuLiang*disPri;
					resultList.add("商品id["+goodsId+"]:"+"小计"+zongJia+"元("+shuLiang+")个");
					//System.out.println(resultList);
				}else{
					resultList.add("商品id["+goodsId+"]:"+"小计"+e.getValue()*price+"元("+e.getValue()+")个");
				}
				
			}else if (discountType==1) {
				if (goodsId%2==0) {
					Integer shuLiang = e.getValue();
					Double disPri =price*0.8;
					Double zongJia = shuLiang*disPri;
					resultList.add("商品id["+goodsId+"]:"+"小计"+zongJia+"元("+shuLiang+")个");
				}else {
					resultList.add("商品id["+goodsId+"]:"+"小计"+e.getValue()*price+"元("+e.getValue()+")个");
				}
				
			}else if (discountType==2) {
				idsList.forEach(s->{
					
					if (orderNum.contains(s)) {
						Integer shuLiang = e.getValue();
						Double disPri =(double) (price-20);
						Double zongJia = shuLiang*disPri;
						resultList.add("商品id["+goodsId+"]:"+"小计"+zongJia+"元("+shuLiang+")个");
					}else {
						resultList.add("商品id["+goodsId+"]:"+"小计"+e.getValue()*price+"元("+e.getValue()+")个");
					}
					
				});
				
			}
			
			
		});
		Set<Entry<String, Integer>> entrySet2 = orderMap.entrySet();
		ArrayList<String> danhao = new ArrayList<String>();
		entrySet2.forEach(e->{
			String danhaostr = e.getKey().split("_")[1];
			danhao.add(danhaostr);
		});
		double zonge=0;
		System.out.println("欢迎惠顾，您共下单"+orderCount+"次、单号分别为"+danhao);
		System.out.println("订单汇总：");
		resultList.forEach(s->{
			System.out.println(s);
			int indexOf = s.indexOf("小计");
			
		});
		System.out.println("总计金额"+zonge);
		System.out.println("优惠后金额：");
		
		
	}
}
