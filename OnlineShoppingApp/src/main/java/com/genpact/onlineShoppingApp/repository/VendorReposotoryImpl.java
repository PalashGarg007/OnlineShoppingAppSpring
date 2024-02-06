package com.genpact.onlineShoppingApp.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.genpact.onlineShoppingApp.entity.Orders;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;
import com.genpact.onlineShoppingApp.exception.OSAException;

import jakarta.transaction.Transactional;

@Component
public class VendorReposotoryImpl implements VendorRepository{
	static private Shopkeeper currentShopkeeper;
	
	@Autowired
	private ShopkeeperRepository shopkeeperRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Override
	@Transactional
	public Integer createAccount(String name, String contact,
			String email, String userName, String password) {
		int result = 0;
		if(!shopkeeperRepository.findByUserName(userName).isEmpty())
			return result;
		else
			result = 1;
		
		Shopkeeper shopkeeper = new Shopkeeper(name, contact, email,
	    		userName, password);
		currentShopkeeper = shopkeeperRepository.save(shopkeeper);
		
		return result;
	}

	@Override
	public Integer shopkeeperLogin(String userName, String password) {
		int result = 0;
		List<Shopkeeper> shopkeeperList = shopkeeperRepository.findByUserNameAndPassword(userName, password);
		if(shopkeeperList.isEmpty())
			return result;
		else
			result = 1;
		
		currentShopkeeper = shopkeeperList.get(0);
		return result;
	}

	@Override
	@Transactional
	public Integer addNewProduct(String name, String brand,
			String category, Double cost, Integer warehouse) {
		int result = 0;
		if(!productRepository.findByAllIgnoringCaseSidAndNameAndCategory(currentShopkeeper.getId(), name, category).isEmpty())
			return result;
		else
			result = 1;
		
		Integer sid = currentShopkeeper.getId();
		Double rating = 0d;
		Integer purchased = 0;
		List<Product> similarProductList = productRepository.findByAllIgnoreCaseCategoryAndName(category, name);
		if(!similarProductList.isEmpty()) {
			rating = similarProductList.get(0).getRating();
			purchased = similarProductList.get(0).getPurchased();
		}
		Product product = new Product(sid, name, brand, category, cost, warehouse, rating, purchased);
		productRepository.save(product);
		
		return result;
	}

	
	@Override
	public Page<Product> getProducts(Integer pageNumber, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		return productRepository.findBySid(currentShopkeeper.getId(), pageRequest);
	}

	
	@Override
	public Shopkeeper getShopkeeper() {
		return currentShopkeeper;
	}

	@Override
	public Integer cahngePersonalInformadtion(String contact, String email, String password) {
		Integer result = 0;
		
		if(!contact.isEmpty()) {
			currentShopkeeper.setContact(contact);
			result = 1;
		}
		if(!email.isEmpty()) {
			currentShopkeeper.setEmail(email);
			result = 1;
		}
		if(!password.isEmpty()) {
			currentShopkeeper.setPassword(password);
			result = 1;
		}
		
		shopkeeperRepository.save(currentShopkeeper);
		return result;
	}

	@Override
	public Page<Object> viewUnacceptedOrders(Integer pageNumber, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		return ordersRepository.findByIdAndConfirmationFalse(currentShopkeeper.getId(), pageRequest);
	}
	
	@Transactional
	@Override
	public Integer setUnacceptedOrders(Orders orders, Boolean isAccepted)throws OSAException {
		Integer result = 0;
		
		if(isAccepted) {
			orders.setConfirmation(true);
			orders.setShippingDate(LocalDate.now());
			ordersRepository.save(orders);
			
			Optional<Product> product = productRepository.findById(orders.getPid());
			product.get().setWarehouse(product.get().getWarehouse()-orders.getQuantity());
			product.get().setPurchased(product.get().getPurchased()+orders.getQuantity());
			productRepository.save(product.get());
			
			result = 1;
		}
		
		return result;
	}
	
	@Override
	public List<Product> inventoryList() {
		return currentShopkeeper.getProducts();
	}
}
