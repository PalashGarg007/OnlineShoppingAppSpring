package com.genpact.onlineShoppingApp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.genpact.onlineShoppingApp.entity.Cart;
import com.genpact.onlineShoppingApp.entity.CartId;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId>{

	List<Cart> findByCid(Integer cid, Pageable pageable);

}
