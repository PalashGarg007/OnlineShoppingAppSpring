package com.genpact.onlineShoppingApp.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import com.genpact.onlineShoppingApp.dto.UnacceptedOrders;
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
	Shopkeeper cahngePersonalInformadtion(String contact, String email, String password); 
	
	/*add new product to the Shop.*/
	Product addAndUpdateProduct(Product product);
	
	/*See the list of all the products he(vendor) is selling.*/
	Page<Product> getProducts(Integer pageNumber, Integer pageSize);
	
	/*should be able to accept orders.*/
	Page<UnacceptedOrders> getUnacceptedOrders(Integer pageNumber, Integer pageSize);
	void setUnacceptedOrders(List<UnacceptedOrders> unacceptedOrders);

	List<Product> inventoryList();

	/*should be able to search products by name or category.*/
	List<Product> searchProducts(String condition);
	
	/*should be able to add products by file.*/
	void addAndUpdateProductsByFile(Stream<String> tokens);
	
}
