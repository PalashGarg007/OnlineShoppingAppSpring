package com.genpact.onlineShoppingApp.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	@Override
	public Map<Integer, String> getProducts() {
		Map<Integer, String> productMap = new HashMap<Integer, String>();
		productMap.put(1, "Laptop");
        productMap.put(2, "Smartphone");
        productMap.put(3, "Headphones");
        productMap.put(4, "Camera");
        productMap.put(5, "Desk Chair");
        productMap.put(6, "Coffee Maker");
        productMap.put(7, "Fitness Tracker");
        productMap.put(8, "Backpack");
        productMap.put(9, "Printer");
        productMap.put(10, "Desk Lamp");
        
        return productMap;
	}
}
