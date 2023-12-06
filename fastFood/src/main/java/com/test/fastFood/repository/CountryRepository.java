package com.test.fastFood.repository;

import com.test.fastFood.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    Optional<CountryEntity> findByName(String name);
}
