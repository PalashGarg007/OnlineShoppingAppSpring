package com.genpact.onlineShoppingApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidSQLQueryException.class)
	@ResponseBody
	public ResponseEntity<String> handleInvalidSQLException(InvalidSQLQueryException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body("[\"Error\": \""+ e.getMessage()+ "\"]");
	}
	
	@ExceptionHandler(InvalidInputException.class)
	@ResponseBody
	public ResponseEntity<String> handleInvalidInputException(InvalidInputException e) {
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.body("[\"Error\": \""+ e.getMessage()+ "\"]");
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	public ResponseEntity<String> handleResourcedNotFoundException(ResourceNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("[\"Error\": \""+ e.getMessage() + "\"]");
	}
	
	@ExceptionHandler(ProductOutOfStockException.class)
	@ResponseBody
	public ResponseEntity<String> handleProductOutOfStockException(ProductOutOfStockException e){
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
				.body("[\"Error\": \""+ e.getMessage() + "\"]");
	}
}

// exception mapper
// web application application -> restful