package com.lukai.crm.workbench.domain;

import java.util.Objects;

public class Tran {
	private String id;
	private String owner;
	private String money;
	private String name;
	private String expectedDate;
	private String customerId;
	private String stage;
	private String type;
	private String source;
	private String activityId;
	private String contactsId;
	private String createBy;
	private String createTime;
	private String editBy;
	private String editTime;
	private String description;
	private String contactSummary;
	private String nextContactTime;
	
	
	
	public Tran() {
		super();
	}



	public Tran(String id, String owner, String money, String name, String expectedDate, String customerId,
			String stage, String type, String source, String activityId, String contactsId, String createBy,
			String createTime, String editBy, String editTime, String description, String contactSummary,
			String nextContactTime) {
		super();
		this.id = id;
		this.owner = owner;
		this.money = money;
		this.name = name;
		this.expectedDate = expectedDate;
		this.customerId = customerId;
		this.stage = stage;
		this.type = type;
		this.source = source;
		this.activityId = activityId;
		this.contactsId = contactsId;
		this.createBy = createBy;
		this.createTime = createTime;
		this.editBy = editBy;
		this.editTime = editTime;
		this.description = description;
		this.contactSummary = contactSummary;
		this.nextContactTime = nextContactTime;
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



	public String getMoney() {
		return money;
	}



	public void setMoney(String money) {
		this.money = money;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getExpectedDate() {
		return expectedDate;
	}



	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}



	public String getCustomerId() {
		return customerId;
	}



	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}



	public String getStage() {
		return stage;
	}



	public void setStage(String stage) {
		this.stage = stage;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getSource() {
		return source;
	}



	public void setSource(String source) {
		this.source = source;
	}



	public String getActivityId() {
		return activityId;
	}



	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}



	public String getContactsId() {
		return contactsId;
	}



	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
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



	@Override
	public int hashCode() {
		return Objects.hash(activityId, contactSummary, contactsId, createBy, createTime, customerId, description,
				editBy, editTime, expectedDate, id, money, name, nextContactTime, owner, source, stage, type);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tran other = (Tran) obj;
		return Objects.equals(activityId, other.activityId) && Objects.equals(contactSummary, other.contactSummary)
				&& Objects.equals(contactsId, other.contactsId) && Objects.equals(createBy, other.createBy)
				&& Objects.equals(createTime, other.createTime) && Objects.equals(customerId, other.customerId)
				&& Objects.equals(description, other.description) && Objects.equals(editBy, other.editBy)
				&& Objects.equals(editTime, other.editTime) && Objects.equals(expectedDate, other.expectedDate)
				&& Objects.equals(id, other.id) && Objects.equals(money, other.money)
				&& Objects.equals(name, other.name) && Objects.equals(nextContactTime, other.nextContactTime)
				&& Objects.equals(owner, other.owner) && Objects.equals(source, other.source)
				&& Objects.equals(stage, other.stage) && Objects.equals(type, other.type);
	}



	@Override
	public String toString() {
		return "Tran [id=" + id + ", owner=" + owner + ", money=" + money + ", name=" + name + ", expectedDate="
				+ expectedDate + ", customerId=" + customerId + ", stage=" + stage + ", type=" + type + ", source="
				+ source + ", activityId=" + activityId + ", contactsId=" + contactsId + ", createBy=" + createBy
				+ ", createTime=" + createTime + ", editBy=" + editBy + ", editTime=" + editTime + ", description="
				+ description + ", contactSummary=" + contactSummary + ", nextContactTime=" + nextContactTime + "]";
	}
	
	
	
}
