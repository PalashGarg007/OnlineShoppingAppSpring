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
import com.genpact.onlineShoppingApp.exception.InvalidInputException;
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
	public Shopkeeper createAccount(Shopkeeper shopkeeper) {
		if(!shopkeeperRepository.findByUserName(shopkeeper.getUserName()).isEmpty())
			throw new InvalidInputException("UserName already exist");
		
		Shopkeeper newShopkeeper = new Shopkeeper(shopkeeper.getName(), shopkeeper.getContact(),
				shopkeeper.getEmail(), shopkeeper.getUserName(), shopkeeper.getPassword());
		
		currentShopkeeper = shopkeeperRepository.save(newShopkeeper);
		
		return currentShopkeeper;
	}

	@Override
	public Shopkeeper shopkeeperLogin(String userName, String password) {
		List<Shopkeeper> shopkeeperList = shopkeeperRepository.findByUserNameAndPassword(userName, password);
		if(shopkeeperList.isEmpty())
			throw new InvalidInputException("Invalid UserName or Password");
		
		currentShopkeeper = shopkeeperList.get(0);
		
		return currentShopkeeper;
	}

	@Override
	@Transactional
	public Product addNewProduct(Product newProduct) {
		Integer sid = currentShopkeeper.getId();
		
		if(!productRepository
				.findByAllIgnoringCaseSidAndNameAndCategory(
						sid, newProduct.getName(), newProduct.getCategory())
				.isEmpty())
			return null;
		
		Double rating = 0d;
		List<Product> similarProductList = productRepository.findByAllIgnoreCaseCategoryAndName(
				newProduct.getCategory(), newProduct.getName());
		
		if(!similarProductList.isEmpty())
			rating = similarProductList.get(0).getRating();
		
		newProduct.setSid(sid);
		newProduct.setRating(rating);
		newProduct.setPurchased(0);
		
		return productRepository.save(newProduct); 
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
