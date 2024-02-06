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
	
	@Override
	@GetMapping("/customer/all/page={pageNumber}")
	public ResponseEntity<String> getCustomers(@PathVariable Integer pageNumber) throws IOException {
		Page<Customer> currentPage = adminRepository.getCustomers(pageNumber, 5);
		List<Customer> customers = currentPage.getContent();
		
		if(customers.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No more Customers available");
		
		customers.forEach(customer -> logger.info(solidBox(viewOfCustomer(customer)) + "\n"));
		
		String customersStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(customers);
		
		return ResponseEntity.ok(customersStr);
		
	}

	@Override
	@GetMapping("/shopkeeper/all/page={pageNumger}")
	public ResponseEntity<String> getShopkeepers(@PathVariable Integer pageNumber) throws IOException {
		Page<Shopkeeper> currentPage = adminRepository.getShopkeepers(pageNumber, 5);
		List<Shopkeeper> shopkeepers = currentPage.getContent();
		
		if(shopkeepers.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No more Shopkeepers available");
		
		shopkeepers.forEach(shopkeeper -> logger.info(solidBox(viewOfShopkeeper(shopkeeper)) + "\n"));
		
		return ResponseEntity.ok(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(shopkeepers));
		
	}

	@Override
	@GetMapping("/payment/all/page={pageNumber}")
	public ResponseEntity<String> getPayments(@PathVariable Integer pageNumber) throws IOException {
		Page<Payment> currentPage = adminRepository.getPayments(pageNumber, 5);
		List<Payment> payments = currentPage.getContent();
		
		if(payments.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No more Payments available.");
		
		payments.forEach(payment -> logger.info(solidBox(viewOfPayment(payment))));
		
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
		
		logger.info((savedpayment != null)?dotedBox("New method added successfully :)") :
			dotedBox("This method alrady exist :("));
		
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
		
		logger.info((savedpayment != null)?dotedBox("Discount updated successfully :)") :
			dotedBox("This Id does't exist :("));
		
		return ResponseEntity.ok(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedpayment));
	}
	
	@Override
	@DeleteMapping("/payment/remove/id={id}")
	public ResponseEntity<String> removePaymentById(@PathVariable Integer id) throws IOException{
		Payment payment = adminRepository.removePaymentById(id);
		
		logger.info((payment != null)?dotedBox("Payment removed successfully :)") :
			dotedBox("This Id does't exist :("));
		
		return ResponseEntity.ok(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payment));
	}
	
	public String viewOfShopkeeper(Shopkeeper shopkeeper) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("%-15s: %-25s\n".formatted("Name", shopkeeper.getName()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Contact", shopkeeper.getContact()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Email", shopkeeper.getName()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Username", shopkeeper.getUserName()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Password", "********"));
		
		return stringBuilder.toString();
	}
	
	public String viewOfCustomer(Customer customer) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("%-15s: %-25s\n".formatted("Name", customer.getName()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Contact", customer.getContact()));
		stringBuilder.append("%-15s: %-25s\n".formatted("DOB", customer.getDob()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Email", customer.getEmail()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Address", customer.getAddress()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Username", customer.getUserName()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Password", "********"));
		
		return stringBuilder.toString();
	}
	
	public String viewOfPayment(Payment payment) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("%-15s: %-25s\n".formatted("ID", payment.getId()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Mode of Payment", payment.getMethod()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Discount", payment.getDiscount()));
		
		return stringBuilder.toString();
	}
	
	public String dotedBox(String string, Integer width) {
		if(width<7)
			width=7;
		int maxStringLen = width-4;
		
		StringBuilder stringBuilder = new StringBuilder();
		List<String> lines = string.lines().toList();
		for (int i = 0; i < width; i++) {
            stringBuilder.append("*");
        }
		stringBuilder.append("\n");
		
		for(String line: lines) {
	        int remainingChars = line.length();
	        int currentIndex = 0;

	        while (remainingChars > 0) {
	            stringBuilder.append("* ");
	            int charsToPrint = Math.min(maxStringLen, remainingChars);
	            
	            if(charsToPrint!=maxStringLen) {
	            	int padding = (maxStringLen - charsToPrint)/2;
	            	stringBuilder.append(" ".repeat(padding));
	            	stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            	stringBuilder.append(" ".repeat((((maxStringLen - charsToPrint)%2)==0)? padding: padding+1));
	            	stringBuilder.append(" *\n");
	            	break;
	            }
	            
	            stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            stringBuilder.append(" ".repeat(maxStringLen - charsToPrint));

	            stringBuilder.append(" *\n");

	            currentIndex += charsToPrint;
	            remainingChars -= charsToPrint;
	        }
		}
		for (int i = 0; i < width; i++) {
            stringBuilder.append("*");
        }
		
		return stringBuilder.toString();
	}

	public String dotedBox(String string) {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> lines = string.lines().toList();
		int maxStringLen = lines.get(0).length();
		for(int i=1; i<lines.size(); i++) {
			if(maxStringLen<lines.get(i).length())
				maxStringLen = lines.get(i).length();
		}
		int width = maxStringLen + 4;
		
		for (int i = 0; i < width; i++) {
            stringBuilder.append("*");
        }
		stringBuilder.append("\n");
		
		for(String line: lines) {
	        int remainingChars = line.length();
	        int currentIndex = 0;

	        while (remainingChars > 0) {
	            stringBuilder.append("* ");
	            int charsToPrint = Math.min(maxStringLen, remainingChars);
	            
	            if(charsToPrint!=maxStringLen) {
	            	int padding = (maxStringLen - charsToPrint)/2;
	            	stringBuilder.append(" ".repeat(padding));
	            	stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            	stringBuilder.append(" ".repeat((((maxStringLen - charsToPrint)%2)==0)? padding: padding+1));
	            	stringBuilder.append(" *\n");
	            	break;
	            }
	            
	            stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            stringBuilder.append(" ".repeat(maxStringLen - charsToPrint));

	            stringBuilder.append(" *\n");

	            currentIndex += charsToPrint;
	            remainingChars -= charsToPrint;
	        }
		}
		for (int i = 0; i < width; i++) {
            stringBuilder.append("*");
        }
		
		return stringBuilder.toString();
	}
	
	public String solidBox(String string, int width) {
		if(width<7)
			width=7;
		int maxStringLen = width-4;
		
		StringBuilder stringBuilder = new StringBuilder("+");
		List<String> lines = string.lines().toList();
		for (int i = 0; i < width-2; i++) {
            stringBuilder.append("-");
        }
		stringBuilder.append("+\n");
		
		for(String line: lines) {
	        int remainingChars = line.length();
	        int currentIndex = 0;

	        while (remainingChars > 0) {
	            stringBuilder.append("| ");
	            int charsToPrint = Math.min(maxStringLen, remainingChars);
	            
	            if(charsToPrint!=maxStringLen) {
	            	int padding = (maxStringLen - charsToPrint)/2;
	            	stringBuilder.append(" ".repeat(padding));
	            	stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            	stringBuilder.append(" ".repeat((((maxStringLen - charsToPrint)%2)==0)? padding: padding+1));
	            	stringBuilder.append(" |\n");
	            	break;
	            }
	            
	            stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            stringBuilder.append(" ".repeat(maxStringLen - charsToPrint));

	            stringBuilder.append(" |\n");

	            currentIndex += charsToPrint;
	            remainingChars -= charsToPrint;
	        }
		}
		stringBuilder.append("+");
		for (int i = 0; i < width-2; i++) {
            stringBuilder.append("-");
        }
		stringBuilder.append("+");
		
		return stringBuilder.toString();
	}
	
	public String solidBox(String string) {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> lines = string.lines().toList();
		int maxStringLen = lines.get(0).length();
		for(int i=1; i<lines.size(); i++) {
			if(maxStringLen<lines.get(i).length())
				maxStringLen = lines.get(i).length();
		}
		int width = maxStringLen + 4;
		
		for (int i = 0; i < width-2; i++) {
            stringBuilder.append("-");
        }
		stringBuilder.append("+\n");
		
		for(String line: lines) {
	        int remainingChars = line.length();
	        int currentIndex = 0;

	        while (remainingChars > 0) {
	            stringBuilder.append("| ");
	            int charsToPrint = Math.min(maxStringLen, remainingChars);
	            
	            if(charsToPrint!=maxStringLen) {
	            	int padding = (maxStringLen - charsToPrint)/2;
	            	stringBuilder.append(" ".repeat(padding));
	            	stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            	stringBuilder.append(" ".repeat((((maxStringLen - charsToPrint)%2)==0)? padding: padding+1));
	            	stringBuilder.append(" |\n");
	            	break;
	            }
	            
	            stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            stringBuilder.append(" ".repeat(maxStringLen - charsToPrint));

	            stringBuilder.append(" |\n");

	            currentIndex += charsToPrint;
	            remainingChars -= charsToPrint;
	        }
		}
		stringBuilder.append("+");
		for (int i = 0; i < width-2; i++) {
            stringBuilder.append("-");
        }
		stringBuilder.append("+");
		
		return stringBuilder.toString();
	}
	
}
