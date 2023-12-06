package com.test.fastFood.repository;

import com.test.fastFood.entity.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
