package com.battle.exception;

import org.springframework.http.HttpStatus;

public class ErrorInfo {

	private String message;
	private HttpStatus status;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	public ErrorInfo() {
		super();
	}
	public ErrorInfo(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}
	@Override
	public String toString() {
		return "ErrorInfo [message=" + message + ", status=" + status + "]";
	}
	
	
}
