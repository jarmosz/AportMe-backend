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
    private final SearchService searchService;
    private final ModelMapper modelMapper;

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Address mapToEntity(AddressBaseDTO dto) {
        Address address = modelMapper.map(dto, Address.class);
        String searchableCity = searchService.prepareSearchableField(dto.getCity());
        address.setSearchableCity(searchableCity);
        return address;
    }
}
