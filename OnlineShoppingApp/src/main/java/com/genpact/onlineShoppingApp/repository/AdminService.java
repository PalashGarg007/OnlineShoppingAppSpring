package com.genpact.onlineShoppingApp.repository;

import org.springframework.data.domain.Page;

import com.genpact.onlineShoppingApp.entity.Customer;
import com.genpact.onlineShoppingApp.entity.Payment;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;
import com.genpact.onlineShoppingApp.exception.InvalidSQLQueryException;

public interface AdminService {
	/*List of all the customers.*/
	Page<Customer> getCustomers(Integer pageNumber, Integer pageSize);
	
	/*List of all the shopkeeper.*/
	Page<Shopkeeper> getShopkeepers(Integer pageNumber, Integer pageSize);
	
	/*Add new payment method*/
	Payment addNewPayment(Payment payment) throws InvalidSQLQueryException;
	
	/*List of all the payment method.*/
	Page<Payment> getPayments(Integer pageNumber, Integer pageSize);
	
	/*Update discount by id*/
	Payment updateDiscountById(Payment payment);
	
}
