package com.genpact.onlineShoppingApp.controller;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.genpact.onlineShoppingApp.Validation;
import com.genpact.onlineShoppingApp.entity.Customer;
import com.genpact.onlineShoppingApp.entity.Payment;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;
import com.genpact.onlineShoppingApp.exception.InvalidInputException;
import com.genpact.onlineShoppingApp.exception.InvalidSQLQueryException;
import com.genpact.onlineShoppingApp.repository.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminControllerImpl implements AdminController {
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private Views views;
	
	@Autowired
	private Validation validation;
	
	private Logger logger = LoggerFactory.getLogger(AdminControllerImpl.class);
	
	public final String GET_CUSTOMERS_URL = "/customers/page={pageNumber}";
	public final String GET_SHOPKEEPERS_URL = "/shopkeepers/page={pageNumber}";
	public final String GET_PAYMENTS_URL = "/payments/page={pageNumber}";
	public final String ADD_NEW_PAYMENTS_URL = "/payments";
	public final String UPDATE_DISCOUNT_BY_ID_URL = "/payments";
	
	@Override
	@GetMapping(value = GET_CUSTOMERS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Customer>> getCustomers(@PathVariable Integer pageNumber) throws IOException {
		Page<Customer> currentPage = adminService.getCustomers(pageNumber, 5);
		List<Customer> customers = currentPage.getContent();
		
		if(customers.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		
		customers.forEach(customer -> logger.info(views.solidBox(views.viewOfCustomerForAdmin(customer)) + "\n"));
		
		return ResponseEntity.ok(customers);
		
	}
	
	@Override
	@GetMapping(value = GET_SHOPKEEPERS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Shopkeeper>> getShopkeepers(@PathVariable Integer pageNumber) throws IOException {
		Page<Shopkeeper> currentPage = adminService.getShopkeepers(pageNumber, 5);
		List<Shopkeeper> shopkeepers = currentPage.getContent();
		
		if(shopkeepers.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		
		shopkeepers.forEach(shopkeeper -> logger.info(views.solidBox(views.viewOfShopkeeperForAdmin(shopkeeper)) + "\n"));
		
		return ResponseEntity.ok(shopkeepers);
		
	}

	@Override
	@GetMapping(value = GET_PAYMENTS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Payment>> getPayments(@PathVariable Integer pageNumber) throws IOException {
		Page<Payment> currentPage = adminService.getPayments(pageNumber, 5);
		List<Payment> payments = currentPage.getContent();
		
		if(payments.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		
		payments.forEach(payment -> logger.info(views.solidBox(views.viewOfPaymentForAdmin(payment))));
		
		return ResponseEntity.ok(payments);
	}
	
	@Override
	@PostMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Payment> addNewPayment(@RequestBody Payment payment) throws InvalidSQLQueryException, IOException{
		Payment savedpayment;
		
		if(!validation.discountValidation(payment.getDiscount()))
			throw new InvalidInputException("Invalid discount");
		
		savedpayment = adminService.addNewPayment(payment);
		
		logger.info((savedpayment != null) ? views.dotedBox("New method added successfully :)") :
			views.dotedBox("This method alrady exist :("));
		
		return ResponseEntity.ok(savedpayment);
		
	}

	@Override
	@PutMapping(value = UPDATE_DISCOUNT_BY_ID_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Payment> updateDiscountById(@RequestBody Payment payment) throws IOException {
		Payment savedpayment;
		
		if(!validation.discountValidation(payment.getDiscount()))
			throw new InvalidInputException("Invalid discount");
		
		savedpayment = adminService.updateDiscountById(payment);
		
		logger.info((savedpayment != null) ? views.dotedBox("Discount updated successfully :)") :
			views.dotedBox("This Id does't exist :("));
		
		return ResponseEntity.ok(savedpayment);
	
	}
	
}
