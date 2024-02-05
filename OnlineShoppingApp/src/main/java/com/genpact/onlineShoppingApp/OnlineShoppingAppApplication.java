package com.genpact.onlineShoppingApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genpact.onlineShoppingApp.service.AdminService;
import com.genpact.onlineShoppingApp.service.VendorService;

@SuppressWarnings("unused")
@RestController
@SpringBootApplication
//public class OnlineShoppingAppApplication implements CommandLineRunner{
public class OnlineShoppingAppApplication{
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private VendorService vendorService;
	
	@RequestMapping("/")
	 String home() {
		return "Hello World!";
	 }
	
	public static void main(String[] args) {
		SpringApplication.run(OnlineShoppingAppApplication.class, args);
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

}
