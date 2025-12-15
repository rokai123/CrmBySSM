package com.lukai.crm.workbench.domain;

import java.util.Objects;

public class ContactsActivityRelation {
	private String id;
	private String contactsId;
	private String activityId;
	
	
	
	public ContactsActivityRelation() {
		super();
	}



	public ContactsActivityRelation(String id, String contactsId, String activityId) {
		super();
		this.id = id;
		this.contactsId = contactsId;
		this.activityId = activityId;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getContactsId() {
		return contactsId;
	}



	public void setContactsId(String contactsId) {
		this.contactsId = contactsId;
	}



	public String getActivityId() {
		return activityId;
	}



	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}



	@Override
	public int hashCode() {
		return Objects.hash(activityId, contactsId, id);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactsActivityRelation other = (ContactsActivityRelation) obj;
		return Objects.equals(activityId, other.activityId) && Objects.equals(contactsId, other.contactsId)
				&& Objects.equals(id, other.id);
	}



	@Override
	public String toString() {
		return "ContactsActivityRelation [id=" + id + ", contactsId=" + contactsId + ", activityId=" + activityId + "]";
	}
	
	
	
	
	
}
