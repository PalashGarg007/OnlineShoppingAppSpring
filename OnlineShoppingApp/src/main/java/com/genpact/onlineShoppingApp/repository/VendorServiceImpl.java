package com.genpact.onlineShoppingApp.repository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.genpact.onlineShoppingApp.dto.UnacceptedOrders;
import com.genpact.onlineShoppingApp.entity.Orders;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;
import com.genpact.onlineShoppingApp.exception.InvalidInputException;
import jakarta.transaction.Transactional;

@Service
public class VendorServiceImpl implements VendorService{
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
	public Product addAndUpdateProduct(Product newProduct) {
		Integer sid = currentShopkeeper.getId();
		
		List<Product> productList = productRepository
				.findByAllIgnoringCaseSidAndNameAndCategory(
				sid, newProduct.getName(), newProduct.getCategory()); 
		if(!productList.isEmpty()) {
			Product existingProduct = productList.get(0);
			if(newProduct.getCost()!=null)
				existingProduct.setCost(newProduct.getCost());
			existingProduct.setWarehouse(newProduct.getWarehouse());
			return productRepository.save(existingProduct);
		}
		
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
	public Shopkeeper cahngePersonalInformadtion(String contact, String email, String password) {

		if(!contact.isEmpty())
			currentShopkeeper.setContact(contact);
		if(!email.isEmpty())
			currentShopkeeper.setEmail(email);
		if(!password.isEmpty())
			currentShopkeeper.setPassword(password);
		
		currentShopkeeper = shopkeeperRepository.save(currentShopkeeper);
		
		return currentShopkeeper;
	}

	@Override
	public Page<UnacceptedOrders> getUnacceptedOrders(Integer pageNumber, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		return ordersRepository.findByIdAndConfirmationFalse(currentShopkeeper.getId(), pageRequest);
	}
	
	@Override
	@Transactional
	@Async
	public void setUnacceptedOrders(List<UnacceptedOrders> unacceptedOrders) {
		unacceptedOrders.forEach(x -> {
			Orders orders = ordersRepository.findById(x.getId()).orElseThrow();
			orders.setConfirmation(true);
			orders.setShippingDate(LocalDate.now());
			orders = ordersRepository.save(orders);
			
			Product product = productRepository.findById(orders.getPid()).orElseThrow();
			product.setWarehouse(product.getWarehouse() - orders.getQuantity());
			product.setPurchased(product.getPurchased() + orders.getQuantity());
			productRepository.save(product);
		});
	}
	
	@Override
	public List<Product> inventoryList() {
		return currentShopkeeper.getProducts();
	}

	
	@Override
	public List<Product> searchProducts(String condition) {
		List<Product> products = new LinkedList<>();
		String[] conditions = condition.split(" ");
		
		for(String minCondition: conditions) {
			products.addAll(productRepository.findByAllIgnoringCaseNameOrCategoryOrBrandOrderByCost(minCondition, minCondition, minCondition));
		}
		
		return products.stream()
				.distinct()
				.toList();
	}

	@Override
	@Async
	public void addAndUpdateProductsByFile(Stream<String> tokens) {
		tokens.parallel().forEach(data -> {
			String[] productStrings = data.split(",");
			Product product = new Product();
			
			product.setName(productStrings[0].strip());
			product.setBrand(productStrings[1].strip());
			product.setCategory(productStrings[2].strip());
			product.setCost(Double.valueOf(productStrings[3].strip()));
			product.setWarehouse(Integer.valueOf(productStrings[4].strip()));
			
			addAndUpdateProduct(product);
		});
		
	}
	
}
