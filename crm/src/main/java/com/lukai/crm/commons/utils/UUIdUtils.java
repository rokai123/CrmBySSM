package com.lukai.crm.commons.utils;

import java.util.UUID;

public class UUIdUtils {
	public static String getUUId() {
		return UUID.randomUUID().toString().replaceAll("-","");
		
	}
}
