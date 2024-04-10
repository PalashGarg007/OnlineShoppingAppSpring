package com.genpact.onlineShoppingApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.genpact.onlineShoppingApp.dto.ReviewWithoutOrderId;
import com.genpact.onlineShoppingApp.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
	@Query(nativeQuery = true, value = ""
			+ "SELECT r.review, r.rating FROM review r "
            + "LEFT JOIN order o ON r.id = o.id "
            + "WHERE o.pid = :pid "
            + "ORDER BY o.shipping_date DESC"
            + "OFFSET :offset LIMIT :limit")
	List<ReviewWithoutOrderId> getReviewByPid(@Param("pid") Integer pid,
			@Param("offset") Integer offset, @Param("limit") Integer limit);
	
	@Query(nativeQuery = true, value = ""
			+ "SELECT COUNT(*) FROM review r "
            + "LEFT JOIN order o ON r.id = o.id "
            + "WHERE o.pid = :pid ")
	Integer noOfReviewsOfProduct(@Param("pid") Integer pid);
}
