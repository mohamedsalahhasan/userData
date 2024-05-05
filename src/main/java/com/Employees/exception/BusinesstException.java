package com.Employees.exception;

import org.springframework.http.HttpStatus;

public class BusinesstException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String message;
	private HttpStatus httpStatus;

	public BusinesstException(String message) {
		this.message = message;
	}

	public BusinesstException(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
