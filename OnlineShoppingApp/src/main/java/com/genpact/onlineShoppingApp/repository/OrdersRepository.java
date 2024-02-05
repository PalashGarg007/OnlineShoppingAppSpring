package com.genpact.onlineShoppingApp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.genpact.onlineShoppingApp.entity.Orders;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
	
	@Query(value = "SELECT p.*, o.*, c.* FROM orders o LEFT JOIN product p ON p.id = o.pid LEFT JOIN c.id = o.cid WHERE o.sid=:id ORDER BY o.id DESC",
				nativeQuery = true)
	Page<Object> findByIdAndConfirmationFalse(Integer id, Pageable pageable);
	
}
