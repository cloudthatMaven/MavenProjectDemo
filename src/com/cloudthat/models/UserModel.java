package com.cloudthat.models;

public class UserModel {
	private String firstName, lastName, userName, emailId, dataTime;

	// setters

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setDateTime(String dateTime) {
		this.dataTime = dateTime;
	}
	// getters

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public String getDateTime() {
		return this.dataTime;
	}

}
