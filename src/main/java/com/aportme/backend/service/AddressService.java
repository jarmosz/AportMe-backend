package com.aportme.backend.service;

import com.aportme.backend.entity.Address;
import com.aportme.backend.entity.dto.address.AddOrUpdateAddressDTO;
import com.aportme.backend.entity.dto.address.AddressDTO;
import com.aportme.backend.exception.AddressNotFoundException;
import com.aportme.backend.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressDTO getAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(AddressNotFoundException::new);
        return modelMapper.map(address, AddressDTO.class);
    }

    public ResponseEntity<Object> updateAddress(Long id, AddOrUpdateAddressDTO addressDTO) {
        Address address = addressRepository.findById(id).orElseThrow(AddressNotFoundException::new);
        modelMapper.map(addressDTO, address);
        addressRepository.save(address);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Address createAddress(AddOrUpdateAddressDTO addressDTO) {
        Address dbAddress = modelMapper.map(addressDTO, Address.class);
        return addressRepository.save(dbAddress);
    }
}
