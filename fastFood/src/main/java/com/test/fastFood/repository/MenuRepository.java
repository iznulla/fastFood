package com.test.fastFood.repository;

import com.test.fastFood.entity.restaurant.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    Optional<MenuEntity> findByName(String name);
}
