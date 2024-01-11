package com.test.fastFood.service.address;

import com.test.fastFood.dto.address.AddressDto;
import com.test.fastFood.entity.address.Address;
import com.test.fastFood.entity.address.CityEntity;
import com.test.fastFood.entity.address.CountryEntity;
import com.test.fastFood.exception.ResourceNotFoundException;
import com.test.fastFood.repository.AddressRepository;
import com.test.fastFood.repository.CityRepository;
import com.test.fastFood.repository.CountryRepository;
import com.test.fastFood.utils.ConvertDtoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    @Override
    public Optional<Address> createAddress(AddressDto addressDto) {
        CountryEntity country = countryRepository.findByName(addressDto.getCountry()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Country with name: %s not found", addressDto.getCountry())));
        CityEntity city = cityRepository.findByName(addressDto.getCity()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("City with name: %s not found", addressDto.getCity())));
        try {
            Address address = new Address();
            address.setCountry(country);
            address.setCity(city);
            address.setStreet(addressDto.getStreet());
            address.setLongitude(addressDto.getLongitude());
            address.setLatitude(addressDto.getLatitude());
            addressRepository.save(address);
            return Optional.of(address);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException("Invalid input params");
        }
    }

    @Override
    public Optional<Address> updateAddress(Long id, AddressDto addressDto) {
        Address address = addressRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Address with id: %s not found", id)));

        CountryEntity country = countryRepository.findByName(addressDto.getCountry()).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Country with name: %s not found", addressDto.getCountry()))
        );
        CityEntity city = cityRepository.findByName(addressDto.getCity()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("City with name: %s not found", addressDto.getCity())));

        try {
            address.setCountry(country);
            address.setCity(city);
            address.setStreet(addressDto.getStreet());
            address.setLatitude(addressDto.getLatitude());
            address.setLongitude(addressDto.getLongitude());
            addressRepository.save(address);

            return Optional.of(address);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException("Invalid input params");
        }
    }

    @Override
    public Optional<AddressDto> getAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Address with id: %s not found", id)));
        return Optional.of(ConvertDtoUtils.convertAddressToDto(address));
    }

    @Override
    public void deleteAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Address with id: %s not found", id)));
        addressRepository.deleteById(address.getId());
    }
}
