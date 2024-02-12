package com.genpact.onlineShoppingApp.service;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.genpact.onlineShoppingApp.entity.Customer;
import com.genpact.onlineShoppingApp.entity.Payment;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;
import com.genpact.onlineShoppingApp.exception.InvalidInputException;
import com.genpact.onlineShoppingApp.exception.InvalidSQLQueryException;
import com.genpact.onlineShoppingApp.repository.AdminRepository;

@RestController
@RequestMapping("/admin")
@Component
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepository adminRepository;
	
	private ObjectMapper mapper = JsonMapper.builder()
			.findAndAddModules()
			.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
			.build();
	
	private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	private Views views = new Views();
	
	@Override
	@GetMapping("/customer/all/page={pageNumber}")
	public ResponseEntity<String> getCustomers(@PathVariable Integer pageNumber) throws IOException {
		Page<Customer> currentPage = adminRepository.getCustomers(pageNumber, 5);
		List<Customer> customers = currentPage.getContent();
		
		if(customers.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No more Customers available");
		
		customers.forEach(customer -> logger.info(views.solidBox(views.viewOfCustomerForAdmin(customer)) + "\n"));
		
		String customersStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customers);
		
		return ResponseEntity.ok(customersStr);
		
	}

	@Override
	@GetMapping("/shopkeeper/all/page={pageNumber}")
	public ResponseEntity<String> getShopkeepers(@PathVariable Integer pageNumber) throws IOException {
		Page<Shopkeeper> currentPage = adminRepository.getShopkeepers(pageNumber, 5);
		List<Shopkeeper> shopkeepers = currentPage.getContent();
		
		if(shopkeepers.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No more Shopkeepers available");
		
		shopkeepers.forEach(shopkeeper -> logger.info(views.solidBox(views.viewOfShopkeeperForAdmin(shopkeeper)) + "\n"));
		
		return ResponseEntity.ok(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(shopkeepers));
		
	}

	@Override
	@GetMapping("/payment/all/page={pageNumber}")
	public ResponseEntity<String> getPayments(@PathVariable Integer pageNumber) throws IOException {
		Page<Payment> currentPage = adminRepository.getPayments(pageNumber, 5);
		List<Payment> payments = currentPage.getContent();
		
		if(payments.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No more Payments available.");
		
		payments.forEach(payment -> logger.info(views.solidBox(views.viewOfPaymentForAdmin(payment))));
		
		return ResponseEntity.ok(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payments));
	}
	
	@Override
	@PostMapping("/payment/addNew")
	public ResponseEntity<String> addNewPayment(@RequestBody Payment payment) throws InvalidSQLQueryException, IOException{
		Function<String, Boolean> discountCondition = (stringDiscount) -> (
				stringDiscount.matches("^(\\d{1,2})((\\.\\d{1,})?)$"));
		
		Payment savedpayment;
		if(discountCondition.apply(String.valueOf(payment.getDiscount())))
			savedpayment = adminRepository.addNewPayment(payment);
		else
			throw new InvalidInputException("Invalid discount");
		
		logger.info((savedpayment != null) ? views.dotedBox("New method added successfully :)") :
			views.dotedBox("This method alrady exist :("));
		
		return ResponseEntity.ok(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedpayment));
		
	}

	@Override
	@PutMapping("/payment/update")
	public ResponseEntity<String> updateDiscountById(@RequestBody Payment payment) throws IOException {
		Function<String, Boolean> discountCondition = (stringDiscount) -> (
				stringDiscount.matches("^(\\d{1,2})((\\.\\d{1,})?)$"));
		
		Payment savedpayment;
		if(discountCondition.apply(String.valueOf(payment.getDiscount())))
			savedpayment = adminRepository.updateDiscountById(payment);
		else
			throw new InvalidInputException("Invalid discount");
		
		logger.info((savedpayment != null) ? views.dotedBox("Discount updated successfully :)") :
			views.dotedBox("This Id does't exist :("));
		
		return ResponseEntity.ok(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedpayment));
	}
	
	@Override
	@DeleteMapping("/payment/remove/id={id}")
	public ResponseEntity<String> removePaymentById(@PathVariable Integer id) throws IOException{
		Payment payment = adminRepository.removePaymentById(id);
		
		logger.info((payment != null) ? views.dotedBox("Payment removed successfully :)") :
			views.dotedBox("This Id does't exist :("));
		
		return ResponseEntity.ok(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payment));
	}
	
}
