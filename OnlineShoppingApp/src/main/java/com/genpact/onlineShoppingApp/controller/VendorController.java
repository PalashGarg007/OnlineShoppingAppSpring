package com.genpact.onlineShoppingApp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.genpact.onlineShoppingApp.dto.UnacceptedOrders;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;

public interface VendorController {
	
	/*Create new account by taking required details*/
	ResponseEntity<Shopkeeper> createAccount(Shopkeeper shopkeeper) throws IOException;
	
	/*Login by taking userName and password*/
	ResponseEntity<Shopkeeper> shopkeeperLogin(Shopkeeper logInDetails) throws IOException;
	
	/*Add a new product.*/
	ResponseEntity<Product> addAndUpdateProduct(Product product) throws IOException;
	
	/*Display all products the shopkeeper has default(5) at a time.*/
	ResponseEntity<List<Product>> getProducts(Integer pageNumber);
	
	/*Change the personal information*/
	ResponseEntity<Shopkeeper> changePersonalInformadtion(Shopkeeper shopkeeper);
	
	/*View first 5 un-accepted orders*/
	ResponseEntity<List<UnacceptedOrders>> getUnacceptedOrders(Integer pageNumber);
	
	/*set UnacceptedOrders to true or false.*/
	ResponseEntity<String> setUnacceptedOrders(List<UnacceptedOrders> unacceptedOrders);
	
	/*Search products by name or category or brand*/
	ResponseEntity<List<Product>> searchProducts(String condition);
	
	/*add and update products by file*/
	ResponseEntity<String> addAndUpdateProductsByFile() throws IOException;
}
