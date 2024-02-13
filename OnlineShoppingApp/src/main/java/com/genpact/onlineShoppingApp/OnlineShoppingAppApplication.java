package com.genpact.onlineShoppingApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.genpact.onlineShoppingApp.controller.AdminController;
import com.genpact.onlineShoppingApp.controller.UserController;
import com.genpact.onlineShoppingApp.controller.VendorController;
import com.genpact.onlineShoppingApp.controller.Views;

@SuppressWarnings("unused")
@RestController
@SpringBootApplication
//public class OnlineShoppingAppApplication{
public class OnlineShoppingAppApplication implements CommandLineRunner{
	@Autowired
	private AdminController adminController;
	
	@Autowired
	private VendorController vendorController;
	
	@Autowired
	private UserController userController;

    @Bean
    ObjectMapper getObjectMapper() {
		return JsonMapper.builder()
				.findAndAddModules()
				.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
				.build();
	}
    
    @Bean
    Views getViews() {
    	return new Views();
    }
	
	public static void main(String[] args) {
		SpringApplication.run(OnlineShoppingAppApplication.class, args);
		
	}

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
		
		streamExample();
//		new BackgroundServices(vendorService);
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
		
//		----------------------------------------------------------------
//		
//		List<Integer> li = Arrays.asList(13,12,13,24,24,33,15,26,15);
//		
//		li.stream()
//				.sorted((o1, o2) -> (o1>o2)? 1:(o2>o1)? -1:0)
//				.distinct()
//				.forEach(x -> System.out.println(x));
//		
//		List<String> colour = Arrays.asList("Red", "Green", "Blue", "Pink", "Brown");
//		
//		colour.stream()
//				.sorted((o1, o2) -> o1.compareTo(o2))
//				.forEach(x -> System.out.println(x));
//		
//		colour.stream()
//				.sorted((o1, o2) -> o2.compareTo(o1))
//				.forEach(x -> System.out.println(x));
//		
	}

}

class BackgroundServices implements Runnable {
	VendorController vendorController;
	
	public BackgroundServices(VendorController vendorController) {
		this.vendorController = vendorController;
		Thread thread1 = new Thread(this, "BackgroundServices");
		thread1.start();
	}
	
	public void run() {
		while(true) {
			System.out.println(vendorController.totalRevinue());
			try {Thread.sleep(1000);} catch(Exception e) {}
		}
	}
}
