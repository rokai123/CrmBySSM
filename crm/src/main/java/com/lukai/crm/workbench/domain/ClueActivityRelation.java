package com.lukai.crm.workbench.domain;

import java.util.Objects;

public class ClueActivityRelation {
	String id;
	String clueId;
	String activityId;
	
	
	
	public ClueActivityRelation() {
		super();
	}



	public ClueActivityRelation(String id, String clueId, String activityId) {
		super();
		this.id = id;
		this.clueId = clueId;
		this.activityId = activityId;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getClueId() {
		return clueId;
	}



	public void setClueId(String clueId) {
		this.clueId = clueId;
	}



	public String getActivityId() {
		return activityId;
	}



	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}



	@Override
	public int hashCode() {
		return Objects.hash(activityId, clueId, id);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClueActivityRelation other = (ClueActivityRelation) obj;
		return Objects.equals(activityId, other.activityId) && Objects.equals(clueId, other.clueId)
				&& Objects.equals(id, other.id);
	}



	@Override
	public String toString() {
		return "ClueActivityRelation [id=" + id + ", clueId=" + clueId + ", activityId=" + activityId + "]";
	}
	
	
	
}
