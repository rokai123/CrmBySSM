package com.lukai.crm.workbench.domain;

import java.util.Objects;

public class Contacts { 
    private String id;
    private String owner;
    private String source;
    private String customerId;
    private String fullname;
    private String appellation;
    private String email;
    private String mphone;
    private String job;
    private String createBy;
    private String createTime;
    private String editBy;
    private String editTime;
    private String description;
    private String contactSummary;
    private String nextContactTime;
    private String address;
    
    
    
	public Contacts() {
		super();
	}



	public Contacts(String id, String owner, String source, String customerId, String fullname, String appellation,
			String email, String mphone, String job, String createBy, String createTime, String editBy, String editTime,
			String description, String contactSummary, String nextContactTime, String address) {
		super();
		this.id = id;
		this.owner = owner;
		this.source = source;
		this.customerId = customerId;
		this.fullname = fullname;
		this.appellation = appellation;
		this.email = email;
		this.mphone = mphone;
		this.job = job;
		this.createBy = createBy;
		this.createTime = createTime;
		this.editBy = editBy;
		this.editTime = editTime;
		this.description = description;
		this.contactSummary = contactSummary;
		this.nextContactTime = nextContactTime;
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



	public String getSource() {
		return source;
	}



	public void setSource(String source) {
		this.source = source;
	}



	public String getCustomerId() {
		return customerId;
	}



	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}



	public String getFullname() {
		return fullname;
	}



	public void setFullname(String fullname) {
		this.fullname = fullname;
	}



	public String getAppellation() {
		return appellation;
	}



	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getMphone() {
		return mphone;
	}



	public void setMphone(String mphone) {
		this.mphone = mphone;
	}



	public String getJob() {
		return job;
	}



	public void setJob(String job) {
		this.job = job;
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



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
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



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	@Override
	public int hashCode() {
		return Objects.hash(address, appellation, contactSummary, createBy, createTime, customerId, description, editBy,
				editTime, email, fullname, id, job, mphone, nextContactTime, owner, source);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacts other = (Contacts) obj;
		return Objects.equals(address, other.address) && Objects.equals(appellation, other.appellation)
				&& Objects.equals(contactSummary, other.contactSummary) && Objects.equals(createBy, other.createBy)
				&& Objects.equals(createTime, other.createTime) && Objects.equals(customerId, other.customerId)
				&& Objects.equals(description, other.description) && Objects.equals(editBy, other.editBy)
				&& Objects.equals(editTime, other.editTime) && Objects.equals(email, other.email)
				&& Objects.equals(fullname, other.fullname) && Objects.equals(id, other.id)
				&& Objects.equals(job, other.job) && Objects.equals(mphone, other.mphone)
				&& Objects.equals(nextContactTime, other.nextContactTime) && Objects.equals(owner, other.owner)
				&& Objects.equals(source, other.source);
	}



	@Override
	public String toString() {
		return "Contacts [id=" + id + ", owner=" + owner + ", source=" + source + ", customerId=" + customerId
				+ ", fullname=" + fullname + ", appellation=" + appellation + ", email=" + email + ", mphone=" + mphone
				+ ", job=" + job + ", createBy=" + createBy + ", createTime=" + createTime + ", editBy=" + editBy
				+ ", editTime=" + editTime + ", description=" + description + ", contactSummary=" + contactSummary
				+ ", nextContactTime=" + nextContactTime + ", address=" + address + "]";
	}
    
    
}
