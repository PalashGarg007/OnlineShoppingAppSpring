package com.genpact.onlineShoppingApp.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.genpact.onlineShoppingApp.dto.ReviewWithoutOrderId;
import com.genpact.onlineShoppingApp.dto.TranscriptDto;
import com.genpact.onlineShoppingApp.entity.Cart;
import com.genpact.onlineShoppingApp.entity.CartId;
import com.genpact.onlineShoppingApp.entity.Customer;
import com.genpact.onlineShoppingApp.entity.Favorite;
import com.genpact.onlineShoppingApp.entity.FavoriteId;
import com.genpact.onlineShoppingApp.entity.Orders;
import com.genpact.onlineShoppingApp.entity.Payment;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.entity.Review;
import com.genpact.onlineShoppingApp.exception.InvalidInputException;
import com.genpact.onlineShoppingApp.exception.ProductOutOfStockException;
import com.genpact.onlineShoppingApp.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private FavoriteRepository favoriteRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private OrdersRepository ordersRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	private Customer currentCustomer;
	
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

	@Override
	@Transactional
	public Customer createAccount(Customer customer) {
		Customer newCustomer = new Customer(customer.getName(), customer.getDob(), customer.getContact(),
				customer.getEmail(), customer.getAddress(), customer.getUserName(), customer.getPassword());
		try {
			currentCustomer = customerRepository.save(newCustomer);
		} catch (Exception e) {
			throw new InvalidInputException("A field is repeating alrady exist: " + e.getMessage());
		}
		
		return currentCustomer;
		
	}

	@Override
	public Customer customerLogin(String userName, String password) {
		List<Customer> customerList = customerRepository.findByUserNameAndPassword(userName, password);
		if(customerList.isEmpty())
			throw new InvalidInputException("Invalid UserName or Password");
		
		currentCustomer = customerList.get(0);
		
		return currentCustomer;
	}

	@Override
	public Customer cahngePersonalInformadtion(String contact, String email, String address, String password) {
		if(!contact.isEmpty())
			currentCustomer.setContact(contact);
		if(!email.isEmpty())
			currentCustomer.setEmail(email);
		if(!address.isBlank())
			currentCustomer.setAddress(address);
		if(!password.isEmpty())
			currentCustomer.setPassword(password);
		
		currentCustomer = customerRepository.save(currentCustomer);
		
		return currentCustomer;
	}
	
	@Override
	public List<Product> searchProducts(String tokens) {
		String[] keywords = tokens.split("+");
		List<Product> productsList = new LinkedList<>();
		
		for(String keyword: keywords) {
			productsList.addAll(productRepository
					.findByAllIgnoringCaseNameOrCategoryOrBrandOrderByCost(keyword, keyword, keyword));
		}
		
		return productsList.stream()
				.filter(product -> product.getWarehouse()!=0)
				.distinct()
				.toList();
	}

	@Override
	@Async
	public List<Product> getFavorite(Integer pageNo, Integer pageSize) {
		PageRequest currentPage = PageRequest.of(pageNo, pageSize);
		
		return favoriteRepository
				.findByCid(currentCustomer.getId(), currentPage)
				.stream()
				.map(fav-> productRepository.findById(fav.getPid()).orElse(null))
				.filter(product-> product!=null)
				.toList();
	}

	@Override
	public Integer addToFavorite(Integer pid) {
		Favorite newFavorite = favoriteRepository.save(new Favorite(currentCustomer.getId(), pid));
		
		return (newFavorite!=null)? 1: 0;
	}

	@Override
	public Integer removeFromFavorite(Integer pid) {
		Integer result = 0;
		FavoriteId favoriteId = new FavoriteId(currentCustomer.getId(), pid);
		
		if(favoriteRepository.findById(favoriteId)==null)
			throw new ResourceNotFoundException("No Product with id: "+ pid +" found.");
		else {
			favoriteRepository.deleteById(favoriteId);
			result = 1;
		}
		
		return result;
	}

	@Override
	@Async
	public List<Product> getCart(Integer pageNo, Integer pageSize) {
		PageRequest currentPage = PageRequest.of(pageNo, pageSize);
		
		return cartRepository
				.findByCid(currentCustomer.getId(), currentPage)
				.stream()
				.map(cart-> productRepository.findById(cart.getPid()).orElse(null))
				.filter(product-> product!=null)
				.toList();
	}
	
	@Override
	public Integer addToCart(Integer pid, Integer quantity) {
		Integer result = 0;
		
		if(productRepository.getReferenceById(pid).getWarehouse() < quantity)
			throw new ProductOutOfStockException("Product with id "+ pid +" is insufficient");
		else {
			cartRepository.save(new Cart(currentCustomer.getId(), pid, quantity));
			result = 1;
		}
		
		return (result>0)? 1: 0;
	}

	@Override
	public Integer removeFromCart(Integer pid) {
		Integer result = 0;
		CartId cartId = new CartId(currentCustomer.getId(), pid);
		
		if(cartRepository.findById(cartId)==null)
			throw new ResourceNotFoundException("No Product with id: "+ pid +" found.");
		else {
			cartRepository.deleteById(cartId);
			result = 1;
		}
		
		return result;
	}

	@Override
	@Async
	public Integer buyCart(Integer payId) {
		List<Cart> cartList = cartRepository.findByCid(currentCustomer.getId());
		Payment payment = paymentRepository.findById(payId).orElse(null);
		
		if(payment==null)
			throw new ResourceNotFoundException("can't find payment with id "+ payId);
		
		cartList.forEach(cart-> {
			Product product = productRepository.getReferenceById(cart.getPid());
			
			if(product.getWarehouse()>=cart.getQuantity()) {
				ordersRepository.save(new Orders(currentCustomer.getId(), product.getId(),
						product.getCost()*cart.getQuantity()*(1 - payment.getDiscount()/100),
						cart.getQuantity(), LocalDate.now(), null, payId, false));
				
				removeFromCart(product.getId());
				
				product.setWarehouse(product.getWarehouse()-cart.getQuantity());
				product.setPurchased(product.getPurchased()+cart.getQuantity());
				productRepository.save(product);
			}
		});
		
		return 1;
	}

	
	@Override
	public List<Orders> getOrderHistory(Integer pageNo, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
		
		return ordersRepository.findByCidOrderByOrderDateDesc(currentCustomer.getId(), pageRequest);
	}

	
	@Override
	public List<ReviewWithoutOrderId> getReviewOfProduct(Integer pid, Integer pageNo, Integer pageSize) {
		List<ReviewWithoutOrderId> reviews = reviewRepository
				.getReviewByPid(pid, pageNo*pageSize, pageSize);
		return reviews;
	}

	@Override
	public Integer giveReview(Review review) {
		if(review.getReview().isBlank() || review.getRating()<0 || review.getRating()>5)
			return 0;
		
		Orders order = ordersRepository.findById(review.getId()).get();
		Product product = productRepository.findById(order.getPid()).get();
		Integer noOfReviews = reviewRepository.noOfReviewsOfProduct(order.getPid());
		
		reviewRepository.save(review);
		
		product.setRating((product.getRating() * noOfReviews + review.getRating()) / (noOfReviews + 1));
		productRepository.save(product);
		
		return 1;
	}

	@Override
	public TranscriptDto downloadTranscript(Integer oid) {
		Orders orders = ordersRepository.getReferenceById(oid);
		Product product = orders.getProduct();
		Payment payment = orders.getPayment();
		
		return new TranscriptDto(oid, orders.getQuantity(),
				orders.getAmount(), orders.getOrderDate(),
				product.getName(), product.getCost(), payment.getDiscount());
	}

}
