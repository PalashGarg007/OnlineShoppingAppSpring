package com.genpact.onlineShoppingApp.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.genpact.onlineShoppingApp.Validation;
import com.genpact.onlineShoppingApp.dto.ReviewWithoutOrderId;
import com.genpact.onlineShoppingApp.dto.TranscriptDto;
import com.genpact.onlineShoppingApp.entity.Customer;
import com.genpact.onlineShoppingApp.entity.Orders;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.entity.Review;
import com.genpact.onlineShoppingApp.exception.InvalidInputException;
import com.genpact.onlineShoppingApp.repository.UserService;

@RestController
@RequestMapping("/user")
public class UserControllerImpl implements UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	Views views;
	
	private Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);
	private final ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	public final Integer PAGE_SIZE = 5;
	public final String LOGIN_URL = "/logIn";
	public final String CREATE_ACCOUNT_URL = "/newAccount";
	public final String CHANGE_PERSONAL_INFORMATION_URL = "/changeInformation";
	public final String SEARCH_PRODUCTS_URL = "/search={tokens}";
	public final String GET_REVIEW_OF_PRODUCT_URL = "/product?id={pid}/review/get?page={pageNo}";
	
	public final String GET_FAVORITE_URL = "/favorite/get?page={pageNo}";
	public final String ADD_TO_FAVORITE_URL = "/favorite/add?id={pid}";
	public final String REMOVE_FROM_FAVORITE_URL = "/favorite/remove?id={pid}";
	
	public final String GET_CART_URL = 	"/cart/get?page={pageNo}";
	public final String ADD_TO_CART_URL = "/cart/add?id={pid}?quantity={quantity}"; 
	public final String REMOVE_FROM_CART_URL = "/cart/remove?id={pid}";
	public final String BUY_CART_URL = "/cart/buy?payId={payId}";
	
	public final String GET_ORDER_HISTORY_URL = "orderHistory/get?page={pageNo}";
	public final String GIVE_REVIEW_URL = "orderHistory/order?id={oid}/review";
	public final String DOWNLOAD_TRANSCRIPT_URL = "orderHistory/order?id={oid}/download";
	
	@Override
	@PutMapping("/orders1")
	public void placeOrder1(@RequestBody Integer[] orders) {
		List<Integer> productId = List.of(orders);
		Map<Integer, String> productMap = Collections.synchronizedMap(userService.getProducts());
		
		productId.forEach(id -> executorService.submit(() ->
			System.out.println(id +" : "+ productMap.get(id))));
	}
	
	@Override
	@PutMapping("/orders2")
	public void placeOrder2(@RequestBody Integer[] orders) {
		List<Integer> productId = List.of(orders);
		Map<Integer, String> productMap = Collections.synchronizedMap(userService.getProducts());
		
		productId.parallelStream()
		.forEach(id -> System.out.println(id + " : " + productMap.get(id)));
	}
	
	@Override
	@Async
	@PutMapping("/orders3")
	public void placeOrder3(@RequestBody Integer[] orders) {
		List<Integer> productId = List.of(orders);
		Map<Integer, String> productMap = Collections.synchronizedMap(userService.getProducts());
		
		productId.stream()
		.forEach(id -> System.out.println(id + " : " + productMap.get(id)));
	}
	
	@Override
	@PostMapping(value = CREATE_ACCOUNT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> createAccount(@RequestBody Customer customer) {
		if(!validation.nameValidation(customer.getName()))
			throw new InvalidInputException("Name can only have characters.");
		
		if(!validation.contactValidation(customer.getContact()))
			throw new InvalidInputException("Enter a valid 10 digits Contact Number.");
		
		if(!validation.emailValidation(customer.getEmail()))
			throw new InvalidInputException("""
                    The following restrictions are imposed in the email address local part:
                    	1. Dot isn’t allowed at the start and end of the local part.
                    	2. Consecutive dots aren’t allowed.
                    	3. A maximum of 64 characters are allowed.
                    Restrictions for the domain part in this regular expression include:
                    	1. Hyphen “-” and dot “.” aren’t allowed at the start and end of the domain part.
                    	2. No consecutive dots.
                    """);
		
		if(!validation.passwordValidation(customer.getPassword()))
			throw new InvalidInputException("""
                    Password must contain:
                    	1. At least one Special character.
                    	2. Minimun length of 8.
                    	3. At least one number.
                    	4. At least one lower and one upper cahracter.
                    	5. Does't contain space, tabs, etc.
                    """);
		
		Customer newCustomer = userService.createAccount(customer);
		
		logger.info((newCustomer!=null)?views.dotedBox("Account created successfully :)") :
			views.dotedBox("Account can't be created successfully :("));
		
		return (newCustomer == null)? ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null):
			ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
		
	}

	@Override
	@PostMapping(value = LOGIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> customerLogin(@RequestBody Customer loginDetails){
		Customer customer = userService.customerLogin(loginDetails.getUserName(), loginDetails.getPassword());
		
		logger.info((customer!=null)?views.dotedBox("Login Successfully :)") :
			views.dotedBox("Login Unsuccessfully :("));
		
		return (customer!=null)? ResponseEntity.ok(customer) :
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		
	}
	
	@Override
	@PatchMapping(value = CHANGE_PERSONAL_INFORMATION_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> changePersonalInformation(@RequestBody Customer customer) {
		if(customer.getContact()!=null && !validation.contactValidation(customer.getContact()))
			throw new InvalidInputException("Enter a valid contact number.");
		
		if(customer.getEmail()!=null && !validation.emailValidation(customer.getEmail()))
			throw new InvalidInputException("Invalid Email ID.");
		
		if(customer.getPassword()!=null && !validation.passwordValidation(customer.getPassword()))
			throw new InvalidInputException("Enter a valid password");
		
		Customer updatedCustomer = userService.cahngePersonalInformadtion(
				customer.getContact(), customer.getEmail(), customer.getAddress(), customer.getPassword());
		
		logger.info((updatedCustomer!=null)? views.dotedBox("Shopkeeper updated successfully :)") :
				views.dotedBox("Shopkeeper not updated successfully :("));
		
		return (updatedCustomer!=null)? ResponseEntity.ok(updatedCustomer) :
				ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
		
	}

	@Override
	@GetMapping(value = SEARCH_PRODUCTS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> searchProducts(@PathVariable String tokens) {
		if(tokens.isBlank())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		
		List<Product> productList = userService.searchProducts(tokens);
		
		return (productList.isEmpty())? ResponseEntity.status(HttpStatus.NO_CONTENT).body(null) :
			ResponseEntity.ok(productList);
	}
	
	@Override
	@GetMapping(value = GET_FAVORITE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getFavorite(@PathVariable Integer pageNo){
		List<Product> favoriteProducts = userService.getFavorite(pageNo, PAGE_SIZE);
		
		return favoriteProducts.isEmpty() ? ResponseEntity.noContent().build() :
			ResponseEntity.ok(favoriteProducts);
	}
	
	@Override
	@PostMapping(value = ADD_TO_FAVORITE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addToFavorite(@PathVariable Integer pid) {
		Integer result = userService.addToFavorite(pid);
		
		return (result>0)? ResponseEntity.ok(null): ResponseEntity.badRequest().body(null);
	}
	
	@Override
	@DeleteMapping(value = REMOVE_FROM_FAVORITE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> removeFromFavorite(@PathVariable Integer pid) {
		Integer result = userService.removeFromFavorite(pid);
		
		return (result>0)? ResponseEntity.ok(null): ResponseEntity.badRequest().body(null);
	}

	@Override
	@GetMapping(value = GET_CART_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> getCart(@PathVariable Integer pageNo) {
		List<Product> cartProducts = userService.getCart(pageNo, PAGE_SIZE);
		
		return cartProducts.isEmpty() ? ResponseEntity.noContent().build() :
			ResponseEntity.ok(cartProducts);
	}

	@Override
	@PutMapping(value = ADD_TO_CART_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addToCart(@PathVariable Integer pid, @PathVariable Integer quantity) {
		Integer result = userService.addToCart(pid, quantity);
		
		return (result>0)? ResponseEntity.ok(null): ResponseEntity.badRequest().body(null);
	}

	@Override
	@DeleteMapping(value = REMOVE_FROM_CART_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> removeFromCart(@PathVariable Integer pid) {
		Integer result = userService.removeFromCart(pid);
		
		return (result>0)? ResponseEntity.ok(null): ResponseEntity.badRequest().body(null);
	}

	@Override
	@PatchMapping(value = BUY_CART_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> buyCart(@PathVariable Integer payId) {
		Integer result = userService.buyCart(payId);
		
		return (result>0)? ResponseEntity.ok(null): ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
		
	}

	@Override
	@GetMapping(value = GET_ORDER_HISTORY_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Orders>> getOrderHistory(@PathVariable Integer pageNo) {
		List<Orders> orderHistories = userService.getOrderHistory(pageNo, PAGE_SIZE);
		
		return orderHistories.isEmpty()? ResponseEntity.noContent().build(): 
			ResponseEntity.ok(orderHistories);
	}

	@Override
	@GetMapping(value = GET_REVIEW_OF_PRODUCT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ReviewWithoutOrderId>> getReviewOfProduct(@PathVariable Integer pid, @PathVariable Integer pageNo) {
		List<ReviewWithoutOrderId> reviews = userService.getReviewOfProduct(pid, pageNo, PAGE_SIZE);
		
		return reviews.isEmpty()? ResponseEntity.noContent().build(): 
			ResponseEntity.ok(reviews);
	}

	@Override
	@PostMapping(value = GIVE_REVIEW_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> giveReview(@PathVariable Integer oid, @RequestBody Review review) {
		review.setId(oid);
		
		Integer result = userService.giveReview(review);
		
		return (result>0)? ResponseEntity.ok(null): ResponseEntity.badRequest().body(null);
	}

	@Override
	@GetMapping(value = DOWNLOAD_TRANSCRIPT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TranscriptDto> downloadTranscript(@PathVariable Integer oid) throws IOException {
		TranscriptDto transcript = userService.downloadTranscript(oid);
		
		if(transcript==null)
			return ResponseEntity.badRequest().body(null);
		
		try {
			File file = new File("C:\\Users\\gargp\\Desktop\\SpringWork\\OnlineShoppingAppSpring\\"
					+ String.valueOf(transcript.getId()) + ".txt");
			file.createNewFile();
			
			FileWriter writer = new FileWriter(file.getAbsolutePath());
			writer.write(transcript.toString());
			writer.flush();
			writer.close();
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return ResponseEntity.ok(transcript);
	}
	
}