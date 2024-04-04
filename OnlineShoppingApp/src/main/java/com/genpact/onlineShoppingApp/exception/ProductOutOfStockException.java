package com.genpact.onlineShoppingApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Insufficient Products in the warehouse.")
public class ProductOutOfStockException extends RuntimeException{
	private static final long serialVersionUID = 4781265006237583170L;
	
	public ProductOutOfStockException(String message) {
		super(message);
	}
	
	public ProductOutOfStockException(String message, Throwable th) {
		super(message, th);
	}
}
