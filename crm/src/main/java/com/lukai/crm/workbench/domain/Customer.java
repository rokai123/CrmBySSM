package com.lukai.crm.workbench.domain;

import java.util.Objects;

public class Customer {
	String id;
	String owner;
	String name;
	String website;
	String phone;
	String createBy;
	String createTime;
	String editBy;
	String editTime;
	String contactSummary;
	String nextContactTime;
	String description;
	String address;
	
	
	
	public Customer() {
		super();
	}



	public Customer(String id, String owner, String name, String website, String phone, String createBy,
			String createTime, String editBy, String editTime, String contactSummary, String nextContactTime,
			String description, String address) {
		super();
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.website = website;
		this.phone = phone;
		this.createBy = createBy;
		this.createTime = createTime;
		this.editBy = editBy;
		this.editTime = editTime;
		this.contactSummary = contactSummary;
		this.nextContactTime = nextContactTime;
		this.description = description;
		this.address = address;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getOwner() {
		return owner;
	}



	public void setOwner(String owner) {
		this.owner = owner;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getWebsite() {
		return website;
	}



	public void setWebsite(String website) {
		this.website = website;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getCreateBy() {
		return createBy;
	}



	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}



	public String getCreateTime() {
		return createTime;
	}



	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	public String getEditBy() {
		return editBy;
	}



	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}



	public String getEditTime() {
		return editTime;
	}



	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}



	public String getContactSummary() {
		return contactSummary;
	}



	public void setContactSummary(String contactSummary) {
		this.contactSummary = contactSummary;
	}



	public String getNextContactTime() {
		return nextContactTime;
	}



	public void setNextContactTime(String nextContactTime) {
		this.nextContactTime = nextContactTime;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	@Override
	public int hashCode() {
		return Objects.hash(address, contactSummary, createBy, createTime, description, editBy, editTime, id, name,
				nextContactTime, owner, phone, website);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(address, other.address) && Objects.equals(contactSummary, other.contactSummary)
				&& Objects.equals(createBy, other.createBy) && Objects.equals(createTime, other.createTime)
				&& Objects.equals(description, other.description) && Objects.equals(editBy, other.editBy)
				&& Objects.equals(editTime, other.editTime) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(nextContactTime, other.nextContactTime)
				&& Objects.equals(owner, other.owner) && Objects.equals(phone, other.phone)
				&& Objects.equals(website, other.website);
	}



	@Override
	public String toString() {
		return "Customer [id=" + id + ", owner=" + owner + ", name=" + name + ", website=" + website + ", phone="
				+ phone + ", createBy=" + createBy + ", createTime=" + createTime + ", editBy=" + editBy + ", editTime="
				+ editTime + ", contactSummary=" + contactSummary + ", nextContactTime=" + nextContactTime
				+ ", description=" + description + ", address=" + address + "]";
	}
	
	
	
	
	
	
	
	
	
	
	

}
