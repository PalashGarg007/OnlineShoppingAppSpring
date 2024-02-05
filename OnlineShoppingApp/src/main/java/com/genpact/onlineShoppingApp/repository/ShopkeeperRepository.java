package com.genpact.onlineShoppingApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.genpact.onlineShoppingApp.entity.Shopkeeper;

@Repository
public interface ShopkeeperRepository extends JpaRepository<Shopkeeper, Integer>{
	
	List<Shopkeeper> findByUserName(String userName);
	
	List<Shopkeeper> findByUserNameAndPassword(String userName, String password);
}
