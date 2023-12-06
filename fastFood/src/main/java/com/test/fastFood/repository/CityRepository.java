package com.test.fastFood.repository;

import com.test.fastFood.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
    Optional<CityEntity> findByName(String name);
}
