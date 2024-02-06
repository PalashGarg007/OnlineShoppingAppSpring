package com.genpact.onlineShoppingApp.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.genpact.onlineShoppingApp.entity.Customer;
import com.genpact.onlineShoppingApp.entity.Payment;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;
import com.genpact.onlineShoppingApp.exception.InvalidInputException;
import com.genpact.onlineShoppingApp.exception.InvalidSQLQueryException;
import com.genpact.onlineShoppingApp.repository.AdminRepository;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {
	
	@InjectMocks
	AdminServiceImpl adminServiceImpl;
	
	@Mock
	AdminRepository adminRepository;
	
	@Mock
	ObjectMapper mapper;
	
	@Mock
	ObjectWriter objectWriter;
	
	@Mock
	Page<Customer> mockPageCustomer;
	
	@Mock
	Page<Shopkeeper> mockPageShopkeeper;
	
	@Mock
	Page<Payment> mockPagePayment;

    @Test
    @DisplayName("getCustomers: when database has customer to show.")
    void getCustomersTest() throws IOException {
		@SuppressWarnings("unchecked")
		List<Customer> customers = mock(List.class);
		
		when(adminRepository.getCustomers(anyInt(), anyInt())).thenReturn(mockPageCustomer);
		when(mockPageCustomer.getContent()).thenReturn(customers);
		when(mapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
		when(objectWriter.writeValueAsString(anyList())).thenReturn("Mock list");
		
		ResponseEntity<String> responseEntity = adminServiceImpl.getCustomers(1);
		
		assertAll(
				() -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
				() -> assertNotNull(responseEntity.getBody())
				);
	}

    @Test
    @DisplayName("getCustomers: when database has not customer to show.")
    void getCustomerTest2() throws IOException {
		when(adminRepository.getCustomers(anyInt(), anyInt())).thenReturn(mockPageCustomer);
		when(mockPageCustomer.getContent()).thenReturn(Collections.emptyList());

		ResponseEntity<String> responseEntity = adminServiceImpl.getCustomers(1);

		assertAll(
				() -> assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode()),
				() -> assertNotNull(responseEntity.getBody())
				);
	}

    @Test
    @DisplayName("getShopkeepers: when database has shopkeeper to show.")
    void getShopkeepersTest() throws IOException {
		@SuppressWarnings("unchecked")
		List<Shopkeeper> shopkeepers = mock(List.class);
		
		when(adminRepository.getShopkeepers(anyInt(), anyInt())).thenReturn(mockPageShopkeeper);
		when(mockPageShopkeeper.getContent()).thenReturn(shopkeepers);
		when(mapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
		when(objectWriter.writeValueAsString(anyList())).thenReturn("Mock list");
		
		ResponseEntity<String> responseEntity = adminServiceImpl.getShopkeepers(1);
		
		assertAll(
				() -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
				() -> assertNotNull(responseEntity.getBody())
				);
	}

    @Test
    @DisplayName("getShopkeepers: when database has no shopkeeper to show.")
    void getShopkeepersTest2() throws IOException {
		when(adminRepository.getShopkeepers(anyInt(), anyInt())).thenReturn(mockPageShopkeeper);
		when(mockPageShopkeeper.getContent()).thenReturn(Collections.emptyList());
		
		ResponseEntity<String> responseEntity = adminServiceImpl.getShopkeepers(1);

		assertAll(
				() -> assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode()),
				() -> assertNotNull(responseEntity.getBody())
				);
	}

    @Test
    @DisplayName("getPayments: when database has payment to show.")
    void getPaymentsTest() throws IOException {
		@SuppressWarnings("unchecked")
		List<Payment> payments = mock(List.class);
		
		when(adminRepository.getPayments(anyInt(), anyInt())).thenReturn(mockPagePayment);
		when(mockPagePayment.getContent()).thenReturn(payments);
		when(mapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
		when(objectWriter.writeValueAsString(anyList())).thenReturn("Mock list");
		
		ResponseEntity<String> responseEntity = adminServiceImpl.getPayments(1);
		
		assertAll(
				() -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
				() -> assertNotNull(responseEntity.getBody())
				);
	}

    @Test
    @DisplayName("getPayments: when database has no payment to show.")
    void getPaymentsTest2() throws IOException {
		when(adminRepository.getPayments(anyInt(), anyInt())).thenReturn(mockPagePayment);
		when(mockPagePayment.getContent()).thenReturn(Collections.emptyList());
		
		ResponseEntity<String> responseEntity = adminServiceImpl.getPayments(1);

		assertAll(
				() -> assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode()),
				() -> assertNotNull(responseEntity.getBody())
				);
	}

    @ParameterizedTest
    @DisplayName("addNewPayment: Invalid discount")
    @ValueSource(doubles = {-0.1, 100.1})
    void addNewPaymentTest(Double discount) {
		Payment payment = mock(Payment.class);
		
		when(payment.getDiscount()).thenReturn(discount);
		
		assertThrows(InvalidInputException.class, ()-> adminServiceImpl.addNewPayment(payment));
	}

    @Test
    @DisplayName("addNewPayment: adding an new payment")
    void addNewPaymentTest2() throws InvalidSQLQueryException, SQLIntegrityConstraintViolationException, IOException {
		Payment payment = mock(Payment.class);
		String paymentString = null;
		
		when(payment.getDiscount()).thenReturn(0.0);
		when(adminRepository.addNewPayment(payment)).thenReturn(payment);
		when(mapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
		when(objectWriter.writeValueAsString(payment)).thenReturn(paymentString);
		
		ResponseEntity<String> responseEntity = adminServiceImpl.addNewPayment(payment);
		
		assertAll(
				() -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
				() -> assertEquals(paymentString, responseEntity.getBody())
				);
	}
    
    @ParameterizedTest
    @DisplayName("updateDiscountById: Invalid discount")
    @ValueSource(doubles = {-0.1, 100.1})
    void updateDiscountByIdTest(Double discount) {
		Payment payment = mock(Payment.class);
		
		when(payment.getDiscount()).thenReturn(discount);
		
		assertThrows(InvalidInputException.class, ()-> adminServiceImpl.updateDiscountById(payment));
	}
    
    @Test
    @DisplayName("updateDiscountById: adding an new payment")
    void updateDiscountByIdTest2() throws InvalidSQLQueryException, SQLIntegrityConstraintViolationException, IOException {
		Payment payment = mock(Payment.class);
		String paymentString = null;
		
		when(payment.getDiscount()).thenReturn(0.0);
		when(adminRepository.updateDiscountById(payment)).thenReturn(payment);
		when(mapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
		when(objectWriter.writeValueAsString(payment)).thenReturn(paymentString);
		
		ResponseEntity<String> responseEntity = adminServiceImpl.updateDiscountById(payment);
		
		assertAll(
				() -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
				() -> assertEquals(paymentString, responseEntity.getBody())
				);
	}
    
    @Test
    @DisplayName("removePaymentById: removing the payment by given id.")
    void removePaymentByIdTest() throws IOException {
    	Payment payment = mock(Payment.class);
    	String paymentString = null;
    	
    	when(adminRepository.removePaymentById(anyInt())).thenReturn(payment);
    	when(mapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
		when(objectWriter.writeValueAsString(payment)).thenReturn(paymentString);
		
		ResponseEntity<String> responseEntity = adminServiceImpl.removePaymentById(anyInt());
		
		assertAll(
				() -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
				() -> assertEquals(paymentString, responseEntity.getBody())
				);
    }
    
}
