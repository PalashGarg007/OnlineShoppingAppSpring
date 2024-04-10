package com.genpact.onlineShoppingApp.repository;

import java.util.List;
import java.util.Map;

import com.genpact.onlineShoppingApp.dto.ReviewWithoutOrderId;
import com.genpact.onlineShoppingApp.dto.TranscriptDto;
import com.genpact.onlineShoppingApp.entity.Customer;
import com.genpact.onlineShoppingApp.entity.Orders;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.entity.Review;

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

	List<Orders> getOrderHistory(Integer pageNo, Integer pageSize);

	List<ReviewWithoutOrderId> getReviewOfProduct(Integer pid, Integer pageNo, Integer pageSize);

	Integer giveReview(Review review);

	TranscriptDto downloadTranscript(Integer oid);

}
