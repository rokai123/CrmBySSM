package com.lukai.crm.workbench.domain;

import java.util.Objects;

public class ClueRemark {
	private String id;
	private String noteContent;
	private String createBy;
	private String createTime;
	private String editBy;
	private String editTime;
	private String editFlag;
	private String clueId;
	
	
	
	public ClueRemark() {
		super();
	}



	public ClueRemark(String id, String noteContent, String createBy, String createTime, String editBy, String editTime,
			String editFlag, String clueId) {
		super();
		this.id = id;
		this.noteContent = noteContent;
		this.createBy = createBy;
		this.createTime = createTime;
		this.editBy = editBy;
		this.editTime = editTime;
		this.editFlag = editFlag;
		this.clueId = clueId;
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



	public String getClueId() {
		return clueId;
	}



	public void setClueId(String clueId) {
		this.clueId = clueId;
	}



	@Override
	public int hashCode() {
		return Objects.hash(clueId, createBy, createTime, editBy, editFlag, editTime, id, noteContent);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClueRemark other = (ClueRemark) obj;
		return Objects.equals(clueId, other.clueId) && Objects.equals(createBy, other.createBy)
				&& Objects.equals(createTime, other.createTime) && Objects.equals(editBy, other.editBy)
				&& Objects.equals(editFlag, other.editFlag) && Objects.equals(editTime, other.editTime)
				&& Objects.equals(id, other.id) && Objects.equals(noteContent, other.noteContent);
	}



	@Override
	public String toString() {
		return "ClueRemark [id=" + id + ", noteContent=" + noteContent + ", createBy=" + createBy + ", createTime="
				+ createTime + ", editBy=" + editBy + ", editTime=" + editTime + ", editFlag=" + editFlag + ", clueId="
				+ clueId + "]";
	}
	
	
}
