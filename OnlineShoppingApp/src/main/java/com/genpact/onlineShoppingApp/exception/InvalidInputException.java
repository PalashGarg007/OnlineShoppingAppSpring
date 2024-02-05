package com.genpact.onlineShoppingApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidInputException extends RuntimeException{
	private static final long serialVersionUID = 8027221301500997190L;

	public InvalidInputException(String message) {
		super(message);
	}
	
	public InvalidInputException(String message, Throwable th) {
		super(message, th);
	}
}
