package com.genpact.onlineShoppingApp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.genpact.onlineShoppingApp.dto.UnacceptedOrders;
import com.genpact.onlineShoppingApp.entity.Orders;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
	
	@Query(value = "SELECT p.name as 'productName', p.brand, p.category, p.cost, o.id, " +
			"o.quantity, o.order_date, o.confirmation, c.name as 'customerName', " +
			"c.contact, c.address, o.amount FROM orders o " +
			"LEFT JOIN product p ON p.id = o.pid " +
			"LEFT JOIN customer c ON c.id = o.cid " +
			"WHERE p.sid=:sid ORDER BY o.id DESC",
			nativeQuery = true)
	Page<UnacceptedOrders> findBySidAndConfirmationFalse(Integer sid, Pageable pageable);
	
	List<Orders> findByCidOrderByOrderDateDesc(Integer cid, Pageable pageable);
	
}
