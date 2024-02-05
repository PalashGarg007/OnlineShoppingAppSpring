package com.genpact.onlineShoppingApp.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
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
	Page<Customer> mockPageCustomer;
	
	@Mock
	Page<Shopkeeper> mockPageShopkeeper;
	
	@Mock
	Page<Payment> mockPagePayment;

    @Test
    @DisplayName("getCustomers: when database has customer to show.")
    void getCustomersTest() throws IOException {
		ObjectWriter objectWriter = mock(ObjectWriter.class);
		@SuppressWarnings("unchecked")
		List<Customer> customers = mock(List.class);
		
		when(adminRepository.getCustomers(anyInt(), anyInt())).thenReturn(mockPageCustomer);
		when(mockPageCustomer.getContent()).thenReturn(customers);
		when(mapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
		when(objectWriter.writeValueAsString(anyList())).thenReturn("Mock list");
		
		ResponseEntity<String> responseEntity = adminServiceImpl.getCustomers(1);
		
//		verify(adminRepository, times(1)).getCustomers(Integer.valueOf(anyInt()), anyInt());
//		verify(mapper).writerWithDefaultPrettyPrinter();
//		verify(objectWriter).writeValueAsString(anyList());
		
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
		
//		verify(adminRepository, times(1)).getCustomers(anyInt(), anyInt());
//		verify(mapper.writerWithDefaultPrettyPrinter(), times(1)).writeValueAsString(anyString());

		ResponseEntity<String> responseEntity = adminServiceImpl.getCustomers(1);

		assertAll(
				() -> assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode()),
				() -> assertNotNull(responseEntity.getBody())
				);
	}

    @Test
    @DisplayName("getShopkeepers: when database has shopkeeper to show.")
    void getShopkeepersTest() throws IOException {
		ObjectWriter objectWriter = mock(ObjectWriter.class);
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
		ObjectWriter objectWriter = mock(ObjectWriter.class);
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
    void addNewPaymentTest2() throws InvalidSQLQueryException, SQLIntegrityConstraintViolationException {
		Payment payment = mock(Payment.class);
		
		when(payment.getDiscount()).thenReturn(0.0);
		when(adminRepository.addNewPayment(payment)).thenReturn(payment);
//		doThrow(new InvalidSQLQueryException(anyString())).when(adminRepository).addNewPayment(payment);
		
		ResponseEntity<Payment> responseEntity = adminServiceImpl.addNewPayment(payment);
		
		assertAll(
				() -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
				() -> assertEquals(payment, responseEntity.getBody())
				);
		
	}
}
