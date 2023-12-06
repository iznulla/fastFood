package com.test.fastFood.repository;

import com.test.fastFood.entity.order.OrderMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMenuRepository extends JpaRepository<OrderMenuEntity, Long> {
}
