package com.lukai.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	//日付形式をカプセル化する
	public static String formateDateTime(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = simpleDateFormat.format(date);
		
		return format;
	}
	
	public static String formateDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String format = simpleDateFormat.format(date);
		
		return format;
	}
}
