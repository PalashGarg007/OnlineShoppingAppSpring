package com.genpact.onlineShoppingApp.repository;

import java.util.List;

import org.springframework.data.domain.Page;

import com.genpact.onlineShoppingApp.entity.Orders;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;

public interface VendorService {
	
	/*create account.*/
	Shopkeeper createAccount(Shopkeeper shopkeeper);
	
	/*shopkeeper login.*/
	Shopkeeper shopkeeperLogin(String userName, String password);
	
	/*show Personal Details.*/
	Shopkeeper getShopkeeper();
	
	/*should be able to change his details.*/
	Integer cahngePersonalInformadtion(String contact, String email, String password); 
	
	/*add new product to the Shop.*/
	Product addNewProduct(Product product);
	
	/*should be able to re-stock*/
	//TODO: create method re-stock.
	
	/*See the list of all the products he(vendor) is selling.*/
	Page<Product> getProducts(Integer pageNumber, Integer pageSize);
	
	/*should be able to accept orders.*/
	Page<Object> viewUnacceptedOrders(Integer pageNumber, Integer pageSize);
	Integer setUnacceptedOrders(Orders orders, Boolean isAccepted);

	List<Product> inventoryList();
	
	/*should be able to add products by file.*/
	//TODO: create method add products by file.
	
	/*should be able to search products by name or category.*/
	//TODO: create method search.
}
