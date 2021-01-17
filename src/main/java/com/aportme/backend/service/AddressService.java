package com.aportme.backend.service;

import com.aportme.backend.entity.Address;
import com.aportme.backend.entity.dto.address.AddressBaseDTO;
import com.aportme.backend.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Address mapToEntity(AddressBaseDTO dto) {
        return modelMapper.map(dto, Address.class);
    }
}
