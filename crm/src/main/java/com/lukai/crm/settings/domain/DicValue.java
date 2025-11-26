package com.lukai.crm.settings.domain;

import java.util.Objects;

public class DicValue {
	String id;
	String value;
	String text;
	String typeCode;
	String orderNo;
	
	
	
	public DicValue() {
		super();
	}



	public DicValue(String id, String value, String text, String typeCode, String orderNo) {
		super();
		this.id = id;
		this.value = value;
		this.text = text;
		this.typeCode = typeCode;
		this.orderNo = orderNo;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}



	public String getTypeCode() {
		return typeCode;
	}



	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}



	public String getOrderNo() {
		return orderNo;
	}



	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}



	@Override
	public int hashCode() {
		return Objects.hash(id, orderNo, text, typeCode, value);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DicValue other = (DicValue) obj;
		return Objects.equals(id, other.id) && Objects.equals(orderNo, other.orderNo)
				&& Objects.equals(text, other.text) && Objects.equals(typeCode, other.typeCode)
				&& Objects.equals(value, other.value);
	}



	@Override
	public String toString() {
		return "DicValue [id=" + id + ", value=" + value + ", text=" + text + ", typeCode=" + typeCode + ", orderNo="
				+ orderNo + "]";
	}
	
	
}
