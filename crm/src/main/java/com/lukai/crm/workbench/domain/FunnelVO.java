package com.lukai.crm.workbench.domain;

import java.util.Objects;

public class FunnelVO {
	private String name;
	private Integer value;
	
	
	public FunnelVO() {
		super();
	}


	public FunnelVO(String name, Integer value) {
		super();
		this.name = name;
		this.value = value;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getValue() {
		return value;
	}


	public void setValue(Integer value) {
		this.value = value;
	}


	@Override
	public int hashCode() {
		return Objects.hash(name, value);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FunnelVO other = (FunnelVO) obj;
		return Objects.equals(name, other.name) && Objects.equals(value, other.value);
	}


	@Override
	public String toString() {
		return "FunnelVO [name=" + name + ", value=" + value + "]";
	}
	
	
}
