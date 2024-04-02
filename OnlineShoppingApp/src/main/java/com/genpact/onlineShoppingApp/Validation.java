package com.genpact.onlineShoppingApp;

import org.springframework.stereotype.Component;

@Component
public class Validation {
	
	public Boolean nameValidation(String name) {
		return name.matches("^[a-zA-Z]+(?: [a-zA-Z]+)?$");
	}
	
	public Boolean contactValidation(String contact) {
		return contact.matches("^\\d{10}$");
	}
	
	public Boolean emailValidation(String email) {
		return email.matches("""
                ^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@\
                [^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$\
                """);
	}
	
	public Boolean passwordValidation(String password) {
		return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
	}
	
	public Boolean costValidition(Double cost) {
		return (cost>0);
	}
	
	public Boolean warehouseValidation(Integer warehouse) {
		return (warehouse>=0);
	}
	
	public Boolean discountValidation(Double discount) {
		return String.valueOf(discount).matches("^(\\d{1,2})((\\.\\d{1,})?)$");
	}

}
