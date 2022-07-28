package com.anstech.speechtotext.model;

public class Response {

	private String message;
	private String description;
	private String statusCode;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	@Override
	public String toString() {
		return "Response [message=" + message + ", description=" + description + ", statusCode=" + statusCode + "]";
	}
	
	
	
}
