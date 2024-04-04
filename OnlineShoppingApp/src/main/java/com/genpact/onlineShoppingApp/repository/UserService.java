package com.genpact.onlineShoppingApp.repository;

import java.util.List;
import java.util.Map;

import com.genpact.onlineShoppingApp.entity.Customer;
import com.genpact.onlineShoppingApp.entity.Product;

public interface UserService {

	Map<Integer, String> getProducts();

	Customer createAccount(Customer customer);

	Customer customerLogin(String userName, String password);

	Customer cahngePersonalInformadtion(String contact, String email, String address, String password);

	List<Product> searchProducts(String tokens);

	Integer addToFavorite(Integer pid);

	List<Product> getFavorite(Integer pageNo, Integer pageSize);

	Integer removeFromFavorite(Integer pid);

	Integer addToCart(Integer pid, Integer quantity);

	List<Product> getCart(Integer pageNo, Integer pAGE_SIZE);

	Integer removeFromCart(Integer pid);

	Integer buyCart(Integer payId);

}
