package com.mesoft.nowshuttle.webapi.om;

public class TransferStatusRequest {

	private String userId;
	private String shuttleId;
	private String state;
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getShuttleId() {
		return shuttleId;
	}

	public void setShuttleId(String shuttleId) {
		this.shuttleId = shuttleId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
