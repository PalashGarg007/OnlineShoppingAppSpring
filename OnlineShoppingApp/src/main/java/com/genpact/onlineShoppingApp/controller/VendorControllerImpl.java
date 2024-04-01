package com.genpact.onlineShoppingApp.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genpact.onlineShoppingApp.dto.UnacceptedOrders;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;
import com.genpact.onlineShoppingApp.exception.InvalidInputException;
import com.genpact.onlineShoppingApp.repository.VendorService;

@RestController
@RequestMapping("/shopkeeper")
public class VendorControllerImpl implements VendorController {
	@Autowired
	private VendorService vendorService;
	
	private Logger logger = LoggerFactory.getLogger(VendorControllerImpl.class);
	
	@Autowired
	private Views views;
	
	@Override
	@PostMapping(value = "/newAccount", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Shopkeeper> createAccount(@RequestBody Shopkeeper shopkeeper) throws IOException {
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
		
		Shopkeeper newShopkeeper = vendorService.createAccount(shopkeeper);
		
		logger.info((newShopkeeper!=null)?views.dotedBox("Account created successfully :)") :
			views.dotedBox("Account can't be created successfully :("));
		
		return (newShopkeeper == null)? ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null):
			ResponseEntity.status(HttpStatus.CREATED).body(newShopkeeper);
		
	}

	@Override
	@PostMapping(value = "/logIn", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Shopkeeper> shopkeeperLogin(@RequestBody Shopkeeper loginDetails) throws IOException {		
		Shopkeeper shopkeeper = vendorService.shopkeeperLogin(loginDetails.getUserName(), loginDetails.getPassword());
		
		logger.info((shopkeeper!=null)?views.dotedBox("Login Successfully :)") :
			views.dotedBox("Login Unsuccessfully :("));
		
		return (shopkeeper!=null)? ResponseEntity.ok(shopkeeper) :
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		
	}
	
	@Override
	@PostMapping(value = "/product/addProduct", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> addAndUpdateProduct(@RequestBody Product product) throws IOException {
		if(product.getCost()<=0)
			throw new InvalidInputException("Invalid cost.");
		
		if(product.getWarehouse()<0)
			throw new InvalidInputException("No of products in the warehouse can't be negative.");
		
		Product newProduct = vendorService.addAndUpdateProduct(product);
		
		logger.info((newProduct!=null) ? views.dotedBox("Product added successfully :)") :
				views.dotedBox("A Product with the same name and category already exist :("));
		
		return (newProduct!=null) ? ResponseEntity.status(201).body(newProduct) :
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
		
	}

	@Override
	@GetMapping(value = "products/page={pageNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getProducts(@PathVariable Integer pageNumber) {
		Page<Product> currentPage = vendorService.getProducts(pageNumber, 5);
		List<Product> products = currentPage.getContent();
		if(products.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		
		products.forEach(product -> logger.info(views.solidBox(views.viewOfProductsForVendor(product), 30) + "\n"));
					
		return ResponseEntity.ok(products);
	}

	@Override
	@PutMapping(value = "/changePersonalInformation", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Shopkeeper> changePersonalInformadtion(@RequestBody Shopkeeper shopkeeper) {
		Function<String, Boolean> contactCondition = (contact) -> (
				contact.matches("^\\d{10}$"));
		
		Function<String, Boolean> emailCondition = (email) -> (
				(email.matches("""
                        ^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@\
                        [^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$\
                        """)));
		
		Function<String, Boolean> passwordCondition = (password) -> (
				(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")));
		
		if(shopkeeper.getContact()!=null && !contactCondition.apply(shopkeeper.getContact()))
				throw new InvalidInputException("Enter a valid contact number.");
		
		if(shopkeeper.getEmail()!=null && !emailCondition.apply(shopkeeper.getEmail()))
				throw new InvalidInputException("Invalid Email ID.");
		
		if(shopkeeper.getPassword()!=null && !passwordCondition.apply(shopkeeper.getPassword()))
                throw new InvalidInputException("Enter a valid password");
		
		Shopkeeper updatedShopkeeper = vendorService.cahngePersonalInformadtion(
				shopkeeper.getContact(), shopkeeper.getEmail(), shopkeeper.getPassword());
		
		logger.info((updatedShopkeeper!=null)? views.dotedBox("Shopkeeper updated successfully :)") :
				views.dotedBox("Shopkeeper not updated successfully :("));
		
		return (shopkeeper!=null)? ResponseEntity.ok(updatedShopkeeper) :
				ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
		
	}

	@Override
	@GetMapping(value = "/unAcceptedOrders/page={pageNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UnacceptedOrders>> getUnacceptedOrders(@PathVariable Integer pageNumber){
		Page<UnacceptedOrders> currentPage = vendorService.getUnacceptedOrders(pageNumber, 5);
		List<UnacceptedOrders> unacceptedOrders = currentPage.getContent();
		
		logger.info(unacceptedOrders.toString());
		
		return (unacceptedOrders.isEmpty())? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null):
				ResponseEntity.ok(unacceptedOrders);
	}
	
	@Override
	@PutMapping(value = "/unacceptableOrders", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> setUnacceptedOrders(@RequestBody List<UnacceptedOrders> unacceptedOrders) {
		if(unacceptedOrders.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
		
		unacceptedOrders = unacceptedOrders.stream()
				.filter(s -> s.getConfirmation()==true)
				.toList();
		
		vendorService.setUnacceptedOrders(unacceptedOrders);
		
		return ResponseEntity.ok("Done");
	}

	@Override
	@GetMapping(value = "/search={condition}?page={pageNo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> searchProducts(@PathVariable String condition, @PathVariable Integer pageNo){
		List<Product> products = vendorService.searchProducts(condition);
		
		return (products.isEmpty())? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) :
			ResponseEntity.ok(products);
	}
	
	@Override
	@PostMapping(value = "/updateByFile")
	public ResponseEntity<String> addAndUpdateProductsByFile() throws IOException {
		try {
			File file = new File("InputProducts.txt");
			if(!file.exists()) {
				file.createNewFile();
				
				FileWriter writer = new FileWriter("InputProducts.txt");
				writer.write("name, brand, category, cost, warehouse");
				writer.close();
				return ResponseEntity.status(HttpStatus.CREATED).body("New file created.");
			}
			
			Scanner Reader = new Scanner(file);
			Reader.nextLine(); //skip first line.
			vendorService.addAndUpdateProductsByFile(Reader.tokens());
            Reader.close();
			
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
		
		return ResponseEntity.ok("Products added successfully.");
	}
}
