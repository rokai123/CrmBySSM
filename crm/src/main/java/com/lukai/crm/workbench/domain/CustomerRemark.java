package com.lukai.crm.workbench.domain;

import java.util.Objects;

public class CustomerRemark {
	private String id;
	private String noteContent;
	private String createBy;
	private String createTime;
	private String editBy;
	private String editTime;
	private String editFlag;
	private String customerId;
	
	public CustomerRemark() {
		super();
	}
	public CustomerRemark(String id, String noteContent, String createBy, String createTime, String editBy,
			String editTime, String editFlag, String customerId) {
		super();
		this.id = id;
		this.noteContent = noteContent;
		this.createBy = createBy;
		this.createTime = createTime;
		this.editBy = editBy;
		this.editTime = editTime;
		this.editFlag = editFlag;
		this.customerId = customerId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
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
	public String getEditFlag() {
		return editFlag;
	}
	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(createBy, createTime, customerId, editBy, editFlag, editTime, id, noteContent);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerRemark other = (CustomerRemark) obj;
		return Objects.equals(createBy, other.createBy) && Objects.equals(createTime, other.createTime)
				&& Objects.equals(customerId, other.customerId) && Objects.equals(editBy, other.editBy)
				&& Objects.equals(editFlag, other.editFlag) && Objects.equals(editTime, other.editTime)
				&& Objects.equals(id, other.id) && Objects.equals(noteContent, other.noteContent);
	}
	@Override
	public String toString() {
		return "CustomerRemark [id=" + id + ", noteContent=" + noteContent + ", createBy=" + createBy + ", createTime="
				+ createTime + ", editBy=" + editBy + ", editTime=" + editTime + ", editFlag=" + editFlag
				+ ", customerId=" + customerId + "]";
	}
	
	
}
