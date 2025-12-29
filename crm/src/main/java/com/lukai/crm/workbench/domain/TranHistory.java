package com.lukai.crm.workbench.domain;

import java.util.Objects;

public class TranHistory {
	private String id;
	private String stage;
	private String money;
	private String expectedDate;
	private String createTime;
	private String createBy;
	private String tranId;
	
	
	
	public TranHistory() {
		super();
	}



	public TranHistory(String id, String stage, String money, String expectedDate, String createtime, String createBy,
			String tranId) {
		super();
		this.id = id;
		this.stage = stage;
		this.money = money;
		this.expectedDate = expectedDate;
		this.createTime = createtime;
		this.createBy = createBy;
		this.tranId = tranId;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getStage() {
		return stage;
	}



	public void setStage(String stage) {
		this.stage = stage;
	}



	public String getMoney() {
		return money;
	}



	public void setMoney(String money) {
		this.money = money;
	}



	public String getExpectedDate() {
		return expectedDate;
	}



	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}



	public String getCreateTime() {
		return createTime;
	}



	public void setCreateTime(String createtime) {
		this.createTime = createtime;
	}



	public String getCreateBy() {
		return createBy;
	}



	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}



	public String getTranId() {
		return tranId;
	}



	public void setTranId(String tranId) {
		this.tranId = tranId;
	}



	@Override
	public int hashCode() {
		return Objects.hash(createBy, createTime, expectedDate, id, money, stage, tranId);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TranHistory other = (TranHistory) obj;
		return Objects.equals(createBy, other.createBy) && Objects.equals(createTime, other.createTime)
				&& Objects.equals(expectedDate, other.expectedDate) && Objects.equals(id, other.id)
				&& Objects.equals(money, other.money) && Objects.equals(stage, other.stage)
				&& Objects.equals(tranId, other.tranId);
	}



	@Override
	public String toString() {
		return "TranHistory [id=" + id + ", stage=" + stage + ", money=" + money + ", expectedDate=" + expectedDate
				+ ", createtime=" + createTime + ", createBy=" + createBy + ", tranId=" + tranId + "]";
	}
	
	
}
