package com.genpact.onlineShoppingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.genpact.onlineShoppingApp.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
