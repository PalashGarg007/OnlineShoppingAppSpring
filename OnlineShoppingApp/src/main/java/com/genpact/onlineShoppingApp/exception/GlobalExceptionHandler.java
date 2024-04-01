package com.genpact.onlineShoppingApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidSQLQueryException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.CONFLICT)
	public ResponseEntity<String> handleInvalidSQLException(InvalidSQLQueryException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body("[\"Error\": \""+ e.getMessage()+ "\"]");
	}
	
	@ExceptionHandler(InvalidInputException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	public ResponseEntity<String> handleInvalidInputException(InvalidInputException e) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body("[\"Error\": \""+ e.getMessage()+ "\"]");
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleResourcedNotFoundException(ResourceNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("[\"Error\": \""+ e.getMessage() + "\"]");
	}
	
}

// exception mapper
// web application application -> restful