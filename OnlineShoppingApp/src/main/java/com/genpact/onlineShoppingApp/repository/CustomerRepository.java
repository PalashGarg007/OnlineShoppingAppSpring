package com.genpact.onlineShoppingApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.genpact.onlineShoppingApp.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	List<Customer> findByUserNameAndPassword(String userName, String password);

}
