package com.genpact.onlineShoppingApp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.genpact.onlineShoppingApp.entity.Favorite;
import com.genpact.onlineShoppingApp.entity.FavoriteId;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

	List<Favorite> findAllByCid(Integer id);

	List<Favorite> findByCid(Integer cid, Pageable pageable);

}
