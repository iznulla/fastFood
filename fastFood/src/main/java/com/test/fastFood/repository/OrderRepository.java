package com.test.fastFood.repository;

import com.test.fastFood.entity.order.OrderEntity;
import com.test.fastFood.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUser(UserEntity user);
}
