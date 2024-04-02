package com.genpact.onlineShoppingApp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.genpact.onlineShoppingApp.controller.AdminController;
import com.genpact.onlineShoppingApp.controller.UserController;
import com.genpact.onlineShoppingApp.controller.VendorController;
import com.genpact.onlineShoppingApp.controller.Views;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;

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

	public static void main(String[] args) {
		SpringApplication.run(OnlineShoppingAppApplication.class, args);
		
	}

	public void run(String... args) throws Exception {
//		new Examples().streamExample();
//		new BackgroundServices(vendorController);
		
//		System.out.println(new ApiHandler().shopkeeperLogInRequest("sophie_t", "turnerpass").toString());
	}

}