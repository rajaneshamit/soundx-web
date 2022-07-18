package com.anstech.speechtotext.payload;

import com.anstech.speechtotext.model.UserResponse;

public class ApiResponse {
	private Boolean success;
	private String message;
	private UserResponse user;

	public ApiResponse(Boolean success, String message, UserResponse user) {
		super();
		this.success = success;
		this.message = message;
		this.user = user;
	}

	public ApiResponse(Boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public ApiResponse(String message) {
		super();
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}

}
