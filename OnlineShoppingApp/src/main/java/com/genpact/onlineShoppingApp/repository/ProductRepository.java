package com.genpact.onlineShoppingApp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.genpact.onlineShoppingApp.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	List<Product> findByAllIgnoringCaseSidAndNameAndCategory(Integer sid, String name, String category);
	
	List<Product> findByAllIgnoreCaseCategoryAndName(String category, String name);
	
	Page<Product> findBySid(Integer sid, Pageable pageRequest);
	
}
