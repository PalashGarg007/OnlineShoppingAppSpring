package com.genpact.onlineShoppingApp.service;

import java.io.IOException;
import org.springframework.http.ResponseEntity;

import com.genpact.onlineShoppingApp.entity.Payment;
import com.genpact.onlineShoppingApp.exception.InvalidSQLQueryException;

public interface AdminService {
	/*Display 5(default) customers at a time of given page and
	 * Returns true if their is something to display.*/
	ResponseEntity<String> getCustomers(Integer pageNumber) throws IOException;
	
	/*Display 5(default) shopkeeper at a time of given page and
	 * Returns true if their is something to display.*/
	ResponseEntity<String> getShopkeepers(Integer pageNumber) throws IOException;
	
	/*Add new Payment method*/
	ResponseEntity<Payment> addNewPayment(Payment payment) throws InvalidSQLQueryException;
	
	/*Display 5(default) payment at a time of given page*/
	ResponseEntity<String> getPayments(Integer pageNumber) throws IOException;
	
	/*Update the discount by taking the payment_id*/
	ResponseEntity<Payment> updateDiscountById(Payment payment);
	
	/*Remove an Payment with it's id*/
	ResponseEntity<Payment> removePaymentById(Integer id);
}
