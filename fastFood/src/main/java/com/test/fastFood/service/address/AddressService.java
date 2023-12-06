package com.test.fastFood.service.address;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.entity.address.Address;

import java.util.Optional;

public interface AddressService {
    Optional<Address> createAddress(AddressDto addressDto);
    Optional<Address> updateAddress(Long id, AddressDto addressDto);
    Optional<AddressDto> getAddressById(Long id);
    void deleteAddressById(Long id);
}
