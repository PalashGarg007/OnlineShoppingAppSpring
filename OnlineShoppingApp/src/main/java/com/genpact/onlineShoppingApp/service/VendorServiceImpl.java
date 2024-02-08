package com.genpact.onlineShoppingApp.service;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;
import com.genpact.onlineShoppingApp.exception.InvalidInputException;
import com.genpact.onlineShoppingApp.repository.VendorRepository;

@RestController
@RequestMapping("/shopkeeper")
@Component
public class VendorServiceImpl implements VendorService, Runnable {
	@Autowired
	private VendorRepository vendorRepository;
	
	private ObjectMapper mapper = JsonMapper.builder()
			.findAndAddModules()
			.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
			.build();
	
	private Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);
	
	private Views views;
	
	@Override
	public void run() {
		//TODO:Write the run method for vendorRepositoryInpl
	}
	
	@Override
	@PostMapping("/newAccount")
	public ResponseEntity<String> createAccount(@RequestBody Shopkeeper shopkeeper) throws IOException {
		Function<String, Boolean> nameCondition = (name) ->(
				name.matches("^([a-zA-Z])(?([ ][a-zA-Z])){1,}$"));
		
		Function<String, Boolean> contactCondition = (contact) -> (
				contact.matches("^\\d{10}$"));
		
		Function<String, Boolean> emailCondition = (email) -> (
				(email.matches("""
                        ^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@\
                        [^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$\
                        """)));
		
		Function<String, Boolean> passwordCondition = (password) -> (
				(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")));
		
		if(!nameCondition.apply(shopkeeper.getName())) {
			throw new InvalidInputException("Name can only have characters.");
		}
		
		if(!contactCondition.apply(shopkeeper.getContact())) {
			throw new InvalidInputException("Enter a valid 10 digits Contact Number.");
		}
		
		if(!emailCondition.apply(shopkeeper.getEmail())) {
			throw new InvalidInputException("""
                    The following restrictions are imposed in the email address local part:
                    	1. Dot isn’t allowed at the start and end of the local part.
                    	2. Consecutive dots aren’t allowed.
                    	3. A maximum of 64 characters are allowed.
                    Restrictions for the domain part in this regular expression include:
                    	1. Hyphen “-” and dot “.” aren’t allowed at the start and end of the domain part.
                    	2. No consecutive dots.
                    """);
		}
		
		if(!passwordCondition.apply(shopkeeper.getPassword())) {
			throw new InvalidInputException("""
                    Password must contain:
                    	1. At least one Special character.
                    	2. Minimun length of 8.
                    	3. At least one number.
                    	4. At least one lower and one upper cahracter.
                    	5. Does't contain space, tabs, etc.
                    """);
		}
		
		Shopkeeper result = vendorRepository.createAccount(shopkeeper);
		String resultString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
		
		logger.info((result!=null)?views.dotedBox("Account created successfully :)") :
			views.dotedBox("Account can't be created successfully :("));
		
		if(result == null)
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(resultString);
		
		return ResponseEntity.ok(resultString);
		
	}

	@Override
	@GetMapping("/logIn")
	public ResponseEntity<String> shopkeeperLogin(@RequestBody String loginDetails) throws IOException {
		Shopkeeper credintials = mapper.readValue(loginDetails, Shopkeeper.class);
		//ToDo: Testing the above line.
		
		Shopkeeper shopkeeper = vendorRepository.shopkeeperLogin(credintials.getUserName(), credintials.getPassword());
		
		logger.info((shopkeeper!=null)?views.dotedBox("Login Successfully :)") :
			views.dotedBox("Login Unsuccessfully :("));
		
		String shopkeeperString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(shopkeeper);
		
		return (shopkeeper!=null)? ResponseEntity.ok(shopkeeperString) :
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(shopkeeperString);
		
	}
	
	@Override
	@PostMapping("/product/addNew")
	public ResponseEntity<String> addNewProduct(@RequestBody String productString) throws IOException {
		Product product;
		
		try {
			product = mapper.readValue(productString, Product.class);
		} catch (JsonProcessingException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
		if(product.getCost()<=0)
			throw new InvalidInputException("Invalid cost.");
		
		if(product.getWarehouse()<0)
			throw new InvalidInputException("No of products in the warehouse can't be negative.");
		
		Product result = vendorRepository.addNewProduct(product);
		String resultString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
		
		logger.info((result!=null) ? views.dotedBox("Product added successfully :)") :
				views.dotedBox("A Product with the same name and category already exist :("));
		
		return (result!=null) ? ResponseEntity.ok(resultString) :
			ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(resultString);
		
	}

	@Override
	public Boolean getProducts(Integer pageNumber) {
		Page<Product> currentPage = vendorRepository.getProducts(pageNumber, 5);
		List<Product> products = currentPage.getContent();
		if(products.isEmpty())
			return false;
		products.forEach(product -> System.out.println(views.solidBox(views.viewOfProductsForVendor(product), 30) + "\n"));
		
		return true;
	}

	@Override
	public void changePersonalInformadtion() {
		Function<String, Boolean> contactCondition = (contact) -> (
				contact.matches("^\\d{10}$"));
		
		Function<String, Boolean> emailCondition = (email) -> (
				(email.matches("""
                        ^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@\
                        [^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$\
                        """)));
		
		Function<String, Boolean> passwordCondition = (password) -> (
				(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")));
		int result = 0;
		Shopkeeper shopkeeper = vendorRepository.getShopkeeper();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n" +views.solidBox(views.viewOfShopkeeperForVendor(shopkeeper), 45));
		
		String contact = "";
		System.out.print("\nDo you want to change your Contact Number (Y)or(N): ");
		String conformation = scanner.nextLine();
		while(!(conformation.equalsIgnoreCase("N") || conformation.equalsIgnoreCase("Y"))) {
			System.out.print("Do you want to change your Contact Number (Y)or(N): ");
			conformation = scanner.nextLine();
		}
		if(conformation.equalsIgnoreCase("Y")) {
			System.out.print("\nEnter Your Contact Number: ");
			contact = scanner.nextLine().strip();
			while(!contactCondition.apply(contact)) {
				System.out.print("\nEnter a valid 10 digits Contact Number: ");
				contact = scanner.nextLine().strip();
			}
		}
		
		String email = "";
		System.out.print("\nDo you want to change your Email (Y)or(N): ");
		conformation = scanner.nextLine();
		while(!(conformation.equalsIgnoreCase("N") || conformation.equalsIgnoreCase("Y"))) {
			System.out.print("Do you want to change your Email (Y)or(N): ");
			conformation = scanner.nextLine();
		}
		if(conformation.equalsIgnoreCase("Y")) {
			System.out.print("\nEnter Your Email ID: ");
			email = scanner.nextLine().strip();
			while(!emailCondition.apply(email)) {
				System.out.print("""
                        
                        The following restrictions are imposed in the email address local part:
                        	1. Dot isn’t allowed at the start and end of the local part.
                        	2. Consecutive dots aren’t allowed.
                        	3. A maximum of 64 characters are allowed.
                        Restrictions for the domain part in this regular expression include:
                        	1. Hyphen “-” and dot “.” aren’t allowed at the start and end of the domain part.
                        	2. No consecutive dots.
                        
                        """);
				System.out.print("Enter a valid Email ID: ");
				email = scanner.nextLine().strip();
			}
		}
		
		String password = "";
		System.out.print("\nDo you want to change your Password (Y)or(N): ");
		conformation = scanner.nextLine();
		while(!(conformation.equalsIgnoreCase("N") || conformation.equalsIgnoreCase("Y"))) {
			System.out.print("Do you want to change your Password (Y)or(N): ");
			conformation = scanner.nextLine();
		}
		while(conformation.equalsIgnoreCase("Y")) {
			System.out.print("\nEnter your Current Password: ");
			String currentPassword = scanner.nextLine().strip();
			if(!currentPassword.equals(shopkeeper.getPassword())) {
				System.out.print("\n\bWrong password!!");
				continue;
			}
			System.out.print("\nEnter Your New Password: ");
			password = scanner.nextLine().strip();
			while(!passwordCondition.apply(password)) {
				System.out.print("""
                        
                        Password must contain:
                        	1. At least one Special character.
                        	2. Minimun length of 8.
                        	3. At least one number.
                        	4. At least one lower and one upper cahracter.
                        	5. Does't contain space, tabs, etc.
                        
                        
                        Enter a valid password: \
                        """);
				password = scanner.nextLine().strip();
			}
			break;
		}
		
		result = vendorRepository.cahngePersonalInformadtion(contact, email, password);
		System.out.println("\n" + ((result==1)? views.dotedBox("Information updated successfully :)") :
			views.dotedBox("Information was't updated :(")));
		
		if(!(contact.isEmpty() && email.isEmpty()))
			System.out.println("\n" + views.viewOfShopkeeperForVendor(shopkeeper) + "\n");
	}

	@Override
	public void setUnacceptedOrders() {
		//ToDo:
	}
	
	@Override
	public Double totalRevinue() {
		List<Product> productList = vendorRepository.inventoryList();
		Double revinue = productList.stream()
				.map((product)-> product.getCost()*product.getPurchased())
				.reduce(0.0, (sum, x) -> sum + x);
		return revinue;
	}

}
