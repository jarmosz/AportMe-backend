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

    public Address save(AddressBaseDTO addressDTO) {
        Address address = modelMapper.map(addressDTO, Address.class);
        return addressRepository.save(address);
    }

    public <T> T mapTo(Object src, Class<T> dest) {
        return modelMapper.map(src, dest);
    }
}
