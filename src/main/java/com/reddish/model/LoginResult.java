package com.reddish.model;

public class LoginResult {
	
	private String error;
	
	private ReddishUser user;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public ReddishUser getUser() {
		return user;
	}
	public void setUser(ReddishUser user) {
		this.user = user;
	}

}
