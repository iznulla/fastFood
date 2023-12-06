package com.test.fastFood.repository;

import com.test.fastFood.entity.restaurant.RestaurantFilial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantFilialRepository extends JpaRepository<RestaurantFilial, Long> {
}
