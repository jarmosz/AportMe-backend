package com.aportme.backend.service;

import com.aportme.backend.entity.Address;
import com.aportme.backend.entity.dto.address.AddressBaseDTO;
import com.aportme.backend.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public Address create(AddressBaseDTO addressDTO) {
        Address dbAddress = modelMapper.map(addressDTO, Address.class);
        return addressRepository.save(dbAddress);
    }
}
