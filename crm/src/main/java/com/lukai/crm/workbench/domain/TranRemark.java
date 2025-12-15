package com.lukai.crm.workbench.domain;

import java.util.Objects;

public class TranRemark {
	private String id;
	private String noteContent;
	private String createBy;
	private String createTime;	
	private String editBy;	
	private String editTime;	
	private String editFlag;
	private String tranId;
	
	
	
	public TranRemark() {
		super();
	}



	public TranRemark(String id, String noteContent, String createBy, String createTime, String editBy, String editTime,
			String editFlag, String tranId) {
		super();
		this.id = id;
		this.noteContent = noteContent;
		this.createBy = createBy;
		this.createTime = createTime;
		this.editBy = editBy;
		this.editTime = editTime;
		this.editFlag = editFlag;
		this.tranId = tranId;
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



	public String getTranId() {
		return tranId;
	}



	public void setTranId(String tranId) {
		this.tranId = tranId;
	}



	@Override
	public int hashCode() {
		return Objects.hash(createBy, createTime, editBy, editFlag, editTime, id, noteContent, tranId);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TranRemark other = (TranRemark) obj;
		return Objects.equals(createBy, other.createBy) && Objects.equals(createTime, other.createTime)
				&& Objects.equals(editBy, other.editBy) && Objects.equals(editFlag, other.editFlag)
				&& Objects.equals(editTime, other.editTime) && Objects.equals(id, other.id)
				&& Objects.equals(noteContent, other.noteContent) && Objects.equals(tranId, other.tranId);
	}



	@Override
	public String toString() {
		return "TranRemark [id=" + id + ", noteContent=" + noteContent + ", createBy=" + createBy + ", createTime="
				+ createTime + ", editBy=" + editBy + ", editTime=" + editTime + ", editFlag=" + editFlag + ", tranId="
				+ tranId + "]";
	}
	
	
	
}
