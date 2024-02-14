package com.genpact.onlineShoppingApp.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genpact.onlineShoppingApp.repository.UserService;

@Controller
@RequestMapping("/user")
public class UserControllerImpl implements UserController {
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	UserService userService;
	
	private final ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	//preparing the request : headers and body.
	//RequestPreparation.
	
	
	@Override
	@PutMapping("/orders2")
	public void placeOrder2(@RequestBody Integer[] orders) {
		List<Integer> productId = List.of(orders);
		Map<Integer, String> productMap = Collections.synchronizedMap(userService.getProducts());
		
		productId.parallelStream()
		.forEach(id -> System.out.println(id + " : " + productMap.get(id)));
	}
	
	
	@Override
	@PutMapping("/orders1")
	public void placeOrder1(@RequestBody Integer[] orders) {
		List<Integer> productId = List.of(orders);
		Map<Integer, String> productMap = Collections.synchronizedMap(userService.getProducts());
		
		productId.forEach(id -> executorService.submit(() ->
			System.out.println(id +" : "+ productMap.get(id))));
	}
}
