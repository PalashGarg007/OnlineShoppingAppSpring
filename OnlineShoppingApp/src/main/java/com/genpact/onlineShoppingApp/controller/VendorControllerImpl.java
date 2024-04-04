package com.genpact.onlineShoppingApp.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
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

import com.genpact.onlineShoppingApp.Validation;
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
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private Views views;
	
	private Logger logger = LoggerFactory.getLogger(VendorControllerImpl.class);
	
	public final String CREATE_ACCOUNT_URL = "/newAccount";
	public final String LOGIN_URL = "/logIn";
	public final String ADD_AND_UPDATE_PRODUCTS_URL = "/product/addProduct";
	public final String GET_PRODUCTS_URL = "products/page={pageNumber}";
	public final String CHANGE_PERSONAL_INFORMATION_URL = "/changePersonalInformation";
	public final String GET_UNACCEPTED_ORDERS_URL = "/getUnacceptedOrders/page={pageNumber}";
	public final String SET_UNACCEPTED_ORDERS_URL = "/setUnacceptedOrders";
	public final String SEARCH_PRODUCTS_URL = "/search={condition}";
	public final String ADD_AND_UPDATE_PRODUCTS_BY_FILE_URL = "/updateByFile";
	public final String ADD_AND_UPDATE_PRODUCTS_FILE_PATH = "C:\\Users\\gargp\\Desktop\\SpringWork\\OnlineShoppingAppSpring\\InputProducts.txt";
	
	@Override
	@PostMapping(value = CREATE_ACCOUNT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<Shopkeeper> createAccount(@RequestBody Shopkeeper shopkeeper) throws IOException {
		if(!validation.nameValidation(shopkeeper.getName()))
			throw new InvalidInputException("Name can only have characters.");
		
		if(!validation.contactValidation(shopkeeper.getContact()))
			throw new InvalidInputException("Enter a valid 10 digits Contact Number.");
		
		if(!validation.emailValidation(shopkeeper.getEmail()))
			throw new InvalidInputException("""
                    The following restrictions are imposed in the email address local part:
                    	1. Dot isn’t allowed at the start and end of the local part.
                    	2. Consecutive dots aren’t allowed.
                    	3. A maximum of 64 characters are allowed.
                    Restrictions for the domain part in this regular expression include:
                    	1. Hyphen “-” and dot “.” aren’t allowed at the start and end of the domain part.
                    	2. No consecutive dots.
                    """);
		
		if(!validation.passwordValidation(shopkeeper.getPassword()))
			throw new InvalidInputException("""
                    Password must contain:
                    	1. At least one Special character.
                    	2. Minimun length of 8.
                    	3. At least one number.
                    	4. At least one lower and one upper cahracter.
                    	5. Does't contain space, tabs, etc.
                    """);
		
		Shopkeeper newShopkeeper = vendorService.createAccount(shopkeeper);
		
		logger.info((newShopkeeper!=null)?views.dotedBox("Account created successfully :)") :
			views.dotedBox("Account can't be created successfully :("));
		
		return (newShopkeeper == null)? ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null):
			ResponseEntity.status(HttpStatus.CREATED).body(newShopkeeper);
		
	}

	@Override
	@PostMapping(value = LOGIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Shopkeeper> shopkeeperLogin(@RequestBody Shopkeeper loginDetails){
		Shopkeeper shopkeeper = vendorService.shopkeeperLogin(loginDetails.getUserName(), loginDetails.getPassword());
		
		logger.info((shopkeeper!=null)?views.dotedBox("Login Successfully :)") :
			views.dotedBox("Login Unsuccessfully :("));
		
		return (shopkeeper!=null)? ResponseEntity.ok(shopkeeper) :
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		
	}
	
	@Override
	@PostMapping(value = ADD_AND_UPDATE_PRODUCTS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> addAndUpdateProduct(@RequestBody Product product) throws IOException {
		if(!validation.costValidition(product.getCost()))
			throw new InvalidInputException("Invalid cost.");
		
		if(!validation.warehouseValidation(product.getWarehouse()))
			throw new InvalidInputException("No of products in the warehouse can't be negative.");
		
		Product newProduct = vendorService.addAndUpdateProduct(product);
		
		logger.info((newProduct!=null) ? views.dotedBox("Product added successfully :)") :
				views.dotedBox("A Product with the same name and category already exist :("));
		
		return (newProduct!=null) ? ResponseEntity.status(201).body(newProduct) :
			ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
		
	}

	@Override
	@GetMapping(value = GET_PRODUCTS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getProducts(@PathVariable Integer pageNumber) {
		Page<Product> currentPage = vendorService.getProducts(pageNumber, 5);
		List<Product> products = currentPage.getContent();
		if(products.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		
		products.forEach(product -> logger.info(views.solidBox(views.viewOfProductsForVendor(product), 30) + "\n"));
					
		return ResponseEntity.ok(products);
	}

	@Override
	@PutMapping(value = CHANGE_PERSONAL_INFORMATION_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Shopkeeper> changePersonalInformadtion(@RequestBody Shopkeeper shopkeeper) {
		if(shopkeeper.getContact()!=null && !validation.contactValidation(shopkeeper.getContact()))
			throw new InvalidInputException("Enter a valid contact number.");
		
		if(shopkeeper.getEmail()!=null && !validation.emailValidation(shopkeeper.getEmail()))
			throw new InvalidInputException("Invalid Email ID.");
		
		if(shopkeeper.getPassword()!=null && !validation.passwordValidation(shopkeeper.getPassword()))
			throw new InvalidInputException("Enter a valid password");
		
		Shopkeeper updatedShopkeeper = vendorService.cahngePersonalInformadtion(
				shopkeeper.getContact(), shopkeeper.getEmail(), shopkeeper.getPassword());
		
		logger.info((updatedShopkeeper!=null)? views.dotedBox("Shopkeeper updated successfully :)") :
				views.dotedBox("Shopkeeper not updated successfully :("));
		
		return (updatedShopkeeper!=null)? ResponseEntity.ok(updatedShopkeeper) :
				ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
		
	}

	@Override
	@GetMapping(value = GET_UNACCEPTED_ORDERS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UnacceptedOrders>> getUnacceptedOrders(@PathVariable Integer pageNumber){
		Page<UnacceptedOrders> currentPage = vendorService.getUnacceptedOrders(pageNumber, 5);
		List<UnacceptedOrders> unacceptedOrders = currentPage.getContent();
		
		logger.info(unacceptedOrders.toString());
		
		return (unacceptedOrders.isEmpty())? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null):
				ResponseEntity.ok(unacceptedOrders);
	}
	
	@Override
	@PutMapping(value = SET_UNACCEPTED_ORDERS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> setUnacceptedOrders(@RequestBody List<UnacceptedOrders> unacceptedOrders) {
		if(unacceptedOrders.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
		
		unacceptedOrders = unacceptedOrders.stream()
				.filter(orders -> orders.getConfirmation()==true)
				.toList();
		
		vendorService.setUnacceptedOrders(unacceptedOrders);
		
		return ResponseEntity.ok("Done");
	}

	@Override
	@GetMapping(value = SEARCH_PRODUCTS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> searchProducts(@PathVariable String condition){
		List<Product> products = vendorService.searchProducts(condition);
		
		return (products.isEmpty())? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) :
			ResponseEntity.ok(products);
	}
	
	@Override
	@PostMapping(value = ADD_AND_UPDATE_PRODUCTS_BY_FILE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addAndUpdateProductsByFile() throws IOException {
		try {
			File file = new File(ADD_AND_UPDATE_PRODUCTS_FILE_PATH);
			if(!file.exists()) {
				file.createNewFile();
				
				FileWriter writer = new FileWriter(ADD_AND_UPDATE_PRODUCTS_FILE_PATH);
				writer.write("name, brand, category, cost, warehouse");
				writer.flush();
				writer.close();
				return ResponseEntity.status(HttpStatus.CREATED).body("New file created.");
			}
			
			vendorService.addAndUpdateProductsByFile(Files.readAllLines(file.toPath()).stream());
			
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
		
		return ResponseEntity.ok("Products added successfully.");
	}
	
}
