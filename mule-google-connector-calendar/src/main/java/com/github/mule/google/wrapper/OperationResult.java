package com.github.mule.google.wrapper;

import com.google.api.client.json.GenericJson;

public class OperationResult extends GenericJson {

	private String message = "";
	private boolean success = false;
	private String id = "";
	
	public OperationResult() {
		
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
