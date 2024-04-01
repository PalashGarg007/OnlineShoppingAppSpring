package com.genpact.onlineShoppingApp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.genpact.onlineShoppingApp.entity.Customer;
import com.genpact.onlineShoppingApp.entity.Payment;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;
import com.genpact.onlineShoppingApp.exception.InvalidSQLQueryException;

public interface AdminController {
	/*Display 5(default) customers at a time of given page and
	 * Returns true if their is something to display.*/
	ResponseEntity<List<Customer>> getCustomers(Integer pageNumber) throws IOException;
	
	/*Display 5(default) shopkeeper at a time of given page and
	 * Returns true if their is something to display.*/
	ResponseEntity<List<Shopkeeper>> getShopkeepers(Integer pageNumber) throws IOException;
	
	/*Add new Payment method*/
	ResponseEntity<Payment> addNewPayment(Payment payment) throws InvalidSQLQueryException, IOException;
	
	/*Display 5(default) payment at a time of given page*/
	ResponseEntity<List<Payment>> getPayments(Integer pageNumber) throws IOException;
	
	/*Update the discount by taking the payment_id*/
	ResponseEntity<Payment> updateDiscountById(Payment payment) throws IOException;
	
	/*Remove an Payment with it's id*/
	ResponseEntity<Payment> removePaymentById(Integer id) throws IOException;
}
