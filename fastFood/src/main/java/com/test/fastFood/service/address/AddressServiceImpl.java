package com.test.fastFood.service.address;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.address.CityEntity;
import com.test.fastFood.entity.address.CountryEntity;
import com.test.fastFood.repository.AddressRepository;
import com.test.fastFood.repository.CityRepository;
import com.test.fastFood.repository.CountryRepository;
import com.test.fastFood.utils.ConvertDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Override
    public Optional<Address> createAddress(AddressDto addressDto) {
        CountryEntity country = countryRepository.findByName(addressDto.getCountry()).orElseThrow();
        CityEntity city = cityRepository.findByName(addressDto.getCity()).orElseThrow();

        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(addressDto.getStreet());
        address.setLongitude(addressDto.getLongitude());
        address.setLatitude(addressDto.getLatitude());

        addressRepository.save(address);

        return Optional.of(address);
    }

    @Override
    public Optional<Address> updateAddress(Long id, AddressDto addressDto) {
        Address address = addressRepository.findById(id).orElseThrow();

        CountryEntity country = countryRepository.findByName(addressDto.getCountry()).orElseThrow();
        CityEntity city = cityRepository.findByName(addressDto.getCity()).orElseThrow();

        address.setCountry(country);
        address.setCity(city);
        address.setStreet(addressDto.getStreet());

        addressRepository.save(address);

        return Optional.of(address);
    }

    @Override
    public Optional<AddressDto> getAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow();
        return Optional.of(ConvertDtoUtils.convertAddressToDto(address));
    }

    @Override
    public void deleteAddressById(Long id) {
        addressRepository.deleteById(id);
    }
}
