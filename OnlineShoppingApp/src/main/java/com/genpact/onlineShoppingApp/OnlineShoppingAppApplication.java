package com.genpact.onlineShoppingApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.service.AdminService;
import com.genpact.onlineShoppingApp.service.VendorService;
import com.genpact.onlineShoppingApp.service.VendorServiceImpl;

@SuppressWarnings("unused")
@RestController
@SpringBootApplication
public class OnlineShoppingAppApplication{
//public class OnlineShoppingAppApplication implements CommandLineRunner{
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private static VendorService vendorService;
	
	@GetMapping("/")
	 String home() {
		return "Hello World!";
	 }
	
	public static void main(String[] args) {
		SpringApplication.run(OnlineShoppingAppApplication.class, args);
		
		streamExample();
		
//		Thread thread1 = new Thread(
//				() -> {
//					while(true) {
//						System.out.println(vendorService.totalRevinue());
//						try{Thread.sleep(10000);} catch(Exception e) {}
//					}
//				});
//		
//		thread1.start();
		
	}

//	@Override
	public void run(String... args) throws Exception {
//		adminService.getCustomers(0);
//		adminService.getShopkeepers(0);
//		adminService.addNewPayment();
//		adminService.getPayments(0);
//		adminService.updateDiscountById();
		
//		vendorService.createAccount();
//		vendorService.shopkeeperLogin();
//		vendorService.addNewProduct();
//		vendorService.getProducts(0);
//		vendorService.changePersonalInformadtion();
	}
	
	private static void streamExample() {
//		List<Integer> li = Arrays.asList(1,3,5,6,2,4,8,7,7,7);
//		
//		List<Integer> newStreamList = li.stream()
//				.map(x -> x*x)
//				.filter(x -> x<30)
//				.sorted((o1, o2) -> (o1>o2)?-1:((o2>o1)?+1:0))
//				.collect(Collectors.toList());
//		
//		Integer newStreamValue = li.stream()
//				.filter(x -> x%2==0)
//				.reduce(0, (value, x) -> value+x);
//		
//		System.out.println("Sum of all even number of:" + li + " is: " + newStreamValue);
//		System.out.println(newStreamList);
//		newStreamList.forEach(x -> System.out.println(x));
		
		List<Integer> li = Arrays.asList(13,12,13,24,24,33,15,26,15);
		
		List<Integer> distinctIntegers = li.stream()
				.sorted((o1, o2) -> (o1>o2)? 1:(o2>o1)? -1:0)
				.distinct()
				.collect(Collectors.toList());
		
		List<String> colour = Arrays.asList("Red", "Green", "Blue", "Pink", "Brown");
		
		List<String> assendingColour = colour.stream()
				.sorted((o1, o2) -> o1.compareTo(o2))
				.collect(Collectors.toList());
		
		List<String> descendingColour = colour.stream()
				.sorted((o1, o2) -> o2.compareTo(o1))
				.collect(Collectors.toList());
		
		System.out.println("Distinct integers: " + distinctIntegers);
		System.out.println("Assending Order: " + assendingColour);
		System.out.println("Descending Order: " + descendingColour);
		
	}
	
	// Actual method available in VendorServiceImpl
//	public Double totalRevinue() {
//		List<Product> productList = vendorRepository.inventoryList();
//		Double revinue = productList.stream()
//				.map((product)-> product.getCost()*product.getPurchased())
//				.reduce(0.0, (sum, x) -> sum + x);
//		return revinue;
//	}

}
