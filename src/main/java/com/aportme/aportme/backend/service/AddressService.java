package com.aportme.aportme.backend.service;

import com.aportme.aportme.backend.entity.Address;
import com.aportme.aportme.backend.repository.AddressRepository;
import com.aportme.aportme.backend.utils.UtilsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address getById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public Address update(Long id, Address address) {
        Address dbAddress = addressRepository.findById(id).get();
        UtilsService.copyNonNullProperties(address, dbAddress);
        return addressRepository.save(dbAddress);
    }

    public Address create(Address address) {
        Address dbAddress = new Address();
        dbAddress.setCity(address.getCity());
        dbAddress.setStreet(address.getStreet());
        dbAddress.setHouseNumber(address.getHouseNumber());
        dbAddress.setZipCode(address.getZipCode());
        dbAddress.setFlatNumber(address.getFlatNumber());
        return addressRepository.save(dbAddress);
    }
}
