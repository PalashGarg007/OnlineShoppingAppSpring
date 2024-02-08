package com.genpact.onlineShoppingApp.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.genpact.onlineShoppingApp.entity.Shopkeeper;

public interface VendorService {
	
	/*Create new account by taking required details*/
	ResponseEntity<String> createAccount(Shopkeeper shopkeeper) throws IOException;
	
	/*Login by taking userName and password*/
	ResponseEntity<String> shopkeeperLogin(String logInDetails) throws IOException;
	
	/*Add a new product.*/
	ResponseEntity<String> addNewProduct(String productString) throws IOException;
	
	/*Display all products the shopkeeper has default(5) at a time.*/
	Boolean getProducts(Integer pageNumber);
	
	/*Change the personal information*/
	void changePersonalInformadtion();
	
	void setUnacceptedOrders();
	
	Double totalRevinue();
	
}
