package com.fsd;

public class AppResponse {
	boolean success;
	Object data;
	String message;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public AppResponse() {
		
	}
	
	public AppResponse(boolean success, Object data, String message) {
		super();
		this.success = success;
		this.data = data;
		this.message = message;
	}
}
