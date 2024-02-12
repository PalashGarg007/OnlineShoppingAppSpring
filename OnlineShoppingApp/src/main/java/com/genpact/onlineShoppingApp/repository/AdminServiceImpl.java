package com.genpact.onlineShoppingApp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.genpact.onlineShoppingApp.entity.Customer;
import com.genpact.onlineShoppingApp.entity.Payment;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;
import com.genpact.onlineShoppingApp.exception.InvalidSQLQueryException;
import jakarta.transaction.Transactional;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ShopkeeperRepository shopkeeperRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Override
	public Page<Customer> getCustomers(Integer pageNumber, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		return customerRepository.findAll(pageRequest);
	}

	@Override
	public Page<Shopkeeper> getShopkeepers(Integer pageNumber, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		return shopkeeperRepository.findAll(pageRequest);
	}

	@Override
	public Payment addNewPayment(Payment payment) throws InvalidSQLQueryException{
		Payment newpayment = new Payment();
		newpayment.setDiscount(payment.getDiscount());
		newpayment.setMethod(payment.getMethod());
		Payment savedPayment = null;
		try {
			savedPayment = paymentRepository.save(newpayment);
		} catch (Exception e) {
			throw new InvalidSQLQueryException("Payment with the same method already exist.", e.getCause());
		}
		
		return savedPayment;
	}

	@Override
	public Page<Payment> getPayments(Integer pageNumber, Integer pageSize) {
		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
		return paymentRepository.findAll(pageRequest);
	}

	@Override
	@Transactional
	public Payment updateDiscountById(Payment payment) {
		Payment modifiedPayment = null;
		if(paymentRepository.existsById(payment.getId())) {
			modifiedPayment = paymentRepository.getReferenceById(payment.getId());
			modifiedPayment.setDiscount(payment.getDiscount());
			paymentRepository.saveAndFlush(modifiedPayment);
		}
		
		return modifiedPayment;
	}

	@Override
	@Transactional
	public Payment removePaymentById(Integer id) {
		Payment payment = null;
		if(paymentRepository.existsById(id)) {
			payment = paymentRepository.getReferenceById(id);
			paymentRepository.deleteById(id);
		}
		
		return payment;
	}

}
