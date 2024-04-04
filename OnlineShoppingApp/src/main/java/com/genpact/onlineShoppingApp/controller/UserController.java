package com.genpact.onlineShoppingApp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.genpact.onlineShoppingApp.entity.Customer;
import com.genpact.onlineShoppingApp.entity.Product;

public interface UserController {

	void placeOrder2(Integer[] orders);

	void placeOrder1(Integer[] orders);

	void placeOrder3(Integer[] orders);

	//create account
	ResponseEntity<Customer> createAccount(Customer customer);
	
	//log in
	ResponseEntity<Customer> customerLogin(Customer customer);
	
	//edit personal information
	ResponseEntity<Customer> changePersonalInformation(Customer customer);
	
	//search product
	ResponseEntity<List<Product>> searchProducts(String tokens);
	
	//get all the favs.
	ResponseEntity<List<Product>> getFavorite(Integer pageNo);
	
	//add to fav.
	ResponseEntity<String> addToFavorite(Integer pid);
	
	//remove from fav.
	ResponseEntity<String> removeFromFavorite(Integer pid);
	
	//get all the items in the cart
	ResponseEntity<List<Product>> getCart(Integer pageNo);
	
	//add to cart
	ResponseEntity<String> addToCart(Integer pid, Integer quantity); 
	
	//remove from cart
	ResponseEntity<String> removeFromCart(Integer pid);
	
	//buy cart
	ResponseEntity<String> buyCart(Integer payId);
	
	//order history
	
	//track order
	
	//download transcript
	
	
}
