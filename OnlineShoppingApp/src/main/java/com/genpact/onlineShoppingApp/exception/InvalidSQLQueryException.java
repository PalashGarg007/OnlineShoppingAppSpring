package com.genpact.onlineShoppingApp.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
@ResponseBody
public class InvalidSQLQueryException extends SQLIntegrityConstraintViolationException{
	private static final long serialVersionUID = -6550997570838895589L;

	public InvalidSQLQueryException(String message) {
		super(message);
	}
	
	public InvalidSQLQueryException(String message, Throwable th) {
		super(message, th);
	}
}
