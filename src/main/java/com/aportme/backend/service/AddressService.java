package com.aportme.backend.service;

import com.aportme.backend.entity.Address;
import com.aportme.backend.entity.dto.address.AddOrUpdateAddressDTO;
import com.aportme.backend.entity.dto.address.AddressDTO;
import com.aportme.backend.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressDTO getById(Long id) {
        Address address = findById(id);
        return modelMapper.map(address, AddressDTO.class);
    }

    public ResponseEntity<Object> update(Long id, AddOrUpdateAddressDTO addressDTO) {
        Address address = findById(id);
        modelMapper.map(addressDTO, address);
        addressRepository.save(address);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Address create(AddOrUpdateAddressDTO addressDTO) {
        Address dbAddress = modelMapper.map(addressDTO, Address.class);
        return addressRepository.save(dbAddress);
    }

    private Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }
}
