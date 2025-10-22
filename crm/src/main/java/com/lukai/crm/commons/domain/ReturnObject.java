package com.lukai.crm.commons.domain;

import java.util.Objects;

public class ReturnObject {
	private String message;
	private String code;
	private Object resultData;
	
	
	
	public ReturnObject() {
		super();
	}



	public ReturnObject(String message, String code, Object resultData) {
		super();
		this.message = message;
		this.code = code;
		this.resultData = resultData;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public Object getResultData() {
		return resultData;
	}



	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}



	@Override
	public int hashCode() {
		return Objects.hash(code, message, resultData);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReturnObject other = (ReturnObject) obj;
		return Objects.equals(code, other.code) && Objects.equals(message, other.message)
				&& Objects.equals(resultData, other.resultData);
	}
	
	
	
}
