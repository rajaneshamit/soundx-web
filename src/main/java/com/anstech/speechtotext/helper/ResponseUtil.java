package com.anstech.speechtotext.helper;

public class ResponseUtil {
	private String message;
	private String status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ResponseUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseUtil(String message,String status) {
		super();
		this.message = message;
		this.status = status;
	}

}
