package com.genpact.onlineShoppingApp.service;

public interface VendorService {
	
	/*Create new account by taking required details*/
	void createAccount();
	
	/*Login by taking userName and password*/
	void shopkeeperLogin();
	
	/*Add a new product.*/
	void addNewProduct();
	
	/*Display all products the shopkeeper has default(5) at a time.*/
	Boolean getProducts(Integer pageNumber);
	
	/*Change the personal information*/
	void changePersonalInformadtion();
	
	void setUnacceptedOrders();
	
}
