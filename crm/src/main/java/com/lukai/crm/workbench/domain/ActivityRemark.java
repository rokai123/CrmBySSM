package com.lukai.crm.workbench.domain;

import java.util.Objects;

public class ActivityRemark {
    
    private String id;
    
    private String noteContent;

    private String createTime;
   
    private String createBy;
    
    private String editTime;
    
    private String editBy;
   
    private String editFlag;
   
    private String activityId;

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getEditTime() {
		return editTime;
	}

	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}

	public String getEditBy() {
		return editBy;
	}

	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}

	public String getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(activityId, createBy, createTime, editBy, editFlag, editTime, id, noteContent);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivityRemark other = (ActivityRemark) obj;
		return Objects.equals(activityId, other.activityId) && Objects.equals(createBy, other.createBy)
				&& Objects.equals(createTime, other.createTime) && Objects.equals(editBy, other.editBy)
				&& Objects.equals(editFlag, other.editFlag) && Objects.equals(editTime, other.editTime)
				&& Objects.equals(id, other.id) && Objects.equals(noteContent, other.noteContent);
	}

	@Override
	public String toString() {
		return "ActivityRemark [id=" + id + ", noteContent=" + noteContent + ", createTime=" + createTime
				+ ", createBy=" + createBy + ", editTime=" + editTime + ", editBy=" + editBy + ", editFlag=" + editFlag
				+ ", activityId=" + activityId + "]";
	}
    
    
}